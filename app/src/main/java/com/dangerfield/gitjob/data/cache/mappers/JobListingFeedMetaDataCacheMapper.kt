package com.dangerfield.gitjob.data.cache.mappers

import com.dangerfield.gitjob.data.cache.model.JobListingFeedMetaDataEntity
import com.dangerfield.gitjob.domain.model.JobListingFeedMetadata
import com.dangerfield.gitjob.domain.util.EntityMapper

class JobListingFeedMetaDataCacheMapper : EntityMapper<JobListingFeedMetaDataEntity, JobListingFeedMetadata> {
    override fun mapFromEntity(entity: JobListingFeedMetaDataEntity): JobListingFeedMetadata {
        return JobListingFeedMetadata( entity.city, entity.timestamp)
    }

    override fun mapToEntity(domainModel: JobListingFeedMetadata): JobListingFeedMetaDataEntity {
        return JobListingFeedMetaDataEntity(0, domainModel.city, domainModel.timeStamp)
    }
}