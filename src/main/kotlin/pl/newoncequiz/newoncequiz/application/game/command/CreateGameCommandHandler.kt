package pl.newoncequiz.newoncequiz.application.game.command

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.*
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryType
import pl.newoncequiz.newoncequiz.infrastructure.clients.Newonce
import pl.newoncequiz.newoncequiz.infrastructure.clients.NewonceClient
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
            questions = (1..5).map { generateQuestion(it, categoryId = createGameCommand.categoryId) }
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
            article = chosenArticle.title.replace(chosenArtist.name, "■■■■■■■■"),
            coverUri = chosenAlbum.image.url,
            randomSong = chosenTrack.title,
            answer = chosenArtist.name,
            possibleAnswers = (chosenAnswersArtists + chosenArtist).map { it.name },
            resultImageUri = chosenArtisNewonce.image.url
        )
    }

    private fun getArtists(categoryId: String): List<Artist> {
        val category = quizCategoryRepository.getById(categoryId)
        val polishRap = listOf(
            Artist("Kizo", "kizo-2778214"),
            Artist("Taco Hemingway", "taco-hemingway-4320863"),
            Artist("PRO8L3M", "pro8l3m-3529948"),
            Artist("Żabson", "zabson-3803974"),
            Artist("Kaz Bałagane", "kaz-balagane-6874375"),
            Artist("Undadasea", "undadasea-7104233"),
            Artist("Syny", "syny-4295254"),
            Artist("JWP", "jwp-235854"),
            Artist("Bedoes", "bedoes-5103702"),
            Artist("Malik Montana", "malik-montana-4990210"),
            Artist("Tede", "tede-182846")
        )
        val artists = when (category.type) {
            QuizCategoryType.POLISH_RAP -> polishRap
            QuizCategoryType.INTERNATIONAL_RAP -> polishRap
            QuizCategoryType.FOOTBALL -> polishRap
            QuizCategoryType.LIFESTYLE -> polishRap
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