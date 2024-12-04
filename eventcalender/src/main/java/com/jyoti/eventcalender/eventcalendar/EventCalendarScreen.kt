package com.jyoti.eventcalender.eventcalendar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import com.getir.gtcalendar.v2.month.model.CalendarMonth
import com.jyoti.eventcalender.core.compose.CalendarHeader
import com.jyoti.eventcalender.core.compose.CalendarTitle
import com.jyoti.eventcalender.core.compose.EventList
import com.jyoti.eventcalender.core.compose.rememberFirstVisibleMonthAfterScroll
import com.jyoti.eventcalender.core.compose.rememberFirstVisibleWeekAfterScroll
import com.jyoti.eventcalender.eventcalendar.model.CalendarUIState
import com.jyoti.eventcalender.eventcalendar.model.EventCalendarState
import com.jyoti.eventcalender.eventcalendar.model.EventUIConfig
import com.jyoti.eventcalender.eventcalendar.model.UIEvent
import com.jyoti.eventcalender.month.compose.MonthCalendar
import com.jyoti.eventcalender.ui.DraggableCalBox
import com.jyoti.eventcalender.util.UIText
import com.jyoti.eventcalender.util.atStartOfMonth
import com.jyoti.eventcalender.util.yearMonth
import com.jyoti.eventcalender.week.compose.WeekCalendar
import com.jyoti.eventcalender.week.model.WeekDayPosition
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import java.time.LocalDate
import java.time.LocalDateTime

//@Composable
//fun CalendarRoute(
//    viewModel: EventCalendarViewModel = hiltViewModel(),
//    navigateToLogin: () -> Unit = {},
//    loadNewData: () -> Unit = {},
//) {
//    val state by viewModel.state.collectAsStateWithLifecycle()
//    val appState by AppState.sharedData.collectAsStateWithLifecycle()
//
//    LaunchedEffect(key1 = appState.refreshCalendar, block = {
//        if (appState.refreshCalendar) {
//            viewModel.onIntent(EventCalendarIntent.RefreshEvents)
//            AppState.setRefreshCalendar(false)
//        }
//    })
//
//    ScreenWrapper(
//        ScreenWrapperData(
//            toolbarData =
//            if (state.isLeaveFeatureEnabled) {
//                GetirToolbarData(
//                    rightIconResourceId = R.drawable.ic_plus_vector,
//                    toolbarRightIconClick = {
//                        onCreateLeaveClick(state.selectedDate?.toString())
//                    }
//                )
//            } else {
//                GetirToolbarData()
//            },
//            loadingFlow = viewModel.loading,
//            errorDialogFlow = viewModel.result,
//            redirectionFlow = viewModel.redirection,
//            navigateToLogin = navigateToLogin
//        ) {
//            EventCalendarScreen(
//                state,
//                viewModel::onIntent,
//                onLeaveClick = onLeaveClick,
//                cordsUpdated = cordsUpdated
//            )
//        }
//    )
//}

@Composable
fun EventCalendarScreen(
    uiState: EventCalendarState,
    onIntent: (EventCalendarIntent) -> Unit = {},
) {

    println("ui state>>>>>${uiState.calendarState.isWeekMode}")
    val calendarState = uiState.calendarState
    val coroutineScope = rememberCoroutineScope()

    val visibleMonth = rememberFirstVisibleMonthAfterScroll(calendarState.monthState)
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(calendarState.weekState)

    LaunchedEffect(calendarState.isWeekMode) {
        if (calendarState.isWeekModeToggle) {
            coroutineScope.launch {
                updateCalendarStateOnViewToggle(uiState, calendarState, onIntent)
            }
        }
    }

    LaunchedEffect(visibleWeek) {
        if (calendarState.isWeekMode && visibleWeek.days.none { it.date == uiState.selectedDate }) {
            onIntent(EventCalendarIntent.UpdateView)
        }
    }

    LaunchedEffect(visibleMonth) {
        if (!calendarState.isWeekMode && visibleMonth.yearMonth != uiState.selectedDate?.yearMonth) {
            onIntent(EventCalendarIntent.UpdateView)
        }
    }

    CalendarContent(calendarState, uiState, visibleMonth, onIntent)
}

