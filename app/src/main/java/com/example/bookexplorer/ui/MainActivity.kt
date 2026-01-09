package com.example.bookexplorer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.bookexplorer.data.api.RetrofitInstance
import com.example.bookexplorer.data.preferences.FavoritesManager
import com.example.bookexplorer.data.preferences.ThemeManager
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
        val themeManager = ThemeManager(this)
        
        val homeViewModel = HomeViewModel(repository)
        val bookDetailViewModel = BookDetailViewModel(repository, favoritesManager)
        val favoritesViewModel = FavoritesViewModel(repository, favoritesManager)

        setContent {
            val systemDarkMode = isSystemInDarkTheme()
            var darkModePreference by remember {
                mutableStateOf(themeManager.getDarkModePreference())
            }
            val resolvedDarkMode = darkModePreference ?: systemDarkMode

            BookExplorerTheme(darkTheme = resolvedDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        bookDetailViewModel = bookDetailViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onToggleDarkMode = {
                            val newValue = !resolvedDarkMode
                            darkModePreference = newValue
                            themeManager.setDarkMode(newValue)
                        },
                        isDarkMode = resolvedDarkMode
                    )
                }
            }
        }
    }
}
