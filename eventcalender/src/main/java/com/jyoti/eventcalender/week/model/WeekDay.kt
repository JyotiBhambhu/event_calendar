package com.jyoti.eventcalender.week.model

import androidx.compose.runtime.Immutable
import com.jyoti.eventcalender.week.model.WeekDayPosition
import java.io.Serializable
import java.time.LocalDate

/**
 * Represents a day on the week calendar.
 *
 * @param date the date for this day.
 * @param position the [WeekDayPosition] for this day.
 */
@Immutable
data class WeekDay(val date: LocalDate, val position: WeekDayPosition) : Serializable
