package com.example.bookexplorer.data.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onBookClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Ulubione",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        when (uiState) {
            is FavoritesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is FavoritesUiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Brak ulubionych książek")
                }
            }
            is FavoritesUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.message)
                }
            }
            is FavoritesUiState.Success -> {
                if (uiState.books.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Brak ulubionych książek")
                    }
                } else {
                    LazyColumn {
                        items(uiState.books) { book ->
                            BookItem(
                                book = book,
                                onClick = { onBookClick(book.key) }
                            )
                        }
                    }
                }
            }
        }
    }
}
