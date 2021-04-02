package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.NetworkError
import com.dangerfield.gitjob.domain.model.DataState
import com.dangerfield.gitjob.domain.datasource.network.NetworkConstants.NETWORK_TIMEOUT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.IOException

class NetworkCallWrapper {
    companion object {
        suspend fun <T> safeApiCall(
            dispatcher: CoroutineDispatcher,
            apiCall: suspend () -> T?
        ): DataState<T?, NetworkError> {
            return withContext(dispatcher) {
                try {
                    // throws TimeoutCancellationException
                    withTimeout(NETWORK_TIMEOUT){
                        val success : DataState<T?, NetworkError> =
                            DataState.Success(
                                apiCall.invoke()
                            )
                        success
                    }
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()

                    when (throwable) {
                        is TimeoutCancellationException -> {
                            val error : DataState<T?, NetworkError> =
                                DataState.Error(
                                    error = NetworkError.Timeout()
                                )
                            error
                        }
                        is IOException -> {
                            val error : DataState<T?, NetworkError> =
                                DataState.Error(
                                    error = NetworkError.IO(
                                        throwable.localizedMessage
                                    )
                                )
                            error
                        }
                        else -> {
                            val error : DataState<T?, NetworkError> =
                                DataState.Error(
                                    error = NetworkError.Unknown()
                                )
                            error
                        }
                    }
                }
            }
        }
    }
}
