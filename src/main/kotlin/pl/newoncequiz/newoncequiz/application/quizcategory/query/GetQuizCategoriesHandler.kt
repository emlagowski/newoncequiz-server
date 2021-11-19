package pl.newoncequiz.newoncequiz.application.quizcategory.query

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategory
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository

@Component
class GetQuizCategoriesHandler(
    private val quizCategoryRepository: QuizCategoryRepository
) {

    operator fun invoke(): List<QuizCategory> {
        return quizCategoryRepository.findAll()
    }
}