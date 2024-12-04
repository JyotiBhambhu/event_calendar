package com.jyoti.eventcalender.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.jyoti.eventcalender.month.compose.CalendarState
import com.getir.gtcalendar.v2.month.model.CalendarMonth
import com.jyoti.eventcalender.week.compose.WeekCalendarState
import com.jyoti.eventcalender.week.model.Week
import kotlinx.coroutines.flow.filter

/**
 * Returns the first visible month in a paged calendar **after** scrolling stops.
 */
@Composable
internal fun rememberFirstVisibleMonthAfterScroll(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleMonth.value = state.firstVisibleMonth }
    }
    return visibleMonth.value
}

/**
 * Find first visible week in a paged week calendar **after** scrolling stops.
 */
@Composable
fun rememberFirstVisibleWeekAfterScroll(state: WeekCalendarState): Week {
    val visibleWeek = remember(state) { mutableStateOf(state.firstVisibleWeek) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleWeek.value = state.firstVisibleWeek }
    }
    return visibleWeek.value
}
