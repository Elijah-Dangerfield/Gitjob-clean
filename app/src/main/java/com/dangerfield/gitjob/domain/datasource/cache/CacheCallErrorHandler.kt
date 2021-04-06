package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.NetworkError

interface CacheCallErrorHandler {
    fun onError(throwable: Throwable) : CacheError

}