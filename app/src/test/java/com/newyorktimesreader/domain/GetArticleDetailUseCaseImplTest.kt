package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetArticleDetailUseCaseImplTest {

  private val repository : ArticlesRepository = mock()

  private lateinit var useCase: GetArticleDetailUseCase

  private val article: Article = mock()

  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setUp() {
    val dispatcher = UnconfinedTestDispatcher()
    useCase = GetArticleDetailUseCaseImpl(dispatcher, repository)
  }

  @Test
  fun `when use case is invoked and there is an article then return success`() = runTest {
    whenever(repository.getArticleDetail("1")).thenReturn(
      flowOf(article))
    val observer = useCase("1").first()

    assertEquals(article, observer)
  }

  @Test
  fun `when use case is invoked and there are no articles then return error`() = runTest {
    val expectedError = NoSuchElementException()
    whenever(repository.getArticleDetail("1")).thenReturn(
      flow { throw expectedError }
    )

    // 2. Act & Assert: Verify that collecting the flow throws the expected exception
    assertFailsWith<NoSuchElementException> {
      useCase("1").first()
    }
  }
}