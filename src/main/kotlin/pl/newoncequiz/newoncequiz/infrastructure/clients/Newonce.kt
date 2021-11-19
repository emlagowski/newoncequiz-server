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

    @GetMapping("/artists/{slug}/releases")
    fun getReleases(@PathVariable("slug") slug: String): NewonceReleases
}

data class NewonceArtist(
    val image: NewonceImage
)

data class NewonceReleases(
    val popular: List<NewonceRelease>
)

data class NewonceRelease(
    val image: NewonceImage
)

data class NewonceImage(
    val url: String
)