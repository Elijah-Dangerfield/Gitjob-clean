package com.dangerfield.gitjob.data.database.mappers

import com.dangerfield.gitjob.data.database.model.JobListingCacheEntity
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.util.EntityMapper

class JobListingCacheMapper : EntityMapper<JobListingCacheEntity, JobListing> {
    override fun mapFromEntity(entity: JobListingCacheEntity): JobListing {
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
            saved = entity.saved,
            cityFilter = entity.cityFilter
        )
    }

    override fun mapToEntity(domainModel: JobListing): JobListingCacheEntity {
        return JobListingCacheEntity(
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
            saved = domainModel.saved,
            cityFilter = domainModel.cityFilter
        )
    }
}