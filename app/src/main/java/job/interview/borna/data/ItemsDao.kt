package job.interview.borna.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item_table ORDER by id DESC")
    fun getItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item): Long

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(item: Item)

}