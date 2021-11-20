package pl.newoncequiz.newoncequiz.infrastructure.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Component
@FeignClient(name = "newonce", url = "http://newonce-api.herokuapp.com")
interface Newonce {

    @GetMapping("/artists/{slug}")
    fun getArtists(@PathVariable("slug") slug: String): NewonceArtist

    @GetMapping("/artists/{slug}/releases")
    fun getReleases(@PathVariable("slug") slug: String): NewonceReleases

    @GetMapping("/releases/{slug}")
    fun getReleaseDetails(@PathVariable("slug") slug: String): NewonceDetailedRelease

    @GetMapping("/related/articles")
    fun getArticles(@RequestParam("search_query") searchQuery: String): NewonceArticles
}

data class NewonceArtist(
    val image: NewonceImage
)

data class NewonceReleases(
    val popular: List<NewonceRelease>
)

data class NewonceDetailedRelease(
    val tracklist: List<Track>
)

data class Track(
    val title: String
)

data class NewonceRelease(
    val image: NewonceImage,
    val slug: String
)

data class NewonceArticles(
    val items: List<NewonceArticle>
)

data class NewonceArticle(
    val title: String
)

data class NewonceImage(
    val url: String
)