package job.interview.borna

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import job.interview.borna.data.ItemsDao
import job.interview.borna.data.ItemsDatabase
import job.interview.borna.data.ItemsRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
@ExperimentalUnsignedTypes
object AppModule {

    @Singleton
    @Provides
    fun provideItemsDatabase(@ApplicationContext context: Context) =
        ItemsDatabase.getDatabase(context)

    @Provides
    fun provideItemsDao(itemsDatabase: ItemsDatabase): ItemsDao {
        return itemsDatabase.itemsDao()
    }

    @Singleton
    @Provides
    fun provideItemsRepository(itemsDao: ItemsDao): ItemsRepository = ItemsRepository(itemsDao)

}