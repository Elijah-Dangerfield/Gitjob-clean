package com.dangerfield.gitjob.domain.model.feed

data class JobListingFeedMetadata(
    val city: String?,
    val timeStamp: String,
    val feedCityState: FeedCityState = FeedCityState.NO_CITY_SELECTED
    ) {
}