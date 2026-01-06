package com.example.bookexplorer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.bookexplorer.data.api.RetrofitInstance
import com.example.bookexplorer.data.preferences.FavoritesManager
import com.example.bookexplorer.data.repository.BookRepository
import com.example.bookexplorer.data.ui.BookDetailViewModel
import com.example.bookexplorer.data.ui.FavoritesViewModel
import com.example.bookexplorer.data.ui.HomeViewModel
import com.example.bookexplorer.data.ui.navigation.AppNavGraph
import com.example.bookexplorer.ui.theme.BookExplorerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitInstance.api
        val repository = BookRepository(api)
        val favoritesManager = FavoritesManager(this)
        
        val homeViewModel = HomeViewModel(repository)
        val bookDetailViewModel = BookDetailViewModel(repository, favoritesManager)
        val favoritesViewModel = FavoritesViewModel(repository, favoritesManager)

        setContent {
            BookExplorerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        bookDetailViewModel = bookDetailViewModel,
                        favoritesViewModel = favoritesViewModel
                    )
                }
            }
        }
    }
}

