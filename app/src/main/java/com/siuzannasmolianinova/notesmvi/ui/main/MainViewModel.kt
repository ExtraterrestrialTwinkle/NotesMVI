package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.domain.usecase.DeleteNoteUseCase
import com.siuzannasmolianinova.notesmvi.domain.usecase.LoadNotesUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    loadNotesUseCase: LoadNotesUseCase,
    deleteNoteUseCase: DeleteNoteUseCase
) : BaseViewModel<MainScreenState, MainScreenEvent>() {

    private val reducer = MainReducer(
        initial = MainScreenState.initial(),
        loadNotesUseCase = loadNotesUseCase,
        deleteNoteUseCase = deleteNoteUseCase,
        viewModelScope = scope
    )

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    init {
        sendEvent(MainScreenEvent.Loading)
    }

    fun sendEvent(event: MainScreenEvent) {
        reducer.sendEvent(event)
    }
}
