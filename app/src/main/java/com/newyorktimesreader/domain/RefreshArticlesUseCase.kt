package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Business logic for pulling the latest articles from the network and storing it in the database.
 */
interface RefreshArticlesUseCase {
  operator fun invoke(): Single<List<Article>>
}

class RefreshArticlesUseCaseImpl @Inject constructor(
  @param:IoScheduler private val ioSchedulers: Scheduler,
  private val repository: ArticlesRepository,
): RefreshArticlesUseCase {

  override operator fun invoke(): Single<List<Article>> {
    return repository.refreshArticles().subscribeOn(ioSchedulers)
  }
}