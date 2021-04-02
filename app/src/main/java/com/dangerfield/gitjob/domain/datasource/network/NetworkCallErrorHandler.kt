package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.NetworkError

interface NetworkCallErrorHandler {
    fun onError() : NetworkError
}