package com.siuzannasmolianinova.notesmvi.ui.note

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.usecase.BaseUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseReducer
import com.siuzannasmolianinova.notesmvi.ui.main.MainScreenEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteReducer(
    initial: NoteScreenState,
    private val viewModelScope: CoroutineScope,
    private val useCase: BaseUseCase<NoteModel?>
) : BaseReducer<NoteScreenState, NoteScreenEvent>(initial) {
    override fun reduce(oldState: NoteScreenState, event: NoteScreenEvent) {
        when (event) {
            is NoteScreenEvent.Loading -> {
                viewModelScope.launch {
                    setState(oldState.copy(isLoading = true, note = null))
                    try {
                        useCase().let { note ->
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
            is NoteScreenEvent.ShowNote -> {
                setState(oldState.copy(isLoading = false, note = event.note))
            }
            is NoteScreenEvent.ShowError -> {
                setState(
                    oldState.copy(
                        isLoading = false,
                        note = null,
                        message = event.message
                    )
                )
            }
        }
    }
}
