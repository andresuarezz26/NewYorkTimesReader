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
    title = "The Global Economy Outlook",
    author = "Dr. Jane Smith",
    imageUrl = "test_url"
  )

  @Test
  fun articleCard_displaysTitleAndAuthor() {
    // 2. Set the composable content on the rule
    composeTestRule.setContent {
      // Use the improved ArticleCard implementation (if available)
      // Otherwise, use your current ArticleCard
      ArticleCard(article = testArticle, onNavigateToDetail = {})
    }

    // 3. Verify that the title text is displayed
    composeTestRule
      .onNodeWithText(testArticle.title)
      .assertExists()

    // 4. Verify that the author text is displayed
    // Note: If you used "By ${article.author}", match that string!
    composeTestRule
      .onNodeWithText(testArticle.author)
      .assertExists()
  }

  @Test
  fun articleCard_clickTriggersNavigationCallback() {
    // 1. Mock the callback function using Mockito-Kotlin
    val mockOnNavigate: (String) -> Unit = mock()

    composeTestRule.setContent {
      ArticleCard(article = testArticle, onNavigateToDetail = mockOnNavigate)
    }

    // 2. Find the card by the title (as the clickable element contains the text)
    composeTestRule
      .onNodeWithText(testArticle.title)
      .performClick()

    // 3. Verify that the mock callback was called exactly once with the correct argument
    verify(mockOnNavigate).invoke(testArticle.title)
  }

}


