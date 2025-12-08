package com.newyorktimesreader.data

import com.newyorktimesreader.data.repositories.ArticlesRepositoryImpl
import com.newyorktimesreader.data.source.database.dao.ArticleDao
import com.newyorktimesreader.data.source.remote.DiscoverServiceApi
import com.newyorktimesreader.data.source.remote.ArticleListResponse
import com.newyorktimesreader.data.source.remote.ArticleResponse
import com.newyorktimesreader.data.source.remote.ByLineResponse
import com.newyorktimesreader.data.source.remote.DefaultMultimediaResponse
import com.newyorktimesreader.data.source.remote.DiscoverServiceResponse
import com.newyorktimesreader.data.source.remote.HeadLineResponse
import com.newyorktimesreader.data.source.remote.MultimediaResponse
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Single
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class ArticlesRepositoryImplTest {

  private val apiService: DiscoverServiceApi = mock()
  private val articleDao: ArticleDao = mock()
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

  @Before
  fun setUp() {
    repository = ArticlesRepositoryImpl(apiService, articleDao)
  }

  @Test
  fun whenGetListOfArticles_NoLocalDataArticlesAndServiceCallSuccess_thenReturnListOfArticlesFromNetwork() {
    whenever(articleDao.getAllArticles()).thenReturn(Single.just(emptyList()))
    whenever(apiService.fetchArticles(any(), any())).thenReturn(Single.just(mockSuccessResponse))
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
  }

  @Test
  fun whenGetAllArticles_EmptyLocalDataAndNetworkError_thenEmitError() {
    whenever(articleDao.getAllArticles()).thenReturn(Single.just(emptyList())) // <--- Line 84

    val error = RuntimeException("Network Error")
    whenever(apiService.fetchArticles())
      .thenReturn(Single.error(error))

    val testObserver = repository.getArticles().test()

    // Assert that the error from the network is correctly propagated
    testObserver.assertError(error)
  }
}