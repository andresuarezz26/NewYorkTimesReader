package com.newyorktimesreader.data.repositories.cachepolicy

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class CachePolicyImpl @Inject constructor(
  private val sharedPreferences: SharedPreferences
) : CachePolicy {
  override var cacheDurationMillis: Long = 5 * 60 * 1000L

  override fun isCacheValid(key: String): Boolean {
    val lastUpdate = sharedPreferences.getLong(key, 0L)
    if (lastUpdate == 0L) return false
    val currentTime = System.currentTimeMillis()
    val isExpired = currentTime - lastUpdate > cacheDurationMillis
    return !isExpired
  }

  override fun setCacheRefreshed(key: String) {
    sharedPreferences.edit {
      putLong(key, System.currentTimeMillis())
    }
  }
}