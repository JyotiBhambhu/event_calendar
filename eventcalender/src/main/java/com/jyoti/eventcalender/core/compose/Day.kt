package com.jyoti.eventcalender.core.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.getir.gtcalendar.v2.eventcalendar.TestTags
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.eventcalendar.model.EventUIConfig
import com.jyoti.eventcalender.eventcalendar.model.UIEvent
import com.jyoti.eventcalender.extensions.clickNotDuplicate
import com.jyoti.eventcalender.theme.CalenderTheme
import com.jyoti.eventcalender.theme.ColorGrey
import com.jyoti.eventcalender.theme.ColorPrimary
import com.jyoti.eventcalender.theme.ColorText
import com.jyoti.eventcalender.util.UIText
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun Day(
    day: LocalDate,
    isCurrentDay: Boolean,
    isSelected: Boolean,
    isSelectable: Boolean,
    dayEvents: List<UIEvent>?,
    onClick: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(dimensionResource(id = R.dimen.spacing_micro_plus))
            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_normal)))
            .background(color = if (isCurrentDay) ColorPrimary._50.id else if (isSelected) ColorGrey._50.id else Color.Transparent)
            .clickNotDuplicate(
                enabled = isSelectable,
                haveIndication = !isSelected,
                onClick = { onClick(day) },
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textColor = when {
            isSelectable -> ColorText.Base.id
            else -> ColorText.DISABLED.id
        }
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            style = CalenderTheme.typography.bodyMediumSemiBold
        )
        Row(modifier = Modifier.testTag(TestTags.DAY_EVENT_ROW)) {
//            dayEvents?.sortFilterDots()
//                ?.forEach { uiEvent ->
//                    Box(
//                        modifier = Modifier
//                            .padding(dimensionResource(id = R.dimen.spacing_line))
//                            .size(dimensionResource(id = R.dimen.spacing_micro))
//                            .clip(CircleShape)
//                            .background(uiEvent.uiConfig.color)
//                            .testTag(uiEvent.id)
//                    )
//                }
        }
    }
}

@Composable
@Preview
fun CurrentDayPreview() {
    Day(
        day = LocalDate.now(),
        isCurrentDay = true,
        isSelected = false,
        isSelectable = false,
        dayEvents = listOf(),
        onClick = {}
    )
}

@Composable
@Preview
fun SelectDayPreview() {
    Day(
        day = LocalDate.now(), isCurrentDay = false, isSelected = true, isSelectable = true,
        dayEvents = listOf(
            UIEvent(
                id = "123",
                name = UIText.DynamicString("Event"),
                repeatRule = 1,
                LocalDateTime.now(),
                uiConfig = EventUIConfig()
            )
        ),
        onClick = {}
    )
}
