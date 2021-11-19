package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/rankings")
@RestController
class RankingsEndpoint {

    @GetMapping
    fun get(@RequestParam("categoryId") categoryId: String) {
        throw NotImplementedError()
    }
}

data class GetRankingsResponseDto(
    val rankings: List<RankingDto>
)

enum class RankingType {
    DAILY, WEEKLY, MONTHLY
}

data class RankingDto(
    val type: RankingType,
    val users: List<RankedUserDto>
)

data class RankedUserDto(
    val place: Int,
    val name: String,
    val slug: String,
    val id: String
)