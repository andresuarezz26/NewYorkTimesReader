package com.newyorktimesreader.data

import com.newyorktimesreader.data.repositories.ArticlesRepositoryImpl
import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicy
import com.newyorktimesreader.data.source.database.dao.ArticleDao
import com.newyorktimesreader.data.source.database.entity.ArticleEntity
import com.newyorktimesreader.data.source.remote.service.DiscoverServiceApi
import com.newyorktimesreader.data.source.remote.response.ArticleListResponse
import com.newyorktimesreader.data.source.remote.response.ArticleResponse
import com.newyorktimesreader.data.source.remote.response.ByLineResponse
import com.newyorktimesreader.data.source.remote.response.DefaultMultimediaResponse
import com.newyorktimesreader.data.source.remote.response.DiscoverServiceResponse
import com.newyorktimesreader.data.source.remote.response.HeadLineResponse
import com.newyorktimesreader.data.source.remote.response.MultimediaResponse
import com.newyorktimesreader.data.source.remote.service.DiscoverServiceApi.SearchApiUrl.GET_ARTICLES
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class ArticlesRepositoryImplTest {

  private val apiService: DiscoverServiceApi = mock()
  private val articleDao: ArticleDao = mock()
  private val cachePolicy: CachePolicy = mock()
  private lateinit var repository: ArticlesRepository

  private val mockArticleResponse = ArticleResponse(
    id = "123",
    headline = HeadLineResponse(main = "Test Article Title"),
    abstract = "Test Abstract",
    multimedia = MultimediaResponse(
      caption = "Test Caption",
      default = DefaultMultimediaResponse(
        url = "http://test.com/image.jpg",
        width = 100,
        height = 100
      )
    ),
    byline = ByLineResponse(original = "By Test Author"),
    webUrl = "http://test.com/article"
  )

  private val mockSuccessResponse = DiscoverServiceResponse(
    status = "OK",
    response = ArticleListResponse(docs = listOf(mockArticleResponse))
  )

  private val mockArticleEntity = ArticleEntity(
    id = "123",
    title = "Test Article Title",
    author = "By Test Author",
    imageUrl = "http://test.com/image.jpg",
    imageDescription = "Test Caption",
    abstract = "Test Abstract",
    webUrl = "http://test.com/article"
  )

  @Before
  fun setUp() {
    repository = ArticlesRepositoryImpl(apiService, articleDao, cachePolicy)
  }

  @Test
  fun `when there is no local data and the service call succeeds, then return the service data`() {
    whenever(cachePolicy.isCacheValid(GET_ARTICLES)).thenReturn(true)
    whenever(articleDao.getAllArticles()).thenReturn(Single.just(emptyList()))
    whenever(apiService.fetchArticles(any())).thenReturn(Single.just(mockSuccessResponse))
    val testObserver = repository.getArticles().test()
    testObserver.assertComplete()
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)
    val emittedList = testObserver.values().first()
    assertEquals(1, emittedList.size)

    val (id, title, author, imageUrl, imageDescription, abstract, webUrl) = emittedList.first()

    assertEquals(id, mockArticleResponse.id)
    assertEquals(title, mockArticleResponse.headline?.main)
    assertEquals(abstract, mockArticleResponse.abstract)
    assertEquals(imageDescription, mockArticleResponse.multimedia?.caption)
    assertEquals(imageUrl, mockArticleResponse.multimedia?.default?.url)
    assertEquals(author, mockArticleResponse.byline?.original)
    assertEquals(webUrl, mockArticleResponse.webUrl)
    verify(cachePolicy).setCacheRefreshed(GET_ARTICLES)
    verify(articleDao).clearAll()
  }

  @Test
  fun `when there is no local data and the service call fails, then return an error`() {
    whenever(cachePolicy.isCacheValid(GET_ARTICLES)).thenReturn(true)
    whenever(articleDao.getAllArticles()).thenReturn(Single.just(emptyList())) // <--- Line 84

    val error = RuntimeException("Network Error")
    whenever(apiService.fetchArticles())
      .thenReturn(Single.error(error))

    val testObserver = repository.getArticles().test()

    testObserver.assertError(error)
  }

  @Test
  fun `when getArticleFromId is called and there are articles in the database, then return them`() {
    whenever(articleDao.getArticleFromId("1")).thenReturn(Maybe.just(mockArticleEntity))

    val testObserver = repository.getArticleDetail("1").test()

    testObserver.assertComplete()
    testObserver.assertNoErrors()
    testObserver.assertValueCount(1)
    val emittedItem = testObserver.values().first()

    val (id, title, author, imageUrl, imageDescription, abstract, webUrl) = emittedItem

    assertEquals(id, mockArticleEntity.id)
    assertEquals(title, mockArticleEntity.title)
    assertEquals(abstract, mockArticleEntity.abstract)
    assertEquals(imageDescription, mockArticleEntity.imageDescription)
    assertEquals(imageUrl, mockArticleEntity.imageUrl)
    assertEquals(author, mockArticleEntity.author)
    assertEquals(webUrl, mockArticleEntity.webUrl)
    verify(cachePolicy, never()).setCacheRefreshed(GET_ARTICLES)
  }

  @Test
  fun `when getArticleFromId is called there are no articles in the database, then return an error`() {
    whenever(articleDao.getArticleFromId("1")).thenReturn(Maybe.empty())

    val testObserver = repository.getArticleDetail("1").test()

    testObserver.assertNotComplete()
    testObserver.assertError(NoSuchElementException::class.java)
    testObserver.assertNoValues()
  }
}