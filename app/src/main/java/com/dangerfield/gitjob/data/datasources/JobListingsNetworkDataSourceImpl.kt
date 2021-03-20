package com.dangerfield.gitjob.data.datasources

import com.dangerfield.gitjob.data.network.mappers.JobListingNetworkMapper
import com.dangerfield.gitjob.data.network.service.JobListingsService
import com.dangerfield.gitjob.domain.datasource.network.JobListingsNetworkDataSource
import com.dangerfield.gitjob.domain.model.JobListing

class JobListingsNetworkDataSourceImpl (
    private val jobsService: JobListingsService,
    private val jobListingNetworkMapper: JobListingNetworkMapper
        ) : JobListingsNetworkDataSource {

    override suspend fun getJobListingsFeed(city: String?): List<JobListing> {
        return jobsService.getJobListings(location = city)?.jobListings?.map {
            jobListingNetworkMapper.mapFromEntity(it)
        } ?: listOf()
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