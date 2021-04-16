package com.dangerfield.gitjob.domain.usecases.search

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.UserMetaDataCacheDataSource
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import com.dangerfield.gitjob.domain.model.util.Resource
import com.dangerfield.gitjob.domain.usecases.NoInputUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClearSearchedTerms(
    private val userMetaDataCacheDataSource: UserMetaDataCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : NoInputUseCase<Flow<Resource<Unit, SearchError>>> {

    override fun invoke(): Flow<Resource<Unit, SearchError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                userMetaDataCacheDataSource.clearSearchedTerms()
            }
            val result: Resource<Unit, SearchError> = when(cacheResult) {
                is CacheResponse.Success -> Resource.Success(Unit)
                is CacheResponse.Error -> Resource.Error(error = SearchError.CACHE_FAILED)
            }
            emit(result)
        }
    }
}