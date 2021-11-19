package pl.newoncequiz.newoncequiz.infrastructure.initializers

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategory
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryRepository
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryType
import javax.annotation.PostConstruct

@Component
class QuizCategoryInitializer(
    private val quizCategoryRepository: QuizCategoryRepository
) {

    @PostConstruct
    fun init() {
        quizCategoryRepository.save(
            QuizCategory(
                type = QuizCategoryType.FOOTBALL,
                name = "Piłka nożna",
                maxTriesCount = 3
            )
        )
        quizCategoryRepository.save(
            QuizCategory(
                type = QuizCategoryType.INTERNATIONAL_RAP,
                name = "Światowy rap",
                maxTriesCount = 2
            )
        )
        quizCategoryRepository.save(
            QuizCategory(
                type = QuizCategoryType.POLISH_RAP,
                name = "Polski rap",
                maxTriesCount = 5
            )
        )
        quizCategoryRepository.save(
            (QuizCategory(
                type = QuizCategoryType.LIFESTYLE,
                name = "Lifestyle",
                maxTriesCount = 3
            ))
        )
    }
}