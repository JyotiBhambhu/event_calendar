package com.jyoti.eventcalender.month.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import com.jyoti.eventcalender.eventcalendar.model.EventCalendarState
import com.getir.gtcalendar.v2.month.model.CalendarDay
import com.getir.gtcalendar.v2.month.model.CalendarMonth
import com.getir.gtcalendar.v2.month.model.ContentHeightMode
import com.getir.gtcalendar.v2.month.model.DayPosition
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.core.compose.CalendarDefaults
import com.jyoti.eventcalender.core.compose.Day
import java.time.LocalDate

@Composable
fun MonthCalendar(
    uiState: EventCalendarState,
    monthCalendarHeight: Dp,
    contentHeightMode: ContentHeightMode = ContentHeightMode.Wrap,
    selectDate: (LocalDate) -> Unit,
) {
    val monthCalendarAlpha by animateFloatAsState(
        if (uiState.calendarState.isWeekMode) 0f else 1f,
        label = "Month Calendar"
    )
    HorizontalCalendar(
        modifier = Modifier
            .background(Color.White)
            .height(monthCalendarHeight)
            .alpha(monthCalendarAlpha)
            .zIndex(if (uiState.calendarState.isWeekMode) 0f else 1f),
        state = uiState.calendarState.monthState,
        dayContent = { day ->
            val isSelectable = day.position == DayPosition.MonthDate
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
        contentHeightMode = contentHeightMode
    )
}

/**
 * A horizontally scrolling calendar.
 *
 * @param modifier the modifier to apply to this calendar.
 * @param state the state object to be used to control or observe the calendar's properties.
 * Examples: `startMonth`, `endMonth`, `firstDayOfWeek`, `firstVisibleMonth`, `outDateStyle`.
 * @param calendarScrollPaged the scrolling behavior of the calendar. When `true`, the calendar will
 * snap to the nearest month after a scroll or swipe action. When `false`, the calendar scrolls normally.
 * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions
 * is allowed. You can still scroll programmatically using the state even when it is disabled.
 * @param reverseLayout reverse the direction of scrolling and layout. When `true`, months will be
 * composed from the end to the start and [CalendarState.startMonth] will be located at the end.
 * @param contentPadding a padding around the whole calendar. This will add padding for the
 * content after it has been clipped, which is not possible via [modifier] param. You can use it
 * to add a padding before the first month or after the last one. If you want to add a spacing
 * between each month use the [monthContainer] composable.
 * @param contentHeightMode Determines how the height of the day content is calculated.
 * @param dayContent a composable block which describes the day content.
 * @param monthHeader a composable block which describes the month header content. The header is
 * placed above each month on the calendar.
 * @param monthBody a composable block which describes the month body content. This is the container
 * where all the month days are placed, excluding the header and footer. This is useful if you
 * want to customize the day container, for example, with a background color or other effects.
 * The actual body content is provided in the block and must be called after your desired
 * customisations are rendered.
 * @param monthFooter a composable block which describes the month footer content. The footer is
 * placed below each month on the calendar.
 * @param monthContainer a composable block which describes the entire month content. This is the
 * container where all the month contents are placed (header => days => footer). This is useful if
 * you want to customize the month container, for example, with a background color or other effects.
 * The actual container content is provided in the block and must be called after your desired
 * customisations are rendered.
 */
@Composable
private fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    state: CalendarState = rememberCalendarState(),
    calendarScrollPaged: Boolean = true,
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(dimensionResource(id = R.dimen.no_spacing)),
    contentHeightMode: ContentHeightMode = ContentHeightMode.Wrap,
    dayContent: @Composable BoxScope.(CalendarDay) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)? = null,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)? = null,
) = LazyRow(
    modifier = modifier,
    state = state.listState,
    flingBehavior = CalendarDefaults.flingBehavior(calendarScrollPaged, state.listState),
    userScrollEnabled = userScrollEnabled,
    reverseLayout = reverseLayout,
    contentPadding = contentPadding,
) {
    calendarMonths(
        monthCount = state.monthIndexCount,
        monthData = { offset -> state.store[offset] },
        contentHeightMode = contentHeightMode,
        dayContent = dayContent,
        monthHeader = monthHeader,
        monthBody = monthBody,
        monthFooter = monthFooter,
        monthContainer = monthContainer,
    )
}

private fun LazyListScope.calendarMonths(
    monthCount: Int,
    monthData: (offset: Int) -> CalendarMonth,
    contentHeightMode: ContentHeightMode,
    dayContent: @Composable BoxScope.(CalendarDay) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)?,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)?,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)?,
) {
    items(
        count = monthCount,
        key = { offset -> monthData(offset).yearMonth },
    ) { offset ->
        val month = monthData(offset)
        val fillHeight = when (contentHeightMode) {
            ContentHeightMode.Wrap -> false
            ContentHeightMode.Fill -> true
        }
        val hasContainer = monthContainer != null
        monthContainer.or(defaultMonthContainer)(month) {
            Column(
                modifier = Modifier
                    .then(if (hasContainer) Modifier.fillMaxWidth() else Modifier.fillParentMaxWidth())
                    .then(
                        if (fillHeight) {
                            if (hasContainer) Modifier.fillMaxHeight() else Modifier.fillParentMaxHeight()
                        } else {
                            Modifier.wrapContentHeight()
                        },
                    )
                    .then(
                        if (fillHeight) Modifier.testTag(TEST_TAG_MONTH_FILL) else Modifier.testTag(
                            TEST_TAG_MONTH_WRAP
                        )
                    ),
            ) {
                monthHeader?.invoke(this, month)
                monthBody.or(defaultMonthBody)(month) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(if (fillHeight) Modifier.weight(1f) else Modifier.wrapContentHeight()),
                    ) {
                        for (week in month.weekDays) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(if (fillHeight) Modifier.weight(1f) else Modifier.wrapContentHeight()),
                            ) {
                                for (day in week) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clipToBounds(),
                                    ) {
                                        dayContent(day)
                                    }
                                }
                            }
                        }
                    }
                }
                monthFooter?.invoke(this, month)
            }
        }
    }
}

private val defaultMonthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit) =
    { _, container -> container() }

private val defaultMonthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit) =
    { _, content -> content() }

private fun <T> T?.or(default: T) = this ?: default

const val TEST_TAG_MONTH_FILL = "TEST_TAG_MONTH_FILL"
const val TEST_TAG_MONTH_WRAP = "TEST_TAG_MONTH_WRAP"
