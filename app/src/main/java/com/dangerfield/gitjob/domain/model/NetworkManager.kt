package com.dangerfield.gitjob.domain.model

interface NetworkManager {
    fun hasInternetConnection() : Boolean
}