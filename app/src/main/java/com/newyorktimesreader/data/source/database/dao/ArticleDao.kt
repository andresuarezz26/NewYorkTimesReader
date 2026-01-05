package com.newyorktimesreader.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newyorktimesreader.data.source.database.entity.ARTICLE_TABLE
import com.newyorktimesreader.data.source.database.entity.ArticleEntity

/**
 * CRUD for articles entities
 */
@Dao
interface ArticleDao {

  @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
  suspend fun insertAll(vararg entity: ArticleEntity)

  @Query("DELETE FROM $ARTICLE_TABLE")
  suspend fun clearAll()

  @Query("SELECT * FROM $ARTICLE_TABLE")
  suspend fun getAllArticles(): List<ArticleEntity>

  @Query("SELECT * FROM $ARTICLE_TABLE WHERE id = :id")
  suspend fun getArticleFromId(id: String): ArticleEntity?
}