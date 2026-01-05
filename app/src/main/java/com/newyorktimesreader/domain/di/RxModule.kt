package com.newyorktimesreader.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class) // Scoped for ViewModels
object RxModule {

  @MainScheduler
  @Provides
  fun provideMainScheduler(): CoroutineDispatcher = Dispatchers.Main

  @IoScheduler
  @Provides
  fun provideIOScheduler(): CoroutineDispatcher = Dispatchers.IO
}