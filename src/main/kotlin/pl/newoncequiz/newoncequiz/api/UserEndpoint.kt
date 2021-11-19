package pl.newoncequiz.newoncequiz.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserEndpoint {

    @PostMapping
    fun create(@RequestBody createUserRequestDto: CreateUserRequestDto): CreateUserResponseDto {
        throw NotImplementedError()
    }
}


data class CreateUserRequestDto(
    val name: String
)

data class CreateUserResponseDto(
    val name: String,
    val slug: String,
    val id: String
)