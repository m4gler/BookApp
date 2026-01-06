package com.example.bookexplorer.data.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookexplorer.data.ui.BookDetailScreen
import com.example.bookexplorer.data.ui.BookDetailViewModel
import com.example.bookexplorer.data.ui.FavoritesScreen
import com.example.bookexplorer.data.ui.FavoritesViewModel
import com.example.bookexplorer.data.ui.HomeScreen
import com.example.bookexplorer.data.ui.HomeViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    bookDetailViewModel: BookDetailViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = homeViewModel,
                onBookClick = { bookId ->
                    val encodedId = java.net.URLEncoder.encode(bookId, "UTF-8")
                    navController.navigate("${Routes.DETAILS}/$encodedId")
                },
                onFavoritesClick = {
                    navController.navigate(Routes.FAVORITES)
                }
            )
        }

        composable(
            route = "${Routes.DETAILS}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedId = backStackEntry.arguments?.getString("bookId") ?: ""
            val bookId = java.net.URLDecoder.decode(encodedId, "UTF-8")
            BookDetailScreen(
                workId = bookId,
                viewModel = bookDetailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.FAVORITES) {
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onBookClick = { bookId ->
                    val encodedId = java.net.URLEncoder.encode(bookId, "UTF-8")
                    navController.navigate("${Routes.DETAILS}/$encodedId")
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
