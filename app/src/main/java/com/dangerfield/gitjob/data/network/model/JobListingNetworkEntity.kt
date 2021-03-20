package com.dangerfield.gitjob.data.network.model

import com.dangerfield.gitjob.domain.model.JobListing

//Job Listing model given by network
data class JobListingNetworkEntity(
    var company: String? = null,
    var company_logo: String? = null,
    var company_url: String? = null,
    var created_at: String? = null,
    var description: String? = null,
    var how_to_apply: String? = null,
    var location: String? = null,
    var title: String? = null,
    var type: String? = null,
    var url: String? = null,
    var id: String
) {
    fun mapToDomainModel(city: String? = null): JobListing {
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
            saved = false,
            cityFilter = city,
        )
    }
}