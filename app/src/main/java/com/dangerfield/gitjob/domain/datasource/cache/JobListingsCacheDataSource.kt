package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.JobListingFeed
import kotlinx.coroutines.flow.Flow

interface JobListingsCacheDataSource {
    suspend fun getJobListingFeed(): Flow<JobListingFeed>
    suspend fun updateJobListingFeed(jobListingFeed: JobListingFeed)
    suspend fun getSavedJobListings() : Flow<List<JobListing>>
    suspend fun saveJobListing(job: JobListing)
    suspend fun unsaveJobListing(job: JobListing)
    suspend fun updateJobListingInFeed(id: String, saved: Boolean)
}