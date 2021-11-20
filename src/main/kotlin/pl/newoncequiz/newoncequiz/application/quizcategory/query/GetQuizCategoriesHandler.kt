package pl.newoncequiz.newoncequiz.application.quizcategory.query

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.GameResult
import pl.newoncequiz.newoncequiz.domain.game.GameResultRepository
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategory
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository

@Component
class GetQuizCategoriesHandler(
    private val quizCategoryRepository: QuizCategoryRepository,
    private val gameResultRepository: GameResultRepository
) {

    operator fun invoke(userId: String): List<QuizCategoryWithResult> {
        val categories = quizCategoryRepository.findAll()
        return categories.map {
            QuizCategoryWithResult(
                quizCategory = it,
                result = gameResultRepository.getByUserIdAndCategoryId(
                    userId, it.id
                )
            )
        }
    }
}

data class QuizCategoryWithResult(
    val quizCategory: QuizCategory,
    val result: List<GameResult>
)