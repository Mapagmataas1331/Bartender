package cyou.ma.bartender.di

import android.content.Context
import androidx.room.Room
import cyou.ma.bartender.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext app: Context) =
    Room.databaseBuilder(
      app,
      AppDatabase::class.java,
      "tail_db"
    ).fallbackToDestructiveMigration().build()


  @Singleton
  @Provides
  fun provideFavoriteDrinkDao(database: AppDatabase) = database.getFavoriteDrinksDao()

  @Singleton
  @Provides
  fun provideLocalIngredientsDao(database: AppDatabase) = database.getLocalIngredientsDao()

  @Singleton
  @Provides
  fun provideUserIngredientsDao(database: AppDatabase) = database.getUserIngredientsDao()
}