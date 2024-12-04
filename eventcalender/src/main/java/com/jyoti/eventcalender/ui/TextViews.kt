package com.jyoti.eventcalender.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.theme.CalenderTheme
import com.jyoti.eventcalender.theme.ColorError
import com.jyoti.eventcalender.theme.ColorGrey
import com.jyoti.eventcalender.theme.ColorText
import com.jyoti.eventcalender.theme.ColorWarning

@Composable
fun TextBodyMediumBold(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ColorText.Base.id
) {
    Text(
        text = text,
        modifier = modifier,
        style = CalenderTheme.typography.bodyMediumBold,
        color = color
    )
}

@Composable
fun TextBodySmallSemiBold(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ColorText.Base.id,
) {
    Text(
        text = text,
        modifier = modifier,
        style = CalenderTheme.typography.bodySmallSemiBold,
        color = color
    )
}

@Composable
fun TextBodyMediumSemiBold(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ColorText.Base.id
) {
    Text(
        text = text,
        modifier = modifier
            .padding(top = dimensionResource(id = R.dimen.spacing_medium)),
        style = CalenderTheme.typography.bodyMediumSemiBold,
        color = color
    )
}

@Composable
fun InformativeText(modifier: Modifier = Modifier, stringId: Int, vararg: Any?, tag: String) {
    Text(
        text = vararg?.let { stringResource(id = stringId, it) } ?: stringResource(id = stringId),
        modifier = modifier
            .fillMaxWidth()
            .testTag(tag),
        textAlign = TextAlign.Center,
        style = CalenderTheme.typography.bodySmallSemiBold,
        color = ColorText.OTHER.id
    )
}

@Composable
fun ShowSupportText(text: String, modifier: Modifier = Modifier, isError: Boolean = false) {
    Text(
        text = text,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_micro)),
        style = if (isError) CalenderTheme.typography.bodySmallRegular else CalenderTheme.typography.bodySmallSemiBold,
        color = if (isError) ColorError.Base.id else ColorGrey._300.id
    )
}

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int,
    color: Color = ColorWarning._50.id
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_normal_plus)),
        color = color,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_irrational)),
        ) {
            LoadIcon(
                icon = Icon.DrawableResourceIcon(iconRes),
                tintColor = ColorWarning._700.id
            )
            Text(
                text = text,
                modifier = modifier.padding(start = dimensionResource(id = R.dimen.padding_micro)),
                style = MaterialTheme.typography.bodySmall,
                color = ColorWarning._700.id
            )
        }
    }
}

@Preview
@Composable
private fun HeaderTextPreview() {
    TextBodyMediumBold(text = "Header")
}

@Preview
@Composable
private fun TitleTextPreview() {
    TextBodySmallSemiBold(text = "Title")
}
