package com.dangerfield.gitjob.domain.util


sealed class DataState<T, E>(
    val stateData: T? = null,
    val stateError: E? = null,
    val stateException: Exception? = null
) {
    class Success<T,E>(val data: T) : DataState<T,E>(data)
    class Error<T,E>(data: T? = null, val error: E, exception: Exception? = null) : DataState<T,E>(data, error, exception)
}