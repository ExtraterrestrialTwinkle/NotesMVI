package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.base.UiState

data class MainScreenState(
    val isLoading: Boolean,
    val data: List<NoteModel>,
    val error: String? = null
) : UiState {
    companion object {
        fun initial() = MainScreenState(isLoading = true, data = emptyList(), error = null)
    }
}
