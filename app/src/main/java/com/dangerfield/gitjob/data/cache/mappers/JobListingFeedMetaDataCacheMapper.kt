package com.dangerfield.gitjob.data.cache.mappers

import com.dangerfield.gitjob.data.cache.model.JobListingFeedMetaDataEntity
import com.dangerfield.gitjob.domain.model.feed.FeedCityState
import com.dangerfield.gitjob.domain.model.feed.JobListingFeedMetadata
import com.dangerfield.gitjob.domain.util.EntityMapper

class JobListingFeedMetaDataCacheMapper : EntityMapper<JobListingFeedMetaDataEntity, JobListingFeedMetadata> {
    override fun mapFromEntity(entity: JobListingFeedMetaDataEntity): JobListingFeedMetadata {
        return JobListingFeedMetadata(
            city = entity.city,
            timeStamp = entity.timestamp,
            feedCityState = enumValueOf(entity.feedCityState)
        )
    }

    override fun mapToEntity(domainModel: JobListingFeedMetadata): JobListingFeedMetaDataEntity {
        return JobListingFeedMetaDataEntity(
            0,
            city = domainModel.city,
            timestamp = domainModel.timeStamp,
            feedCityState = domainModel.feedCityState.name
        )
    }
}