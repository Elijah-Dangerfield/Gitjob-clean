package com.dangerfield.gitjob.data.datasources.cache

import com.dangerfield.gitjob.data.cache.mappers.CityCacheMapper
import com.dangerfield.gitjob.data.cache.room.GitJobDatabase
import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City

class CityCacheDataSourceImpl   (
    private val db: GitJobDatabase,
private val savedCityCacheMapper: CityCacheMapper
) : CityCacheDataSource{

    override suspend fun saveCity(city: City) {
       return db.mainDao().insertSearchedCity(savedCityCacheMapper.mapToEntity(city))
    }

    override suspend fun getAllSavedCities(): List<City> {
        return db.mainDao().getAllCitiesSearched().map { savedCityCacheMapper.mapFromEntity(it) }
    }

    override suspend fun removeSavedCity(city: City) {
        db.mainDao().deleteSearchedCity(city.name)
    }

    override suspend fun clearAllSavedCities() {
       db.mainDao().clearSearchedCities()
    }
}