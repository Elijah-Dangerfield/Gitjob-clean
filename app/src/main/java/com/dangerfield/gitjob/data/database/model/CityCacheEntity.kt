package com.dangerfield.gitjob.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dangerfield.gitjob.domain.model.City
import kotlinx.parcelize.Parcelize

@Entity(tableName = "CITIES_SEARCHED")
@Parcelize
class CityCacheEntity (
    @PrimaryKey var name: String
) : Parcelable {

    fun toDomainModel(): City {
        return City(name = this.name)
    }
}