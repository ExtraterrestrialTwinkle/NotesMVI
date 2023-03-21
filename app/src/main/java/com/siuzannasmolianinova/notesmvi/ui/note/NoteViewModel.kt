package com.siuzannasmolianinova.notesmvi.ui.note

import androidx.lifecycle.SavedStateHandle
import com.siuzannasmolianinova.notesmvi.domain.usecase.LoadNoteUseCase
import com.siuzannasmolianinova.notesmvi.domain.usecase.SaveNoteUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    loadNoteUseCase: LoadNoteUseCase,
    saveNoteUseCase: SaveNoteUseCase,
    private val savedState: SavedStateHandle
) : BaseViewModel<NoteScreenState, NoteScreenEvent>() {

    private val reducer = NoteReducer(
        initial = NoteScreenState.initial(),
        loadUseCase = loadNoteUseCase,
        saveUseCase = saveNoteUseCase,
        id = savedState.get<Long>("id") ?: 0,
        viewModelScope = scope
    )

    override val state: StateFlow<NoteScreenState>
        get() = reducer.state

    init {
        sendEvent(NoteScreenEvent.Loading)
    }

    fun sendEvent(event: NoteScreenEvent) {
        reducer.sendEvent(event)
    }
}
