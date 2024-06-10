package com.mohaberabi.sibha.core.util


sealed interface AppResult<out D, out E : AppError> {


    data class Error<out E : AppError>(val error: E) : AppResult<Nothing, E>


    data class Done<out D>(val data: D) : AppResult<D, Nothing>
}

inline fun <T, E : AppError, R> AppResult<T, E>.map(map: (T) -> R): AppResult<R, E> {
    return when (this) {
        is AppResult.Error -> AppResult.Error(error)
        is AppResult.Done -> AppResult.Done(map(data))
    }
}

fun <T, E : AppError> AppResult<T, E>.asEmptyResult(): EmptyDataResult<E> {
    return map { Unit }
}

val emptyDoneResult = AppResult.Done(Unit)
typealias EmptyDataResult<E> = AppResult<Unit, E>