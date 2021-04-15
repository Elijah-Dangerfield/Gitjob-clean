package com.dangerfield.gitjob.domain.model

data class JobListingFeed(
    val metadata: JobListingFeedMetadata,
    val jobs : List<JobListing>
) {
}