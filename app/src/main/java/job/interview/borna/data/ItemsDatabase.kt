package job.interview.borna.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
public abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    "items_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}