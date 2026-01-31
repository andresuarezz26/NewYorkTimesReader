package com.newyorktimesreader.data.source.remote.interceptors

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

/** This class adds the API key as a query parameter to every outgoing request.
 *
 */
class ApiKeyInterceptor @Inject constructor(
  private val apiKey: String
) : Interceptor {

  private val API_KEY_QUERY_NAME = "api-key" // CHANGE THIS if your API uses a different name

  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()

    val newUrl = originalRequest.url.newBuilder()
      .addQueryParameter(API_KEY_QUERY_NAME, apiKey)
      .build()

    // Build the new request with the modified URL
    val newRequest = originalRequest.newBuilder()
      .url(newUrl)
      .build()

    return chain.proceed(newRequest)
  }
}