package pl.newoncequiz.newoncequiz.domain.ranking

import java.math.BigInteger

data class Ranking(
    val ranking: List<RankingUser>
)

data class RankingUser(
    val name: String,
    val slug: String,
    val place: Int,
    val score: BigInteger,
    val thisUser: Boolean
)