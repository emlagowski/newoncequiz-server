package pl.newoncequiz.newoncequiz.application.game.command

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.GameResultRepository
import java.math.BigInteger
import javax.transaction.Transactional

@Component
class SaveGameResultCommandHandler(
    private val gameResultRepository: GameResultRepository
) {
    @Transactional
    operator fun invoke(command: SaveGameResultCommand) {
        val result = gameResultRepository.getById(command.gameId)
        result.score = command.score
        gameResultRepository.save(result)
    }
}

data class SaveGameResultCommand(
    val gameId: String,
    val score: BigInteger
)