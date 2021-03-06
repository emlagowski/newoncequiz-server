package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.*
import pl.newoncequiz.newoncequiz.application.user.command.CreateUserCommand
import pl.newoncequiz.newoncequiz.application.user.command.CreateUserHandler
import pl.newoncequiz.newoncequiz.domain.user.User

@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class UserEndpoint(
    private val createUserHandler: CreateUserHandler
) {

    @PostMapping
    fun create(@RequestBody createUserRequestDto: CreateUserRequestDto): CreateUserResponseDto {
        println("/api/users createUserRequestDto=" + createUserRequestDto)
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