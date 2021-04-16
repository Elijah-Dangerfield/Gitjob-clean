package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.data.datasources.cache.CacheCallWrapperImpl
import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class AddSearchedCityTest {

   private val cacheDataSource: CityCacheDataSource = mockk()
   private val cacheCallWrapper : CacheCallWrapper = CacheCallWrapperImpl()
   @ExperimentalCoroutinesApi
   private val testDispatcher = TestCoroutineDispatcher()

   @ExperimentalCoroutinesApi
   private val testSubject = AddSearchedCity(cacheDataSource, cacheCallWrapper, testDispatcher)
   private val FORCE_FAIL = "force_fail"

   @Before
   fun setup() {
      coEvery { cacheDataSource.saveCity(match {city -> city.name != FORCE_FAIL}) } returns Unit
      coEvery { cacheDataSource.saveCity(match {city -> city.name == FORCE_FAIL}) } throws Error()
   }


   @ExperimentalCoroutinesApi
   @Test
   fun `given normal city input when cache is succesful then succes result should be returned `() = runBlockingTest {
      val result = testSubject.invoke(City("Nashville")).first()
      assert(result is Resource.Success)
   }

   @ExperimentalCoroutinesApi
   @Test
   fun `given failing city input when cache is error then error result should be returned `() = runBlockingTest {
      val it = testSubject.invoke(City(FORCE_FAIL)).first()
      assert(it is Resource.Error && it.error == CityError.ERROR_ADDING_SEARCHED_CITY)

   }

   @ExperimentalCoroutinesApi
   @Test
   fun `given city input when use case is called then verify cache data source is called`() = runBlockingTest {
      val city = City("Nashville")
      testSubject.invoke(city).onCompletion {
         coVerify { cacheDataSource.saveCity(city) }
      }
   }
}