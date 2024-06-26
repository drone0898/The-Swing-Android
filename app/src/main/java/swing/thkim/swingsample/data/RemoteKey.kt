package swing.thkim.swingsample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?,
    val lastUpdated: Long
)
