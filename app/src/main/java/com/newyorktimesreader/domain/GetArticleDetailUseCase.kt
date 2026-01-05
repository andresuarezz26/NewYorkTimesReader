package com.newyorktimesreader.domain

import com.newyorktimesreader.domain.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import com.newyorktimesreader.domain.repository.ArticlesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Represents the way this application gets a single article and all their information
 */
interface GetArticleDetailUseCase {
  operator fun invoke(articleId: String): Flow<Article>
}

class GetArticleDetailUseCaseImpl @Inject constructor(
  @param:IoScheduler private val ioDispatcher: CoroutineDispatcher,
  private val repository: ArticlesRepository
) : GetArticleDetailUseCase {
  override fun invoke(articleId: String): Flow<Article> {
    return repository.getArticleDetail(articleId).flowOn(ioDispatcher)
  }
}