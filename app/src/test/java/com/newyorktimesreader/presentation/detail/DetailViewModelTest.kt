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
  fun whenViewModelIsCreated_invokeArticleDetailUseCase() {
    assertEquals(mockArticle, viewModel.articleDetail.value)
  }

  private fun getMockArticle() : Article {
    return Article(
      id = "nyt://article/067a379c-81e2-5465-bc6c-7411bbdee9b6",
      title = "Want to Avoid Texts From the Office? This Ring Could Help.",
      author = "By Tina Isaac-Goizé",
      imageUrl = "https://static01.nyt.com/images/2025/08/28/multimedia/28sp-jewelry-color-inyt-digi-03-cmkl/28sp-jewelry-color-inyt-digi-03-cmkl-articleLarge.jpg",
      imageDescription = "Katia de Lasteyrie, the founder of Spktrl, said the ring took two years to develop and was designed as “a tool of empowerment.”",
      abstract = "The Spktrl Light ring uses technology to trigger coded light displays through the diamond on its surface.",
      webUrl = "https://www.nytimes.com/2025/08/27/fashion/jewelry-technology-lab-grown-diamonds.html"
    )
  }
}