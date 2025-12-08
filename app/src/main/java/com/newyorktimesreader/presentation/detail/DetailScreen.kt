package com.newyorktimesreader.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.widget.NYTTopBar

@Composable
fun DetailScreen(
  viewModel: DetailViewModel = hiltViewModel<DetailViewModel>(),
  onBack: () -> Unit
) {

  val articleDetail = viewModel.articleDetail.observeAsState(null)
  DetailScreenContent(articleDetail.value) {
    onBack()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(article: Article?, onBack: () -> Unit) {
  Scaffold(
    topBar = {
      NYTTopBar(onBack = onBack)
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
        .verticalScroll(rememberScrollState()), // Allows content to scroll
      horizontalAlignment = Alignment.Start
    ) {

      Text(
        text = article?.title ?: "",
        modifier = Modifier
          .padding(top = 16.dp),
        style = TextStyle(
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif
        )
      )

      Text(
        text = article?.abstract ?: "",
        modifier = Modifier
          .padding(top = 4.dp),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
      )

      Spacer(Modifier.height(16.dp))

      AsyncImage(
        modifier = Modifier
          .fillMaxWidth()
          .height(250.dp),
        model = article?.imageUrl ?: "",
        contentDescription = null,
        contentScale = ContentScale.Crop
      )

      Text(
        text = article?.imageDescription ?: "",
        modifier = Modifier
          .padding(top = 8.dp),
        style = MaterialTheme.typography.labelSmall
      )

      Spacer(Modifier.height(6.dp))
      Text(
        text = article?.author ?: "",
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.SemiBold,
          fontFamily = FontFamily.Serif
        )
      )
      Spacer(Modifier.height(6.dp))
      Text(
        text = article?.fullArticleContent ?: "",
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.Normal,
          fontFamily = FontFamily.Serif
        )
      )
    }
  }
}

@Preview
@Composable
fun DetailScreenPreview() {
  DetailScreenContent(article = null) {

  }
}