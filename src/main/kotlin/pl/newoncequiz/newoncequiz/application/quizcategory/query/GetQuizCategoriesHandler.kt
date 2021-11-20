package pl.newoncequiz.newoncequiz.application.quizcategory.query

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.GamePlayed
import pl.newoncequiz.newoncequiz.domain.game.GamePlayedRepository
import pl.newoncequiz.newoncequiz.domain.game.GameResult
import pl.newoncequiz.newoncequiz.domain.game.GameResultRepository
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategory
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository

@Component
class GetQuizCategoriesHandler(
    private val quizCategoryRepository: QuizCategoryRepository,
    private val gamePlayedRepository: GamePlayedRepository
) {

    operator fun invoke(userId: String): List<QuizCategoryWithResult> {
        val categories = quizCategoryRepository.findAll()
        return categories.map {
            QuizCategoryWithResult(
                quizCategory = it,
                playedUserCount = gamePlayedRepository.countByUserIdAndCategoryId(
                    userId, it.id
                ),
                playedCount = gamePlayedRepository.countDistinctByCategoryId(it.id)
            )
        }
    }
}

data class QuizCategoryWithResult(
    val quizCategory: QuizCategory,
    val playedUserCount: Long,
    val playedCount: Long
)