package swing.thkim.swingsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import swing.thkim.swingsample.api.UnsplashService
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named(UNSPLASH)
    fun provideBaseUrl() = "https://api.unsplash.com/"

    private fun okHttpClientBuilder(): OkHttpClient.Builder {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        return OkHttpClient.Builder().addInterceptor(logger)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return okHttpClientBuilder().build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        @Named(UNSPLASH) baseurl:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUnsplashService(retrofit: Retrofit): UnsplashService {
        return retrofit.create(UnsplashService::class.java)
    }

    companion object {
        private const val UNSPLASH = "UNSPLASH"
    }
}