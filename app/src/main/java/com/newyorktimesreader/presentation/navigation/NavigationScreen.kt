package com.newyorktimesreader.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavigationScreen {

  @Serializable
  object Main: NavigationScreen()
  @Serializable
  data class Detail(val id: String) : NavigationScreen()
}