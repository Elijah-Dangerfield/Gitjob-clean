package com.dangerfield.gitjob.domain.usecases.search

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.UserMetaDataCacheDataSource
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import com.dangerfield.gitjob.domain.model.util.Resource
import com.dangerfield.gitjob.domain.model.SearchedTerm
import com.dangerfield.gitjob.domain.usecases.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveSearchedTerm(
    private val userMetaDataCacheDataSource: UserMetaDataCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : UseCase<Flow<Resource<Unit, SearchError>>, SearchedTerm> {

    override fun invoke(input: SearchedTerm): Flow<Resource<Unit, SearchError>> {
        return flow {
            val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                userMetaDataCacheDataSource.removeSearchedTerm(input)
            }
            val result: Resource<Unit, SearchError> = when(cacheResult) {
                is CacheResponse.Success -> Resource.Success(Unit)
                is CacheResponse.Error -> Resource.Error(error = SearchError.CACHE_FAILED)
            }
            emit(result)
        }
    }
}