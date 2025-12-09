package com.newyorktimesreader.data.repositories

import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicy
import com.newyorktimesreader.data.source.database.dao.ArticleDao
import com.newyorktimesreader.data.source.database.entity.ArticleEntity
import com.newyorktimesreader.data.source.remote.response.ArticleResponse
import com.newyorktimesreader.data.source.remote.service.DiscoverServiceApi
import com.newyorktimesreader.data.source.remote.service.DiscoverServiceApi.SearchApiUrl.GET_ARTICLES
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Repository pattern that check first if current cached data is valid, if is valid get the data
 * from database, if is not, go to the network, get the data and update the database.
 */
class ArticlesRepositoryImpl @Inject constructor(
  private val apiService: DiscoverServiceApi,
  private val articleDao: ArticleDao,
  private val cachePolicy: CachePolicy
) : ArticlesRepository {

  override fun getArticles(): Single<List<Article>> {
    return Single.fromCallable { cachePolicy.isCacheValid(GET_ARTICLES) }
      .flatMap { isCacheValid ->
        if (isCacheValid) {
          getArticlesFromCache()
        } else {
          refreshArticles()
        }
      }
  }


  override fun getArticleDetail(articleId: String): Single<Article> {
    return articleDao.getArticleFromId(articleId).map { entity ->
      mapEntityToDomain(entity)
    }.switchIfEmpty(Single.error(NoSuchElementException(articleId)))
  }

  private fun getArticlesFromCache(): Single<List<Article>> {
    return articleDao.getAllArticles().flatMap { articlesFromDb ->
      if (articlesFromDb.isNotEmpty()) {
        Single.just(articlesFromDb.map { mapEntityToDomain(it) }) // Assuming .toDomain() extension is used
      } else {
        refreshArticles()
      }
    }
  }

  override fun refreshArticles(): Single<List<Article>> {
    return apiService.fetchArticles()
      .map { response ->
        response.response.docs?.map { mapResponseToDomain(it) } ?: listOf()
      }
      .flatMap { articles ->
        if (articles.isNotEmpty()) {
          val entities: List<ArticleEntity> = articles.map { mapDomainToEntity(it) }
          articleDao.clearAll()
          articleDao.insertAll(*entities.toTypedArray())
          cachePolicy.setCacheRefreshed(GET_ARTICLES)
        }

        Single.just(articles)
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