package com.jyoti.eventcalender.week.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.core.compose.CalendarDefaults.flingBehavior
import com.jyoti.eventcalender.core.compose.Day
import com.jyoti.eventcalender.eventcalendar.model.EventCalendarState
import com.jyoti.eventcalender.week.model.Week
import com.jyoti.eventcalender.week.model.WeekDay
import com.jyoti.eventcalender.week.model.WeekDayPosition
import java.time.LocalDate

@Composable
fun WeekCalendar(
    uiState: EventCalendarState,
    calendarScrollPaged: Boolean = true,
    onSizeChanged: (IntSize) -> Unit,
    selectDate: (LocalDate) -> Unit
) {
    val weekCalendarAlpha by animateFloatAsState(if (uiState.calendarState.isWeekMode) 1f else 0f)
    WeekCalendarContent(
        modifier = Modifier
            .background(Color.White)
            .wrapContentHeight()
            .onSizeChanged {
                onSizeChanged(it)
            }
            .alpha(weekCalendarAlpha)
            .zIndex(if (uiState.calendarState.isWeekMode) 1f else 0f),
        state = uiState.calendarState.weekState,
        dayContent = { day ->
            val isSelectable = day.position == WeekDayPosition.RangeDate
            Day(
                day.date,
                isCurrentDay = LocalDate.now() == day.date,
                isSelected = isSelectable && uiState.selectedDate == day.date,
                isSelectable = isSelectable,
                uiState.events[day.date]
            ) { clicked ->
                selectDate(clicked)
            }
        },
        calendarScrollPaged = calendarScrollPaged
    )
}

/**
 * A horizontally scrolling week calendar.
 *
 * @param modifier the modifier to apply to this calendar.
 * @param state the state object to be used to control or observe the calendar's properties.
 * Examples: `startDate`, `endDate`, `firstDayOfWeek`, `firstVisibleWeek`.
 * @param calendarScrollPaged the scrolling behavior of the calendar. When `true`, the calendar will
 * snap to the nearest week after a scroll or swipe action. When `false`, the calendar scrolls normally.
 * Note that when `false`, you should set the desired day width on the [dayContent] composable.
 * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions
 * is allowed. You can still scroll programmatically using the state even when it is disabled.
 * @param reverseLayout reverse the direction of scrolling and layout. When `true`, weeks will be
 * composed from the end to the start and [WeekCalendarState.startDate] will be located at the end.
 * @param contentPadding a padding around the whole calendar. This will add padding for the
 * content after it has been clipped, which is not possible via [modifier] param. You can use it
 * to add a padding before the first week or after the last one.
 * @param dayContent a composable block which describes the day content.
 * @param weekHeader a composable block which describes the week header content. The header is
 * placed above each week on the calendar.
 * @param weekFooter a composable block which describes the week footer content. The footer is
 * placed below each week on the calendar.
 */
@Composable
private fun WeekCalendarContent(
    modifier: Modifier = Modifier,
    state: WeekCalendarState = rememberWeekCalendarState(),
    calendarScrollPaged: Boolean = true,
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(dimensionResource(id = R.dimen.no_spacing)),
    dayContent: @Composable BoxScope.(WeekDay) -> Unit,
    weekHeader: (@Composable ColumnScope.(Week) -> Unit)? = null,
    weekFooter: (@Composable ColumnScope.(Week) -> Unit)? = null,
) = WeekCalendarImpl(
    modifier = modifier,
    state = state,
    calendarScrollPaged = calendarScrollPaged,
    userScrollEnabled = userScrollEnabled,
    reverseLayout = reverseLayout,
    dayContent = dayContent,
    weekHeader = weekHeader,
    weekFooter = weekFooter,
    contentPadding = contentPadding,
)

@Composable
private fun WeekCalendarImpl(
    modifier: Modifier,
    state: WeekCalendarState,
    calendarScrollPaged: Boolean,
    userScrollEnabled: Boolean,
    reverseLayout: Boolean,
    contentPadding: PaddingValues,
    dayContent: @Composable BoxScope.(WeekDay) -> Unit,
    weekHeader: (@Composable ColumnScope.(Week) -> Unit)? = null,
    weekFooter: (@Composable ColumnScope.(Week) -> Unit)? = null,
) {
    LazyRow(
        modifier = modifier.testTag(TEST_TAG_WEEK_ROW),
        state = state.listState,
        flingBehavior = flingBehavior(calendarScrollPaged, state.listState),
        userScrollEnabled = userScrollEnabled,
        reverseLayout = reverseLayout,
        contentPadding = contentPadding,
    ) {
        items(
            count = state.weekIndexCount,
            key = { offset -> state.store[offset].days.first().date },
        ) { offset ->
            val week = state.store[offset]
            Column(
                modifier = Modifier
                    .then(
                        if (calendarScrollPaged) {
                            Modifier.fillParentMaxWidth()
                        } else {
                            Modifier.width(IntrinsicSize.Max)
                        },
                    )
                    .then(
                        if (calendarScrollPaged) Modifier.testTag(TEST_TAG_WEEK_SCROLL_PAGE) else Modifier.testTag(
                            TEST_TAG_WEEK_SCROLL_NORMAL
                        )
                    ),
            ) {
                weekHeader?.invoke(this, week)
                Row {
                    for (date in week.days) {
                        Box(
                            modifier = Modifier
                                .then(if (calendarScrollPaged) Modifier.weight(1f) else Modifier)
                                .clipToBounds(),
                        ) {
                            dayContent(date)
                        }
                    }
                }
                weekFooter?.invoke(this, week)
            }
        }
    }
}

const val TEST_TAG_WEEK_ROW = "TEST_TAG_WEEK_ROW"
const val TEST_TAG_WEEK_SCROLL_PAGE = "TEST_TAG_WEEK_SCROLL_PAGE"
const val TEST_TAG_WEEK_SCROLL_NORMAL = "TEST_TAG_WEEK_SCROLL_NORMAL"
