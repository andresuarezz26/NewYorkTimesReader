package com.newyorktimesreader.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.newyorktimesreader.domain.GetArticleDetailUseCase
import com.newyorktimesreader.domain.di.MainScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val getArticleDetailUseCase: GetArticleDetailUseCase,
  @param:MainScheduler private val mainDispatcher: CoroutineDispatcher,
  savedStateHandle: SavedStateHandle
  ): BaseViewModel(){

  private val articleId: String = checkNotNull(savedStateHandle["id"])

  private val _articleDetail = MutableStateFlow<Article?>(null)
  val articleDetail : StateFlow<Article?> = _articleDetail

  init {
    getArticleDetail()
  }

  private fun getArticleDetail() {
    viewModelScope.launch(mainDispatcher) {
      getArticleDetailUseCase.invoke(articleId)
        .catch{
          Log.e("DetailViewModel", "Error fetching article detail: ${it.message}")
        }
        .collect {
        _articleDetail.value = it
      }
    }
  }

  override fun dispose() {
  }
}