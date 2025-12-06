package com.newyorktimesreader.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

  @Rule
  @JvmField
  public var rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var getArticlesUseCase: GetArticlesUseCase

  private lateinit var homeViewModel : HomeViewModel



  // 🔑 1. Setup method to configure mocks
  @Before
  fun setup() {

  }

  @Test
  fun whenGetArticlesSuccess_thenVerifyListOfArticles() {
    // When
    val result = getMockList()
    whenever(getArticlesUseCase())
      .thenReturn(Single.just(result))
    val trampolineScheduler = TrampolineScheduler.instance()

    // Then
    homeViewModel = HomeViewModel(getArticlesUseCase, trampolineScheduler)

    // Assert
    assertEquals(homeViewModel.listOfArticles.value, result)
  }


  private fun getMockList() : List<Article> {
    return listOf(Article(title = "1", author = "", imageUrl=""))
  }

}