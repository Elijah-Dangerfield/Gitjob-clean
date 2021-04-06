package com.dangerfield.gitjob.data.database.model

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallErrorHandler
import com.dangerfield.gitjob.domain.datasource.cache.CacheError
import com.dangerfield.gitjob.domain.model.DataState
import kotlinx.coroutines.TimeoutCancellationException

class CacheCallErrorHandlerImpl : CacheCallErrorHandler{
    override fun onError(throwable: Throwable): CacheError {
        return when (throwable) {

            is TimeoutCancellationException -> {
                CacheError.Timeout()
            }
            else -> {
                CacheError.Unknown()
            }
        }
    }
}