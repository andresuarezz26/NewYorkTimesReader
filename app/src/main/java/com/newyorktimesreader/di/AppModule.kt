package com.newyorktimesreader.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

@Module
@InstallIn(ViewModelComponent::class) // Scoped for ViewModels
object RxModule {

  @MainScheduler
  @Provides
  fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

  @IoScheduler
  @Provides
  fun provideIOScheduler(): Scheduler = io.reactivex.rxjava3.schedulers.Schedulers.io()
}