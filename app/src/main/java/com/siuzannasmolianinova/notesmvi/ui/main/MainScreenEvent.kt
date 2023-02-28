package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.base.UiEvent

sealed class MainScreenEvent : UiEvent {
    object Loading : MainScreenEvent()
    data class ShowNotes(val notes: List<NoteModel>) : MainScreenEvent()
    data class ShowError(val message: String?) : MainScreenEvent()
}


