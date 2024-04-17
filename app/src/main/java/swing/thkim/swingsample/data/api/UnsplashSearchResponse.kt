package swing.thkim.swingsample.data.api

import com.google.gson.annotations.SerializedName
import swing.thkim.swingsample.data.Feed

data class UnsplashSearchResponse(
    @field:SerializedName("results") val results: List<UnsplashPhoto>,
    @field:SerializedName("total_pages") val totalPages: Int
)

fun List<UnsplashPhoto>.asFeedList(): List<Feed> = this.map {
    Feed(id = it.id, imageUrl = it.urls.small)
}