package com.dangerfield.gitjob.data.database.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dangerfield.gitjob.data.database.model.JobListingCacheEntity

interface SavedJobsDao {
    /**
     * gets all job listings that have been saved
     */
    @Query("SELECT * from SAVED_JOB_LISTINGS")
    suspend fun getAllSavedJobs(): List<JobListingCacheEntity>

    /**
     * saves a job listing
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedJob(savedJob: JobListingCacheEntity)

    /**
     * queries for a saved job with a job id
     */
    @Query("SELECT * from SAVED_JOB_LISTINGS WHERE id = :withID")
    suspend fun querySavedJob(withID: String): List<JobListingCacheEntity>

    /**
     * deletes a saved job
     */
    @Query("DELETE from SAVED_JOB_LISTINGS WHERE id = :withID")
    suspend fun deleteSavedJob(withID: String)
}