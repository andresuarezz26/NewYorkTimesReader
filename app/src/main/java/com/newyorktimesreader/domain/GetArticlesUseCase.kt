package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Representatin of the way this application get a list of articles. The logic of where
 * this data comes from is delegated to the Repository.
 */
interface GetArticlesUseCase {
  operator fun invoke(): Single<List<Article>>
}


class GetArticlesUseCaseImpl @Inject constructor(
  @param:IoScheduler private val ioSchedulers: Scheduler,
  private val repository: ArticlesRepository,
): GetArticlesUseCase {

  override operator fun invoke(): Single<List<Article>> {
    return repository.getArticles().subscribeOn(ioSchedulers)
  }
}