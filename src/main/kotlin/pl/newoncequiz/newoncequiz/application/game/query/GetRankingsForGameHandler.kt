package pl.newoncequiz.newoncequiz.application.game.query

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.game.GameResultRepository
import pl.newoncequiz.newoncequiz.domain.ranking.Ranking
import pl.newoncequiz.newoncequiz.domain.ranking.RankingUser
import pl.newoncequiz.newoncequiz.domain.user.UserRepository

@Component
class GetRankingsForGameHandler(
    private val gameResultRepository: GameResultRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(gameId: String): Ranking {
        val gameResultOfUSer = gameResultRepository.getById(gameId)
        val top3 = gameResultRepository.findTop3ByCategoryId(gameResultOfUSer.categoryId)
        val behindUser = gameResultRepository.getTopByScoreLessThanEqualOrderByScoreDesc(gameResultOfUSer.score)
        return Ranking(top3.mapIndexed { index, score ->
            val user = userRepository.getById(score.userId)
            RankingUser(
                name = user.name,
                slug = user.slug,
                place = index + 1,
                score = score.score
            )
        } +
                listOf(
                    RankingUser(
                        name = userRepository.getById(gameResultOfUSer.userId).name,
                        slug = userRepository.getById(gameResultOfUSer.userId).slug,
                        place = gameResultRepository.countByScoreGreaterThan(gameResultOfUSer.score) + 1,
                        score = gameResultOfUSer.score
                    )
                ) +
                listOf(
                    RankingUser(
                        name = userRepository.getById(behindUser.userId).name,
                        slug = userRepository.getById(behindUser.userId).slug,
                        place = gameResultRepository.countByScoreGreaterThan(behindUser.score) + 1,
                        score = behindUser.score
                    )
                ))
    }
}
