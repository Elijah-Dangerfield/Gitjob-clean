package com.dangerfield.gitjob.data.network.model

import com.dangerfield.gitjob.domain.datasource.network.NetworkCallErrorHandler
import com.dangerfield.gitjob.domain.model.NetworkError
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

class NetworkCallErrorHandlerImpl : NetworkCallErrorHandler {
    override fun onError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is TimeoutCancellationException -> { NetworkError.Timeout() }
            is IOException -> { NetworkError.IO() }
            is HttpException -> { NetworkError.Other(throwable.response()?.errorBody()?.string()) }
            else -> { NetworkError.Unknown() }
        }
    }
}