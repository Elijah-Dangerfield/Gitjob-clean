package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.network.CityNetworkDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.Location
import com.dangerfield.gitjob.domain.usecases.UseCase
import com.dangerfield.gitjob.domain.util.DataState
import com.dangerfield.gitjob.domain.util.NetworkCallWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCity(
    private val cityNetworkDataSource: CityNetworkDataSource
) : UseCase<Flow<DataState<City, CityError>>, Location> {

    override fun invoke(input: Location): Flow<DataState<City, CityError>> {
        return flow {
            val networkResult = NetworkCallWrapper.safeApiCall(Dispatchers.IO) {
                cityNetworkDataSource.getCity(input)
            }
            val result: DataState<City, CityError> = when (networkResult) {
                is DataState.Success -> {
                    if (networkResult.data != null) {
                        DataState.Success(networkResult.data)
                    } else {
                        DataState.Error(error = CityError.ERROR_GETTING_CITY)
                    }
                }
                //TODO this is where I could do more specific error checking
                is DataState.Error -> DataState.Error(error = CityError.ERROR_GETTING_CITY)
            }
            emit(result)
        }
    }
}