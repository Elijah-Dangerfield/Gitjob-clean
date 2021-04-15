package com.dangerfield.gitjob.data.cache.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "SEARCHED_TERMS")
@Parcelize
data class SearchTermEntity(
    val term: String,
    val city: String? = "",
    @PrimaryKey val id: String = term + city

): Parcelable {
    constructor() : this("", "")
}