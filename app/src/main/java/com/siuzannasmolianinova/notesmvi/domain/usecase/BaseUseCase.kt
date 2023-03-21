package com.siuzannasmolianinova.notesmvi.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<T, A> {
    suspend operator fun invoke(args: A): T = withContext(Dispatchers.IO) { run(args) }
    protected abstract suspend fun run(args: A): T
}