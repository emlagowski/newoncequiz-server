package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*
import pl.newoncequiz.newoncequiz.application.quizcategory.query.GetQuizCategoriesHandler
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategory
import pl.newoncequiz.newoncequiz.domain.quizcategory.QuizCategoryType

@RequestMapping("/api/quiz-categories")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class QuizCategoryEndpoint(
    private val getQuizCategoriesHandler: GetQuizCategoriesHandler
) {

    @GetMapping
    fun get(@RequestParam("userId") userId: String): GetCategoriesResponseDto {
        return getQuizCategoriesHandler().toDto()
    }
}

private fun List<QuizCategory>.toDto(): GetCategoriesResponseDto {
    return GetCategoriesResponseDto(
        categories = this.map { it.toDto() }
    )
}

data class GetCategoriesResponseDto(
    val categories: List<CategoryDto>
)

fun QuizCategory.toDto(): CategoryDto {
    return CategoryDto(
        id = id,
        type = type,
        typeName = name,
        playedUsersCount = 420,
        leftTriesCount = maxTriesCount,
        maxTriesCount = maxTriesCount
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

