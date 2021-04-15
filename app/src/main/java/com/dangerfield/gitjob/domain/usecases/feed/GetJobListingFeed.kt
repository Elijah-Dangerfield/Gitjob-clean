package com.dangerfield.gitjob.domain.usecases.feed

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.JobListingsCacheDataSource
import com.dangerfield.gitjob.domain.datasource.network.JobListingsNetworkDataSource
import com.dangerfield.gitjob.domain.datasource.network.NetworkCallWrapper
import com.dangerfield.gitjob.domain.model.*
import com.dangerfield.gitjob.domain.usecases.UseCase
import com.dangerfield.gitjob.domain.util.RateLimiter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class GetJobListingFeed(
    private val jobListingsCacheDataSource: JobListingsCacheDataSource,
    private val jobListingsNetworkDataSource: JobListingsNetworkDataSource,
    private val jobFeedRateLimiter: RateLimiter<String>,
    private val networkManager: NetworkManager,
    private val networkCallWrapper: NetworkCallWrapper,
    private val cacheCallWrapper: CacheCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : UseCase<Flow<Resource<JobListingFeed, JobListingFeedError>>, City?> {

    private val requestKey = "JOB_FEED_REQUEST"

    override fun invoke(input: City?): Flow<Resource<JobListingFeed, JobListingFeedError>> = flow {

        val cachedFeedResponse = getCachedFeed()

        if (shouldFetchNewFeed(cachedFeedResponse, input)) {

            emit(Resource.Loading(cachedFeedResponse.cacheData))

            when (val networkResponse = fetchNewFeed(input)) {
                is NetworkResponse.Success -> {
                    saveFeed(networkResponse.data) // TODO cache call could fail
                    emitAll(jobListingsCacheDataSource.getJobListingFeed().map {
                        val resource: Resource<JobListingFeed, JobListingFeedError> =
                            Resource.Success(it)
                        resource
                    })
                }
                is NetworkResponse.Error -> {
                    emitAll(
                        jobListingsCacheDataSource.getJobListingFeed()
                            .map { Resource.Error(it, networkResponse.error) })
                }

            }

        } else {
            val dbFlow: Flow<Resource<JobListingFeed, JobListingFeedError>> =
                jobListingsCacheDataSource.getJobListingFeed().map { Resource.Success(it) }
            emitAll(dbFlow)
        }
    }

    /*
    If user is connected to network we fetch new data under these conditions
    1. Data has not been fetched since launching application
    2. The last fetch has expired (defined in rate limiter)
    3. If the cities of the request and our previous fetch dont match (new request is being made)
    4. If the jobs in the feed are null or empty
    5. If the call to get the feed in cache failed
     */
    private fun shouldFetchNewFeed(
        cacheResult: CacheResponse<JobListingFeed?>,
        input: City?
    ): Boolean {

        val isDataUsable = when (cacheResult) {
            is CacheResponse.Success -> !(cacheResult.data?.jobs.isNullOrEmpty() && cacheResult.data?.metadata?.city == input?.name)
            is CacheResponse.Error -> false
        }
        return networkManager.hasInternetConnection()
                && (jobFeedRateLimiter.shouldFetch(requestKey)
                || !isDataUsable)
    }

    private suspend fun fetchNewFeed(city: City?): NetworkResponse<JobListingFeed> {
        return networkCallWrapper.safeNetworkCall(dispatcher) {
            jobListingsNetworkDataSource.getJobListingsFeed(city?.name)
        }
    }

    private suspend fun getCachedFeed(): CacheResponse<JobListingFeed?> {
        return cacheCallWrapper.safeCacheCall(dispatcher) {
            jobListingsCacheDataSource.getJobListingFeed().first()
        }
    }

    private suspend fun saveFeed(feed: JobListingFeed) {
        cacheCallWrapper.safeCacheCall(dispatcher) {
            jobListingsCacheDataSource.updateJobListingFeed(feed)
        }
    }
}