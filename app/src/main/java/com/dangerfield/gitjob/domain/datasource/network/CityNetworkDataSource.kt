package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.Location


interface CityNetworkDataSource {
    suspend fun getCity(location: Location): City?
}