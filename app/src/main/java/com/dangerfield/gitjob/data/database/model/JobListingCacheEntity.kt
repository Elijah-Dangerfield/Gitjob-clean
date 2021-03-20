package com.dangerfield.gitjob.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dangerfield.gitjob.domain.model.JobListing
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
    var cityFilter: String? = null, //TODO not sure if i like this
    var saved: Boolean = false
) : Parcelable {
    fun mapToDomainModel(): JobListing {
        return JobListing(
            company = this.company,
            company_logo = this.company_logo,
            company_url = this.company_url,
            created_at = this.created_at,
            description = this.description,
            how_to_apply = this.how_to_apply,
            id = this.id,
            location = this.location,
            title = this.title,
            type = this.type,
            url = this.url,
            saved = this.saved,
            cityFilter = this.cityFilter,
        )
    }
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

    fun mapToDomainModel(): JobListing {
        return JobListing(
            company = this.company,
            company_logo = this.company_logo,
            company_url = this.company_url,
            created_at = this.created_at,
            description = this.description,
            how_to_apply = this.how_to_apply,
            id = this.id,
            location = this.location,
            title = this.title,
            type = this.type,
            url = this.url,
            saved = true,
            cityFilter = null,
        )
    }
}