package com.jyoti.eventcalender.util

import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

private fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.getDefault())
}
