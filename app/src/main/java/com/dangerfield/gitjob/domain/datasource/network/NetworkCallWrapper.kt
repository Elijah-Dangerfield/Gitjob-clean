package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.NetworkError
import com.dangerfield.gitjob.domain.model.DataState
import com.dangerfield.gitjob.domain.datasource.network.NetworkConstants.NETWORK_TIMEOUT
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class NetworkCallWrapper constructor(
    private val networkCallErrorHandler: NetworkCallErrorHandler
) {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T?
    ): DataState<T?, NetworkError> {
        return withContext(dispatcher) {
            try {
                // throws TimeoutCancellationException
                withTimeout(NETWORK_TIMEOUT) {
                    val success: DataState<T?, NetworkError> =
                        DataState.Success(
                            apiCall.invoke()
                        )
                    success
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                val error: DataState<T?, NetworkError> =
                    DataState.Error(
                        error = networkCallErrorHandler.onError(throwable)
                    )
                error
            }
        }
    }
}
