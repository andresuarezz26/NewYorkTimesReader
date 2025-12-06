package com.newyorktimesreader.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.newyorktimesreader.presentation.detail.DetailScreen
import com.newyorktimesreader.presentation.home.HomeScreen

@Composable
fun NavigationStack() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = NavigationScreen.Main) {
    composable<NavigationScreen.Main> {
      HomeScreen(// 💡 CHANGE: Implement the navigation logic here
        onNavigateToDetail = { id ->
          navController.navigate(NavigationScreen.Detail(id))
        })
    }
    composable<NavigationScreen.Detail>
       {
         val args = it.toRoute<NavigationScreen.Detail>()
         DetailScreen(id = args.id)
    }
  }
}