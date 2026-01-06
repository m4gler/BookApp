package com.example.bookexplorer.data.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.bookexplorer.data.ui.HomeUiState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onBookClick: (String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Book Explorer",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onFavoritesClick) {
                Text("Ulubione")
            }
        }

        when (uiState) {
            is HomeUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is HomeUiState.Error -> {
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
                    Button(onClick = { viewModel.loadBooks() }) {
                        Text("Spróbuj ponownie")
                    }
                }
            }
            is HomeUiState.Success -> {
                if (uiState.books.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text("Brak książek")
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

@Composable
fun BookItem(
    book: com.example.bookexplorer.data.model.Book,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (book.cover_id != null) {
            val imageUrl = "https://covers.openlibrary.org/b/id/${book.cover_id}-M.jpg"
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = book.title,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Brak okładki", style = MaterialTheme.typography.bodySmall)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium
            )
            if (book.authors != null && book.authors.isNotEmpty()) {
                var authorsText = ""
                for (i in book.authors.indices) {
                    authorsText += book.authors[i].name
                    if (i < book.authors.size - 1) {
                        authorsText += ", "
                    }
                }
                Text(
                    text = authorsText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
