package com.dangerfield.gitjob.domain.usecases.city

import com.dangerfield.gitjob.domain.datasource.cache.CityCacheDataSource
import com.dangerfield.gitjob.domain.model.City
import com.dangerfield.gitjob.domain.usecases.UseCase
import com.dangerfield.gitjob.domain.util.CacheCallWrapper
import com.dangerfield.gitjob.domain.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

//TODO: ReadMe
/*
if im not doing anything speical with seeing exactly what error it is then maybe
I dont need them? Like obviously if this use case returns an error then I know its an error
adding a searched city. I imagine ill care more on network things
 */
class AddSearchedCity(
    private val cityCacheDataSource: CityCacheDataSource,
) : UseCase<Flow<DataState<Unit, CityError>>, City>{

    //TODO README
    //who handles retries? Doesnt seem view model should. HMMMMMMM
    override fun invoke(input: City): Flow<DataState<Unit, CityError>> {
        return flow {
            //TODO READ ME
            // do i even need to catch errors here? i could just call .catch() on the flow
            // i guess the question is who should do error catching and is that the same thing
            //that should do error handling?
            val cacheResult = CacheCallWrapper.safeCacheCall(Dispatchers.IO) {
                cityCacheDataSource.saveCity(input)
            }
            val result: DataState<Unit, CityError> = when(cacheResult) {
                is DataState.Success -> DataState.Success(Unit)
                is DataState.Error -> DataState.Error(error = CityError.ERROR_ADDING_SEARCHED_CITY)
            }
            emit(result)
        }
        //TODO README
        //should i have a.flowON()
        // seems like it would help with injecting dispatchers
        // if i call invoke and collect I assume it kinda flows it on the context I call it from?
        // would calling it on one and saying flow on another be wierd?
    }
}