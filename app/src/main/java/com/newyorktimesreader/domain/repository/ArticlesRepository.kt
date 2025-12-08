package com.newyorktimesreader.domain.repository

import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Single

/**
 * This interface represents the implementation of a repository pattern, this is an abstraction
 * that have the logic to handle different data sources like Network calls, local databases and
 * shared preferences, to manage Article
 */
interface ArticlesRepository {

  fun getArticles(): Single<List<Article>>

  fun getArticleDetail(articleId: String): Single<Article>
}