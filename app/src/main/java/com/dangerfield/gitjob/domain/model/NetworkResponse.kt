package com.dangerfield.gitjob.domain.model

sealed class NetworkResponse<T>(
    val networkData: T? = null,
    val networkError: NetworkError? = null
) {
    class Success<T>(val data: T) : NetworkResponse<T>(data)
    class Error<T>(val error: NetworkError, val data: T? = null) : NetworkResponse<T>(data, error)
}