package com.dangerfield.gitjob.data.datasources

import com.dangerfield.gitjob.data.database.mappers.SearchTermCacheMapper
import com.dangerfield.gitjob.data.database.room.GitJobDatabase
import com.dangerfield.gitjob.domain.datasource.cache.MetaDataCacheDataSource
import com.dangerfield.gitjob.domain.model.SearchedTerm

class MetaDataCacheSourceImpl (
    private val db: GitJobDatabase,
    private val searchTermCacheMapper: SearchTermCacheMapper
        ) : MetaDataCacheDataSource {

    override suspend fun getSearchedTerms(): List<SearchedTerm> {
        return db.mainDao().getAllSearchedTerms().map { searchTermCacheMapper.mapFromEntity(it) }
    }

    override suspend fun clearSearchedTerms() {
        db.mainDao().clearSearchedTerms()
    }
}