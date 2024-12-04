package com.jyoti.eventcalender.eventcalendar

import com.jyoti.eventcalender.base.ReduceAction
import com.jyoti.eventcalender.data.Event
import java.time.LocalDate

/**
 * Effect
 */
sealed class EventCalendarReduceAction : ReduceAction {
    data class ToggleView(val isWeekMode: Boolean) : EventCalendarReduceAction()
    data class ViewScrolled(val persistState: Boolean = false) : EventCalendarReduceAction()
    object LoadEvents : EventCalendarReduceAction()
    data class SelectDateWithEvents(val date: LocalDate) : EventCalendarReduceAction()
    data class EventsLoaded(val events: ArrayList<Event>) : EventCalendarReduceAction()
    data class EventsLoadError(val error: String) : EventCalendarReduceAction()
}
