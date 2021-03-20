package com.dangerfield.gitjob.domain.repository

import com.dangerfield.gitjob.domain.model.JobListing

interface JobListingsRepository {

    suspend fun geNewJobListingsFeed(city: String?): List<JobListing>

    suspend fun getCachedJobListingsFeed(): List<JobListing>

    suspend fun getSavedJobListings(): List<JobListing>

    suspend fun saveJobListing(job: JobListing)

    suspend fun unsaveJobListing(job: JobListing)

    suspend fun searchJobListings(
        city: String?,
        description: String?
    ): List<JobListing>
}