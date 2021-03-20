package com.dangerfield.gitjob.data

import com.dangerfield.gitjob.domain.datasource.cache.JobListingsCacheDataSource
import com.dangerfield.gitjob.domain.datasource.network.JobListingsNetworkDataSource
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.repository.JobListingsRepository

class JobListingsRepositoryImpl(
    private val jobListingsNetworkDataSource: JobListingsNetworkDataSource,
    private val jobListingsCacheDataSource: JobListingsCacheDataSource
) : JobListingsRepository {
    override suspend fun geNewJobListingsFeed(city: String?): List<JobListing> {
        return jobListingsNetworkDataSource.getJobListingsFeed(city)
    }

    override suspend fun getCachedJobListingsFeed(): List<JobListing> {
        return jobListingsCacheDataSource.getCachedJobListingFeed()
    }

    override suspend fun getSavedJobListings(): List<JobListing> {
        return jobListingsCacheDataSource.getSavedJobListings()
    }

    override suspend fun saveJobListing(job: JobListing) {
        jobListingsCacheDataSource.saveJobListing(job)
    }

    override suspend fun unsaveJobListing(job: JobListing) {
        jobListingsCacheDataSource.unsaveJobListing(job)
    }

    override suspend fun searchJobListings(
        city: String?,
        description: String?
    ): List<JobListing> {
        return jobListingsNetworkDataSource.searchJobListings(city = city, description = description)
    }
}