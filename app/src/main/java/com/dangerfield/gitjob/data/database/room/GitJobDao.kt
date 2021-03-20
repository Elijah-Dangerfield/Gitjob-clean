package com.dangerfield.gitjob.data.database.room

import androidx.room.Dao

@Dao
interface GitJobDao : JobListingFeedDao, SavedJobsDao, SavedCityDao, SearchTermDao {
}