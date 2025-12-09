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
class GetArticleDetailUseCaseImplTest {

  private val repository : ArticlesRepository = mock()

  private lateinit var useCase: GetArticleDetailUseCase

  private val article: Article = mock()

  @Before
  fun setUp() {
    val trampoline = TrampolineScheduler.instance()
    useCase = GetArticleDetailUseCaseImpl(trampoline, repository)
  }

  @Test
  fun `when use case is invoked and there is an article then return success`() {
    whenever(repository.getArticleDetail("1")).thenReturn(
      Single.just(article))
    val observer = useCase("1").test()
    observer.assertComplete()
    observer.assertNoErrors()
    observer.assertValue(article)
  }

  @Test
  fun `when use case is invoked and there are no articles then return error`() {
    whenever(repository.getArticleDetail("1")).thenReturn(
      Single.error(NoSuchElementException()))
    val observer = useCase("1").test()
    observer.assertError(NoSuchElementException::class.java)
  }
}