package swing.thkim.swingsample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import swing.thkim.swingsample.data.AppDatabase
import swing.thkim.swingsample.data.dao.FeedDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideFeedDao(appDatabase: AppDatabase): FeedDao {
        return appDatabase.feedDao()
    }
}