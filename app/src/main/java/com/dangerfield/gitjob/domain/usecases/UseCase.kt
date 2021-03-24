package com.dangerfield.gitjob.domain.usecases

import com.dangerfield.gitjob.domain.util.DataState

//R: the result type upon success
//E: the Error type upon failure
//I: the input to the invoke of the usecase
interface UseCase<R,I> {
    fun invoke(input: I) : R
}

interface NoInputUseCase<R> {
    fun invoke() : R
}

