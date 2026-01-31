package com.newyorktimesreader.data.di

import com.newyorktimesreader.data.repositories.ArticlesRepositoryImpl
import com.newyorktimesreader.domain.repository.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun provideArticlesRepository(
    articlesRepository: ArticlesRepositoryImpl
  ): ArticlesRepository
}