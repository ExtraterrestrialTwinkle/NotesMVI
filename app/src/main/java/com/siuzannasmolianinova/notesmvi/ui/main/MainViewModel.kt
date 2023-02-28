package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.domain.usecase.LoadNotesUseCase
import com.siuzannasmolianinova.notesmvi.ui.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(loadNotesUseCase: LoadNotesUseCase) :
    BaseViewModel<MainScreenState, MainScreenEvent>() {
    private val reducer = MainReducer(
        initial = MainScreenState.initial(),
        useCase = loadNotesUseCase,
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
