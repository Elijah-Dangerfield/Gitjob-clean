package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.data.datasources.cache.CacheCallWrapperImpl
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSearchedCitiesTest {
    private val cacheDataSource: CityCacheDataSource = mockk()
    private val cacheCallWrapper : CacheCallWrapper = CacheCallWrapperImpl()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testSubject = GetSearchedCities(cacheDataSource, cacheCallWrapper, testDispatcher)
    private val cities = listOf(City("Nashville"), City("San Diego"), City("New York"))

    @Test
    fun `given cache is successful, when getting all searched cities, then verify success is returned with correct list of cities`() = runBlockingTest {
        coEvery { cacheDataSource.getAllSavedCities() } returns cities
        testSubject.invoke().first().let {
            assert(it is Resource.Success && it.data == cities)
        }
    }

    @Test
    fun `given cache is failure, when getting all searched cities, then error state should be set with correct error`() = runBlockingTest {
        coEvery { cacheDataSource.getAllSavedCities() } throws Error()
        testSubject.invoke().first().let {
            assert(it is Resource.Error && it.error == CityError.ERROR_GETTING_SEARCHED_CITIES)
        }
    }

    @Test
    fun `given normal stuff, when get searched cities is invoked, then cache should be called`() = runBlockingTest {
        testSubject.invoke().onCompletion {
            coVerify { cacheDataSource.getAllSavedCities() }
        }
    }
}