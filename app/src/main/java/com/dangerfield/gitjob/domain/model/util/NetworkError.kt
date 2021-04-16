package com.dangerfield.gitjob.domain.model.util

sealed class NetworkError(val message: String) {
    class IO(message: String? = null) : NetworkError(message ?: "IO Error")
    class Unauthorized(message: String? = null) : NetworkError("User is unauthorized to make this request")
    class Unknown() : NetworkError("Unknown network error")
    class Timeout : NetworkError("Network timeout")
    class Other(message: String?) : NetworkError(message ?: "Unknown network error")
}