package swing.thkim.swingsample.data.api

import com.google.gson.annotations.SerializedName

data class UnsplashPhotoUrls(
    @field:SerializedName("small") val small: String
)