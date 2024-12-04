package com.jyoti.eventcalender.eventcalendar

import com.jyoti.eventcalender.base.MviIntent
import java.time.LocalDate

/**
 * Intent
 */
sealed class EventCalendarIntent: MviIntent {
    object UpdateView : EventCalendarIntent()
    data class WeekModeToggled(
        val weekMode: Boolean
    ) : EventCalendarIntent()

    data class ScrollDone(val persistSelectState: Boolean = false) : EventCalendarIntent()
    object GoToPrev : EventCalendarIntent()
    object GoToNext : EventCalendarIntent()
    data class LoadNewEvents(val date: LocalDate) : EventCalendarIntent()
    data class SelectDate(val date: LocalDate) : EventCalendarIntent()
    object RefreshEvents : EventCalendarIntent()
}
