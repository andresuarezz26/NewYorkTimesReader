package com.newyorktimesreader.presentation.home

import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.RefreshArticlesUseCase
import com.newyorktimesreader.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

  val getArticlesUseCase: GetArticlesUseCase = mock()
  val refreshArticlesUseCase: RefreshArticlesUseCase = mock()

  private lateinit var homeViewModel : HomeViewModel

  val result = getMockList()

  // 🔑 1. Setup method to configure mocks
  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setup() {
    // The use case is invoked when viewmodel inits
    whenever(getArticlesUseCase())
      .thenReturn(flowOf(result))
    val dispatcher = UnconfinedTestDispatcher()

    homeViewModel = HomeViewModel(getArticlesUseCase, refreshArticlesUseCase,dispatcher)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @After
  fun tearDown() {
    // 3. Always reset after the test
    Dispatchers.resetMain()
  }

  @Test
  fun `when get articles success then verify the list of articles correspons with the usecase returned data`() =
    runTest {
      assertEquals(result, homeViewModel.listOfArticles.value)
      assertEquals(false, homeViewModel.isRefreshing.value)
    }

  @Test
  fun `when refresh the data is invoked then verify the list of articles correspons with the usecase returned data`() = runTest {
    val result = getMockListWith2Articles()
    whenever(refreshArticlesUseCase.invoke()).thenReturn(flowOf(result))
    homeViewModel.refreshArticles()
    assertEquals(result, homeViewModel.listOfArticles.value)
    assertEquals(false, homeViewModel.isRefreshing.value)
  }


  private fun getMockList() : List<Article> {
    return listOf(mock(Article::class.java))
  }

  private fun getMockListWith2Articles() : List<Article> {
    return listOf(mock(Article::class.java), mock(Article::class.java))
  }
}