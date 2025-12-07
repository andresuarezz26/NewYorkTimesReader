package com.newyorktimesreader.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Single
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

  val result = getMockList()

  // 🔑 1. Setup method to configure mocks
  @Before
  fun setup() {
    // The use case is invoked when viewmodel inits
    whenever(getArticlesUseCase())
      .thenReturn(Single.just(result))
    val trampolineScheduler = TrampolineScheduler.instance()

    homeViewModel = HomeViewModel(getArticlesUseCase, trampolineScheduler)
  }

  @Test
  fun whenGetArticlesSuccess_thenVerifyListOfArticles() {

    assertEquals(homeViewModel.listOfArticles.value, result)
  }

  @Test
  fun whenDispose_thenVerifyCompositeDisposableIsDisposed() {
    homeViewModel.dispose()

    assertTrue(homeViewModel.compositeDisposable.isDisposed)
  }


  private fun getMockList() : List<Article> {
    return listOf(Article(
      id = "nyt://article/067a379c-81e2-5465-bc6c-7411bbdee9b6",
      title = "Want to Avoid Texts From the Office? This Ring Could Help.",
      author = "By Tina Isaac-Goizé",
      imageUrl = "https://static01.nyt.com/images/2025/08/28/multimedia/28sp-jewelry-color-inyt-digi-03-cmkl/28sp-jewelry-color-inyt-digi-03-cmkl-articleLarge.jpg",
      imageDescription = "Katia de Lasteyrie, the founder of Spktrl, said the ring took two years to develop and was designed as “a tool of empowerment.”",
      abstract = "The Spktrl Light ring uses technology to trigger coded light displays through the diamond on its surface.",
      webUrl = "https://www.nytimes.com/2025/08/27/fashion/jewelry-technology-lab-grown-diamonds.html"
    ))
  }
}