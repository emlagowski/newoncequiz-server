package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*
import pl.newoncequiz.newoncequiz.application.quizcategory.query.GetQuizCategoriesHandler
import pl.newoncequiz.newoncequiz.application.quizcategory.query.QuizCategoryWithResult
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryType

@RequestMapping("/api/quiz-categories")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class QuizCategoryEndpoint(
    private val getQuizCategoriesHandler: GetQuizCategoriesHandler
) {

    @GetMapping
    fun get(@RequestParam("userId") userId: String): GetCategoriesResponseDto {
        return getQuizCategoriesHandler(userId).toDto()
    }
}

private fun List<QuizCategoryWithResult>.toDto(): GetCategoriesResponseDto {
    return GetCategoriesResponseDto(
        categories = this.map { it.toDto() }
    )
}

data class GetCategoriesResponseDto(
    val categories: List<CategoryDto>
)

fun QuizCategoryWithResult.toDto(): CategoryDto {
    return CategoryDto(
        id = quizCategory.id,
        type = quizCategory.type,
        typeName = quizCategory.name,
        playedUsersCount = playedCount,
        leftTriesCount = quizCategory.maxTriesCount - result.size,
        maxTriesCount = quizCategory.maxTriesCount
    )
}

data class CategoryDto(
    val id: String,
    val type: QuizCategoryType,
    val typeName: String,
    val playedUsersCount: Long,
    val leftTriesCount: Long,
    val maxTriesCount: Long
)

