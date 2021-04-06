package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.usecases.NoInputUseCase
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.model.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClearSearchedCities(
    private val cityCacheDataSource: CityCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper
) : NoInputUseCase<Flow<DataState<Unit, CityError>>> {

    override fun invoke(): Flow<DataState<Unit, CityError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(Dispatchers.IO) {
                cityCacheDataSource.clearAllSavedCities()
            }
            val result: DataState<Unit, CityError> = when(cacheResult) {
                is DataState.Success -> DataState.Success(Unit)
                is DataState.Error -> DataState.Error(error = CityError.ERROR_CLEARING_CITIES)
            }
            emit(result)
        }
    }
}