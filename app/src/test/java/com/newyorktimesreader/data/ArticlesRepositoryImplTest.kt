package com.newyorktimesreader.data

import com.newyorktimesreader.data.source.ApiService
import com.newyorktimesreader.data.source.ArticleListResponse
import com.newyorktimesreader.data.source.ArticleResponse
import com.newyorktimesreader.data.source.ByLineResponse
import com.newyorktimesreader.data.source.DefaultMultimediaResponse
import com.newyorktimesreader.data.source.DiscoverServiceResponse
import com.newyorktimesreader.data.source.HeadLineResponse
import com.newyorktimesreader.data.source.MultimediaResponse
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

  private val apiService: ApiService = mock()
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
    repository = ArticlesRepositoryImpl(apiService)
  }

  @Test
  fun whenGetListOfArticles_andArticlesIsSuccessfull_thenReturnListOfArticles() {
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
  fun whenGetListOfArticles_andArticlesThrowsError_thenEmitError() {
    val error = RuntimeException("Network Error")
    whenever(apiService.fetchArticles(any(), any()))
      .thenReturn(Single.error(error))
    val testObserver = repository.getArticles().test()
    testObserver.assertError(error)
  }
}