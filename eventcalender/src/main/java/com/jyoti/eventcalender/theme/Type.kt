package com.jyoti.eventcalender.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jyoti.eventcalender.R

val CalenderFont = FontFamily(
    fonts = listOf(
        Font(R.font.opensans_regular),
        Font(R.font.opensans_semibold, FontWeight.SemiBold),
        Font(R.font.opensans_bold, FontWeight.Bold)
    )
)

val LocalCalenderTypography = staticCompositionLocalOf {
    CalenderTypography(
        headingXLargeSemiBold = TextStyle.Default,
        headingXLargeBold = TextStyle.Default,
        headingLargeBold = TextStyle.Default,
        headingLargeSemiBold = TextStyle.Default,

        headingMediumBold = TextStyle.Default,
        headingMediumSemiBold = TextStyle.Default,

        headingSmallBold = TextStyle.Default,
        headingSmallSemiBold = TextStyle.Default,

        bodyXLargeBold = TextStyle.Default,
        bodyXLargeSemiBold = TextStyle.Default,
        bodyXLargeRegular = TextStyle.Default,

        bodyLargeBold = TextStyle.Default,
        bodyLargeSemiBold = TextStyle.Default,
        bodyLargeRegular = TextStyle.Default,

        bodyMediumBold = TextStyle.Default,
        bodyMediumSemiBold = TextStyle.Default,
        bodyMediumRegular = TextStyle.Default,

        bodySmallBold = TextStyle.Default,
        bodySmallSemiBold = TextStyle.Default,
        bodySmallRegular = TextStyle.Default,

        bodyXSmallBold = TextStyle.Default,
        bodyXSmallSemiBold = TextStyle.Default,
        bodyXSmallRegular = TextStyle.Default,

        inputMediumBold = TextStyle.Default,
        inputMediumSemiBold = TextStyle.Default,
        inputMediumRegular = TextStyle.Default,

        inputSmallBold = TextStyle.Default,
        inputSmallSemiBold = TextStyle.Default,
        inputSmallRegular = TextStyle.Default,

        buttonBold = TextStyle.Default,
        buttonSemiBold = TextStyle.Default,
        buttonRegular = TextStyle.Default,
    )
}

@Immutable
data class CalenderTypography(
    val headingXLargeSemiBold: TextStyle,
    val headingXLargeBold: TextStyle,

    val headingLargeBold: TextStyle,
    val headingLargeSemiBold: TextStyle,

    val headingMediumBold: TextStyle,
    val headingMediumSemiBold: TextStyle,

    val headingSmallBold: TextStyle,
    val headingSmallSemiBold: TextStyle,

    val bodyXLargeBold: TextStyle,
    val bodyXLargeSemiBold: TextStyle,
    val bodyXLargeRegular: TextStyle,

    val bodyLargeBold: TextStyle,
    val bodyLargeSemiBold: TextStyle,
    val bodyLargeRegular: TextStyle,

    val bodyMediumBold: TextStyle,
    val bodyMediumSemiBold: TextStyle,
    val bodyMediumRegular: TextStyle,

    val bodySmallBold: TextStyle,
    val bodySmallSemiBold: TextStyle,
    val bodySmallRegular: TextStyle,

    val bodyXSmallBold: TextStyle,
    val bodyXSmallSemiBold: TextStyle,
    val bodyXSmallRegular: TextStyle,

    val inputMediumBold: TextStyle,
    val inputMediumSemiBold: TextStyle,
    val inputMediumRegular: TextStyle,

    val inputSmallBold: TextStyle,
    val inputSmallSemiBold: TextStyle,
    val inputSmallRegular: TextStyle,

    val buttonBold: TextStyle,
    val buttonSemiBold: TextStyle,
    val buttonRegular: TextStyle,
)

val calenderTypography = CalenderTypography(
    headingXLargeSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headingXLargeBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),

    headingLargeBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
        lineHeight = 38.sp
    ),
    headingLargeSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 28.sp,
        lineHeight = 38.sp
    ),

    headingMediumBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 22.sp,
        lineHeight = 26.sp
    ),
    headingMediumSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 22.sp,
        lineHeight = 26.sp
    ),

    headingSmallBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    headingSmallSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyXLargeBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 27.sp
    ),
    bodyXLargeSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 27.sp
    ),
    bodyXLargeRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp,
        lineHeight = 27.sp
    ),
    bodyLargeBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLargeSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLargeRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    bodyMediumBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    bodyMediumSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    bodyMediumRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),

    bodySmallBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    bodySmallSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    bodySmallRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    bodyXSmallBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 10.sp,
        lineHeight = 14.sp
    ),
    bodyXSmallSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 10.sp,
        lineHeight = 14.sp
    ),
    bodyXSmallRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        lineHeight = 14.sp
    ),

    inputMediumBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    inputMediumSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    inputMediumRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),

    inputSmallBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    inputSmallSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    inputSmallRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    buttonBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    buttonSemiBold = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    buttonRegular = TextStyle(
        fontFamily = CalenderFont,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
)
