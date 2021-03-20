package com.dangerfield.gitjob.data.database.mappers

import com.dangerfield.gitjob.data.database.model.CityCacheEntity
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.util.EntityMapper

class CityCacheMapper : EntityMapper<CityCacheEntity, City> {
    override fun mapFromEntity(entity: CityCacheEntity): City {
        return City(entity.name)
    }

    override fun mapToEntity(domainModel: City): CityCacheEntity {
        return CityCacheEntity(domainModel.name)
    }
}