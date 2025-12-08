package com.newyorktimesreader.data.di

import com.newyorktimesreader.data.source.remote.DiscoverServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
      .setLevel(
        HttpLoggingInterceptor.Level.BODY
      )

    return OkHttpClient.Builder().apply {
      cache(null)
      addInterceptor(loggingInterceptor)
    }.build()
  }

  @Provides
  @Singleton
  fun providesConverterFactory(): Converter.Factory = GsonConverterFactory.create()

  @Provides
  @Singleton
  fun providesRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory
  ): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.nytimes.com/svc/")
    .client(okHttpClient)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .addConverterFactory(converterFactory)
    .build()

  @Provides
  @Singleton
  fun providesApiService(retrofit: Retrofit): DiscoverServiceApi = retrofit.create(DiscoverServiceApi::class.java)
}