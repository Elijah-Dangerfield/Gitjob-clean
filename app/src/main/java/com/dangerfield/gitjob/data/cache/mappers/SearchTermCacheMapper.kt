package com.dangerfield.gitjob.data.cache.mappers

import com.dangerfield.gitjob.data.cache.model.SearchTermEntity
import com.dangerfield.gitjob.domain.model.SearchedTerm
import com.dangerfield.gitjob.domain.util.EntityMapper

class SearchTermCacheMapper : EntityMapper<SearchTermEntity,SearchedTerm> {
    override fun mapFromEntity(entity: SearchTermEntity): SearchedTerm {
        return SearchedTerm(city = entity.city, description = entity.term)
    }

    override fun mapToEntity(domainModel: SearchedTerm): SearchTermEntity {
        return SearchTermEntity(term = domainModel.description, city = domainModel.city)
    }
}