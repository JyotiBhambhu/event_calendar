package com.jyoti.eventcalender.util

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.jyoti.eventcalender.extensions.toInt
import java.io.Serializable

sealed class UIText : Serializable {
    data class DynamicString(val value: String) : UIText()
    class StringResource(@StringRes val resId: Int, vararg val args: Any) : UIText()
    class PluralStringResource(@PluralsRes val resId: Int, vararg val args: Any) : UIText()

    @Composable
    fun asString(): String {
        return when (this@UIText) {
            is DynamicString -> value
            is StringResource -> {
                stringResource(resId, *args)
            }
            is PluralStringResource -> {
                pluralStringResource(resId, args[0].toInt(), *args)
            }
        }
    }

    fun asString(context: Context): String {
        return when (this@UIText) {
            is DynamicString -> value
            is StringResource -> {
                context.getString(resId, *args)
            }
            is PluralStringResource -> {
                context.resources.getQuantityString(resId, args[0].toInt(), *args)
            }
        }
    }
}
