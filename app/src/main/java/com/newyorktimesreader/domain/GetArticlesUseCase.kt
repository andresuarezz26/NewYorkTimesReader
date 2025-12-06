package com.newyorktimesreader.domain

import com.newyorktimesreader.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetArticlesUseCase {
  operator fun invoke(): Single<List<Article>>
}

class GetArticlesUseCaseImpl @Inject constructor(
  @IoScheduler private val ioSchedulers: Scheduler
): GetArticlesUseCase {

  override operator fun invoke(): Single<List<Article>> {
    return Single.just(
      listOf(
        Article(
          title = "Article 1",
          author = "Description 1",
          imageUrl = "https://via.placeholder.com/150"
        ),
        Article(
          title = "Article 2",
          author = "Description 2",
          imageUrl = "https://via.placeholder.com/150"
        ))).subscribeOn(ioSchedulers)
  }
}