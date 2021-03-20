package com.dangerfield.gitjob.data.database.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dangerfield.gitjob.data.database.model.SearchTermEntity

interface SearchTermDao {

    /**
     * retrieves all searched terms
     */
    @Query("SELECT * from SEARCHED_TERMS")
    suspend fun getAllSearchedTerms(): List<SearchTermEntity>

    /**
     * inserts a searched term into the database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchedTerm(term: SearchTermEntity)

    /**
     * deletes a searched term
     */
    @Query("DELETE from SEARCHED_TERMS WHERE term = :term")
    suspend fun deleteSearchedTerm(term: String)

    /**
     * removes all searched terms
     */
    @Query("DELETE from SEARCHED_TERMS")
    suspend fun clearSearchedTerms()
}