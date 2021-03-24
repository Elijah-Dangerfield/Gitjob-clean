package com.dangerfield.gitjob.domain.datasource.cache

import com.dangerfield.gitjob.domain.model.JobListing

interface JobListingsCacheDataSource {
    suspend fun getCachedJobListingFeed(): List<JobListing>
    suspend fun getSavedJobListings() : List<JobListing>
    suspend fun saveJobListing(job: JobListing)
    suspend fun unsaveJobListing(job: JobListing)
    suspend fun getSavedJob(id: String) : List<JobListing>
}