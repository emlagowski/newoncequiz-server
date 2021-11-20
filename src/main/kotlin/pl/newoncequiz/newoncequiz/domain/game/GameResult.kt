package pl.newoncequiz.newoncequiz.domain.game

import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class GameResult(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    var score: BigInteger = BigInteger.ZERO,
    val categoryId: String,
    var gameId: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    override fun toString(): String {
        return "GameResult(id='$id', userId='$userId', score=$score, categoryId='$categoryId', gameId='$gameId', createdAt=$createdAt)"
    }
}