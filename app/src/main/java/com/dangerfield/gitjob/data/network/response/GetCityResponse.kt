package com.dangerfield.gitjob.data.network.response

import com.dangerfield.gitjob.data.network.model.mapquest.Info
import com.dangerfield.gitjob.data.network.model.mapquest.Options
import com.dangerfield.gitjob.data.network.model.mapquest.Result

data class GetCityResponse(val info: Info, val options: Options, val results: List<Result>)