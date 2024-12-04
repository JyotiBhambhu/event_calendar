package com.jyoti.eventcalender.extensions

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

val Dp.textDp: TextUnit
    @Composable get() = this.textDp(density = LocalDensity.current)

private fun Dp.textDp(density: Density): TextUnit = with(density) {
    this@textDp.toSp()
}

fun Modifier.clickNotDuplicate(
    role: Role? = null,
    enabled: Boolean = true,
    haveIndication: Boolean = false,
    duplicateTime: Long = 1000L,
    onClick: () -> Unit
): Modifier =
    composed {
        clickable(
            enabled = enabled,
            role = role,
            interactionSource = remember { MutableInteractionSource() },
            indication = if (haveIndication) LocalIndication.current else null,
            onClick = {
                System.currentTimeMillis().also {
                    if (it - lastClick > duplicateTime) {
                        lastClick = it
                        onClick()
                    }
                }
            }
        )
    }

private var lastClick = 0L