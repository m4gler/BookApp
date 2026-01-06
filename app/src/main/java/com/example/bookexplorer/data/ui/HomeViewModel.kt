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

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val books = repository.getBooks()
                _uiState.value = HomeUiState.Success(books)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Błąd pobierania danych")
            }
        }
    }
}
