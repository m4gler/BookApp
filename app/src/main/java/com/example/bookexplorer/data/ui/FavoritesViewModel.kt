package com.example.bookexplorer.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookexplorer.data.model.Book
import com.example.bookexplorer.data.preferences.FavoritesManager
import com.example.bookexplorer.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: BookRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState

    fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = FavoritesUiState.Loading
            try {
                val favoriteIds = favoritesManager.getFavorites()
                
                if (favoriteIds.isEmpty()) {
                    _uiState.value = FavoritesUiState.Empty
                } else {
                    val books = mutableListOf<Book>()
                    
                    for (workId in favoriteIds) {
                        val bookDetail = repository.getBookDetails(workId)
                        if (bookDetail != null) {
                            val book = Book(
                                key = bookDetail.key,
                                title = bookDetail.title,
                                cover_id = bookDetail.cover_id,
                                authors = bookDetail.authors,
                                first_publish_year = bookDetail.first_publish_year,
                                number_of_pages_median = bookDetail.number_of_pages_median
                            )
                            books.add(book)
                        }
                    }
                    
                    _uiState.value = FavoritesUiState.Success(books)
                }
            } catch (e: Exception) {
                _uiState.value = FavoritesUiState.Error("Błąd pobierania ulubionych")
            }
        }
    }
}

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()
    object Empty : FavoritesUiState()
    data class Success(val books: List<Book>) : FavoritesUiState()
    data class Error(val message: String) : FavoritesUiState()
}

