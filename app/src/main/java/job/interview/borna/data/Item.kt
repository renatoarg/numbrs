package job.interview.borna.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "item_table")
class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: Long = 0,
    var name: String = "",
    var value: Double = 0.0,
    var type: Int = -1
) : Parcelable