package job.interview.borna.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val itemsDao: ItemsDao
) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allItems: Flow<List<Item>> = itemsDao.getItems()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Item) {
        itemsDao.insert(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(item: Item) {
        itemsDao.delete(item)
    }
}