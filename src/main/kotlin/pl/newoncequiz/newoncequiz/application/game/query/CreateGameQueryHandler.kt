package pl.newoncequiz.newoncequiz.application.game.query

import pl.newoncequiz.newoncequiz.domain.game.Game

class CreateGameQueryHandler {
    operator fun invoke(createGameCommand: CreateGameCommand): Game {
        throw NotImplementedError()
    }
}

data class CreateGameCommand(
    val userId: String,
    val categoryId: String
)