package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.usecases.UseCase
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import com.dangerfield.gitjob.domain.model.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveSearchedCity(
    private val cityCacheDataSource: CityCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : UseCase<Flow<Resource<Unit, CityError>>, City> {

    override fun invoke(input: City): Flow<Resource<Unit, CityError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                cityCacheDataSource.removeSavedCity(input)
            }
            val result: Resource<Unit, CityError> = when(cacheResult) {
                is CacheResponse.Success -> Resource.Success(Unit)
                is CacheResponse.Error -> Resource.Error(error = CityError.ERROR_REMOVING_CITY)
            }
            emit(result)
        }
    }
}