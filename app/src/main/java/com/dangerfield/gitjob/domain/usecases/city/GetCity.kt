package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.network.CityNetworkDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.Location
import com.dangerfield.gitjob.domain.usecases.UseCase
import com.dangerfield.gitjob.domain.model.Resource
import com.dangerfield.gitjob.domain.datasource.network.NetworkCallWrapper
import com.dangerfield.gitjob.domain.model.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCity(
    private val cityNetworkDataSource: CityNetworkDataSource,
    private val networkCallWrapper: NetworkCallWrapper,
    private val dispatcher: CoroutineDispatcher
) : UseCase<Flow<Resource<City, CityError>>, Location> {

    override fun invoke(input: Location): Flow<Resource<City, CityError>> {
        return flow {
            val networkResult = networkCallWrapper.safeNetworkCall(dispatcher) {
                cityNetworkDataSource.getCity(input)
            }
            val result: Resource<City, CityError> = when (networkResult) {
                is NetworkResponse.Success -> {
                    if (networkResult.data != null) {
                        Resource.Success(networkResult.data)
                    } else {
                        Resource.Error(error = CityError.ERROR_GETTING_CITY)
                    }
                }
                is NetworkResponse.Error -> Resource.Error(error = CityError.ERROR_GETTING_CITY)
            }
            emit(result)
        }
    }
}