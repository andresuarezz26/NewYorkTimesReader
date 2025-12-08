package com.newyorktimesreader.data.source.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newyorktimesreader.data.source.database.entity.ARTICLE_TABLE
import com.newyorktimesreader.data.source.database.entity.ArticleEntity
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface ArticleDao {

  @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
  fun insertAll(vararg entity: ArticleEntity)

  @Query("DELETE FROM $ARTICLE_TABLE")
  fun clearAll()

  @Query("SELECT * FROM $ARTICLE_TABLE")
  fun getAllArticles(): Single<List<ArticleEntity>>

  @Query("SELECT * FROM $ARTICLE_TABLE WHERE id = :id")
  fun getArticleFromId(id: String): Maybe<ArticleEntity>
}