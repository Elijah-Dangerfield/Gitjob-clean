package com.dangerfield.gitjob.data.cache.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dangerfield.gitjob.domain.model.feed.FeedCityState
import kotlinx.parcelize.Parcelize


@Entity(tableName = "JOB_FEED_META_DATA")
@Parcelize

data class JobListingFeedMetaDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var city: String? = "",
    var timestamp: String,
    var feedCityState: String
) : Parcelable {
}