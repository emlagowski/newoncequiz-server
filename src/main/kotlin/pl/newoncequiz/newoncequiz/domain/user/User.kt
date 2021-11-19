package pl.newoncequiz.newoncequiz.domain.user

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val slug: String,
)