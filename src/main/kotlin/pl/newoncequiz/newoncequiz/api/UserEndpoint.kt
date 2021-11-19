package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.newoncequiz.newoncequiz.application.user.command.CreateUserCommand
import pl.newoncequiz.newoncequiz.application.user.command.CreateUserHandler
import pl.newoncequiz.newoncequiz.domain.user.User

@RequestMapping("/api/users")
@RestController
class UserEndpoint(
    private val createUserHandler: CreateUserHandler
) {

    @PostMapping
    fun create(@RequestBody createUserRequestDto: CreateUserRequestDto): CreateUserResponseDto {
        val user = createUserHandler(createUserRequestDto.toDto())
        return user.toDto()
    }
}

private fun User.toDto(): CreateUserResponseDto {
    return CreateUserResponseDto(
        name = name,
        slug = slug,
        id = id.toString()
    )
}


data class CreateUserRequestDto(
    val name: String
) {
    fun toDto(): CreateUserCommand {
        return CreateUserCommand(
            name = name
        )
    }
}

data class CreateUserResponseDto(
    val name: String,
    val slug: String,
    val id: String
)