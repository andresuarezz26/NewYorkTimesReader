package com.newyorktimesreader.data.source.remote.service

import com.newyorktimesreader.data.source.remote.response.DiscoverServiceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverServiceApi {

  companion object SearchApiUrl {

    const val QUERY = "q"
    const val API_KEY = "api-key"
    const val GET_ARTICLES = "search/v2/articlesearch.json"
  }

  @GET(GET_ARTICLES)
  fun fetchArticles(
    @Query(QUERY) query: String = "tech",
    @Query(API_KEY) apiKey: String = "SizJDA5sr6gsQTZqZKbbpXtqrAnDmhiv"
  ): Single<DiscoverServiceResponse>
}