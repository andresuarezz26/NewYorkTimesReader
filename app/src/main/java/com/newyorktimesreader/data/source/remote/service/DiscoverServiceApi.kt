package com.newyorktimesreader.data.source.remote.service

import com.newyorktimesreader.data.source.remote.response.DiscoverServiceResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to fetch the article search official New York Times API
 */
interface DiscoverServiceApi {

  companion object SearchApiUrl {

    const val QUERY = "q"
    const val GET_ARTICLES = "search/v2/articlesearch.json"
  }

  @GET(GET_ARTICLES)
  suspend fun fetchArticles(
    @Query(QUERY) query: String = "tech"
  ): DiscoverServiceResponse
}