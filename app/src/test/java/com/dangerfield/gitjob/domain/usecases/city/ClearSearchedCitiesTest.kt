package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.data.datasources.cache.CacheCallWrapperImpl
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ClearSearchedCitiesTest {
    private val cacheDataSource: CityCacheDataSource = mockk()
    private val cacheCallWrapper : CacheCallWrapper = CacheCallWrapperImpl()
    @ExperimentalCoroutinesApi
    private val testSubject = ClearSearchedCities(cacheDataSource, cacheCallWrapper, TestCoroutineDispatcher())

    @ExperimentalCoroutinesApi
    @Test
    fun `given cache is successful, when calling clear searched cities, then success should be returned`() = runBlockingTest {
        coEvery { cacheDataSource.clearAllSavedCities() } returns Unit
        testSubject.invoke().toList().last().let {
            assert(it is Resource.Success)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given cache is unsuccessful, when calling clear searched cities, then error should be returned`() = runBlockingTest {
        coEvery { cacheDataSource.clearAllSavedCities() } throws Error()
        testSubject.invoke().first().let {
            assert(it is Resource.Error && it.error == CityError.ERROR_CLEARING_CITIES)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given nothing, when a call is made to clear cities, then verify cache data source is called`()  = runBlockingTest {
        coEvery { cacheDataSource.clearAllSavedCities() } returns Unit
        testSubject.invoke().onCompletion {
            coVerify { cacheDataSource.clearAllSavedCities() }
        }
        Unit
    }
}