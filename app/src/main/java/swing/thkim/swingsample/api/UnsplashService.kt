package swing.thkim.swingsample.api

import retrofit2.http.GET
import retrofit2.http.Query
import swing.thkim.swingsample.BuildConfig
import swing.thkim.swingsample.data.api.UnsplashSearchResponse

interface UnsplashService {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY
    ): UnsplashSearchResponse
}