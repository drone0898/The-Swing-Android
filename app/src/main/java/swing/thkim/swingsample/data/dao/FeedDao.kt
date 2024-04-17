package swing.thkim.swingsample.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import swing.thkim.swingsample.data.Feed

@Dao
interface FeedDao {
    @Query("SELECT * FROM feeds WHERE favorite == 1")
    fun getFavoriteFeeds(): Flow<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(feeds: List<Feed>)

    @Query("UPDATE feeds SET favorite = :isFavorite WHERE id = :feedId")
    suspend fun updateFavorite(feedId: String, isFavorite: Boolean)

    @Query("DELETE FROM feeds")
    suspend fun clearAllFeeds()

    @Query("SELECT * FROM feeds")
    fun pagingSource(): PagingSource<Int, Feed>
}