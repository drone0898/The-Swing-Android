package swing.thkim.swingsample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import swing.thkim.swingsample.data.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE id=:key")
    suspend fun getKeyByFeed(key: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun clearKeys()
}

