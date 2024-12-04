package com.jyoti.eventcalender.core.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jyoti.eventcalender.theme.CalenderTheme
import com.jyoti.eventcalender.theme.ColorText
import com.jyoti.eventcalender.util.daysOfWeek
import com.jyoti.eventcalender.util.displayText
import java.time.DayOfWeek

@Composable
fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.displayText(),
                color = ColorText.DISABLED.id,
                style = CalenderTheme.typography.bodyMediumSemiBold,
            )
        }
    }
}
@Composable
@Preview
fun CalendarHeaderPreview() {
    CalendarHeader(daysOfWeek = daysOfWeek())
}
