package com.example.bookexplorer.data.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
fun BookDetailScreen(
    workId: String,
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(workId) {
        viewModel.loadBookDetails(workId)
    }

    when (uiState) {
        is BookDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is BookDetailUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(
                    text = uiState.message,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = onBackClick) {
                    Text("Wstecz")
                }
            }
        }
        is BookDetailUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (uiState.bookDetail.cover_id != null) {
                    val imageUrl = "https://covers.openlibrary.org/b/id/${uiState.bookDetail.cover_id}-L.jpg"
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = uiState.bookDetail.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(300.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(300.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Brak okładki", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Text(
                    text = uiState.bookDetail.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
                if (uiState.bookDetail.authors != null && uiState.bookDetail.authors.isNotEmpty()) {
                    var authorsText = "Autorzy: "
                    for (i in uiState.bookDetail.authors.indices) {
                        authorsText += uiState.bookDetail.authors[i].name
                        if (i < uiState.bookDetail.authors.size - 1) {
                            authorsText += ", "
                        }
                    }
                    Text(
                        text = authorsText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (uiState.bookDetail.first_publish_year != null) {
                    Text(
                        text = "Rok publikacji: ${uiState.bookDetail.first_publish_year}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (uiState.bookDetail.number_of_pages_median != null) {
                    Text(
                        text = "Liczba stron: ${uiState.bookDetail.number_of_pages_median}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (!uiState.bookDetail.description.isNullOrBlank()) {
                    Text(
                        text = "Opis:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = uiState.bookDetail.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = { viewModel.toggleFavorite(workId) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text(if (uiState.isFavorite) "Usuń z ulubionych" else "Dodaj do ulubionych")
                }

                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Wstecz")
                }
            }
        }
    }
}
