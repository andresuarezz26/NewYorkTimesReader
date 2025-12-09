package com.newyorktimesreader.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.widget.NYTTopBar
import com.newyorktimesreader.presentation.home.widget.ArticleCard

@Composable
fun HomeScreen(
  onNavigateToDetail: (String) -> Unit,
  viewModel: HomeViewModel = hiltViewModel()
) {
  val listOfArticles by viewModel.listOfArticles.observeAsState(emptyList())
  val isRefreshing by viewModel.isRefreshing.observeAsState(false)

  HomeScreenContent(listOfArticles, onNavigateToDetail, isRefreshing) {
    viewModel.getArticles()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
  listOfArticles: List<Article>,
  onNavigateToDetail: (String) -> Unit,
  isRefreshing: Boolean,
  onRefresh: () -> Unit
) {
  Log.d("HomeScreenContent", "HomeScreenContent called $isRefreshing")
  Scaffold(
    topBar = {
      NYTTopBar()
    }
  ) { innerPadding ->
    PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = onRefresh,
      modifier = Modifier.fillMaxSize().padding(innerPadding),
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp)
      ) {
        items(listOfArticles) { article ->
          ArticleCard(article) {
            onNavigateToDetail(it)
          }
        }
      }
    }
  }
}

@Preview
@Composable
fun HomeScreenPreview() {
  HomeScreenContent(
    listOf(
      Article(
        id = "nyt://article/067a379c-81e2-5465-bc6c-7411bbdee9b6",
        title = "Want to Avoid Texts From the Office? This Ring Could Help.",
        author = "By Tina Isaac-Goizé",
        imageUrl = "https://static01.nyt.com/images/2025/08/28/multimedia/28sp-jewelry-color-inyt-digi-03-cmkl/28sp-jewelry-color-inyt-digi-03-cmkl-articleLarge.jpg",
        imageDescription = "Katia de Lasteyrie, the founder of Spktrl, said the ring took two years to develop and was designed as “a tool of empowerment.”",
        abstract = "The Spktrl Light ring uses technology to trigger coded light displays through the diamond on its surface.",
        webUrl = "https://www.nytimes.com/2025/08/27/fashion/jewelry-technology-lab-grown-diamonds.html"
      )
    ), onNavigateToDetail = {}, false, onRefresh = {})
}

