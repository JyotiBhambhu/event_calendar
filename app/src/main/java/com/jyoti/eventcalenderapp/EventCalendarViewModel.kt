package com.jyoti.eventcalenderapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyoti.eventcalender.eventcalendar.EventCalendarIntent
import com.jyoti.eventcalender.eventcalendar.EventCalendarReduceAction
import com.jyoti.eventcalender.eventcalendar.model.CalendarUIState
import com.jyoti.eventcalender.eventcalendar.model.EventCalendarState
import com.jyoti.eventcalender.util.atStartOfMonth
import com.jyoti.eventcalender.util.nextMonth
import com.jyoti.eventcalender.util.previousMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventCalendarViewModel @Inject constructor(
//    private val eventsHelper: EventsHelper,
//    private val calendarUseCase: CalendarUseCase,
    private val reducer: EventCalendarReducer,
) : /*MviViewModel<EventCalendarState, EventCalendarIntent, EventCalendarReduceAction>(
    initialState = EventCalendarState.initial
)*/ViewModel() {
    private var calendarDataJob: Job? = null

    private val stateFlow = MutableStateFlow<EventCalendarState>(EventCalendarState.initial)
    val state: StateFlow<EventCalendarState> = stateFlow.asStateFlow()
    private val intentFlow = MutableSharedFlow<EventCalendarIntent>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)
    private val reduceFlow = MutableSharedFlow<EventCalendarReduceAction>(extraBufferCapacity = FLOW_BUFFER_CAPACITY, replay = 1)

    init {
        intentFlow
            .onEach { intent ->
                executeIntent(intent)
            }
            .launchIn(viewModelScope)
        reduceFlow
            .onEach { action ->
                stateFlow.value = reduce(stateFlow.value, action)
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(mviIntent: EventCalendarIntent) {
        intentFlow.tryEmit(mviIntent)
    }

    private fun handle(reduceAction: EventCalendarReduceAction) {
        reduceFlow.tryEmit(reduceAction)
    }

    private fun reduceIntent(
        intent: EventCalendarIntent
    ) {
        when (intent) {
            is EventCalendarIntent.UpdateView -> {
                handle(currentMonthChange(state.value.calendarState))
            }

            is EventCalendarIntent.WeekModeToggled -> {
                handle(EventCalendarReduceAction.ToggleView(intent.weekMode))
            }

            is EventCalendarIntent.GoToPrev -> {
                goToPrev(state.value.calendarState)
            }

            is EventCalendarIntent.GoToNext -> {
                goToNext(state.value.calendarState)
            }

            is EventCalendarIntent.ScrollDone -> handle(
                EventCalendarReduceAction.ViewScrolled(
                    intent.persistSelectState
                )
            )

            is EventCalendarIntent.LoadNewEvents -> {
//                val viewRange = intent.date.getFilterRange()
//                if (eventsHelper.prevRange?.contains(viewRange) == true) {
//                    eventsHelper.getEvents(
//                        viewRange.lower.millisToSec(),
//                        viewRange.upper.millisToSec()
//                    ) {
//                        handle(EventCalendarReduceAction.EventsLoaded(it))
//                    }
//                } else {
//                    fetchCalendarData(intent.date)
//                }
//                handle(EventCalendarReduceAction.LoadEvents)
            }

            is EventCalendarIntent.RefreshEvents -> {
                fetchCalendarData(state.value.calendarState.currentDate)
                handle(EventCalendarReduceAction.LoadEvents)
            }

            is EventCalendarIntent.SelectDate -> handle(
                EventCalendarReduceAction.SelectDateWithEvents(
                    intent.date
                )
            )

            else -> {}
        }
    }

    private fun fetchCalendarData(localDate: LocalDate) {
//        val filterRange = localDate.getFilterRange()
//        calendarDataJob.cancelIfActive()
//        calendarDataJob = makeCall(
//            collectDispatcher = mainDispatcher,
//            call = {
//                calendarUseCase(
//                    CalendarEventRequestModel(
//                        filterRange.lower.millisToLocalDate()
//                            .formatTo(DateFormats.DATE_DASH_FORMAT),
//                        filterRange.upper.millisToLocalDate().formatTo(DateFormats.DATE_DASH_FORMAT)
//                    ),
//                    eventsHelper.timeZone
//                )
//            }
//        ) { response ->
//            response?.let { events ->
//                val filteredEvent =
//                    if (isShiftPrefEnabled) events else events.filter { !it.isEventTypeIsAvailability() }
//                        .toArrayList()
//
//                eventsHelper.updateEvents(filteredEvent, eventsHelper.timeZone)
//                eventsHelper.prevRange = filterRange
//            }
//            onIntent(EventCalendarIntent.LoadNewEvents(localDate))
//        }
    }

    private fun currentMonthChange(calendarState: CalendarUIState): EventCalendarReduceAction {
        if (calendarState.isWeekMode) {
            val targetDate =
                calendarState.weekState.firstVisibleWeek.days.first().date
            onIntent(EventCalendarIntent.LoadNewEvents(targetDate))
        } else {
            val targetMonth =
                calendarState.monthState.firstVisibleMonth.yearMonth
            onIntent(EventCalendarIntent.LoadNewEvents(targetMonth.atStartOfMonth()))
        }
        return EventCalendarReduceAction.ViewScrolled()
    }

    private fun goToPrev(calendarState: CalendarUIState) {
        if (calendarState.isWeekMode) {
            val targetDate =
                calendarState.weekState.firstVisibleWeek.days.first().date.minusDays(
                    1
                )
            onIntent(EventCalendarIntent.LoadNewEvents(targetDate))
        } else {
            val targetMonth =
                calendarState.monthState.firstVisibleMonth.yearMonth.previousMonth
            onIntent(EventCalendarIntent.LoadNewEvents(targetMonth.atStartOfMonth()))
        }
    }

    private fun goToNext(calendarState: CalendarUIState) {
        if (calendarState.isWeekMode) {
            val targetDate =
                calendarState.weekState.firstVisibleWeek.days.last().date.plusDays(1)
            onIntent(EventCalendarIntent.LoadNewEvents(targetDate))
        } else {
            val targetMonth =
                calendarState.monthState.firstVisibleMonth.yearMonth.nextMonth
            onIntent(EventCalendarIntent.LoadNewEvents(targetMonth.atStartOfMonth()))
        }
    }

    private fun executeIntent(mviIntent: EventCalendarIntent) {
        reduceIntent(mviIntent)
    }

    private fun reduce(
        state: EventCalendarState,
        reduceAction: EventCalendarReduceAction
    ): EventCalendarState = reducer.reduceEffect(reduceAction, state)

    companion object{
        private const val FLOW_BUFFER_CAPACITY = 64
    }
}