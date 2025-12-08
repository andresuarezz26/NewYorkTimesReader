package com.newyorktimesreader.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newyorktimesreader.data.source.database.dao.ArticleDao
import com.newyorktimesreader.data.source.database.entity.ArticleEntity

@Database(
  entities = [ArticleEntity::class],
  version = 1
)
abstract class AppDatabase : RoomDatabase() {

  companion object {
    const val DATABASE_NAME = "NewYorkTimesDatabase"
  }

  abstract fun articleDao(): ArticleDao
}