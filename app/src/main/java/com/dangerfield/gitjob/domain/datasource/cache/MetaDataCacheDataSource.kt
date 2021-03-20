package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.SearchedTerm

interface MetaDataCacheDataSource {
    suspend fun getSearchedTerms() : List<SearchedTerm>
    suspend fun clearSearchedTerms()
}