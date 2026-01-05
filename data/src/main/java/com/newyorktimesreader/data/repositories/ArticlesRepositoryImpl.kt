package com.newyorktimesreader.data.repositories

import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicy
import com.newyorktimesreader.data.source.database.dao.ArticleDao
import com.newyorktimesreader.data.source.database.entity.ArticleEntity
import com.newyorktimesreader.data.source.remote.response.ArticleResponse
import com.newyorktimesreader.data.source.remote.service.DiscoverServiceApi
import com.newyorktimesreader.data.source.remote.service.DiscoverServiceApi.SearchApiUrl.GET_ARTICLES
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Repository pattern that check first if current cached data is valid, if is valid get the data
 * from database, if is not, go to the network, get the data and update the database.
 */
class ArticlesRepositoryImpl @Inject constructor(
  private val apiService: DiscoverServiceApi,
  private val articleDao: ArticleDao,
  private val cachePolicy: CachePolicy
) : ArticlesRepository {

  @OptIn(ExperimentalCoroutinesApi::class)
  override fun getArticles(): Flow<List<Article>> {
    return flowOf(cachePolicy.isCacheValid(GET_ARTICLES))
      .flatMapConcat { isCacheValid ->
        if (isCacheValid) {
          getArticlesFromCache()
        } else {
          refreshArticles()
        }
      }.flowOn(Dispatchers.IO)
  }


  override fun getArticleDetail(articleId: String): Flow<Article> {
    return flow { emit(articleDao.getArticleFromId(articleId)) }.map { entity ->
      entity?.let { mapEntityToDomain(it) } ?:
      throw NoSuchElementException(articleId)
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  private fun getArticlesFromCache(): Flow<List<Article>> {
    return flow { emit(articleDao.getAllArticles()) }
      .flatMapConcat { articlesFromDb ->
        if (articlesFromDb.isNotEmpty()) {
          flowOf(articlesFromDb.map { mapEntityToDomain(it) })
        } else {
          refreshArticles()
        }
      }.distinctUntilChanged()
  }


  override fun refreshArticles(): Flow<List<Article>> {
    return flow { emit(apiService.fetchArticles()) }
      .map { response ->
        response.response.docs?.map { mapResponseToDomain(it) } ?: listOf()
      }
      .onEach { articles ->
        if (articles.isNotEmpty()) {
          val entities: List<ArticleEntity> = articles.map { mapDomainToEntity(it) }
          articleDao.clearAll()
          articleDao.insertAll(*entities.toTypedArray())
          cachePolicy.setCacheRefreshed(GET_ARTICLES)
        }
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