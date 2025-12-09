package com.newyorktimesreader.data.source.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ARTICLE_TABLE = "article"
const val ARTICLE_ID = "id"

@Entity(tableName = ARTICLE_TABLE)
data class ArticleEntity(
  @PrimaryKey @ColumnInfo(name = ARTICLE_ID) val id: String,
  val title: String,
  val author: String,
  val imageUrl: String,
  val imageDescription: String,
  val abstract: String,
  val webUrl: String,
  val fullArticleContent: String? = null
)
