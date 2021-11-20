package pl.newoncequiz.newoncequiz.infrastructure.clients

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Component
class Newonce(
    private val newonceClient: NewonceClient
) {

    @Cacheable("artists")
    fun getArtists(@PathVariable("slug") slug: String): NewonceArtist {
        return newonceClient.getArtists(slug)
    }

    @Cacheable("releases")
    fun getReleases(@PathVariable("slug") slug: String): NewonceReleases {
        return newonceClient.getReleases(slug)
    }

    @Cacheable("releases-details")
    fun getReleaseDetails(@PathVariable("slug") slug: String): NewonceDetailedRelease {
        return newonceClient.getReleaseDetails(slug)
    }

    @Cacheable("articles")
    fun getArticles(@RequestParam("search_query") searchQuery: String): NewonceArticles {
        return newonceClient.getArticles(searchQuery)
    }

}