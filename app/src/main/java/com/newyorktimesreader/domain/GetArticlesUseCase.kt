package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Getting the articles in the most efficient way regarding to the rules stablished by the
 * articles repository.
 */
interface GetArticlesUseCase {
  operator fun invoke(): Flow<List<Article>>
}

class GetArticlesUseCaseImpl @Inject constructor(
  @param:IoScheduler private val ioSchedulers: Scheduler,
  private val repository: ArticlesRepository,
): GetArticlesUseCase {

  override operator fun invoke(): Flow<List<Article>> {
    return repository.getArticles().flowOn(Dispatchers.IO)
  }
}