package pl.newoncequiz.newoncequiz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class NewonceQuizApplication

fun main(args: Array<String>) {
    runApplication<NewonceQuizApplication>(*args)
}
