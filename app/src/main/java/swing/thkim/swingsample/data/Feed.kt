package swing.thkim.swingsample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeds")
data class Feed(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    val imageUrl: String = "",
    val favorite: Boolean = false
)
