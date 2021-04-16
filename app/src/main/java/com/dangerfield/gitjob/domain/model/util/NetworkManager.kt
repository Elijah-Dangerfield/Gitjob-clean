package com.dangerfield.gitjob.domain.model.util

interface NetworkManager {
    fun hasInternetConnection() : Boolean
}