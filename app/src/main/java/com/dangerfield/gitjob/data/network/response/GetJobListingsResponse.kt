package com.dangerfield.gitjob.data.network.response

import com.dangerfield.gitjob.data.network.model.JobListingNetworkEntity

data class GetJobListingsResponse(val jobListings: List<JobListingNetworkEntity>)
