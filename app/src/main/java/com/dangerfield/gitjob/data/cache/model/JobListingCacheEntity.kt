package com.dangerfield.gitjob.data.cache.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//Used to store listings for feed
@Entity(tableName = "JOB_LISTINGS_FEED")
@Parcelize
class JobListingCacheEntity (
    var company: String? = null,
    var company_logo: String? = null,
    var company_url: String? = null,
    var created_at: String? = null,
    var description: String? = null,
    var how_to_apply: String? = null,
    @PrimaryKey var id: String,
    var location: String? = null,
    var title: String? = null,
    var type: String? = null,
    var url: String? = null,
    var saved: Boolean = false
) : Parcelable {
}

//used to store listings that were saved by user
@Entity(tableName = "SAVED_JOB_LISTINGS")
@Parcelize
class SavedJobListingCacheEntity (
    var company: String? = null,
    var company_logo: String? = null,
    var company_url: String? = null,
    var created_at: String? = null,
    var description: String? = null,
    var how_to_apply: String? = null,
    @PrimaryKey var id: String,
    var location: String? = null,
    var title: String? = null,
    var type: String? = null,
    var url: String? = null,
) : Parcelable {
}