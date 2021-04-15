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
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoveSearchedCityTest {
    private val cacheDataSource: CityCacheDataSource = mockk()
    private val cacheCallWrapper : CacheCallWrapper = CacheCallWrapperImpl()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testSubject = RemoveSearchedCity(cacheDataSource, cacheCallWrapper, testDispatcher)

    @Test
    fun `given cache removal succeeds, when removing searched city, then result should be success`() = runBlockingTest {
        val city = City("Nashville")
        coEvery { cacheDataSource.removeSavedCity(city) } returns  Unit
        testSubject.invoke(city).first().let {
            assert(it is Resource.Success )
        }
    }

    @Test
    fun `given cache removal fails, when removing searched city, then result should be an error`() = runBlockingTest {
        val city = City("Nashville")
        coEvery { cacheDataSource.removeSavedCity(city) } throws Error()
        testSubject.invoke(city).first().let {
            assert(it is Resource.Error && it.error == CityError.ERROR_REMOVING_CITY)
        }
    }

    @Test
    fun `given normal, when removing searched city, then verify cache removal is called`() = runBlockingTest {
        val city = City("Nashville")
        coEvery { cacheDataSource.removeSavedCity(city) } throws Error()
        testSubject.invoke(city).first().let {
           coVerify { cacheDataSource.removeSavedCity(city) }
        }
    }
}