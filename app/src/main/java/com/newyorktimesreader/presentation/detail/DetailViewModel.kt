package com.newyorktimesreader.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.newyorktimesreader.di.MainScheduler
import com.newyorktimesreader.domain.GetArticleDetailUseCase
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val getArticleDetailUseCase: GetArticleDetailUseCase,
  @MainScheduler private val mainScheduler: Scheduler,
  savedStateHandle: SavedStateHandle
  ): BaseViewModel(){

  private val articleId: String = checkNotNull(savedStateHandle["id"])
  private var compositeDisposable = CompositeDisposable()

  private val _articleDetail = MutableLiveData<Article>()
  val articleDetail : LiveData<Article> = _articleDetail

  init {
    getArticleDetail()
  }

  private fun getArticleDetail() {
    compositeDisposable.add(
      getArticleDetailUseCase.invoke(articleId)
        .observeOn(mainScheduler).subscribe({
          _articleDetail.value = it
        }, {

        }))
  }

  override fun dispose() {
    compositeDisposable.dispose()
  }
}