package com.newyorktimesreader.data.repositories.cachepolicy

/**
 * This is cache policy stored in shared preferences  policy to store the last time a service was updated
 * and check if the cache is still valid.
 */
interface CachePolicy {
  // Cache duration in milliseconds (e.g., 60 * 1000 for 1 minute)
  var cacheDurationMillis: Long

  /**
   * Checks if the cache for a specific key is still valid (not expired).
   * @param key The unique identifier for the cached data
   * @return True if the cache has not yet expired, False otherwise.
   */
  fun isCacheValid(key: String): Boolean

  /**
   * Updates the timestamp for a specific cache key, marking it as recently refreshed.
   * @param key The unique identifier for the cached data.
   */
  fun setCacheRefreshed(key: String)
}