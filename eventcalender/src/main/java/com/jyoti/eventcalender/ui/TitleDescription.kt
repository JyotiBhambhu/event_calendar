package com.jyoti.eventcalender.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.theme.CalenderTheme
import com.jyoti.eventcalender.theme.ColorText
import com.jyoti.eventcalender.util.UIText

data class TextModel(
    val text: UIText,
    val textTag: String,
    val style: TextStyle? = null,
    val color: Color? = null
)

@Composable
fun ColumnTitleDescription(
    modifier: Modifier = Modifier,
    titleTextModel: TextModel? = null,
    descTextModel: TextModel? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    vertical: Arrangement.Vertical = Arrangement.Center,
    textAlign: TextAlign = TextAlign.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = vertical
    ) {
        titleTextModel?.apply {
            Text(
                text = text.asString(),
                modifier = Modifier.testTag(textTag),
                style = style ?: CalenderTheme.typography.bodyMediumSemiBold,
                color = color ?: ColorText.Base.id,
                textAlign = textAlign
            )
        }

        descTextModel?.apply {
            if (text.asString().isNotEmpty()) {
                Text(
                    text = text.asString(),
                    modifier = Modifier.testTag(textTag),
                    style = style ?: CalenderTheme.typography.bodyMediumSemiBold,
                    color = color ?: ColorText.DISABLED.id,
                    textAlign = textAlign
                )
            }
        }
    }
}

@Composable
fun ColumnTitleDescriptionAnnotated(
    modifier: Modifier = Modifier,
    titleTextModel: TextModel? = null,
    descTextModel: TextModel? = null,
    textAlign: TextAlign = TextAlign.Start,
    delim: UIText = UIText.DynamicString("/"),
) {
    Column(
        modifier = modifier
            .wrapContentWidth(),
    ) {
        titleTextModel?.apply {
            AutoSizeText(
                textAlign = textAlign,
                text = text.asString(),
                style = style ?: CalenderTheme.typography.bodyMediumSemiBold,
                color = color ?: ColorText.Base.id,
                testTag = textTag
            )
        }

        val partAt = delim.asString()
        descTextModel?.apply {
            val textToDisplay = text.asString()

            if (textToDisplay.contains(partAt)) {
                AnnotatedText(
                    textToDisplay, textTag, partAtIndex = textToDisplay.indexOf(partAt)
                )
            } else {
                AutoSizeText(
                    textAlign = textAlign,
                    text = text.asString(),
                    style = style ?: CalenderTheme.typography.bodyMediumSemiBold,
                    color = color ?: ColorText.DISABLED.id,
                    testTag = textTag
                )
            }
        }
    }
}

@Composable
fun AnnotatedText(
    text: String,
    textTag: String,
    textStyleA: TextStyle = TextStyle(color = ColorText.Base.id),
    textStyleB: TextStyle = TextStyle(
        color = ColorText.DISABLED.id,
        fontSize = dimensionResource(id = R.dimen.textview_text_size_small).value.sp
    ),
    partAtIndex: Int,
) {
    val s1 = text.substring(0, partAtIndex)
    val s2 = text.substring(partAtIndex, text.length)
    Row(modifier = Modifier.testTag(textTag), verticalAlignment = Alignment.CenterVertically) {
        AutoSizeText(text = s1, testTag = s1, style = textStyleA, color = textStyleA.color)
        AutoSizeText(text = s2, testTag = s2, style = textStyleB, color = textStyleB.color)
    }
}

@Preview
@Composable
fun AnnotatedTextPreview() {
    val annotatedText = "12 July / XYZ Warehouse ABC DEF GH IJK LMN OPQ RST UV WXYZ ABc def"
    AnnotatedText(
        annotatedText, "textTag", TextStyle(color = ColorText.Base.id),
        TextStyle(
            color = ColorText.DISABLED.id,
            fontSize = dimensionResource(id = R.dimen.textview_text_size_small).value.sp
        ),
        annotatedText.indexOf("/")
    )
}

@Preview
@Composable
fun AnnotatedTextShortPreview() {
    val annotatedText = "12 July / XYZ Warehouse ABC DEF"
    AnnotatedText(
        annotatedText, "textTag", TextStyle(color = ColorText.Base.id),
        TextStyle(
            color = ColorText.DISABLED.id,
            fontSize = dimensionResource(id = R.dimen.textview_text_size_small).value.sp
        ),
        annotatedText.indexOf("/")
    )
}
