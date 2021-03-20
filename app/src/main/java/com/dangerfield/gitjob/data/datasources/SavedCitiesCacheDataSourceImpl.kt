package com.dangerfield.gitjob.data.datasources

import com.dangerfield.gitjob.data.database.mappers.CityCacheMapper
import com.dangerfield.gitjob.data.database.room.GitJobDatabase
import com.dangerfield.gitjob.domain.datasource.cache.SavedCitiesCacheDataSource
import com.dangerfield.gitjob.domain.model.City

class SavedCitiesCacheDataSourceImpl   (
    private val db: GitJobDatabase,
private val savedCityCacheMapper: CityCacheMapper
) : SavedCitiesCacheDataSource{

    override suspend fun saveCity(city: City) {
       db.mainDao().insertSearchedCity(savedCityCacheMapper.mapToEntity(city))
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