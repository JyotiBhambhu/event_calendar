package com.jyoti.eventcalender.eventcalendar.model

import androidx.compose.ui.graphics.Color
import com.getir.gtcalendar.v2.month.model.OutDateStyle
import com.jyoti.eventcalender.base.State
import com.jyoti.eventcalender.data.Event
import com.jyoti.eventcalender.month.compose.CalendarState
import com.jyoti.eventcalender.theme.ColorBackground
import com.jyoti.eventcalender.theme.ColorPrimary
import com.jyoti.eventcalender.util.UIText
import com.jyoti.eventcalender.util.atStartOfMonth
import com.jyoti.eventcalender.util.daysOfWeek
import com.jyoti.eventcalender.util.secToLocalDatetime
import com.jyoti.eventcalender.util.yearMonth
import com.jyoti.eventcalender.week.compose.WeekCalendarState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

const val ADJACENT_MONTHS: Long = 500

/**
 * UI State
 */

data class EventCalendarState(
    val selectedDate: LocalDate?,
    val selectDateEvents: List<UIEvent>,
    val events: Map<LocalDate, List<UIEvent>>,
    val calendarState: CalendarUIState,
    val isLoading: Boolean = false,
    val error: String? = null
): State {
    companion object {
        val initial = EventCalendarState(
            selectedDate = LocalDate.now(),
            selectDateEvents = listOf(),
            events = mapOf(),
            isLoading = false,
            error = null,
            calendarState = CalendarUIState(),
        )
    }
}

data class CalendarUIState(
    val currentDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = currentDate.yearMonth,
    val startMonth: YearMonth = currentMonth.minusMonths(ADJACENT_MONTHS),
    val endMonth: YearMonth = currentMonth.plusMonths(ADJACENT_MONTHS),
    val daysOfWeek: List<DayOfWeek> = daysOfWeek(),
    val weekState: WeekCalendarState = WeekCalendarState(
        startDate = startMonth.atStartOfMonth(),
        endDate = endMonth.atEndOfMonth(),
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = daysOfWeek.first(),
        visibleItemState = null,
    ),
    val monthState: CalendarState = CalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfRow,
        visibleItemState = null
    ),
    val isWeekMode: Boolean = false,
    val isWeekModeToggle: Boolean = false
)

data class UIEvent(
    val id: String,
    val name: UIText,
    val repeatRule: Int = 0,
    val date: LocalDateTime? = null,
    val uiConfig: EventUIConfig,
)

data class EventUIConfig(
    val id: Long = 1,
    val color: Color = ColorPrimary.Base.id,
    val bgColor: Color = ColorBackground.Body.id,
    var icon: Int = 0,
    val title: Int? = null,
    val statusText: Int? = null,
    var priority: Int = 1
)

fun Event.toUiEvent() =
    UIEvent(
        id = id,
        name = title,
        repeatRule = repeatRule,
        date = startTS.secToLocalDatetime(),
        uiConfig = uiConfig,
    )
