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
        println("GetRankingsForGameHandler.invoke count=" + userRepository.count())
        val top3 = gameResultRepository.findTop3ByCategoryIdOrderByScoreDesc(gameResultOfUSer.categoryId)
        println("GetRankingsForGameHandler.invoke top3=" + top3)
        val behindUser = gameResultRepository.getTopByScoreLessThanAndCategoryIdOrderByScoreDesc(
            gameResultOfUSer.score,
            gameResultOfUSer.categoryId
        )
        println("GetRankingsForGameHandler.invoke behindUser=" + behindUser)
        val thisUser = userRepository.getById(gameResultOfUSer.userId) //todo tutaj dodac kategorie
        println("GetRankingsForGameHandler.invoke thisUser=" + thisUser)
        val top3Ranking = top3Ranking(top3, thisUser)
        println("GetRankingsForGameHandler.invoke top3Ranking=" + thisUser)
        val currentResult = currentResult(top3Ranking, thisUser, gameResultOfUSer)
        println("GetRankingsForGameHandler.invoke currentResult=" + currentResult)
        val oneBehind = oneBehindResult(behindUser, gameResultOfUSer, thisUser, top3Ranking)
        println("GetRankingsForGameHandler.invoke oneBehind=" + oneBehind)
        var ranking = Ranking(
            top3Ranking + currentResult + oneBehind
        )
        println("GetRankingsForGameHandler.invoke ranking=" + ranking)
        return ranking
    }

    private fun top3Ranking(top3: List<GameResult>, thisUser: User) =
        top3.mapIndexed { index, score ->
            val user = userRepository.getById(score.userId)
            RankingUser(
                id = user.id,
                name = user.name,
                slug = user.slug,
                place = index + 1,
                score = score.score,
                thisUser = user.slug == thisUser.slug
            )
        }

    private fun currentResult(
        top3Ranking: List<RankingUser>,
        thisUser: User,
        gameResultOfUSer: GameResult
    ) = if (top3Ranking.any { it.slug == thisUser.slug } || thisUser.id == gameResultOfUSer.userId) {
        emptyList()
    } else {
        listOf(
            RankingUser(
                id = thisUser.id,
                name = thisUser.name,
                slug = thisUser.slug,
                place = gameResultRepository.countByScoreGreaterThan(gameResultOfUSer.score) + 1, // todo chyba to +1
                score = gameResultOfUSer.score,
                thisUser = true
            )
        )
    }

    private fun oneBehindResult(
        behindUser: GameResult?,
        gameResultOfUSer: GameResult,
        thisUser: User,
        top3Ranking: List<RankingUser>
    ) = if (behindUser == null
        || behindUser.userId == gameResultOfUSer.userId
        || top3Ranking.any { it.id == behindUser.userId }) {
        emptyList()
    } else {
        val behindUserData = userRepository.getById(behindUser.userId)
        listOf(
            RankingUser(
                id = behindUserData.id,
                name = behindUserData.name,
                slug = behindUserData.slug,
                place = gameResultRepository.countByScoreGreaterThan(behindUser.score) + 1,
                score = behindUser.score,
                thisUser = behindUserData.slug == thisUser.slug
            )
        ).filter { it.name != thisUser.name }
    }
}
