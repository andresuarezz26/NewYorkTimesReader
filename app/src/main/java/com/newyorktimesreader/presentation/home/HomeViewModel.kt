package com.newyorktimesreader.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.RefreshArticlesUseCase
import com.newyorktimesreader.domain.di.MainScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home Screen.
 *
 * This viewModel interacts with domain layer to get the information and pass it via
 * mutable live data to the view.
 */
@HiltViewModel
open class HomeViewModel @Inject constructor(
  private val getArticlesUseCase: GetArticlesUseCase,
  private val refreshArticlesUseCase: RefreshArticlesUseCase,
  @param:MainScheduler private val mainScheduler: Scheduler
) :
  BaseViewModel() {

  internal val compositeDisposable = CompositeDisposable()
  private val _listOfArticles = MutableStateFlow<List<Article>>(listOf())
  val listOfArticles: StateFlow<List<Article>> = _listOfArticles

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing: StateFlow<Boolean> = _isRefreshing

  init {
    getArticles()
  }

  private fun getArticles() {
    _isRefreshing.value = true
    viewModelScope.launch {
      getArticlesUseCase.invoke()
        .catch {
          Log.e("DetailViewModel", "Error fetching article detail: ${it.message}")
        }
        .collect {
          _listOfArticles.value = it
          _isRefreshing.value = false
        }
    }
  }

  fun refreshArticles() {
    viewModelScope.launch {
      refreshArticlesUseCase.invoke()
        .catch {
          Log.e("DetailViewModel", "Error fetching article detail: ${it.message}")
        }
        .collect {
          _listOfArticles.value = it
          _isRefreshing.value = false
        }
    }
  }

  override fun dispose() {
    compositeDisposable.dispose()
  }
}