package com.dangerfield.gitjob.domain.datasource.cache

sealed class CacheError(val message: String) {
    class Unknown: CacheError("Unknown cache error")
    class Timeout: CacheError("Cache timeout")
}