package pl.newoncequiz.newoncequiz.domain.ranking

import java.math.BigInteger

data class Ranking(
    val ranking: List<RankingUser>
)

data class RankingUser(
    val id: String,
    val name: String,
    val slug: String,
    val place: Int? = null,
    val score: BigInteger,
    val thisUser: Boolean
) {
    override fun toString(): String {
        return "RankingUser(name='$name', slug='$slug', place=$place, score=$score, thisUser=$thisUser)"
    }
}