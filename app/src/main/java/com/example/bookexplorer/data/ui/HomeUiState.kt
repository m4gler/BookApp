package com.example.bookexplorer.data.ui

import com.example.bookexplorer.data.model.Book

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val books: List<Book>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
