package com.migueldk17.breeze.uistate

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val exception: String) : UiState<Nothing>()
    object Empty: UiState<Nothing>()
}