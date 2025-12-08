package com.newyorktimesreader.data.repositories

import com.newyorktimesreader.data.source.database.dao.ArticleDao
import com.newyorktimesreader.data.source.database.entity.ArticleEntity
import com.newyorktimesreader.data.source.remote.ArticleResponse
import com.newyorktimesreader.data.source.remote.DiscoverServiceApi
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
  private val apiService: DiscoverServiceApi,
  private val articleDao: ArticleDao
) :
  ArticlesRepository {

  override fun getArticles(): Single<List<Article>> {
    return articleDao.getAllArticles()
      .flatMap { articlesFromDb ->
        if (articlesFromDb.isNotEmpty()) {
          Single.just(articlesFromDb.map { mapEntityToDomain(it) })
        } else {
          fetchAndCacheArticles()
        }
      }
  }

  private fun fetchAndCacheArticles(): Single<List<Article>> {
    return apiService.fetchArticles()
      .map { response ->
        response.response.docs?.map { mapResponseToDomain(it) } ?: listOf()
      }
      .flatMap { articles ->
        if (articles.isNotEmpty()) {
          val entities = articles.map { mapDomainToEntity(it) }

          articleDao.insertAll(*entities.toTypedArray())
          articles
        }

        Single.just(articles)
      }
  }


  override fun getArticleDetail(articleId: String): Single<Article> {
    return articleDao.getArticleFromId(articleId).map { entity ->
      mapEntityToDomain(entity)
    }
  }

  private fun mapResponseToDomain(articleResponse: ArticleResponse): Article {
    return Article(
      id = articleResponse.id,
      title = articleResponse.headline?.main ?: "",
      author = articleResponse.byline?.original ?: "",
      imageUrl = articleResponse.multimedia?.default?.url ?: "",
      imageDescription = articleResponse.multimedia?.caption ?: "",
      abstract = articleResponse.abstract ?: "",
      webUrl = articleResponse.webUrl ?: "",
      fullArticleContent = null
    )
  }

  private fun mapEntityToDomain(articleEntity: ArticleEntity): Article {
    return Article(
      id = articleEntity.id,
      title = articleEntity.title,
      author = articleEntity.author,
      imageUrl = articleEntity.imageUrl,
      imageDescription = articleEntity.imageDescription,
      abstract = articleEntity.abstract,
      webUrl = articleEntity.webUrl,
      fullArticleContent = articleEntity.fullArticleContent
    )
  }

  private fun mapDomainToEntity(article: Article): ArticleEntity {
    return ArticleEntity(
      id = article.id,
      title = article.title,
      author = article.author,
      imageUrl = article.imageUrl,
      imageDescription = article.imageDescription,
      abstract = article.abstract,
      webUrl = article.webUrl,
      fullArticleContent = article.fullArticleContent
    )
  }
}