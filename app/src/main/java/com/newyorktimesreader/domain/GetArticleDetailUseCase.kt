package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Represents the way this application gets a single article and all their information
 */
interface GetArticleDetailUseCase {
  operator fun invoke(articleId: String): Single<Article>
}

class GetArticleDetailUseCaseImpl @Inject constructor(
  @param:IoScheduler private val ioSchedulers: Scheduler,
  private val repository: ArticlesRepository
) : GetArticleDetailUseCase {
  override fun invoke(articleId: String): Single<Article> {
    return repository.getArticleDetail(articleId).subscribeOn(ioSchedulers)
  }
}