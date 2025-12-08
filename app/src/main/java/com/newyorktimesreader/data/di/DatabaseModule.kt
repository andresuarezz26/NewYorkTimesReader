package com.newyorktimesreader.data.di

import android.content.Context
import androidx.room.Room
import com.newyorktimesreader.data.source.database.AppDatabase
import com.newyorktimesreader.data.source.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Singleton
  @Provides
  fun provideDatabase(
    @ApplicationContext context: Context
  ) = Room
    .databaseBuilder(
      context,
      AppDatabase::class.java,
      AppDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration(true)
    .build()

  @Singleton
  @Provides
  fun provideArticleDao(database: AppDatabase): ArticleDao = database.articleDao()
}