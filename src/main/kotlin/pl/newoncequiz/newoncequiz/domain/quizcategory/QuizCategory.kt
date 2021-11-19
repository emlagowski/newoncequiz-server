package pl.newoncequiz.newoncequiz.domain.quizcategory

import java.util.*
import javax.persistence.*

@Entity
data class QuizCategory(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val type: QuizCategoryType,
    val name: String,
    val maxTriesCount: Long
)

enum class QuizCategoryType {
    POLISH_RAP, INTERNATIONAL_RAP, FOOTBALL, LIFESTYLE
}