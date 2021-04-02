package com.dangerfield.gitjob.domain.model

sealed class NetworkError(val message: String) {
    class IO(message: String? = null) : NetworkError(message ?: "IO Error")
    class Unknown() : NetworkError("Unknown network error")
    class Other(message: String) : NetworkError(message)
    class Timeout : NetworkError("Network timeout")
}