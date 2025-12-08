package com.newyorktimesreader.presentation.home.widget

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.newyorktimesreader.domain.model.Article
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ArticleCardTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  // Mock Article data
  private val testArticle = Article(
    id = "nyt://article/067a379c-81e2-5465-bc6c-7411bbdee9b6",
    title = "Want to Avoid Texts From the Office? This Ring Could Help.",
    author = "By Tina Isaac-Goizé",
    imageUrl = "https://static01.nyt.com/images/2025/08/28/multimedia/28sp-jewelry-color-inyt-digi-03-cmkl/28sp-jewelry-color-inyt-digi-03-cmkl-articleLarge.jpg",
    imageDescription = "Katia de Lasteyrie, the founder of Spktrl, said the ring took two years to develop and was designed as “a tool of empowerment.”",
    abstract = "The Spktrl Light ring uses technology to trigger coded light displays through the diamond on its surface.",
    webUrl = "https://www.nytimes.com/2025/08/27/fashion/jewelry-technology-lab-grown-diamonds.html"
  )

  @Test
  fun articleCard_displaysTitleAndAuthor() {
    composeTestRule.setContent {
      ArticleCard(article = testArticle, onNavigateToDetail = {})
    }

    composeTestRule
      .onNodeWithText(testArticle.title)
      .assertExists()

    composeTestRule
      .onNodeWithText(testArticle.author)
      .assertExists()
  }

  @Test
  fun articleCard_clickTriggersNavigationCallback() {
    val mockOnNavigate: (String) -> Unit = mock()

    composeTestRule.setContent {
      ArticleCard(article = testArticle, onNavigateToDetail = mockOnNavigate)
    }

    composeTestRule
      .onNodeWithText(testArticle.title)
      .performClick()

    verify(mockOnNavigate).invoke(testArticle.id)
  }

}


