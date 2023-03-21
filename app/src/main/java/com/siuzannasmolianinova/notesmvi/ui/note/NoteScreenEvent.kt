package com.siuzannasmolianinova.notesmvi.ui.note

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.base.UiEvent

sealed class NoteScreenEvent : UiEvent {
    object Loading : NoteScreenEvent()
    data class ShowNote(val note: NoteModel) : NoteScreenEvent()
    data class OnUpdateTitle(val title: String) : NoteScreenEvent()
    data class SaveNote(val note: NoteModel) : NoteScreenEvent()
    data class ShowError(val message: String?) : NoteScreenEvent()
}