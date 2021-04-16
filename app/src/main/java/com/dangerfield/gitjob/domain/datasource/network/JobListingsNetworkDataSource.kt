package com.dangerfield.gitjob.domain.datasource.network

import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.feed.FeedCityState
import com.dangerfield.gitjob.domain.model.feed.JobListingFeed

interface JobListingsNetworkDataSource {

    suspend fun getJobListingsFeed(city: String?, feedCityState: FeedCityState): JobListingFeed

    suspend fun searchJobListings(
        city: String?,
        description: String?
    ): List<JobListing>
}