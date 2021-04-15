package com.dangerfield.gitjob.domain.util

import java.text.SimpleDateFormat
import java.util.*


class DateUtil
constructor(
    private val dateFormat: SimpleDateFormat
)
{
    // dates format looks like this: "2019-07-23 HH:mm:ss"
    fun getCurrentTimestamp(): String {
        return dateFormat.format(Date())
    }

}