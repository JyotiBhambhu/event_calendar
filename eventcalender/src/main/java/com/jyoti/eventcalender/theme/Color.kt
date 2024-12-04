package com.jyoti.eventcalender.theme

import androidx.compose.ui.graphics.Color

/**
 * Getir HR colors.
 */

private val colorBackgroundBody = Color(0xFFF8F8F8)
private val colorBackgroundModal = Color(0xD92E2644)

private val colorSuccess50 = Color(0xFFEBF6ED)
private val colorSuccessBase = Color(0xFF008C20)
private val colorSuccess700 = Color(0xFF05751F)

private val colorError50 = Color(0xFFFFEFEF)
private val colorErrorBase = Color(0xFFFF3939)
private val colorError700 = Color(0xFFD13333)

private val colorWarning50 = Color(0xFFFEF7E8)
private val colorWarningBase = Color(0xFFF8AA1C)
private val colorWarning700 = Color(0xFF97670F)

private val colorTextBase = Color(0xFF191919)
private val colorTextSecondary = Color(0xFF697488)
private val colorTextDisabled = Color(0xFFA1A1A1)
private val colorTextAlpha = Color(0xF0182739)

private val colorPrimary700 = Color(0xFF422C85)
private val colorPrimary600 = Color(0xFF5538AB)
private val colorPrimaryBase = Color(0xFF5D3EBC)
private val colorPrimary300 = Color(0xFF927ED2)
private val colorPrimary100 = Color(0xFFCDC3EA)
private val colorPrimary50 = Color(0xFFF3F0FE)

private val colorBlack50 = Color(0xFFF6F6F6)
private val colorBlack100 = Color(0xFFE2E2E2)
private val colorBlack400 = Color(0xFF727272)
private val colorBlack600 = Color(0x331C375A)
private val colorBlack2000 = Color(0xFFD4D4D4)

private val colorWhite50 = Color(0xFFF6F6F6)
private val colorWhite80 = Color(0xFFF8F8F8)
private val colorWhite100 = Color(0xFFFFFFFF)

private val colorGrey50 = Color(0xFFF0F1F3)
private val colorGrey200 = Color(0xFFBABFC8)
private val colorGrey300 = Color(0x9C1C2E45)
private val colorGreyBase = Color(0xFF697488)

private val colorVioletBase = Color(0xFF7849F7)

private val colorYellowBase = Color(0xFFFFD10D)

private val colorBackgroundSurface = Color(0x0D193B67)

enum class ColorMain(val id: Color) {
    White(Color.White), Black(Color.Black), Transparent(Color.Transparent)
}

enum class ColorBackground(val id: Color) {
    Body(colorBackgroundBody), MODAL(colorBackgroundModal)
}

//enum class ColorSuccess(val id: Color) {
//    _50(colorSuccess50), Base(colorSuccessBase), _700(colorSuccess700)
//}

enum class ColorError(val id: Color) {
    _50(colorError50), Base(colorErrorBase), _700(colorError700)
}

enum class ColorWarning(val id: Color) {
    _50(colorWarning50), Base(colorWarningBase), _700(colorWarning700)
}

enum class ColorText(val id: Color) {
    Base(colorTextBase), SECONDARY(colorTextSecondary), DISABLED(colorTextDisabled),
    OTHER(colorGrey200), ALPHA(colorTextAlpha)
}

enum class ColorPrimary(val id: Color) {
    _700(colorPrimary700), _600(colorPrimary600), Base(colorPrimaryBase),
    _300(colorPrimary300), _100(colorPrimary100), _50(colorPrimary50)
}

enum class ColorBlack(val id: Color) {
    _50(colorBlack50), _100(colorBlack100), _200(colorBlack2000), _400(colorBlack400),
    _600(colorBlack600)
}

enum class ColorGrey(val id: Color) {
    _50(colorGrey50), _200(colorGrey200), _300(colorGrey300), Base(colorGreyBase)
}

enum class ColorWhite(val id: Color) {
    _50(colorWhite50), _80(colorWhite80), _100(colorWhite100)
}
//
//enum class ColorSurface(val id: Color) {
//    Surface(colorBackgroundSurface)
//}
//
//enum class ColorViolet(val id: Color) {
//    Base(colorVioletBase)
//}
//
//enum class ColorYellow(val id: Color) {
//    Base(colorYellowBase)
//}
