package pl.newoncequiz.newoncequiz.domain.user

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER")
class User(
    @Column(name = "ID")
    @Id
    val id: UUID = UUID.randomUUID(),
    @Column(name = "NAME")
    val name: String,
    @Column(name = "SLUG", unique = true)
    val slug: String,
)