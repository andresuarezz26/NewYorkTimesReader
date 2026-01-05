package com.newyorktimesreader.domain.di

import javax.inject.Qualifier

// Qualifier for the Android Main Thread Scheduler
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScheduler

// Qualifier for the RxJava IO Scheduler
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoScheduler