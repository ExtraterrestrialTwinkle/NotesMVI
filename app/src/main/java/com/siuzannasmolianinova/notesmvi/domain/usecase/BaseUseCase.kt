package com.siuzannasmolianinova.notesmvi.domain.usecase

abstract class BaseUseCase<T> {
    abstract suspend operator fun invoke(): T
}