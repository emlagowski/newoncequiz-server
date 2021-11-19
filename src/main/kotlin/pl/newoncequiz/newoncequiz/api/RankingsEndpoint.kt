package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*

@RequestMapping("/api/rankings")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class RankingsEndpoint {

    @GetMapping
    fun get(@RequestParam("categoryId") categoryId: String) : GetRankingsResponseDto{
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