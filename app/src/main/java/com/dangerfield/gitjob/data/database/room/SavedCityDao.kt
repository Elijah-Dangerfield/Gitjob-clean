package com.dangerfield.gitjob.data.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dangerfield.gitjob.data.database.model.CityCacheEntity

@Dao
interface SavedCityDao {
    /**
     * retrieves all locations
     */
    @Query("SELECT * from CITIES_SEARCHED")
    suspend fun getAllCitiesSearched(): List<CityCacheEntity>

    /**
     * inserts a searched location into the database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchedCity(city: CityCacheEntity)

    /**
     * deletes a searched location
     */
    @Query("DELETE from CITIES_SEARCHED WHERE name = :city")
    suspend fun deleteSearchedCity(city: String)

    /**
     * removes all saved cities
     */
    @Query("DELETE from CITIES_SEARCHED")
    suspend fun clearSearchedCities()
}