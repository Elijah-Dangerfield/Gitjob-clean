package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.DataState
import com.dangerfield.gitjob.domain.datasource.cache.DatabaseConstants.CACHE_TIMEOUT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

//TODO READ ME
//maybe I should interface out this call wrapper thing for individual platforms to use their
//networking tools to do a bit more network handling

class CacheCallWrapper(
    private val cacheCallErrorHandler: CacheCallErrorHandler
) {

    suspend fun <T> safeCacheCall(
        dispatcher: CoroutineDispatcher,
        cacheCall: suspend () -> T?
    ): DataState<T?, CacheError> {
        return withContext(dispatcher) {
            try {
                // throws TimeoutCancellationException
                withTimeout(CACHE_TIMEOUT) {
                    val result: DataState<T?, CacheError> =
                        DataState.Success(
                            cacheCall.invoke()
                        )
                    result
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                val result: DataState<T?, CacheError> =
                    DataState.Error(
                        error = cacheCallErrorHandler.onError(throwable)
                    )
                result
            }
        }
    }
}