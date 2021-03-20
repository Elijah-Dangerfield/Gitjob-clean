package com.dangerfield.gitjob.data.database.mappers

import com.dangerfield.gitjob.data.database.model.SearchTermEntity
import com.dangerfield.gitjob.domain.model.SearchedTerm
import com.dangerfield.gitjob.domain.util.EntityMapper

class SearchTermCacheMapper : EntityMapper<SearchTermEntity,SearchedTerm> {
    override fun mapFromEntity(entity: SearchTermEntity): SearchedTerm {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domainModel: SearchedTerm): SearchTermEntity {
        TODO("Not yet implemented")
    }
}