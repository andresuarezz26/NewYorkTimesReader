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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.newyorktimesreader.domain.model.Article

@Composable
fun ArticleCard(article: Article, onNavigateToDetail: (String) -> Unit) {
  Column(modifier = Modifier
    .padding(vertical = 8.dp)
    .fillMaxWidth()
    .clickable { onNavigateToDetail(article.id) }
  ) {

    Text(
      text = article.title,
      style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(8.dp))

    AsyncImage(
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
      model = article.imageUrl,
      contentDescription = null,
      contentScale = FillWidth
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = article.author,
      style = MaterialTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(8.dp))
  }
}