package com.dangerfield.gitjob.data.datasources.cache

import com.dangerfield.gitjob.data.cache.mappers.JobListingCacheMapper
import com.dangerfield.gitjob.data.cache.mappers.JobListingFeedMetaDataCacheMapper
import com.dangerfield.gitjob.data.cache.room.GitJobDatabase
import com.dangerfield.gitjob.domain.datasource.cache.JobListingsCacheDataSource
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.feed.JobListingFeed
import kotlinx.coroutines.flow.*

class JobListingsCacheDataSourceImpl(
    private val db: GitJobDatabase,
    private val jobListingCacheMapper: JobListingCacheMapper,
    private val jobListingFeedMetaDataCacheMapper: JobListingFeedMetaDataCacheMapper
) : JobListingsCacheDataSource {

    override suspend fun getJobListingFeed(): Flow<JobListingFeed> {

            val jobsFlow = db.mainDao().getFeedJobItems().map {
                it.map { jobListingCacheMapper.mapFromEntity(it) }
            }

            val metadataFlow = db.mainDao().getFeedMetaData()
                .map { jobListingFeedMetaDataCacheMapper.mapFromEntity(it) }

            return jobsFlow.zip(metadataFlow) { jobs, meta ->
                JobListingFeed(jobs = jobs, metadata = meta)
            }
    }

    override suspend fun updateJobListingFeed(feed: JobListingFeed) {
        db.mainDao().replaceFeedJobItems(feed.jobs.map { jobListingCacheMapper.mapToEntity(it) })
        db.mainDao()
            .replaceFeedMetaData(jobListingFeedMetaDataCacheMapper.mapToEntity(feed.metadata))
    }

    override suspend fun getSavedJobListings(): Flow<List<JobListing>> {
        return db.mainDao().getAllSavedJobs().map {
            it.map { jobListingCacheMapper.mapFromEntity(it) }
        }
    }

    override suspend fun saveJobListing(job: JobListing) {
        db.mainDao().insertSavedJob(jobListingCacheMapper.mapToEntity(job))
    }

    override suspend fun unsaveJobListing(job: JobListing) {
        db.mainDao().deleteSavedJob(job.id)
    }

    override suspend fun updateJobListingInFeed(id: String, saved: Boolean) {
        db.mainDao().updateJobListingInFeed(id, saved)
    }

    override suspend fun querySavedJob(id: String) : List<JobListing> {
        return db.mainDao().querySavedJob(id).map { jobListingCacheMapper.mapFromEntity(it) }
    }
}