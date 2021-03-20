package com.dangerfield.gitjob.data.network.model.mapquest

data class Result(
    val locations: List<Location>,
    val providedLocation: ProvidedLocation
)