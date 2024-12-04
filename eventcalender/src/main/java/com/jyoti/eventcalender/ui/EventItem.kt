package com.jyoti.eventcalender.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.eventcalendar.model.UIEvent
import com.jyoti.eventcalender.util.UIText

@Composable
fun EventItem(item: UIEvent) {
    Row(
        modifier = Modifier
            .testTag(EVENT_ROW)
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_normal_plus)),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        item.uiConfig.let { uiConfig ->
            LoadIconWithBackground(
                icon = Icon.DrawableResourceIcon(uiConfig.icon),
                tintColor = uiConfig.color,
                backgroundColor = uiConfig.bgColor,
                wrapSize = false,
                size = R.dimen.spacing_normal_plus_plus,
                modifier = Modifier
                    .testTag(EVENT_ICON)
                    .padding(end = dimensionResource(id = R.dimen.padding_small))
                    .align(Alignment.CenterVertically)
            )
        }
        val title = item.uiConfig.title?.let { UIText.StringResource(it) }?.asString()
        ColumnTitleDescription(
            modifier = Modifier
                .weight(1.0f)
                .padding(end = dimensionResource(id = R.dimen.padding_small)),
            titleTextModel = TextModel(
                text = UIText.DynamicString(title ?: ""),
                textTag = EVENT_TITLE
            ),
            descTextModel = TextModel(
                text = item.name,
                textTag = EVENT_DESC
            )
        )
        item.uiConfig.statusText?.let {
            PlainStatusBadgeView(
                it
            )
        }
    }
}

//@Composable
//@Preview
//private fun Preview() {
//    EventItem(
//        item = UIEvent(
//            id = "123",
//            type = 100,
//            name = UIText.DynamicString("Shift"),
//            uiConfig = EventUIConfigV2.SHIFT_APPROVED,
//            warehouseName = "Warehouse Name",
//            isShiftContinue = true,
//            personRole = 200
//        )
//    )
//}


const val EVENT_ROW = "EVENT_ROW"
const val EVENT_TITLE = "EVENT_TITLE"
const val EVENT_DESC = "EVENT_DESC"
const val EVENT_ICON = "EVENT_ICON"
