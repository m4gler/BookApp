package com.example.bookexplorer.data.ui

import com.example.bookexplorer.data.model.Book

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val books: List<Book>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true,
        val loadMoreError: String? = null
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
