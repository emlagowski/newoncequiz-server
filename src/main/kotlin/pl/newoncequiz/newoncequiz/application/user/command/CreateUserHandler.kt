package pl.newoncequiz.newoncequiz.application.user.command

import org.springframework.stereotype.Component
import pl.newoncequiz.newoncequiz.domain.user.User
import pl.newoncequiz.newoncequiz.domain.user.UserRepository
import javax.transaction.Transactional

@Component
class CreateUserHandler(
    private val userRepository: UserRepository
) {
    @Transactional
    operator fun invoke(createUserCommand: CreateUserCommand): User {
        return try {
            val user = User(
                name = createUserCommand.name,
                slug = "${createUserCommand.name}#${userRepository.countByName(createUserCommand.name)}"
            )
            userRepository.save(user)
            user
        } catch (ex: Exception) {
            invoke(createUserCommand)
        }
    }
}

data class CreateUserCommand(
    val name: String
)