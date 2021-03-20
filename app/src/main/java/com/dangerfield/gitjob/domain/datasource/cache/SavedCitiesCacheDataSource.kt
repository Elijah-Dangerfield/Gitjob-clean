package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.City

interface SavedCitiesCacheDataSource {
    suspend fun saveCity(city: City)
    suspend fun getAllSavedCities(): List<City>
    suspend fun removeSavedCity(city: City)
    suspend fun clearAllSavedCities()
}