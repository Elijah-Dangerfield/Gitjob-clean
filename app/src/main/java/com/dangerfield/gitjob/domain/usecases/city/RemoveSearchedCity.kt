package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.usecases.UseCase
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.model.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveSearchedCity(
    private val cityCacheDataSource: CityCacheDataSource
) : UseCase<Flow<DataState<Unit, CityError>>, City> {

    override fun invoke(input: City): Flow<DataState<Unit, CityError>> {
        return flow {
            val cacheResult = CacheCallWrapper.safeCacheCall(Dispatchers.IO) {
                cityCacheDataSource.removeSavedCity(input)
            }
            val result: DataState<Unit, CityError> = when(cacheResult) {
                is DataState.Success -> DataState.Success(Unit)
                is DataState.Error -> DataState.Error(error = CityError.ERROR_REMOVING_CITY)
            }
            emit(result)
        }
    }
}