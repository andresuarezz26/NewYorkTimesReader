package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RefreshArticlesUseCaseImplTest {

  private val repository: ArticlesRepository = mock()

  private lateinit var getArticlesUseCase: RefreshArticlesUseCase
  private val article: Article = mock()

  @Before
  fun setUp() {
    val trampolineScheduler = TrampolineScheduler.instance()
    getArticlesUseCase = RefreshArticlesUseCaseImpl(trampolineScheduler, repository)
  }

  @Test
  fun `when repository returns empty list then use case returns empty list`() {
    whenever(repository.refreshArticles()).thenReturn(Single.just(emptyList()))
    val expectedArticleCount = 0
    val testObserver = getArticlesUseCase.invoke().test()

    testObserver.assertComplete()
    testObserver.assertNoErrors()

    testObserver.assertValueCount(1)

    val emittedList = testObserver.values().first()
    assert(emittedList.size == expectedArticleCount)
  }

  @Test
  fun `when repository returns list with 2 items then use case returns list with 2 items`() {
    val articles = listOf(article, article)
    whenever(repository.refreshArticles()).thenReturn(Single.just(articles))
    val expectedArticleCount = 2
    val testObserver = getArticlesUseCase.invoke().test()

    testObserver.assertComplete()
    testObserver.assertNoErrors()
    val emittedList = testObserver.values().first()
    assert(emittedList.size == expectedArticleCount)
  }
}