package pl.newoncequiz.newoncequiz.application.game.command

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.*
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryType
import pl.newoncequiz.newoncequiz.infrastructure.clients.Newonce
import java.util.*
import javax.transaction.Transactional

@Component
class CreateGameCommandHandler(
    private val quizCategoryRepository: QuizCategoryRepository,
    private val gameResultRepository: GameResultRepository,
    private val gamePlayedRepository: GamePlayedRepository,
    private val newonce: Newonce
) {

    @Transactional
    operator fun invoke(createGameCommand: CreateGameCommand): Game {
        val game = Game(
            id = UUID.randomUUID(),
            questions = (1..2).map { generateQuestion(it, categoryId = createGameCommand.categoryId) }
        )
        val optionalGameResult =
            gameResultRepository.findByUserIdAndCategoryId(createGameCommand.userId, createGameCommand.categoryId)
        val finalGameResult = if (optionalGameResult != null) {
            optionalGameResult.gameId = game.id.toString()
            optionalGameResult
        } else {
            GameResult(
                id = game.id.toString(),
                userId = createGameCommand.userId,
                categoryId = createGameCommand.categoryId,
                gameId = game.id.toString()
            )
        }
        gameResultRepository.save(finalGameResult)
        gamePlayedRepository.save(
            GamePlayed(
                userId = createGameCommand.userId,
                categoryId = createGameCommand.categoryId
            )
        )
        return game
    }

    private fun generateQuestion(number: Int, categoryId: String): Question {
        val artists = getArtists(categoryId)
        val chosenArtist = artists.random()
        val chosenArtisNewonce = newonce.getArtists(chosenArtist.slug)
        val chosenArtistReleasesNewonce = newonce.getReleases(chosenArtist.slug)
        val chosenAlbum =
            chosenArtistReleasesNewonce.popular.random()
        val chosenTrack = newonce.getReleaseDetails(chosenAlbum.slug).tracklist.random()
        val artistsForAnswers = artists - chosenArtist
        val chosenArticle =
            newonce.getArticles(chosenArtist.name).items.filter { it.title.contains(chosenArtist.name) }.random()
        val chosenAnswersArtists = artistsForAnswers.shuffled().subList(0, 3)
        return Question(
            number = number,
            article = chosenArticle.title.replace(chosenArtist.name, "<<CENSORED XD>>"),
            coverUri = chosenAlbum.image.url,
            randomSong = chosenTrack.title,
            answer = chosenArtist.name,
            possibleAnswers = (chosenAnswersArtists + chosenArtist).map { it.name },
            resultImageUri = chosenArtisNewonce.image.url
        )
    }

    private fun getArtists(categoryId: String): List<Artist> {
        val category = quizCategoryRepository.getById(categoryId)
        val artists = when (category.type) {
            QuizCategoryType.POLISH_RAP -> listOf(
                Artist("Kizo", "kizo-2778214"),
                Artist("Taco Hemingway", "taco-hemingway-4320863"),
                Artist("PRO8L3M", "pro8l3m-3529948"),
                Artist("Żabson", "zabson-3803974")
            )
            QuizCategoryType.INTERNATIONAL_RAP -> TODO()
            QuizCategoryType.FOOTBALL -> TODO()
            QuizCategoryType.LIFESTYLE -> TODO()
        }
        return artists
    }
}

data class Artist(
    val name: String,
    val slug: String
)

data class CreateGameCommand(
    val userId: String,
    val categoryId: String
)