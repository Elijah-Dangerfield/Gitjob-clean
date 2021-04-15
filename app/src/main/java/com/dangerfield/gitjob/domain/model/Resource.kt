package com.dangerfield.gitjob.domain.model



sealed class Resource<T, E>(
    val resourceData: T? = null,
    val resourceError: E? = null
) {
    class Success<T,E>(val data: T) : Resource<T, E>(data)
    class Loading<T,E>(val data: T? = null) : Resource<T, E>(data)
    class Error<T, E>(val error: E, val data: T? = null) : Resource<T, E>(data, error)
}