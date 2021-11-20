package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*
import pl.newoncequiz.newoncequiz.application.game.query.GetRankingsForGameHandler
import pl.newoncequiz.newoncequiz.domain.ranking.Ranking
import pl.newoncequiz.newoncequiz.domain.ranking.RankingUser
import java.math.BigInteger
import javax.websocket.server.PathParam

@RequestMapping("/api/rankings")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class RankingsEndpoint(
    private val getRankingsForGameHandler: GetRankingsForGameHandler
) {

    @GetMapping
    fun get(@RequestParam("categoryId") categoryId: String): GetRankingsResponseDto {
        throw NotImplementedError()
    }

    @GetMapping("/game/{gameId}")
    fun getForGame(@PathVariable("gameId") gameId: String): GetRankingsResponseDto {
        return getRankingsForGameHandler(gameId).toDto()
    }
}

private fun Ranking.toDto(): GetRankingsResponseDto {
    return GetRankingsResponseDto(
        rankings = ranking.map { it.toDto() }.sortedBy { it.place }.toSet()
    )
}

private fun RankingUser.toDto(): RankedUserDto {
    return RankedUserDto(
        place = place,
        score = score,
        name = name,
        slug = slug,
        thisUser = thisUser
    )
}

data class GetRankingsResponseDto(
    val rankings: Set<RankedUserDto>
)

data class RankedUserDto(
    val thisUser: Boolean,
    val place: Int,
    val score: BigInteger,
    val name: String,
    val slug: String,
)