package swing.thkim.swingsample.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import swing.thkim.swingsample.api.UnsplashService
import swing.thkim.swingsample.data.AppDatabase
import swing.thkim.swingsample.data.Feed
import swing.thkim.swingsample.data.paging.FeedRemoteMediator
import javax.inject.Inject

class DefaultFeedRepository @Inject constructor(
    private val service: UnsplashService,
    private val appDatabase: AppDatabase
): FeedRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getSearchResultStream(searchQuery: String): Flow<PagingData<Feed>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = true, pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = FeedRemoteMediator(searchQuery, service, appDatabase),
            pagingSourceFactory = { appDatabase.feedDao().pagingSource() }
        ).flow
    }

    override fun getFavoriteFeeds(): Flow<List<Feed>> {
        return appDatabase.feedDao().getFavoriteFeeds()
    }

    override suspend fun updateFavoriteStatus(feedId: String, isFavorite: Boolean) {
        appDatabase.feedDao().updateFavorite(feedId, isFavorite)
    }

    override suspend fun clearFeeds() {
        appDatabase.withTransaction {
            appDatabase.remoteKeyDao().clearKeys()
            appDatabase.feedDao().clearAllFeeds()
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
}