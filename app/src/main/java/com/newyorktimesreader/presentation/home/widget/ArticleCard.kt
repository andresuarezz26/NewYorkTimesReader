package com.newyorktimesreader.presentation.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newyorktimesreader.domain.model.Article

@Composable
fun ArticleCard(article: Article, onNavigateToDetail: (String) -> Unit) {
  Column(modifier = Modifier
    .padding(vertical = 8.dp)
    .fillMaxWidth()
    .clickable { onNavigateToDetail(article.title) }
  ) {

    Text(
      text = article.title,
      style = MaterialTheme.typography.titleMedium // Use semantic typography
    )
    Spacer(modifier = Modifier.height(8.dp))
    Box(modifier = Modifier
      .fillMaxWidth()
      .height(200.dp)
      .background(color = androidx.compose.ui.graphics.Color.Gray),
      contentAlignment = Alignment.Center) {

    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = article.author,
      style = MaterialTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(8.dp))
  }
}

@Preview
@Composable
fun HomeScreenPreview() {

  ArticleCard(Article(title = "Sample Article", author = "Author", imageUrl = "")) {

  }
}