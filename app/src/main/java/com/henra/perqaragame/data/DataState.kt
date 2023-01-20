package com.henra.perqaragame.data

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Failed<T>(val error: Exception) : DataState<T>()
}

inline fun <T> DataState<T>.onSuccess(block: (T) -> Unit): DataState<T> {
    if (this is DataState.Success) block(data)
    return this
}

inline fun <T> DataState<T>.onFailed(block: (Exception) -> Unit): DataState<T> {
    if (this is DataState.Failed) block(error)
    return this
}

inline fun <T, R> DataState<T>.mapSuccess(block: (T) -> DataState<R>): DataState<R> {
    return when (this) {
        is DataState.Failed -> DataState.Failed(error)
        is DataState.Success -> block(data)
    }
}
