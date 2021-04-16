package com.dangerfield.gitjob.data.datasources.cache

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.CacheError
import com.dangerfield.gitjob.domain.datasource.cache.DatabaseConstants.CACHE_TIMEOUT
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class CacheCallWrapperImpl : CacheCallWrapper {

    override suspend fun <T> safeCacheCall(
        dispatcher: CoroutineDispatcher,
        cacheCall: suspend () -> T?
    ): CacheResponse<T?> {
        return withContext(dispatcher) {
            try {
                // throws TimeoutCancellationException
                withTimeout(CACHE_TIMEOUT) {
                    val result: CacheResponse<T?> =
                        CacheResponse.Success(
                            cacheCall.invoke()
                        )
                    result
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                val error = when (throwable) {

                    is TimeoutCancellationException -> {
                        CacheError.Timeout()
                    }
                    else -> {
                        CacheError.Unknown()
                    }
                }
                val result: CacheResponse<T?> =
                    CacheResponse.Error(
                        error = error
                    )
                result
            }
        }
    }
}