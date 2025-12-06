package com.newyorktimesreader.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.home.widget.ArticleCard

@Composable
fun HomeScreen(
  onNavigateToDetail: (String) -> Unit,
  viewModel: HomeViewModel = hiltViewModel()
) {
  val listOfArticles = viewModel.listOfArticles.observeAsState(emptyList())

  HomeScreenContent(listOfArticles.value, onNavigateToDetail)
}

@Composable
fun HomeScreenContent(listOfArticles: List<Article>, onNavigateToDetail: (String) -> Unit) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    item {
      BasicText(text = "Top Stories",
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
      Spacer(modifier = Modifier.height(16.dp))
    }

    items(listOfArticles) { article ->
      ArticleCard(article) {
        onNavigateToDetail(it)
      }
    }
  }
}

@Preview
@Composable
fun HomeScreenPreview() {
  HomeScreenContent(
    listOf(
      Article(title = "Sample Article", author = "Author", imageUrl = ""),
      Article(title = "Sample Article", author = "Author", imageUrl = ""),
          Article(title = "Sample Article", author = "Author", imageUrl = ""),
        Article(title = "Sample Article", author = "Author", imageUrl = "")
    ), onNavigateToDetail = {})
}

