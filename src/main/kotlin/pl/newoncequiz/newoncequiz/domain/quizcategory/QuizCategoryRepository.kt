package pl.newoncequiz.newoncequiz.domain.quizcategory

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface QuizCategoryRepository : JpaRepository<QuizCategory, UUID> {

}