package com.dangerfield.gitjob.data.network.service

import com.dangerfield.gitjob.data.network.response.GetJobListingsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JobListingsService {

    @GET("positions.json")
    suspend fun getJobListings(
        @Query("location") location: String? = null,
        @Query("description") description: String? = null
    ): GetJobListingsResponse?
}