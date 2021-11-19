package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*

@RequestMapping("/api/games")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class GameEndpoint {

    @GetMapping
    fun get(
        @RequestParam("userId") userId: String,
        @RequestParam("categoryId") categoryId: String
    ): GetGameResponseDto {
        throw NotImplementedError()
    }
}

data class GetGameResponseDto(
    val game: GameDto
)

data class GameDto(
    val questions: List<QuestionDto>
)

data class QuestionDto(
    val number: Int,
    val article: String,
    val coverUri: String,
    val randomSong: String,
    val answer: String,
    val possibleAnswers: List<String>,
    val resultImageUri: String
)