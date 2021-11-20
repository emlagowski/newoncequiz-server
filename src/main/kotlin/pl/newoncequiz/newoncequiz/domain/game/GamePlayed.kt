package pl.newoncequiz.newoncequiz.domain.game

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class GamePlayed(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val categoryId: String
)
