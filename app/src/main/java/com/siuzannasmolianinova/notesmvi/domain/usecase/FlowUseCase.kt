package com.siuzannasmolianinova.notesmvi.domain.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<T> {

    operator fun invoke(): Flow<Result<T>> =
        performAction()
            .catch {
                Log.e("error", it.message, it)
                emit(Result.failure(it))
            }
            .flowOn(Dispatchers.IO)

    protected abstract fun performAction(): Flow<Result<T>>
}
