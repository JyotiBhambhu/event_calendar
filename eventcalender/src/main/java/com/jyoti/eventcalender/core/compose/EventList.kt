package com.jyoti.eventcalender.core.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.getir.gtcalendar.v2.eventcalendar.TestTags
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.eventcalendar.model.EventUIConfig
import com.jyoti.eventcalender.eventcalendar.model.UIEvent
import com.jyoti.eventcalender.theme.ColorPrimary
import com.jyoti.eventcalender.theme.ColorWhite
import com.jyoti.eventcalender.ui.CardView
import com.jyoti.eventcalender.ui.EventItem
import com.jyoti.eventcalender.util.TestTags.EVENT_LIST
import com.jyoti.eventcalender.util.UIText
import java.time.LocalDateTime

@Composable
fun EventList(
    uiEvents: List<UIEvent>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .testTag(EVENT_LIST)
            .padding(dimensionResource(id = R.dimen.spacing_normal_plus))
            .background(ColorWhite._50.id),
    ) {
        items(items = uiEvents) { item ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.spacing_micro)),
                contentAlignment = Alignment.Center
            ) {
                CardView(
                    modifier = Modifier.testTag(TestTags.EVENT_CARD),
                    isClickable = false,
                    onClick = {
                        onClick(item.id)
                    }
                ) {
                    EventItem(item = item)
                }
            }
        }
    }
}

@Composable
@Preview
fun EventListPreview() {
    EventList(
        uiEvents = listOf(
            UIEvent(
                id = "123",
                name = UIText.DynamicString("Event"),
                repeatRule = 1,
                LocalDateTime.now(),
                uiConfig = EventUIConfig(
                    id = 301,
                    color = ColorPrimary.Base.id,
                    bgColor = ColorPrimary._50.id,
                    icon = 0,
                    title = 0,
                    priority = 1
                )
            ),
        ),
    ) {}
}
