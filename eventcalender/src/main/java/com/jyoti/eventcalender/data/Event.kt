package com.jyoti.eventcalender.data

import com.jyoti.eventcalender.eventcalendar.model.EventUIConfig
import com.jyoti.eventcalender.util.UIText

data class Event(
    var id: String,
    var startTS: Long = 0L,
    var endTS: Long = 0L,
    var title: UIText,
    var type: Long = 0,
    var uiConfig: EventUIConfig,
    var repeatInterval: Int = 0,
    var repeatLimit: Long = 0,
    var repeatRule: Int = 0,
    var flags: Int = 0,
    var addTimeZoneOffset: Boolean
)
