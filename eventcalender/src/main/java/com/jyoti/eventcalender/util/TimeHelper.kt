package com.jyoti.eventcalender.util

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

// E.g DayOfWeek.SATURDAY.daysUntil(DayOfWeek.TUESDAY) = 3
fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (other.value - value)) % 7

/**
 * Returns a [LocalDate] at the start of the month.
 *
 * Complements [YearMonth.atEndOfMonth].
 */
fun YearMonth.atStartOfMonth(): LocalDate = this.atDay(1)

/**
 * Returns the first day of the week from the default locale.
 */
fun firstDayOfWeek(): DayOfWeek = DayOfWeek.MONDAY

/**
 * Returns the days of week values such that the desired
 * [firstDayOfWeek] property is at the start position.
 *
 * @see [firstDayOfWeek]
 */
@JvmOverloads
fun daysOfWeek(firstDayOfWeek: DayOfWeek = firstDayOfWeek()): List<DayOfWeek> {
    val pivot = 7 - firstDayOfWeek.ordinal
    val daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at the start position.
    return (daysOfWeek.takeLast(pivot) + daysOfWeek.dropLast(pivot))
}

fun DayOfWeek.displayText(
    textStyle: TextStyle = TextStyle.SHORT,
    uppercase: Boolean = false
): String {
    return getDisplayName(textStyle, Locale.getDefault()).let { value ->
        if (uppercase) value.uppercase(Locale.getDefault()) else value
    }
}

fun checkDateRange(startMonth: YearMonth, endMonth: YearMonth) {
    check(endMonth >= startMonth) {
        "startMonth: $startMonth is greater than endMonth: $endMonth"
    }
}

val LocalDate.yearMonth: YearMonth
    get() = YearMonth.of(year, month)

val YearMonth.nextMonth: YearMonth
    get() = this.plusMonths(1)

val YearMonth.previousMonth: YearMonth
    get() = this.minusMonths(1)

fun Long.secToLocalDate(timeZone: String? = UTC): LocalDate {
    return Instant.ofEpochSecond(this).atZone(getZoneId(timeZone)).toLocalDate()
}

fun Long.secToLocalDatetime(timeZone: String? = UTC): LocalDateTime {
    return Instant.ofEpochSecond(this).atZone(getZoneId(timeZone)).toLocalDateTime()
}

fun getZoneId(timeZone: String?): ZoneId {
    return timeZone?.let {
        ZoneId.of(it)
    } ?: run {
        ZoneId.systemDefault()
    }
}

const val UTC = "UTC"