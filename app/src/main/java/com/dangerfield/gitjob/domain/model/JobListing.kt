package com.dangerfield.gitjob.domain.model

class JobListing(
    var company: String? = null,
    var company_logo: String? = null,
    var company_url: String? = null,
    var created_at: String? = null,
    var description: String? = null,
    var how_to_apply: String? = null,
    var id: String,
    var location: String? = null,
    var title: String? = null,
    var type: String? = null,
    var url: String? = null,
    var saved: Boolean = false,
    var cityFilter: String? = null
)
