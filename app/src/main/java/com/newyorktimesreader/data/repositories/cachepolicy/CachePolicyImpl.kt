package com.newyorktimesreader.data.repositories.cachepolicy

import javax.inject.Inject

class CachePolicyImpl @Inject constructor() : CachePolicy {

  override var cacheDurationMillis: Long = 60 * 1000L

  private val lastUpdateTime: MutableMap<String, Long> = mutableMapOf()

  override fun isCacheValid(key: String): Boolean {
    val lastUpdate = lastUpdateTime[key] ?: return false

    val currentTime = System.currentTimeMillis()

    val isExpired = currentTime - lastUpdate > cacheDurationMillis

    return !isExpired
  }

  override fun setCacheRefreshed(key: String) {
    lastUpdateTime[key] = System.currentTimeMillis()
  }
}