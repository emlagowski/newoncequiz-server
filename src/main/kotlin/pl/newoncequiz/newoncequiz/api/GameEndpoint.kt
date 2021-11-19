package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*

@RequestMapping("/api/games")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class GameEndpoint {

    @PostMapping
    fun createGame(
        @RequestBody createGameRequestDto: CreateGameRequestDto
    ): GetGameResponseDto {
        throw NotImplementedError()
    }
}

data class CreateGameRequestDto(
    val userId: String,
    val categoryId: String
)

data class GetGameResponseDto(
    val game: GameDto
)

data class GameDto(
    val id: String,
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