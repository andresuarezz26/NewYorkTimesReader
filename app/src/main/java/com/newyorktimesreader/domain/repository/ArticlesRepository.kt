package com.newyorktimesreader.domain.repository

import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

/**
 * This interface represents the implementation of a repository pattern, this is an abstraction
 * that have the logic to handle different data sources like Network calls, local databases and
 * shared preferences, to manage Article.
 */
interface ArticlesRepository {

  /**
   * Get articles following the internal business rules of the repository
   */
  fun getArticles(): Flow<List<Article>>

  /**
   * Get a detail of an articles
   */
  fun getArticleDetail(articleId: String): Flow<Article>

  /**
   * Get the articles from the network and update the database
   */
  fun refreshArticles(): Flow<List<Article>>


}