package com.newyorktimesreader.domain.di

import com.newyorktimesreader.domain.GetArticleDetailUseCase
import com.newyorktimesreader.domain.GetArticleDetailUseCaseImpl
import com.newyorktimesreader.domain.GetArticlesUseCase
import com.newyorktimesreader.domain.GetArticlesUseCaseImpl
import com.newyorktimesreader.domain.RefreshArticlesUseCase
import com.newyorktimesreader.domain.RefreshArticlesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

  @Binds
  abstract fun provideGetArticlesUseCase(
    useCase: GetArticlesUseCaseImpl
  ): GetArticlesUseCase

  @Binds
  abstract fun provideGetArticleDetailUseCase(
    useCase: GetArticleDetailUseCaseImpl
  ): GetArticleDetailUseCase

  @Binds
  abstract fun provideRefreshArticlesUseCase(
    useCase: RefreshArticlesUseCaseImpl
  ): RefreshArticlesUseCase
}