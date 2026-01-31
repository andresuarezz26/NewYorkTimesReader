package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Business logic for pulling the latest articles from the network and storing it in the database.
 */
interface RefreshArticlesUseCase {
  operator fun invoke(): Flow<List<Article>>
}

class RefreshArticlesUseCaseImpl @Inject constructor(
  @param:IoScheduler private val ioDispatcher: CoroutineDispatcher,
  private val repository: ArticlesRepository,
) : RefreshArticlesUseCase {

  override operator fun invoke(): Flow<List<Article>> {
    return repository.refreshArticles().flowOn(ioDispatcher)
  }
}