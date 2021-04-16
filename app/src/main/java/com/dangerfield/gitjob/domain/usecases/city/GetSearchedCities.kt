package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.usecases.NoInputUseCase
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import com.dangerfield.gitjob.domain.model.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchedCities(
    private val cityCacheDataSource: CityCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : NoInputUseCase<Flow<Resource<List<City>, CityError>>> {
    override fun invoke(): Flow<Resource<List<City>, CityError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                cityCacheDataSource.getAllSavedCities()
            }
            val result: Resource<List<City>, CityError> = when(cacheResult) {
                is CacheResponse.Success -> Resource.Success(cacheResult.data ?: emptyList())
                is CacheResponse.Error -> Resource.Error(error = CityError.ERROR_GETTING_SEARCHED_CITIES)
            }
            emit(result)
        }
    }
}