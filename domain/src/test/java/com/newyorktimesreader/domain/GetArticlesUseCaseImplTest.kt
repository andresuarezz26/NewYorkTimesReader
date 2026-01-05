package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class GetArticlesUseCaseImplTest {

  private val repository: ArticlesRepository = mock()

  private lateinit var getArticlesUseCase: GetArticlesUseCase
  private val article: Article = mock()

  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setUp() {
    val dispatcher = UnconfinedTestDispatcher()
    getArticlesUseCase = GetArticlesUseCaseImpl(dispatcher, repository)
  }

  @Test
  fun `when repository returns empty list then use case returns empty list`() = runTest {
    whenever(repository.getArticles()).thenReturn(flowOf(emptyList()))
    val expectedArticleCount = 0
    val list = getArticlesUseCase.invoke().first()
    assert(list.size == expectedArticleCount)
  }

  @Test
  fun `when repository returns list with 2 items then use case returns list with 2 items`()  = runTest  {
    val articles = listOf(article, article)
    whenever(repository.getArticles()).thenReturn(flowOf(articles))
    val expectedArticleCount = 2
    val articleListResult = getArticlesUseCase.invoke().first()

    assert(articleListResult.size == expectedArticleCount)
  }
}