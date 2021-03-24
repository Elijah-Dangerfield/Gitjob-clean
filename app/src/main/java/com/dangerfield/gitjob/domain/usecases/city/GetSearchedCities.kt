package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.usecases.NoInputUseCase
import com.dangerfield.gitjob.domain.util.CacheCallWrapper
import com.dangerfield.gitjob.domain.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchedCities(
    private val cityCacheDataSource: CityCacheDataSource
) : NoInputUseCase<Flow<DataState<List<City>, CityError>>> {
    override fun invoke(): Flow<DataState<List<City>, CityError>> {
        return flow {
            val cacheResult = CacheCallWrapper.safeCacheCall(Dispatchers.IO) {
                cityCacheDataSource.getAllSavedCities()
            }
            val result: DataState<List<City>, CityError> = when(cacheResult) {
                is DataState.Success -> DataState.Success(cacheResult.data ?: emptyList())
                is DataState.Error -> DataState.Error(error = CityError.ERROR_GETTING_SEARCHED_CITIES)
            }
            emit(result)
        }
    }
}