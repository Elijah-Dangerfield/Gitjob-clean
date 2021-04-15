package com.dangerfield.gitjob.data.datasources.network

import com.dangerfield.gitjob.domain.datasource.network.NetworkCallWrapper
import com.dangerfield.gitjob.domain.model.NetworkError
import com.dangerfield.gitjob.domain.datasource.network.NetworkConstants.NETWORK_TIMEOUT
import com.dangerfield.gitjob.domain.model.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

class NetworkCallWrapperImpl : NetworkCallWrapper {

   override suspend fun <T> safeNetworkCall(
       dispatcher: CoroutineDispatcher,
       networkCall: suspend () -> T
   ): NetworkResponse<T> {
        return withContext(dispatcher) {
            try {
                // throws TimeoutCancellationException
                withTimeout(NETWORK_TIMEOUT) {
                    val result: NetworkResponse<T> =
                        NetworkResponse.Success(
                            networkCall.invoke()
                        )
                    result
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                val error = when (throwable) {
                    is TimeoutCancellationException -> {
                        NetworkError.Timeout()
                    }
                    is IOException -> {
                        NetworkError.IO()
                    }
                    is HttpException -> {
                        NetworkError.Other(throwable.localizedMessage)
                    }
                    else -> {
                        NetworkError.Unknown()
                    }
                }
                val result: NetworkResponse<T> =
                    NetworkResponse.Error(
                        error = error
                    )
                result
            }
        }
    }
}
