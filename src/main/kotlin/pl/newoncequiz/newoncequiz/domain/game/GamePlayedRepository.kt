package pl.newoncequiz.newoncequiz.domain.game

import org.springframework.data.jpa.repository.JpaRepository

interface GamePlayedRepository : JpaRepository<GamePlayed, String> {
    fun countDistinctByCategoryId(id: String): Long
    fun countByUserIdAndCategoryId(userId: String, categoryId: String) : Long
}