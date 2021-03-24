package com.dangerfield.gitjob.data.database.mappers

import com.dangerfield.gitjob.data.database.model.SearchTermEntity
import com.dangerfield.gitjob.domain.model.SearchedTerm
import com.dangerfield.gitjob.domain.util.EntityMapper

class SearchTermCacheMapper : EntityMapper<SearchTermEntity,SearchedTerm> {
    override fun mapFromEntity(entity: SearchTermEntity): SearchedTerm {
        return SearchedTerm(entity.term)
    }

    override fun mapToEntity(domainModel: SearchedTerm): SearchTermEntity {
        return SearchTermEntity(domainModel.term)
    }
}