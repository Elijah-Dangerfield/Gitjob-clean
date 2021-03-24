package com.dangerfield.gitjob.data.datasources

import com.dangerfield.gitjob.data.database.mappers.JobListingCacheMapper
import com.dangerfield.gitjob.data.database.room.GitJobDatabase
import com.dangerfield.gitjob.domain.datasource.cache.JobListingsCacheDataSource
import com.dangerfield.gitjob.domain.model.JobListing

class JobListingsCacheDataSourceImpl(
    private val db: GitJobDatabase,
    private val jobListingCacheMapper: JobListingCacheMapper
) : JobListingsCacheDataSource {

    override suspend fun getCachedJobListingFeed(): List<JobListing> {
        return db.mainDao().getFeed().map {
            jobListingCacheMapper.mapFromEntity(it)
        }
    }

    override suspend fun getSavedJobListings(): List<JobListing> {
        return db.mainDao().getAllSavedJobs().map {
            jobListingCacheMapper.mapFromEntity(it)
        }
    }

    override suspend fun saveJobListing(job: JobListing) {
        db.mainDao().insertSavedJob(jobListingCacheMapper.mapToEntity(job))
    }

    override suspend fun unsaveJobListing(job: JobListing) {
        db.mainDao().deleteSavedJob(job.id)
    }

    override suspend fun getSavedJob(id: String): List<JobListing> {
        return db.mainDao().querySavedJob(id).map { jobListingCacheMapper.mapFromEntity(it) }
    }
}