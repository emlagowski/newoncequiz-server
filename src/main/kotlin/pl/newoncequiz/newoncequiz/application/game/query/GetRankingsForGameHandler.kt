package pl.newoncequiz.newoncequiz.application.game.query

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.GameResult
import pl.newoncequiz.newoncequiz.domain.game.GameResultRepository
import pl.newoncequiz.newoncequiz.domain.ranking.Ranking
import pl.newoncequiz.newoncequiz.domain.ranking.RankingUser
import pl.newoncequiz.newoncequiz.domain.user.User
import pl.newoncequiz.newoncequiz.domain.user.UserRepository

@Component
class GetRankingsForGameHandler(
    private val gameResultRepository: GameResultRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(gameId: String): Ranking {
        val gameResultOfUSer = gameResultRepository.getByGameId(gameId)
        val top3 = gameResultRepository.findTop3ByCategoryId(gameResultOfUSer.categoryId)
        val behindUser = gameResultRepository.getTopByScoreLessThanEqualOrderByScoreDesc(gameResultOfUSer.score)
        val thisUser = userRepository.getById(gameResultOfUSer.userId)
        val top3Ranking = top3Ranking(top3)
        val currentResult = currentResult(top3Ranking, thisUser, gameResultOfUSer)
        val oneBehind = oneBehindResult(behindUser, gameResultOfUSer, thisUser)
        return Ranking(
            top3Ranking + currentResult + oneBehind
        )
    }

    private fun top3Ranking(top3: List<GameResult>) =
        top3.mapIndexed { index, score ->
            val user = userRepository.getById(score.userId)
            RankingUser(
                name = user.name,
                slug = user.slug,
                place = index + 1,
                score = score.score,
                thisUser = false
            )
        }

    private fun currentResult(
        top3Ranking: List<RankingUser>,
        thisUser: User,
        gameResultOfUSer: GameResult
    ) = if (!top3Ranking.any { it.name == thisUser.name }) {
        emptyList()
    } else {
        listOf(
            RankingUser(
                name = thisUser.name,
                slug = thisUser.slug,
                place = gameResultRepository.countByScoreGreaterThan(gameResultOfUSer.score) + 1,
                score = gameResultOfUSer.score,
                thisUser = true
            )
        )
    }

    private fun oneBehindResult(
        behindUser: GameResult,
        gameResultOfUSer: GameResult,
        thisUser: User
    ) = if (behindUser.userId == gameResultOfUSer.userId) {
        emptyList()
    } else {
        listOf(
            RankingUser(
                name = userRepository.getById(behindUser.userId).name,
                slug = userRepository.getById(behindUser.userId).slug,
                place = gameResultRepository.countByScoreGreaterThan(behindUser.score) + 1,
                score = behindUser.score,
                thisUser = false
            )
        ).filter { it.name != thisUser.name }
    }
}
