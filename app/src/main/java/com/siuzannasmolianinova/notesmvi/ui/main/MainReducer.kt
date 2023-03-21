package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.domain.usecase.DeleteNoteUseCase
import com.siuzannasmolianinova.notesmvi.domain.usecase.LoadNotesUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainReducer(
    initial: MainScreenState,
    private val viewModelScope: CoroutineScope,
    private val loadNotesUseCase: LoadNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : BaseReducer<MainScreenState, MainScreenEvent>(initial) {
    override fun reduce(oldState: MainScreenState, event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.Loading -> {
                setState(oldState.copy(isLoading = true, data = emptyList(), error = null))

                loadNotesUseCase().onEach { result ->
                    result.fold(
                        onSuccess = { notes ->
                            if (notes.isNotEmpty()) {
                                sendEvent(MainScreenEvent.ShowNotes(notes = notes))
                            } else {
                                sendEvent(MainScreenEvent.ShowNotes(notes = notes))
                                sendEvent(MainScreenEvent.ShowError(message = "Notes list is empty"))
                            }
                        },
                        onFailure = {
                            sendEvent(MainScreenEvent.ShowError(message = "Loading error"))
                        }
                    )
                }.launchIn(viewModelScope)
            }
            is MainScreenEvent.ShowNotes -> {
                setState(oldState.copy(isLoading = false, data = event.notes, error = null))
            }
            is MainScreenEvent.ShowError -> {
                setState(
                    oldState.copy(
                        isLoading = false,
                        data = emptyList(),
                        error = event.message
                    )
                )
            }
            is MainScreenEvent.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        deleteNoteUseCase(event.id)
                    } catch (e: Exception) {
                        sendEvent(MainScreenEvent.ShowError(message = e.message ?: "Delete error!"))
                    }
                }
            }
        }
    }
}
