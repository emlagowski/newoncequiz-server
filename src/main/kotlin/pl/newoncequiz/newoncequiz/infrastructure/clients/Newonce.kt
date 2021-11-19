package pl.newoncequiz.newoncequiz.infrastructure.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Component
@FeignClient(name = "newonce", url = "http://newonce-api.herokuapp.com")
interface Newonce {

    @GetMapping("/artists/{slug}")
    fun getArtists(@PathVariable("slug") slug: String): NewonceArtist



}

data class NewonceArtist(
    val image: NewonceImage
)

data class NewonceImage(
    val url: String
)