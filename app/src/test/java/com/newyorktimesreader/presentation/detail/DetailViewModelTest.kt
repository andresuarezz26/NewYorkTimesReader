package com.newyorktimesreader.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.newyorktimesreader.domain.GetArticleDetailUseCase
import com.newyorktimesreader.domain.model.Article
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class DetailViewModelTest {

  val getArticleDetailUseCase: GetArticleDetailUseCase = mock()
  val savedStateHandle: SavedStateHandle = mock()

  private lateinit var viewModel: DetailViewModel

  private val mockArticle : Article = getMockArticle()

  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setUp() {
    val dispatcher = UnconfinedTestDispatcher()
    whenever(savedStateHandle.get<String>("id")).thenReturn("1")
    whenever(getArticleDetailUseCase.invoke("1")).thenReturn(flowOf(mockArticle))
    viewModel = DetailViewModel(getArticleDetailUseCase, dispatcher, savedStateHandle)
  }

  @Test
  fun `when viewmodel is created, use case returns a valid article, then assert we get the same article`() = runTest {
    assertEquals(mockArticle, viewModel.articleDetail.value)
  }

  private fun getMockArticle() : Article {
    return mock(Article::class.java)
  }
}