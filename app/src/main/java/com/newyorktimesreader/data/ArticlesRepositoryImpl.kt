package com.newyorktimesreader.data

import com.newyorktimesreader.data.source.ApiService
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlin.String

class ArticlesRepositoryImpl @Inject constructor(private val apiService: ApiService) : ArticlesRepository {

  override fun getArticles(): Single<List<Article>> {
    return apiService.fetchArticles("").map { response ->
      response.response.docs?.map { article ->
        Article(
          id = article.id,
          title = article.headline?.main ?: "",
          author = article.byline?.original ?: "",
          imageUrl = article.multimedia?.default?.url ?: "",
          imageDescription = article.multimedia?.caption ?:"",
          abstract = article.abstract ?: "",
          webUrl = article.webUrl ?: "",
          fullArticleContent = null
        )
      } ?: emptyList()
    }
  }

  override fun getArticleDetail(articleId: String): Single<Article> {
    TODO("Not yet implemented")
  }
}