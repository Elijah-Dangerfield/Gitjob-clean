package com.dangerfield.gitjob.data.database.mappers

import com.dangerfield.gitjob.data.database.model.JobListingCacheEntity
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.util.EntityMapper

class JobListingCacheMapper : EntityMapper<JobListingCacheEntity, JobListing> {
    override fun mapFromEntity(entity: JobListingCacheEntity): JobListing {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domainModel: JobListing): JobListingCacheEntity {
        TODO("Not yet implemented")
    }
}