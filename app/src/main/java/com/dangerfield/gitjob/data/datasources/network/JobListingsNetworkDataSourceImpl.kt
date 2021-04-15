package com.dangerfield.gitjob.data.datasources.network

import com.dangerfield.gitjob.data.network.mappers.JobListingNetworkMapper
import com.dangerfield.gitjob.data.network.service.JobListingsService
import com.dangerfield.gitjob.domain.datasource.network.JobListingsNetworkDataSource
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.JobListingFeed
import com.dangerfield.gitjob.domain.model.JobListingFeedMetadata
import com.dangerfield.gitjob.domain.util.DateUtil

class JobListingsNetworkDataSourceImpl (
    private val jobsService: JobListingsService,
    private val jobListingNetworkMapper: JobListingNetworkMapper,
    private val dateUtil : DateUtil
        ) : JobListingsNetworkDataSource {

    override suspend fun getJobListingsFeed(city: String?): JobListingFeed {
            val jobs = jobsService.getJobListings(location = city)?.jobListings?.map {
                jobListingNetworkMapper.mapFromEntity(it)
            } ?: listOf()

            val meta = JobListingFeedMetadata(city = city, timeStamp = dateUtil.getCurrentTimestamp())

            return JobListingFeed(jobs = jobs, metadata = meta)

    }

    override suspend fun searchJobListings(
        city: String?,
        description: String?
    ): List<JobListing> {
        return jobsService.getJobListings(
            location = city,
            description = description)?.jobListings?.map {
            jobListingNetworkMapper.mapFromEntity(it)
        } ?: emptyList()
    }
}