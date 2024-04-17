package swing.thkim.swingsample.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import swing.thkim.swingsample.data.Feed

interface FeedRepository {

    suspend fun clearFeeds()

    suspend fun getSearchResultStream(searchQuery: String): Flow<PagingData<Feed>>

    suspend fun updateFavoriteStatus(feedId: String, isFavorite: Boolean)

    fun getFavoriteFeeds(): Flow<List<Feed>>

}