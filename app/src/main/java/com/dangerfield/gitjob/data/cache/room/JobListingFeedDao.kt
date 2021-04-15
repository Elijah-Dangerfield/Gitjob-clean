package com.dangerfield.gitjob.data.cache.room

import androidx.room.*
import com.dangerfield.gitjob.data.cache.model.JobListingFeedMetaDataEntity
import com.dangerfield.gitjob.data.cache.model.JobListingCacheEntity
import kotlinx.coroutines.flow.Flow

interface JobListingFeedDao {

    /**
     * returns all job listings in table
     */
    @Query("SELECT * from JOB_LISTINGS_FEED")
    suspend fun getFeedJobItems(): Flow<List<JobListingCacheEntity>>

    /**
     * returns the job feed meta data
     */
    @Query("SELECT * from JOB_FEED_META_DATA LIMIT 1")
    suspend fun getFeedMetaData(): Flow<JobListingFeedMetaDataEntity>

    /**
     * inserts all passed job listings into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedJobItems(jobs: List<JobListingCacheEntity>)

    /**
     * Deletes all previous meta data
     * inserts new feed meta data
     */
    @Transaction
    suspend fun replaceFeedMetaData(meta: JobListingFeedMetaDataEntity) {
        deleteFeedMetaData()
        insertFeedMetaData(meta)
    }

    /**
     * removes all job listings in the database
     */
    @Query("DELETE from JOB_LISTINGS_FEED")
    suspend fun deleteFeedJobItems()

    /**
     * removes all feed meta data
     */
    @Query("DELETE from JOB_FEED_META_DATA")
    suspend fun deleteFeedMetaData()

    /**
     * inserts feed meta data
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedMetaData(meta: JobListingFeedMetaDataEntity)

    /**
     * updates single listing, used to notify job feed of a change to saved status
     */
    @Query("UPDATE JOB_LISTINGS_FEED SET saved=:saved where id = :id ")
    suspend fun updateJobListingInFeed(id: String, saved: Boolean)

    /**
     * replaces all data in database
     */
    @Transaction
    suspend fun replaceFeedJobItems(listings: List<JobListingCacheEntity>) {
        deleteFeedJobItems()
        insertFeedJobItems(listings)
    }
}