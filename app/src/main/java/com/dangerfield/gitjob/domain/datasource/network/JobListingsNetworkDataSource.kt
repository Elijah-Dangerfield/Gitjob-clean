package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.Location

interface JobListingsNetworkDataSource {

    suspend fun getJobListingsFeed(city: String?): List<JobListing>

    suspend fun searchJobListings(
        city: String?,
        description: String?
    ): List<JobListing>
}