private suspend fun updateCalendarStateOnViewToggle(
    uiState: EventCalendarState,
    calendarState: CalendarUIState,
    onIntent: (EventCalendarIntent) -> Unit
) {
    val selDate = uiState.selectedDate ?: LocalDate.now()
    if (calendarState.isWeekMode) {
        val visibleYearMonth = calendarState.monthState.firstVisibleMonth.yearMonth
        val selDay =
            if (visibleYearMonth.isValidDay(selDate.dayOfMonth)) selDate.dayOfMonth else visibleYearMonth.atStartOfMonth().dayOfMonth
        val targetDate = visibleYearMonth.atDay(
            selDay
        )
        calendarState.weekState.scrollToWeek(targetDate)
        calendarState.weekState.animateScrollToWeek(targetDate) // Trigger a layout pass for title update
    } else {
        val weekday =
            calendarState.weekState.firstVisibleWeek.days.firstOrNull { it.date == selDate }
                ?: calendarState.weekState.firstVisibleWeek.days.first { it.position == WeekDayPosition.RangeDate }

        val targetMonth = weekday.date.yearMonth
        calendarState.monthState.scrollToMonth(targetMonth)
        calendarState.monthState.animateScrollToMonth(targetMonth) // Trigger a layout pass for title update
    }
    onIntent(EventCalendarIntent.ScrollDone(true))
}

@Composable
private fun CalendarContent(
    calendarState: CalendarUIState,
    uiState: EventCalendarState,
    visibleMonth: CalendarMonth,
    onIntent: (EventCalendarIntent) -> Unit,
) {

    var weekCalendarSize by remember { mutableStateOf(DpSize.Zero) }
    val weeksInVisibleMonth = visibleMonth.weekDays.count()
    val monthCalendarHeight by animateDpAsState(
        if (calendarState.isWeekMode) {
            weekCalendarSize.height
        } else {
            weekCalendarSize.height * weeksInVisibleMonth
        },
        tween(durationMillis = 250), label = "Month Calendar",
    )
    val density = LocalDensity.current
    val weekModeToggled: (isWeekMode: Boolean) -> Unit = { weekMode ->
        onIntent(EventCalendarIntent.WeekModeToggled(weekMode))
    }

    DraggableCalBox(
        modifier = Modifier,
        calendarState.isWeekMode, weekModeToggled = weekModeToggled
    ) {

        CalendarTitle(
            calendarState = calendarState,
            weekModeToggled = weekModeToggled,
            onIntent = onIntent
        )
        CalendarHeader(daysOfWeek = calendarState.daysOfWeek)
        Box {
            MonthCalendar(
                uiState = uiState,
                monthCalendarHeight = monthCalendarHeight
            ) { onIntent(EventCalendarIntent.SelectDate(it)) }
            WeekCalendar(uiState, onSizeChanged = {
                val size = density.run { DpSize(it.width.toDp(), it.height.toDp()) }
                if (weekCalendarSize != size) {
                    weekCalendarSize = size
                }
            }, selectDate = { onIntent(EventCalendarIntent.SelectDate(it)) })
        }
        EventList(
            uiState.selectDateEvents,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun EventCalendarPreview() {
    EventCalendarScreen(
        uiState = EventCalendarState(
            LocalDate.now(),
            selectDateEvents = listOf(),
            mapOf(
                Pair(
                    LocalDate.now(),
                    mutableListOf<UIEvent>().apply {
                        for (i in 1..10) {
                            add(
                                UIEvent(
                                    id = "123",
                                    name = UIText.DynamicString("Event"),
                                    repeatRule = 1,
                                    LocalDateTime.now(),
                                    uiConfig = EventUIConfig()
                                )
                            )
                        }
                    }
                )
            ),
            calendarState = CalendarUIState(),
        )
    )
}
