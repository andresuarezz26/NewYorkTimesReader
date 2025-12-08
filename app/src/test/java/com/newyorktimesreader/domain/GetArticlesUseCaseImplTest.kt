package com.newyorktimesreader.domain

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
class GetArticlesUseCaseImplTest {

  private val repository: ArticlesRepository = mock()

  private lateinit var getArticlesUseCase: GetArticlesUseCase

  @Before
  fun setUp() {
    val trampolineScheduler = TrampolineScheduler.instance()
    getArticlesUseCase = GetArticlesUseCaseImpl(trampolineScheduler,repository)
  }

  @Test
  fun whenInvoked_returnsListOfArticles() {
    whenever(repository.getArticles()).thenReturn(Single.just(emptyList()))
    val expectedArticleCount = 0
    val testObserver = getArticlesUseCase.invoke().test()

    testObserver.assertComplete()
    testObserver.assertNoErrors()

    testObserver.assertValueCount(1)

    val emittedList = testObserver.values().first()
    assert(emittedList.size == expectedArticleCount) {
      "Expected list size to be $expectedArticleCount but was ${emittedList.size}"
    }
  }
}