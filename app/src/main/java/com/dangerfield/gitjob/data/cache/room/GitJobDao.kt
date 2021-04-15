package com.dangerfield.gitjob.data.cache.room

import androidx.room.Dao

@Dao
interface GitJobDao : JobListingFeedDao, SavedJobsDao, SavedCityDao, SearchTermDao {
}