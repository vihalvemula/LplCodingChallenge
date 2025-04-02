package com.tc.lplcodingchallenge.presentation

sealed class UiState<out T> {
    data class SUCCESS<out T>(val data:T): UiState<T>()
    data class ERROR(val msg:Exception): UiState<Nothing>()
    object LOADING: UiState<Nothing>()
}


