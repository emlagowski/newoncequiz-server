package pl.newoncequiz.newoncequiz.domain.game

import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigInteger

interface GameResultRepository : JpaRepository<GameResult, BigInteger> {
    fun getByUserIdAndCategoryId(userId: String, categoryId: String): List<GameResult>
    fun countDistinctByCategoryId(categoryId: String): Long
}