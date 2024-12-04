package com.jyoti.eventcalender.ui

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import coil.compose.AsyncImage
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.extensions.clickNotDuplicate
import com.jyoti.eventcalender.theme.ColorGrey
import com.jyoti.eventcalender.theme.ColorPrimary

@Composable
fun LoadUrl(
    url: String?,
    defaultIcon: Icon,
    modifier: Modifier = Modifier,
    @StringRes contentDesc: Int,
    @ColorRes tintColorId: Int? = null,
    tintColor: Color? = null,
) {
    if (url.isNullOrEmpty()) {
        GetIcon(
            icon = defaultIcon,
            modifier = modifier,
            contentDesc = contentDesc,
            tintColorId = tintColorId,
            tintColor = tintColor
        )
    } else {
        AsyncImage(
            model = url,
            contentDescription = stringResource(contentDesc),
            modifier = modifier
        )
    }
}

@Composable
fun LoadIcon(
    icon: Icon,
    modifier: Modifier = Modifier,
    @StringRes contentDesc: Int? = null,
    @DimenRes size: Int = 0,
    wrapSize: Boolean = true,
    @ColorRes tintColorId: Int? = null,
    tintColor: Color? = null
) {
    GetIcon(
        icon = icon,
        modifier = if (wrapSize) modifier.wrapContentSize() else modifier.size(dimensionResource(id = size)),
        contentDesc = contentDesc,
        tintColorId = tintColorId,
        tintColor = tintColor
    )
}

@Composable
fun LoadIconWithBackground(
    modifier: Modifier = Modifier,
    icon: Icon,
    @StringRes contentDesc: Int? = null,
    @DimenRes size: Int = R.dimen.no_spacing,
    wrapSize: Boolean = true,
    tintColor: Color? = null,
    backgroundColor: Color = ColorPrimary._50.id
) {
    LoadIcon(
        icon = icon,
        contentDesc = contentDesc,
        modifier = modifier
            .clip(CircleShape)
            .background(color = backgroundColor)
            .padding(dimensionResource(id = R.dimen.spacing_normal_plus)),
        size = size,
        wrapSize = wrapSize,
        tintColor = tintColor
    )
}

@Composable
fun LoadIconWithBgWithIrrationalPadding(
    modifier: Modifier = Modifier,
    icon: Icon,
    @StringRes contentDesc: Int? = null,
    @DimenRes size: Int = R.dimen.no_spacing,
    wrapSize: Boolean = true,
    tintColor: Color? = null,
    backgroundColor: Color = ColorPrimary._50.id
) {
    LoadIcon(
        icon = icon,
        contentDesc = contentDesc,
        modifier = modifier
            .clip(CircleShape)
            .background(color = backgroundColor)
            .padding(
                horizontal = dimensionResource(id = R.dimen.spacing_irrational),
                vertical = dimensionResource(id = R.dimen.spacing_normal_plus)
            ),
        size = size,
        wrapSize = wrapSize,
        tintColor = tintColor
    )
}

@Composable
private fun GetIcon(
    icon: Icon,
    modifier: Modifier,
    @StringRes contentDesc: Int? = null,
    @ColorRes tintColorId: Int?,
    tintColor: Color? = null
) = when (icon) {
    is Icon.ImageVectorIcon -> Icon(
        modifier = modifier,
        imageVector = icon.imageVector,
        tint = tintColor ?: tintColorId?.let { colorResource(id = it) } ?: Color.Unspecified,
        contentDescription = contentDesc?.let { stringResource(it) }
    )

    is Icon.DrawableResourceIcon -> Icon(
        modifier = modifier,
        tint = tintColor ?: tintColorId?.let { colorResource(id = it) } ?: Color.Unspecified,
        painter = painterResource(id = icon.id),
        contentDescription = contentDesc?.let { stringResource(it) }
    )
}

data class RoundRectButtonProperties(
    @DimenRes val size: Int = 0,
    val wrapSize: Boolean = true,
    val tintColor: Color? = null,
    val backgroundColor: Color = ColorGrey._50.id,
    val clickable: Boolean = true,
    val role: Role = Role.Button,
)

@Composable
fun LoadRoundRectIconButton(
    modifier: Modifier = Modifier,
    icon: Icon,
    properties: RoundRectButtonProperties,
    onClick: () -> Unit = {}
) {
    with(properties) {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_normal)))
                .background(backgroundColor)
                .size(dimensionResource(id = R.dimen.size_small_lite))
                .clickNotDuplicate(enabled = clickable, role = role, onClick = onClick)
        ) {
            LoadIcon(
                icon = icon,
                tintColor = tintColor,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.spacing_micro))
                    .align(Alignment.Center),
                size = size,
                wrapSize = wrapSize
            )
        }
    }
}
