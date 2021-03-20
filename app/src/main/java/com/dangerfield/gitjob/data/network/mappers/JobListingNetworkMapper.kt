package com.dangerfield.gitjob.data.network.mappers

import com.dangerfield.gitjob.data.network.model.JobListingNetworkEntity
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.util.EntityMapper

class JobListingNetworkMapper : EntityMapper<JobListingNetworkEntity, JobListing> {
    override fun mapFromEntity(entity: JobListingNetworkEntity): JobListing {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domainModel: JobListing): JobListingNetworkEntity {
        TODO("Not yet implemented")
    }
}