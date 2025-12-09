package com.newyorktimesreader.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newyorktimesreader.domain.di.MainScheduler
import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.RefreshArticlesUseCase
import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

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
  private val _listOfArticles = MutableLiveData<List<Article>>()
  val listOfArticles: LiveData<List<Article>> = _listOfArticles

  private val _isRefreshing = MutableLiveData(false)
  val isRefreshing: LiveData<Boolean> = _isRefreshing

  init {
    getArticles()
  }

  private fun getArticles() {
    compositeDisposable.add(
      getArticlesUseCase.invoke()
        .observeOn(mainScheduler)
        .doOnSubscribe { _isRefreshing.value = true}
        .doFinally { _isRefreshing.value = false }
        .subscribe({
          _listOfArticles.value = it
        }, {
          Log.d("HomeVM", "Data Error: ${it.message}")
        })
    )
  }

  fun refreshArticles(){
    compositeDisposable.add(
      refreshArticlesUseCase.invoke()
        .observeOn(mainScheduler)
        .doOnSubscribe { _isRefreshing.value = true}
        .doFinally { _isRefreshing.value = false }
        .subscribe({
          _listOfArticles.value = it
        }, {
          Log.d("HomeVM", "Data Error: ${it.message}")
        })
    )
  }

  override fun dispose() {
    compositeDisposable.dispose()
  }
}