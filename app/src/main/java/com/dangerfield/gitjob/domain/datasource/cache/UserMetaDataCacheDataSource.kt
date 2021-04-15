package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.SearchedTerm

interface UserMetaDataCacheDataSource {
    suspend fun getSearchedTerms() : List<SearchedTerm>
    suspend fun clearSearchedTerms()
    suspend fun addSearchedTerm(term: SearchedTerm)
    suspend fun removeSearchedTerm(term: SearchedTerm)
}