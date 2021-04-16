package com.dangerfield.gitjob.domain.usecases.search

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.UserMetaDataCacheDataSource
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import com.dangerfield.gitjob.domain.model.util.Resource
import com.dangerfield.gitjob.domain.model.SearchedTerm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddSearchedTerm(
    private val userMetaDataCacheDataSource: UserMetaDataCacheDataSource,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
)  {

    fun invoke(description: String, city: String?): Flow<Resource<Unit, SearchError>> {
        return flow {
            val term = SearchedTerm(city = city, description = description)
            val result = if(isValidSearch(term)) {
                val cacheResult = cacheCallWrapper.safeCacheCall(dispatcher) {
                    userMetaDataCacheDataSource.addSearchedTerm(term)
                }
                val resource: Resource<Unit, SearchError> = when(cacheResult) {
                    is CacheResponse.Success -> Resource.Success(Unit)
                    is CacheResponse.Error -> Resource.Error(error = SearchError.CACHE_FAILED)
                }
                resource

            } else {
                val resource : Resource<Unit, SearchError> = Resource.Error(error = SearchError.INVALID_TERM)
                resource
            }
            emit(result)
        }
    }

    private fun isValidSearch(searchTerm: SearchedTerm): Boolean {
        return searchTerm.description.isNotEmpty()
    }
}