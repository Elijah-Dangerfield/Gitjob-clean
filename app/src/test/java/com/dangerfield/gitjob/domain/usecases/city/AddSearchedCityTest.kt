package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CacheCallWrapper
import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.DataState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddSearchedCityTest {

   private val cacheDataSource: CityCacheDataSource = mockk()
   private val cacheCallWrapper: CacheCallWrapper = mockk()

   private val testSubject = AddSearchedCity(cacheDataSource, cacheCallWrapper)
   private val FORCE_FAIL = "force_fail"

   @Before
   fun setup() {
      coEvery { cacheDataSource.saveCity(match {city -> city.name != FORCE_FAIL}) } returns Unit
      coEvery { cacheDataSource.saveCity(match {city -> city.name == FORCE_FAIL}) } throws Error()
   }


   @Test
   fun `given normal city input when cache is succesful then succes result should be returned `() = runBlocking {
      testSubject.invoke(City("Nashville")).collect {
         assert(it is DataState.Success)
      }
   }
}