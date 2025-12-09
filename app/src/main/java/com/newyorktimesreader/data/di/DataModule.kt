package com.newyorktimesreader.data.di


import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicy
import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicyImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

  @Singleton
  @Provides
  fun provideCachePolicy(): CachePolicy = CachePolicyImpl()

}