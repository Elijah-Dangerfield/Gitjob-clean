package com.dangerfield.gitjob.domain.repository

import com.dangerfield.gitjob.domain.model.Location

interface LocationRepository {
    fun getCity(location: Location): String?
}