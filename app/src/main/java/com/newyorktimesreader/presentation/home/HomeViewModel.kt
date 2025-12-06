package com.newyorktimesreader.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newyorktimesreader.di.MainScheduler
import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
  private val getArticlesUseCase: GetArticlesUseCase,
  @MainScheduler private val mainScheduler: Scheduler) :
  BaseViewModel() {

  internal val compositeDisposable = CompositeDisposable()
  private val _listOfArticles = MutableLiveData<List<Article>>()
  val listOfArticles: LiveData<List<Article>> = _listOfArticles

  init {
    getArticles()
  }

  private fun getArticles() {
    compositeDisposable.add(
      getArticlesUseCase.invoke()
        .observeOn(mainScheduler)
        .subscribe({
          _listOfArticles.value = it
        }, {})
    )
  }

  override fun dispose() {
    compositeDisposable.dispose()
  }
}