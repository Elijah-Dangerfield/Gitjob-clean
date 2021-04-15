package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.usecases.NoInputUseCase
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.model.CacheResponse
import com.dangerfield.gitjob.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClearSearchedCities(
    private val cityCacheDataSource: CityCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : NoInputUseCase<Flow<Resource<Unit, CityError>>> {

    override fun invoke(): Flow<Resource<Unit, CityError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                cityCacheDataSource.clearAllSavedCities()
            }
            val result: Resource<Unit, CityError> = when(cacheResult) {
                is CacheResponse.Success -> Resource.Success(Unit)
                is CacheResponse.Error -> Resource.Error(error = CityError.ERROR_CLEARING_CITIES)
            }
            emit(result)
        }
    }
}