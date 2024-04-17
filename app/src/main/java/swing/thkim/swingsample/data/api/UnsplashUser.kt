package swing.thkim.swingsample.data.api

import com.google.gson.annotations.SerializedName

data class UnsplashUser(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("username") val username: String
)