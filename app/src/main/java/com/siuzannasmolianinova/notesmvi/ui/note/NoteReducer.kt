package com.siuzannasmolianinova.notesmvi.ui.note

import com.siuzannasmolianinova.notesmvi.domain.usecase.LoadNoteUseCase
import com.siuzannasmolianinova.notesmvi.domain.usecase.SaveNoteUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteReducer(
    initial: NoteScreenState,
    private val viewModelScope: CoroutineScope,
    private val loadUseCase: LoadNoteUseCase,
    private val saveUseCase: SaveNoteUseCase,
    private val id: Long
) : BaseReducer<NoteScreenState, NoteScreenEvent>(initial) {
    override fun reduce(oldState: NoteScreenState, event: NoteScreenEvent) {
        when (event) {
            is NoteScreenEvent.Loading -> {
                viewModelScope.launch {
                    setState(oldState.copy(isLoading = true, note = null, error = null))
                    try {
                        loadUseCase(id).let { note ->
                            if (note == null) {
                                sendEvent(NoteScreenEvent.ShowError(message = "Note is not exists"))
                            } else {
                                sendEvent(NoteScreenEvent.ShowNote(note = note))
                            }
                        }
                    } catch (e: Exception) {
                        sendEvent(NoteScreenEvent.ShowError(message = e.message ?: "Error!"))
                    }
                }
            }
            is NoteScreenEvent.OnUpdateTitle -> {
                oldState.note?.let { sendEvent(NoteScreenEvent.SaveNote(it.copy(title = event.title))) }
            }
            is NoteScreenEvent.SaveNote -> {
                viewModelScope.launch {
                    setState(oldState.copy(isLoading = true, note = null, error = null))
                    try {
                        saveUseCase(event.note)
                    } catch (e: Exception) {
                        sendEvent(NoteScreenEvent.ShowError(message = e.message ?: "Can't save the note!"))
                    } finally {
                        sendEvent(NoteScreenEvent.ShowNote(event.note))
                    }
                }
            }
            is NoteScreenEvent.ShowNote -> {
                setState(oldState.copy(isLoading = false, note = event.note, error = null))
            }
            is NoteScreenEvent.ShowError -> {
                setState(
                    oldState.copy(
                        isLoading = false,
                        note = null,
                        error = event.message
                    )
                )
            }
        }
    }
}
