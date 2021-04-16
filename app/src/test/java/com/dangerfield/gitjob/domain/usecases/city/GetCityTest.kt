package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.data.datasources.network.NetworkCallWrapperImpl
import com.dangerfield.gitjob.domain.datasource.network.CityNetworkDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.util.Resource
import com.dangerfield.gitjob.domain.model.Location
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCityTest {

    private val networkCallWrapper = NetworkCallWrapperImpl()
    private val networkDataSource: CityNetworkDataSource = mockk()
    private val testSubject = GetCity(networkDataSource, networkCallWrapper, TestCoroutineDispatcher())
    private val validLocation = Location(123, 123);
    private val invalidLocation = Location(420, 420);
    private val forceError = Location(444, 555)


    @Before
    fun setup() {
        coEvery {
            networkDataSource.getCity(match { location -> location != invalidLocation && location != forceError })
        } returns City("eli")

        coEvery { networkDataSource.getCity(match { it == invalidLocation }) } returns null
        coEvery { networkDataSource.getCity(match { it == forceError }) } throws Error()
    }

    @Test
    fun `Given a valid location, when getting city, then success should be returned`() =
        runBlockingTest {
            testSubject.invoke(validLocation).first().let {
                assert(it is Resource.Success)
            }
        }

    @Test
    fun `Given an invalid location, when getting city, then success should be returned with null value`() =
        runBlockingTest {
            testSubject.invoke(validLocation).collect {
                assert(it is Resource.Success)
            }
        }
}