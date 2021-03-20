package com.dangerfield.gitjob.data.datasources

import com.dangerfield.gitjob.data.network.mappers.CityNetworkMapper
import com.dangerfield.gitjob.data.network.service.LocationService
import com.dangerfield.gitjob.domain.datasource.network.CityNetworkDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.Location

class CityNetworkDataSourceImpl(
    private val locationService: LocationService,
    private val cityNetworkMapper: CityNetworkMapper
) : CityNetworkDataSource {

    private val mapquestApiKey = "qoPCzFujaMkzRvdkprCGAfpaVwJy2Phn" //TODO add interceptor or something?

    override suspend fun getCity(location: Location): City? {
        val locationString = "${location.lat},${location.lng}"
        return locationService.getMapQuestData(
            key = mapquestApiKey,
            location = locationString
        )?.let { cityNetworkMapper.mapFromEntity(it) }
    }
}