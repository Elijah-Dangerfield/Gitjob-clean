package com.dangerfield.gitjob.domain.model.feed

import com.dangerfield.gitjob.domain.model.JobListing

data class JobListingFeed(
    val metadata: JobListingFeedMetadata,
    val jobs : List<JobListing>
) {
}