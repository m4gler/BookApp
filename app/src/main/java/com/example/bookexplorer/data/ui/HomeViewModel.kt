package com.example.bookexplorer.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookexplorer.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState
    private val pageSize = 20
    private var currentOffset = 0

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                currentOffset = 0
                val books = repository.getBooks(limit = pageSize, offset = currentOffset)
                currentOffset = books.size
                _uiState.value = HomeUiState.Success(
                    books = books,
                    canLoadMore = books.size == pageSize
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Błąd pobierania danych: ${e.message ?: "nieznany błąd"}")
            }
        }
    }

    fun loadMore() {
        val state = _uiState.value
        if (state !is HomeUiState.Success) {
            return
        }
        if (state.isLoadingMore || !state.canLoadMore) {
            return
        }
        viewModelScope.launch {
            _uiState.value = state.copy(isLoadingMore = true, loadMoreError = null)
            try {
                val moreBooks = repository.getBooks(limit = pageSize, offset = currentOffset)
                currentOffset += moreBooks.size
                _uiState.value = state.copy(
                    books = state.books + moreBooks,
                    isLoadingMore = false,
                    canLoadMore = moreBooks.size == pageSize,
                    loadMoreError = null
                )
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoadingMore = false,
                    loadMoreError = "Błąd ładowania kolejnych danych"
                )
            }
        }
    }
}
