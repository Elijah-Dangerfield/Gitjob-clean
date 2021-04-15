package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.JobListingFeed
import com.dangerfield.gitjob.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface JobListingsNetworkDataSource {

    suspend fun getJobListingsFeed(city: String?): JobListingFeed

    suspend fun searchJobListings(
        city: String?,
        description: String?
    ): List<JobListing>
}