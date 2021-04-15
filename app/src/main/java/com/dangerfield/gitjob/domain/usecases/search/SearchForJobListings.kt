package com.dangerfield.gitjob.domain.usecases.search

import com.dangerfield.gitjob.domain.datasource.network.JobListingsNetworkDataSource
import com.dangerfield.gitjob.domain.datasource.network.NetworkCallWrapper
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.model.JobListing
import com.dangerfield.gitjob.domain.model.NetworkResponse
import com.dangerfield.gitjob.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchForJobListings(
    private val addSearchedTerm: AddSearchedTerm,
    private val jobListingsNetworkDataSource: JobListingsNetworkDataSource,
    private val networkCallWrapper: NetworkCallWrapper,
    private val dispatcher: CoroutineDispatcher
)  {

    //MVP user can not filter search by city or any other attribute, but todo
    fun invoke(term: String, city: City?): Flow<Resource<List<JobListing>, SearchError>> {
        return flow {
            val networkResponse = networkCallWrapper.safeNetworkCall(dispatcher) {
                jobListingsNetworkDataSource.searchJobListings(city = city?.name, description = term)
            }

            val result: Resource<List<JobListing>, SearchError> = when(networkResponse) {
                is NetworkResponse.Error -> {
                    Resource.Error(error = SearchError.SEARCH_FAILED)
                }
                is NetworkResponse.Success -> {
                    Resource.Success(networkResponse.data)
                }
            }
            emit(result)

            if(result is Resource.Success) {
                addSearchedTerm.invoke(description = term, city = city?.name)
            }
        }
    }
}