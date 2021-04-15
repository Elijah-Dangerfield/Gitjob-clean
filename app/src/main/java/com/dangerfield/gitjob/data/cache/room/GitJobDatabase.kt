package com.dangerfield.gitjob.data.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dangerfield.gitjob.data.cache.model.JobListingCacheEntity
import com.dangerfield.gitjob.data.cache.model.SavedJobListingCacheEntity
import com.dangerfield.gitjob.data.cache.model.SearchTermEntity

@Database(
    entities = [JobListingCacheEntity::class,
        SearchTermEntity::class,
        SavedJobListingCacheEntity::class], version = 1, exportSchema = false
)
abstract class GitJobDatabase : RoomDatabase() {
    abstract fun mainDao(): GitJobDao

    companion object {
        @Volatile
        private var instance: GitJobDatabase? = null
        private val LOCK = Any()

        /**
         * using invoke will make it very simple to create a database instance
         * val db = ArticlesDatabase(context)
         */
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            GitJobDatabase::class.java, "gitjob.db"
        )
            .build()
    }
}