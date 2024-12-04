package com.jyoti.eventcalenderapp

import com.jyoti.eventcalender.data.Event
import com.jyoti.eventcalender.eventcalendar.EventCalendarReduceAction
import com.jyoti.eventcalender.eventcalendar.model.EventCalendarState
import com.jyoti.eventcalender.eventcalendar.model.UIEvent
import com.jyoti.eventcalender.eventcalendar.model.toUiEvent
import com.jyoti.eventcalender.util.secToLocalDate
import java.time.LocalDate
import javax.inject.Inject

class EventCalendarReducer @Inject constructor() {

    internal fun reduceEffect(
        effect: EventCalendarReduceAction,
        model: EventCalendarState
    ): EventCalendarState {
        return when (effect) {
            is EventCalendarReduceAction.ToggleView -> model.copy(
                calendarState = model.calendarState.copy(
                    isWeekMode = effect.isWeekMode,
                    isWeekModeToggle = true
                )
            )
            is EventCalendarReduceAction.ViewScrolled -> model.copy(
                selectedDate = if (effect.persistState) model.selectedDate else null,
                selectDateEvents = if (effect.persistState) model.selectDateEvents else listOf(),
                calendarState = model.calendarState.copy(isWeekModeToggle = false)
            )
            is EventCalendarReduceAction.LoadEvents -> model.copy(isLoading = true, error = null)
            is EventCalendarReduceAction.EventsLoaded -> {
                val newEvents = effect.events.filterEvent()
                model.copy(
                    isLoading = false,
                    events = newEvents,
                    selectDateEvents =
                    newEvents[model.selectedDate]?.sortUIEvent()
                        ?: listOf()
                )
            }
            is EventCalendarReduceAction.EventsLoadError -> model.copy(
                isLoading = false,
                error = effect.error
            )
            is EventCalendarReduceAction.SelectDateWithEvents -> model.copy(
                selectedDate = effect.date,
                selectDateEvents =
                model.events[effect.date]?.sortUIEvent()
                    ?: listOf()
            )

            else -> model.copy()
        }
    }
}

fun List<UIEvent>.sortFilterDots() =
    distinctBy { it.uiConfig.color }.sortedBy { it.uiConfig.priority }

fun List<UIEvent>.sortUIEvent() =
    sortedWith((compareBy<UIEvent> { it.date }.thenBy { it.uiConfig.priority }))

fun ArrayList<Event>.filterEvent(): Map<LocalDate, ArrayList<UIEvent>> {
    val dayEvents = HashMap<LocalDate, ArrayList<UIEvent>>()
    forEach { event ->
        val endCode = event.endTS.secToLocalDate()
        var currDay = event.startTS.secToLocalDate()
        var currDayEvents = dayEvents[currDay] ?: arrayListOf()
        currDayEvents.add(event.toUiEvent())
        dayEvents[currDay] = currDayEvents

        while (currDay < endCode) {
            currDay = currDay.plusDays(1)
            currDayEvents = dayEvents[currDay] ?: ArrayList()
            currDayEvents.add(event.toUiEvent())
            dayEvents[currDay] = currDayEvents
        }
    }

//    dayEvents.forEach { mapEntry ->
//        if (dayEvents[mapEntry.key]?.filter { it.isEventTypeIsShift() }?.size.orZero() > 0) { // Shift Present
//            // Filter this day event with removing availability
//            dayEvents[mapEntry.key] = dayEvents[mapEntry.key]?.filter {
//                !it.isEventTypeIsAvailability()
//            }?.toArrayList() ?: arrayListOf()
//        }
//    }

    return dayEvents
}
