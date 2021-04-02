package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test


class AddSearchedCityTest {

   @MockK
   lateinit var cacheDataSource: CityCacheDataSource
   private val testSubject = AddSearchedCity(cacheDataSource)
   private val FORCE_FAIL = "force_fail"

   @Before
   fun setup() {
      coEvery { cacheDataSource.saveCity(match {city -> city.name != FORCE_FAIL}) } returns Unit
      coEvery { cacheDataSource.saveCity(match {city -> city.name == FORCE_FAIL}) } throws Error()
   }


   @Test
   fun `given normal city input when cache is succesful then succes result should be returned `() {
      testSubject.invoke(City("Nashville")).cou
      assert()
   }
}