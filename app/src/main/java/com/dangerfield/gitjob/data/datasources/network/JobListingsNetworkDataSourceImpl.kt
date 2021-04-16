package com.dangerfield.gitjob.data.datasources.network

import com.dangerfield.gitjob.data.network.mappers.JobListingNetworkMapper
import com.dangerfield.gitjob.data.network.service.JobListingsService
import com.dangerfield.gitjob.data.util.pmap
import com.dangerfield.gitjob.data.util.removeHtml
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.JobListingsCacheDataSource
import com.dangerfield.gitjob.domain.datasource.network.JobListingsNetworkDataSource
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.feed.FeedCityState
import com.dangerfield.gitjob.domain.model.feed.JobListingFeed
import com.dangerfield.gitjob.domain.model.feed.JobListingFeedMetadata
import com.dangerfield.gitjob.domain.model.util.CacheResponse
import com.dangerfield.gitjob.domain.util.DateUtil
import kotlinx.coroutines.CoroutineDispatcher

class JobListingsNetworkDataSourceImpl (
    private val jobsService: JobListingsService,
    private val jobListingNetworkMapper: JobListingNetworkMapper,
    private val jobListingsCacheDataSource: JobListingsCacheDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dateUtil : DateUtil
        ) : JobListingsNetworkDataSource {

    override suspend fun getJobListingsFeed(city: String?, feedCityState: FeedCityState): JobListingFeed {
            val jobs = jobsService.getJobListings(location = city)?.jobListings?.map {
                jobListingNetworkMapper.mapFromEntity(it)
            } ?: listOf()

            processJobs(jobs)

            val meta = JobListingFeedMetadata(city = city, timeStamp = dateUtil.getCurrentTimestamp(), feedCityState = feedCityState)

            return JobListingFeed(jobs = jobs, metadata = meta)

    }

    private suspend fun processJobs(jobs: List<JobListing>) {
        jobs.pmap {
            it.saved = isJobSaved(it)
            it.description = it.description?.removeHtml()
            it.title = it.title?.removeHtml()
            if(it.how_to_apply?.contains("http") == true) {
                it.url = "http" + it.how_to_apply?.substringAfter("\"http")?.substringBefore("\">")
            }
            it.how_to_apply = it.how_to_apply?.removeHtml()
            it
        }
    }

    private suspend fun isJobSaved(job: JobListing) : Boolean {
        val cacheResponse = cacheCallWrapper.safeCacheCall(dispatcher) {
            jobListingsCacheDataSource.querySavedJob(job.id)
        }
        return when(cacheResponse) {
            is CacheResponse.Error -> false
            is CacheResponse.Success -> !cacheResponse.data.isNullOrEmpty()
        }
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