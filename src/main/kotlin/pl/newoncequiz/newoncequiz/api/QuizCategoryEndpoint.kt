package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/quiz-categories")
@RestController
class QuizCategoryEndpoint {

    @GetMapping
    fun get(@RequestParam("userId") userId: String): GetCategoriesResponseDto {
        throw NotImplementedError()
    }
}

data class GetCategoriesResponseDto(
    val id: String,
    val type: QuizCategoryType,
    val typeName: String,
    val playedUsersCount: Long,
    val leftTriesCount: Long
)

enum class QuizCategoryType {
    POLISH_RAP, INTERNATIONAL_RAP, FOOTBALL, LIFESTYLE
}