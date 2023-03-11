package com.siuzannasmolianinova.notesmvi.ui.note

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.base.UiState

data class NoteScreenState(
    val isLoading: Boolean = true,
    val note: NoteModel? = null,
    val message: String? = null
) : UiState
