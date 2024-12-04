package com.jyoti.eventcalender.core.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.getir.gtcalendar.v2.eventcalendar.TestTags.CALENDAR_TITLE
import com.getir.gtcalendar.v2.eventcalendar.TestTags.LEFT_ARROW
import com.getir.gtcalendar.v2.eventcalendar.TestTags.RIGHT_ARROW
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.eventcalendar.EventCalendarIntent
import com.jyoti.eventcalender.eventcalendar.model.CalendarUIState
import com.jyoti.eventcalender.extensions.clickNotDuplicate
import com.jyoti.eventcalender.theme.CalenderTheme
import com.jyoti.eventcalender.theme.ColorBlack
import com.jyoti.eventcalender.theme.ColorGrey
import com.jyoti.eventcalender.theme.ColorPrimary
import com.jyoti.eventcalender.theme.ColorText
import com.jyoti.eventcalender.ui.CalenderIcons.ArrowBack
import com.jyoti.eventcalender.ui.CalenderIcons.ArrowForward
import com.jyoti.eventcalender.ui.CalenderIcons.CalendarMonth
import com.jyoti.eventcalender.ui.Icon
import com.jyoti.eventcalender.ui.LoadIcon
import com.jyoti.eventcalender.ui.LoadRoundRectIconButton
import com.jyoti.eventcalender.ui.RoundRectButtonProperties
import com.jyoti.eventcalender.util.displayText
import com.jyoti.eventcalender.util.nextMonth
import com.jyoti.eventcalender.util.previousMonth
import com.jyoti.eventcalender.util.yearMonth
import kotlinx.coroutines.launch
import java.time.YearMonth

@Composable
internal fun CalendarTitle(
    calendarState: CalendarUIState = CalendarUIState(),
    weekModeToggled: (Boolean) -> Unit = {},
    onIntent: (EventCalendarIntent) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(calendarState.monthState)
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(calendarState.weekState)
    val visibleWeekMonth = visibleWeek.days.first().date.yearMonth
    val currentMonth =
        if (calendarState.isWeekMode) {
            visibleWeekMonth
        } else {
            visibleMonth.yearMonth
        }
    CalendarTitleContent(
        currentMonth = currentMonth,
        isWeekMode = calendarState.isWeekMode,
        goToPrevious = {
            onIntent(EventCalendarIntent.GoToPrev)
            coroutineScope.launch {
                if (calendarState.isWeekMode) {
                    val targetDate =
                        calendarState.weekState.firstVisibleWeek.days.first().date.minusDays(1)
                    calendarState.weekState.animateScrollToWeek(targetDate)
                } else {
                    val targetMonth =
                        calendarState.monthState.firstVisibleMonth.yearMonth.previousMonth
                    calendarState.monthState.animateScrollToMonth(targetMonth)
                }
                onIntent(EventCalendarIntent.ScrollDone())
            }
        },
        goToNext = {
            onIntent(EventCalendarIntent.GoToNext)
            coroutineScope.launch {
                if (calendarState.isWeekMode) {
                    val targetDate =
                        calendarState.weekState.firstVisibleWeek.days.last().date.plusDays(1)
                    calendarState.weekState.animateScrollToWeek(targetDate)
                } else {
                    val targetMonth =
                        calendarState.monthState.firstVisibleMonth.yearMonth.nextMonth
                    calendarState.monthState.animateScrollToMonth(targetMonth)
                }
                onIntent(EventCalendarIntent.ScrollDone())
            }
        },
        weekModeToggled = weekModeToggled
    )
}

@Composable
fun CalendarTitleContent(
    currentMonth: YearMonth = YearMonth.now(),
    isWeekMode: Boolean = true,
    goToPrevious: () -> Unit = {},
    goToNext: () -> Unit = {},
    weekModeToggled: (isWeekMode: Boolean) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(dimensionResource(id = R.dimen.padding_normal)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            icon = Icon.ImageVectorIcon(ArrowBack),
            onClick = goToPrevious,
            testTag = LEFT_ARROW
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .testTag(CALENDAR_TITLE),
            color = ColorText.Base.id,
            text = currentMonth.displayText(),
            style = CalenderTheme.typography.bodyLargeSemiBold,
            textAlign = TextAlign.Center,
        )
        CalendarNavigationIcon(
            icon = Icon.ImageVectorIcon(ArrowForward),
            onClick = goToNext,
            testTag = RIGHT_ARROW
        )
        WeekModeToggle(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_normal)
                ),
            isWeekMode = isWeekMode, weekModeToggled = weekModeToggled
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    modifier: Modifier = Modifier,
    icon: Icon,
    testTag: String,
    onClick: () -> Unit,
) = Box(
    modifier = modifier
        .wrapContentSize()
        .clip(shape = CircleShape)
        .clickNotDuplicate(role = Role.Button) { onClick() },
) {
    LoadIcon(
        icon = icon,
        tintColor = ColorGrey._200.id,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.spacing_micro))
            .testTag(testTag)
            .align(Alignment.Center),
    )
}

@Composable
private fun WeekModeToggle(
    modifier: Modifier = Modifier,
    isWeekMode: Boolean,
    weekModeToggled: (isWeekMode: Boolean) -> Unit,
) = LoadRoundRectIconButton(
    icon = Icon.ImageVectorIcon(CalendarMonth),
    modifier = modifier.padding(
        start = dimensionResource(id = R.dimen.padding_normal)
    ),
    properties = RoundRectButtonProperties(
        backgroundColor = ColorBlack._50.id,
        role = Role.Switch,
        tintColor = if (isWeekMode) ColorGrey.Base.id else ColorPrimary.Base.id
    ),
    onClick = { weekModeToggled(!isWeekMode) },
)

@Composable
@Preview
fun CalendarTitlePreview() {
    CalendarTitleContent()
}
