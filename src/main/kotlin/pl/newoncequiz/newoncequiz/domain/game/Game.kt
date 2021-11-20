package pl.newoncequiz.newoncequiz.domain.game

import java.util.*

data class Game(
    val id: UUID,
    val questions: List<Question>
)

data class Question(
    val number: Int,
    val article: String,
    val coverUri: String,
    val randomSong: String,
    val answer: String,
    val possibleAnswers: List<String>,
    val resultImageUri: String
) {
    override fun toString(): String {
        return "Question(number=$number, article='$article', coverUri='$coverUri', randomSong='$randomSong', answer='$answer', possibleAnswers=$possibleAnswers, resultImageUri='$resultImageUri')"
    }
}