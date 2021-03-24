package com.dangerfield.gitjob.domain.util

import com.dangerfield.gitjob.domain.util.DatabaseConstants.CACHE_TIMEOUT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

//TODO READ ME
//maybe I should interface out this call wrapper thing for individual platforms to use their
//networking tools to do a bit more network handling

class CacheCallWrapper {

    companion object {
        suspend fun <T> safeCacheCall(
            dispatcher: CoroutineDispatcher,
            cacheCall: suspend () -> T?
        ): DataState<T?, CacheError> {
            return withContext(dispatcher) {
                try {
                    // throws TimeoutCancellationException
                    withTimeout(CACHE_TIMEOUT){
                        val result : DataState<T?, CacheError> = DataState.Success(cacheCall.invoke())
                        result
                    }
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                    when (throwable) {

                        is TimeoutCancellationException -> {
                            val error : DataState<T?, CacheError> = DataState.Error(error = CacheError.Timeout())
                            error
                        }
                        else -> {
                            val error : DataState<T?, CacheError> = DataState.Error(error = CacheError.Unknown())
                            error
                        }
                    }
                }
            }
        }

        sealed class CacheError(val message: String) {
            class Unknown: CacheError("Unknown cache error")
            class Timeout: CacheError("Cache timeout")
        }
    }
}