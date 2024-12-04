package com.jyoti.eventcalender.ui

import androidx.annotation.DimenRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.theme.ColorGrey
import com.jyoti.eventcalender.theme.ColorText

@Composable
fun PlainStatusBadgeView(
    containerColor: Color,
    @DimenRes corner: Int = R.dimen.spacing_micro_plus,
    content: @Composable() (BoxScope.() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(dimensionResource(id = corner)))
            .background(color = containerColor)
            .padding(
                horizontal = dimensionResource(id = R.dimen.spacing_normal),
                vertical = dimensionResource(
                    id = R.dimen.spacing_micro
                )
            )
    ) {
        content?.invoke(this)
    }
}
@Composable
fun PlainStatusBadgeView(statusText: Int, containerColor: Color = ColorGrey._50.id, textColor: Color = ColorText.Base.id) {
    PlainStatusBadgeView(
        stringResource(id = statusText),
        containerColor = containerColor,
        textColor = textColor,
    )
}

@Composable
fun PlainStatusBadgeView(statusText: String, containerColor: Color = ColorGrey._50.id, textColor: Color = ColorText.Base.id) {
    PlainStatusBadgeView(containerColor) {
        TextBodySmallSemiBold(
            color = textColor,
            text = statusText,
            modifier =
            Modifier
                .testTag(statusText)
                .padding(start = dimensionResource(id = R.dimen.spacing_micro))
        )
    }
}

//@Preview
//@Composable
//fun StatusBadgePreView() {
//    Column {
//        PlainStatusBadgeView(
//            R.string.waiting
//        )
//    }
//}
//
//@Preview
//@Composable
//fun StatusBadgeDropPreView() {
//    Column {
//        PlainStatusBadgeView(
//            R.string.drop,
//            containerColor = ColorPrimary._50.id
//        )
//    }
//}
//
//@Preview
//@Composable
//fun StatusBadgeSuccessPreView() {
//    Column {
//        PlainStatusBadgeView(
//            R.string.approved,
//            containerColor = ColorSuccess._50.id,
//            textColor = ColorSuccess._700.id,
//        )
//    }
//}
//
//@Preview
//@Composable
//fun StatusBadgeErrorPreView() {
//    Column {
//        PlainStatusBadgeView(
//            R.string.rejected,
//            containerColor = ColorError._50.id,
//            textColor = ColorError._700.id,
//        )
//    }
//}
//
//@Preview
//@Composable
//fun StatusBadgeWaitingPreView() {
//    Column {
//        PlainStatusBadgeView(
//            R.string.waiting_for_approval,
//            containerColor = ColorGrey._50.id,
//            textColor = ColorGrey.Base.id,
//        )
//    }
//}
