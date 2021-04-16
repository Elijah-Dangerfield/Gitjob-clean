package com.dangerfield.gitjob.domain.model.util

import com.dangerfield.gitjob.domain.datasource.cache.CacheError

sealed class CacheResponse<T>(
    val cacheData: T? = null,
    val cacheError: CacheError? = null
) {
    class Success<T>(val data: T) : CacheResponse<T>(data)
    class Error<T>(val error: CacheError, val data: T? = null) : CacheResponse<T>(data, error)
}