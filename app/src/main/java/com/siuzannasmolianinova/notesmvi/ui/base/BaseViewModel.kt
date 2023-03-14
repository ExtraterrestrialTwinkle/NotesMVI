package com.siuzannasmolianinova.notesmvi.ui.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<S : UiState, in E : UiEvent> : ViewModel() {

    abstract val state: Flow<S>

    private val job = SupervisorJob()

    protected val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        scope.cancel()
    }
}
