package com.jyoti.eventcalender.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object CalenderIcons {
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
    val ArrowForward = Icons.AutoMirrored.Rounded.KeyboardArrowRight
    val ArrowDropDown = Icons.Rounded.KeyboardArrowDown
    val ArrowDropUp = Icons.Rounded.KeyboardArrowUp
    val CalendarMonth = Icons.Rounded.CalendarMonth
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
