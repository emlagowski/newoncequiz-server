package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*
import pl.newoncequiz.newoncequiz.application.game.command.CreateGameCommand
import pl.newoncequiz.newoncequiz.application.game.command.CreateGameCommandHandler
import pl.newoncequiz.newoncequiz.domain.game.Game
import pl.newoncequiz.newoncequiz.domain.game.Question

@RequestMapping("/api/games")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class GameEndpoint(
    private val createGameCommandHandler: CreateGameCommandHandler
) {

    @PostMapping
    fun createGame(
        @RequestBody createGameRequestDto: CreateGameRequestDto
    ): GetGameResponseDto {
       return createGameCommandHandler(
            CreateGameCommand(
                userId = createGameRequestDto.userId,
                categoryId = createGameRequestDto.categoryId
            )
        ).toDto()
    }
}

private fun Game.toDto(): GetGameResponseDto {
    return GetGameResponseDto(
        GameDto(
            id = id.toString(),
            questions = questions.map {
                it.toDto()
            }
        )
    )
}

private fun Question.toDto(): QuestionDto {
    return QuestionDto(
        number = number,
        article = article,
        coverUri = coverUri,
        randomSong = randomSong,
        answer = answer,
        possibleAnswers = possibleAnswers,
        resultImageUri = resultImageUri
    )
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