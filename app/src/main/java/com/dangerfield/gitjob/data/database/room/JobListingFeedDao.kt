package com.dangerfield.gitjob.data.database.room

import androidx.room.*
import com.dangerfield.gitjob.data.database.model.JobListingCacheEntity

interface JobListingFeedDao {

    /**
     * returns all job listings in table
     */
    @Query("SELECT * from JOB_LISTINGS_FEED")
    suspend fun getFeed(): List<JobListingCacheEntity>

    /**
     * inserts all passed job listings into database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeed(articles: List<JobListingCacheEntity>)

    /**
     * removes all job listings in the database
     */
    @Query("DELETE from JOB_LISTINGS_FEED")
    suspend fun deleteFeed()

    /**
     * updates single listing, used to notify job feed of a change to saved status
     */
    @Query("UPDATE JOB_LISTINGS_FEED SET saved=:saved where id = :id ")
    suspend fun updateJobListing(id: String, saved: Boolean)

    /**
     * replaces all data in database
     */
    @Transaction
    suspend fun replaceFeed(listings: List<JobListingCacheEntity>) {
        deleteFeed()
        insertFeed(listings)
    }
}