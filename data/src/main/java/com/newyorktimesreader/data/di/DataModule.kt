  package com.newyorktimesreader.data.di


  import android.content.Context
  import android.content.SharedPreferences
  import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicy
  import com.newyorktimesreader.data.repositories.cachepolicy.CachePolicyImpl
  import dagger.Module
  import dagger.Provides
  import dagger.hilt.InstallIn
  import dagger.hilt.android.qualifiers.ApplicationContext
  import dagger.hilt.components.SingletonComponent
  import javax.inject.Singleton

  @Module
  @InstallIn(SingletonComponent::class)
  class DataModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
      return context.getSharedPreferences("cache_policy_prefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideCachePolicy(sharedPreferences: SharedPreferences): CachePolicy {
      return CachePolicyImpl(sharedPreferences)
    }

  }