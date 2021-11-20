package pl.newoncequiz.newoncequiz.domain.game

import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigInteger

interface GameResultRepository : JpaRepository<GameResult, String> {
    fun getByUserIdAndCategoryId(userId: String, categoryId: String): List<GameResult>
    fun countDistinctByCategoryId(categoryId: String): Long
    fun findTop3ByCategoryId(categoryId: String): List<GameResult>
    fun getTopByScoreLessThanEqualOrderByScoreDesc(score: BigInteger): GameResult
    fun countByScoreGreaterThan(score: BigInteger): Int
}