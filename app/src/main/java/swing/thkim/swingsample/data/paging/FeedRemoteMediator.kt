package swing.thkim.swingsample.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import swing.thkim.swingsample.api.UnsplashService
import swing.thkim.swingsample.data.AppDatabase
import swing.thkim.swingsample.data.Feed
import swing.thkim.swingsample.data.RemoteKey
import swing.thkim.swingsample.data.api.asFeedList
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator(
    private val query: String,
    private val service: UnsplashService,
    private val database: AppDatabase
) : RemoteMediator<Int, Feed>() {

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            database.remoteKeyDao().getKeyByFeed("remote_keys")
        }
        val cacheTimeout = TimeUnit.HOURS.toMillis(1)

        return if (remoteKey != null && (System.currentTimeMillis() - remoteKey.lastUpdated) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Feed>): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> UNSPLASH_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = database.withTransaction {
                    database.remoteKeyDao().getKeyByFeed("remote_keys")
                } ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKey.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        try {
            val response = service.searchPhotos(query, nextPage, state.config.pageSize)
            val endOfPaginationReached = response.totalPages == nextPage

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().clearKeys()
                    database.feedDao().clearAllFeeds()
                }
                database.remoteKeyDao().insertKey(RemoteKey("remote_keys", nextPage + 1, System.currentTimeMillis()))
                database.feedDao().insertAll(response.results.asFeedList())
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        private const val UNSPLASH_STARTING_PAGE_INDEX = 1
    }
}