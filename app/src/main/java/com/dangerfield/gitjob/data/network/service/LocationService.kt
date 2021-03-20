package com.dangerfield.gitjob.data.network.service

import com.dangerfield.gitjob.data.network.response.GetCityResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface LocationService {
    @GET("geocoding/v1/reverse")
    suspend fun getMapQuestData(
        @Query("key") key : String,
        @Query("location") location : String, // in form of 'lat,long'
        @Query("thumbMaps") thumbMaps : Boolean = false
    ): GetCityResponse?
}