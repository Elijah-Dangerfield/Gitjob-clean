package com.dangerfield.gitjob.domain.usecases.search

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.UserMetaDataCacheDataSource
import com.dangerfield.gitjob.domain.model.CacheResponse
import com.dangerfield.gitjob.domain.model.Resource
import com.dangerfield.gitjob.domain.model.SearchedTerm
import com.dangerfield.gitjob.domain.usecases.NoInputUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSearchedTerms(
    private val userMetaDataCacheDataSource: UserMetaDataCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : NoInputUseCase<Flow<Resource<List<SearchedTerm>, SearchError>>> {
    override fun invoke(): Flow<Resource<List<SearchedTerm>, SearchError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                userMetaDataCacheDataSource.getSearchedTerms()
            }
            val result: Resource<List<SearchedTerm>, SearchError> = when(cacheResult) {
                is CacheResponse.Success -> Resource.Success(cacheResult.data ?: emptyList())
                is CacheResponse.Error -> Resource.Error(error = SearchError.CACHE_FAILED)
            }
            emit(result)
        }
    }
}