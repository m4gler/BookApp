package com.example.bookexplorer.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookexplorer.data.model.BookDetail
import com.example.bookexplorer.data.preferences.FavoritesManager
import com.example.bookexplorer.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailUiState>(BookDetailUiState.Loading)
    val uiState: StateFlow<BookDetailUiState> = _uiState

    fun loadBookDetails(workId: String) {
        viewModelScope.launch {
            _uiState.value = BookDetailUiState.Loading
            try {
                val bookDetail = repository.getBookDetails(workId)
                if (bookDetail != null) {
                    val isFavorite = favoritesManager.isFavorite(workId)
                    _uiState.value = BookDetailUiState.Success(bookDetail, isFavorite)
                } else {
                    _uiState.value = BookDetailUiState.Error("Nie udało się pobrać szczegółów książki")
                }
            } catch (e: Exception) {
                _uiState.value = BookDetailUiState.Error("Błąd pobierania danych")
            }
        }
    }

    fun toggleFavorite(workId: String) {
        val currentState = _uiState.value
        if (currentState is BookDetailUiState.Success) {
            if (currentState.isFavorite) {
                favoritesManager.removeFavorite(workId)
            } else {
                favoritesManager.addFavorite(workId)
            }
            _uiState.value = currentState.copy(isFavorite = !currentState.isFavorite)
        }
    }
}

sealed class BookDetailUiState {
    object Loading : BookDetailUiState()
    data class Success(val bookDetail: BookDetail, val isFavorite: Boolean) : BookDetailUiState()
    data class Error(val message: String) : BookDetailUiState()
}

