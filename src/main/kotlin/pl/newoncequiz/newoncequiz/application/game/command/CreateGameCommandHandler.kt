package pl.newoncequiz.newoncequiz.application.game.command

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.Game
import pl.newoncequiz.newoncequiz.domain.game.Question
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryType
import pl.newoncequiz.newoncequiz.infrastructure.clients.Newonce
import java.util.*

@Component
class CreateGameCommandHandler(
    private val quizCategoryRepository: QuizCategoryRepository,
    private val newonce: Newonce
) {
    operator fun invoke(createGameCommand: CreateGameCommand): Game {
        return Game(
            id = UUID.randomUUID(),
            questions = (1..10).map { generateQuestion(it, categoryId = createGameCommand.categoryId) }
        )
    }

    private fun generateQuestion(number: Int, categoryId: String): Question {
        val artists = getArtists(categoryId)
        val chosenArtist = artists[Random().nextInt(artists.size)]
        val chosenArtisNewonce = newonce.getArtists(chosenArtist.slug)
        val chosenArtistReleasesNewonce = newonce.getReleases(chosenArtist.slug)
        val chosenAlbum =
            chosenArtistReleasesNewonce.popular[Random().nextInt(chosenArtistReleasesNewonce.popular.size)]
        val artistsForAnswers = artists - chosenArtist
        val chosenAnswersArtists = artistsForAnswers.shuffled().subList(0, 3)
        return Question(
            number = number,
            article = fetchArticle(),
            coverUri = chosenAlbum.image.url,
            randomSong = "Dzień Dobry",
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

    private fun fetchArticle(): String {
        return "Dzień dobry, młody G z Gdyni"
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