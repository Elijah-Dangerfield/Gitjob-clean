package com.dangerfield.gitjob.data.network.mappers

import com.dangerfield.gitjob.data.network.response.GetCityResponse
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.util.EntityMapper

class CityNetworkMapper : EntityMapper<GetCityResponse?, City> {

    override fun mapFromEntity(entity: GetCityResponse?): City {
        return City(entity?.results?.getOrNull(0)?.locations?.getOrNull(0)?.adminArea5 ?: "")
    }

    override fun mapToEntity(domainModel: City): GetCityResponse? {
        return null //will never need to map to a network response
    }
}