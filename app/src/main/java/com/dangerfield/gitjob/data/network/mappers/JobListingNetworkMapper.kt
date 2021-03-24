package com.dangerfield.gitjob.data.network.mappers

import com.dangerfield.gitjob.data.network.model.JobListingNetworkEntity
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.util.EntityMapper

class JobListingNetworkMapper : EntityMapper<JobListingNetworkEntity, JobListing> {

    fun mapFromEntity(entity: JobListingNetworkEntity, saved: Boolean = false, cityFilter: String? = null): JobListing {
        val domainModel = mapFromEntity(entity)
        domainModel.cityFilter = cityFilter
        domainModel.saved = saved
        return domainModel
    }
    override fun mapFromEntity(entity: JobListingNetworkEntity): JobListing {
        return JobListing(
            company = entity.company,
            company_logo = entity.company_logo,
            company_url = entity.company_url,
            created_at = entity.created_at,
            description = entity.description,
            how_to_apply = entity.how_to_apply,
            id = entity.id,
            location = entity.location,
            title = entity.title,
            type = entity.type,
            url = entity.url,
        )
    }

    override fun mapToEntity(domainModel: JobListing): JobListingNetworkEntity {
        return JobListingNetworkEntity(
            company = domainModel.company,
            company_logo = domainModel.company_logo,
            company_url = domainModel.company_url,
            created_at = domainModel.created_at,
            description = domainModel.description,
            how_to_apply = domainModel.how_to_apply,
            id = domainModel.id,
            location = domainModel.location,
            title = domainModel.title,
            type = domainModel.type,
            url = domainModel.url,
        )
    }
}