package com.dangerfield.gitjob.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "SEARCHED_TERMS")
@Parcelize
data class SearchTermEntity(@PrimaryKey val term: String): Parcelable {
    constructor() : this("")
}