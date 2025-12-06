package com.newyorktimesreader.domain

import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler
import org.junit.Before
import org.junit.Test

class GetArticlesUseCaseImplTest {

  private lateinit var getArticlesUseCase: GetArticlesUseCase

  @Before
  fun setUp() {
    val trampolineScheduler = TrampolineScheduler.instance()
    getArticlesUseCase = GetArticlesUseCaseImpl(trampolineScheduler)
  }

  @Test
  fun whenInvoked_returnsListOfArticles() {
    val expectedArticleCount = 2
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