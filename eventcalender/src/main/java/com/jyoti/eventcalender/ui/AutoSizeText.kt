package com.jyoti.eventcalender.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.extensions.textDp

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun AutoSizeText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    text: String,
    testTag: String,
    style: TextStyle,
    color: Color,
) {

    val minFontSize = dimensionResource(id = R.dimen.textview_text_size_nano).textDp
    BoxWithConstraints(modifier = modifier) {
        var shrunkFontSize = style.fontSize
        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text = text,
                style = TextStyle(
                    fontSize = shrunkFontSize,
                    fontWeight = style.fontWeight,
                    lineHeight = style.lineHeight,
                    fontFamily = style.fontFamily,
                ),
                density = LocalDensity.current,
                fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
            )
        }

        var intrinsics = calculateIntrinsics()
        with(LocalDensity.current) {
            while (intrinsics.maxIntrinsicWidth > maxWidth.toPx()) {
                shrunkFontSize *= TEXT_SCALE_REDUCTION_INTERVAL
                intrinsics = calculateIntrinsics()
            }
        }

        if (shrunkFontSize.value < minFontSize.value) {
            shrunkFontSize = minFontSize
        }

        Text(
            text = text,
            modifier = Modifier.testTag(testTag),
            color = color,
            textAlign = textAlign,
            style = TextStyle(
                fontSize = shrunkFontSize,
                fontWeight = style.fontWeight,
                lineHeight = style.lineHeight,
                fontFamily = style.fontFamily,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
