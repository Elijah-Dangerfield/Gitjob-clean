package com.dangerfield.gitjob.data.datasources.cache

import com.dangerfield.gitjob.data.cache.mappers.SearchTermCacheMapper
import com.dangerfield.gitjob.data.cache.room.GitJobDatabase
import com.dangerfield.gitjob.domain.datasource.cache.UserMetaDataCacheDataSource
import com.dangerfield.gitjob.domain.model.SearchedTerm

class UserUserMetaDataCacheSourceImpl (
    private val db: GitJobDatabase,
    private val searchTermCacheMapper: SearchTermCacheMapper
        ) : UserMetaDataCacheDataSource {

    override suspend fun getSearchedTerms(): List<SearchedTerm> {
        return db.mainDao().getAllSearchedTerms().map { searchTermCacheMapper.mapFromEntity(it) }
    }

    override suspend fun clearSearchedTerms() {
        db.mainDao().clearSearchedTerms()
    }

    override suspend fun addSearchedTerm(term: SearchedTerm) {
        db.mainDao().insertSearchedTerm(searchTermCacheMapper.mapToEntity(term))
    }

    override suspend fun removeSearchedTerm(term: SearchedTerm) {
        db.mainDao().deleteSearchedTerm(term.description)
    }
}