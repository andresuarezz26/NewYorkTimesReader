package com.newyorktimesreader.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.newyorktimesreader.domain.GetArticleDetailUseCase
import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  val getArticleDetailUseCase: GetArticleDetailUseCase = mock()
  val scheduler = TrampolineScheduler.instance()
  val savedStateHandle: SavedStateHandle = mock()

  private lateinit var viewModel: DetailViewModel

  private val mockArticle : Article = getMockArticle()

  @Before
  fun setUp() {
    whenever(savedStateHandle.get<String>("id")).thenReturn("1")
    whenever(getArticleDetailUseCase.invoke("1")).thenReturn(Single.just(mockArticle))
    viewModel = DetailViewModel(getArticleDetailUseCase, scheduler, savedStateHandle)
  }

  @Test
  fun `when viewmodel is created, use case returns a valid article, then assert we get the same article`() {
    assertEquals(mockArticle, viewModel.articleDetail.value)
  }

  private fun getMockArticle() : Article {
    return mock(Article::class.java)
  }
}