package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.usecase.BaseUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainReducer(
    initial: MainScreenState,
    private val viewModelScope: CoroutineScope,
    private val useCase: BaseUseCase<List<NoteModel>>
) : BaseReducer<MainScreenState, MainScreenEvent>(initial) {
    override fun reduce(oldState: MainScreenState, event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.Loading -> {
                viewModelScope.launch {
                    setState(oldState.copy(isLoading = true, data = emptyList()))
                    try {
                        useCase().let { notes ->
                            if (notes.isNotEmpty()) {
                                sendEvent(MainScreenEvent.ShowNotes(notes = notes))
                            } else {
                                sendEvent(MainScreenEvent.ShowError(message = "Notes list is empty"))
                            }
                        }
                    } catch (e: Exception) {
                        sendEvent(MainScreenEvent.ShowError(message = e.message ?: "Error!"))
                    }
                }
            }
            is MainScreenEvent.ShowNotes -> {
                setState(oldState.copy(isLoading = false, data = event.notes))
            }
            is MainScreenEvent.ShowError -> {
                setState(oldState.copy(isLoading = false, data = emptyList(), error = event.message))
            }
        }
    }
}