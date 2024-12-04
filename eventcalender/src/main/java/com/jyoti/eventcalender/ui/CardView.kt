package com.jyoti.eventcalender.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.annotation.DimenRes
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jyoti.eventcalender.R
import com.jyoti.eventcalender.theme.ColorMain
import kotlin.math.roundToInt

@Composable
fun RoundRectBox(
    modifier: Modifier = Modifier,
    bgColor: Color = ColorMain.White.id,
    @DimenRes radius: Int = R.dimen.spacing_normal,
    cardContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(dimensionResource(id = radius)))
            .background(bgColor)
    ) {
        cardContent.invoke()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardView(
    modifier: Modifier = Modifier,
    bgColor: Color = ColorMain.White.id,
    borderStroke: BorderStroke = BorderStroke(
        dimensionResource(id = R.dimen.no_spacing),
        color = ColorMain.Transparent.id
    ),
    radius: Int = R.dimen.spacing_normal,
    isClickable: Boolean = false,
    onClick: () -> Unit = {},
    cardContent: @Composable ColumnScope.() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = bgColor,
        ),
        border = borderStroke,
        shape = RoundedCornerShape(dimensionResource(id = radius)),
        modifier = modifier,
        onClick = {
            if (isClickable) onClick()
        },
        enabled = isClickable
    ) {
        cardContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    modifier: Modifier,
    isRevealed: Boolean,
    cardOffset: Float,
    isClickable: Boolean = false,
    isDraggable: Boolean = false,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onClick: () -> Unit,
    borderStroke: BorderStroke = BorderStroke(
        dimensionResource(id = R.dimen.no_spacing),
        color = ColorMain.Transparent.id
    ),
    bgColor: Color = Color.White,
    cardContent: @Composable () -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) -cardOffset else 0f },
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.spacing_micro)
            )
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .let {
                if (isDraggable) {
                    return@let it.pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            when {
                                dragAmount >= MIN_DRAG_AMOUNT -> onCollapse()
                                dragAmount < -MIN_DRAG_AMOUNT -> onExpand()
                            }
                        }
                    }
                }
                return@let it
            },
        colors = CardDefaults.cardColors(
            containerColor = bgColor,
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_normal_plus)),
        border = borderStroke,
        onClick = {
            if (isClickable) onClick()
        },
        enabled = isClickable || isDraggable,
    ) {
        cardContent()
    }
}

@Composable
fun DraggableCalBox(
    modifier: Modifier,
    isWeekMode: Boolean,
    isDraggable: Boolean = true,
    weekModeToggled: (isWeekMode: Boolean) -> Unit,
    calContent: @Composable () -> Unit
) {
    val currentWeekMode by rememberUpdatedState(newValue = isWeekMode)
    Column(
        modifier = modifier.let {
            if (isDraggable) {
                return@let it.pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        when {
                            dragAmount >= MIN_DRAG_AMOUNT && currentWeekMode -> weekModeToggled(
                                false
                            )

                            dragAmount < -MIN_DRAG_AMOUNT && !currentWeekMode -> weekModeToggled(
                                true
                            )
                        }
                    }
                }
            }
            return@let it
        },
    ) {
        calContent()
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun PreviewDraggableCard() {
//    var revealedCardId by remember { mutableStateOf("") }
//    val item = UIEvent(
//        id = "4",
//        type = EventUIConfigV2.LEAVE_CONFIRMED.id,
//        name = UIText.DynamicString("Event"),
//        repeatRule = 1,
//        LocalDateTime.now(),
//        uiConfig = EventUIConfigV2.LEAVE_CONFIRMED
//    )
//
//    Box(
//        Modifier.fillMaxWidth(),
//        contentAlignment = Alignment.Center
//    ) {
//        ActionsRow(
//            modifier = Modifier.testTag("ACTION_ROW"),
//            leaveAction = LeaveAction(showDeleteAction = true, showEditAction = true),
//            onEdit = {
//                revealedCardId = ""
//            },
//            onDelete = {
//                revealedCardId = ""
//            }
//        )
//        DraggableCard(
//            modifier = Modifier.testTag("EVENT_DRAG_CARD"),
//            isRevealed = revealedCardId == item.id,
//            isClickable = true,
//            isDraggable = true,
//            cardOffset = CARD_OFFSET.dp(),
//            onExpand = {
//                revealedCardId = item.id
//            },
//            onCollapse = { revealedCardId = "" },
//            onClick = {
//            }
//        ) {
//            EventItem(item = item)
//        }
//    }
//}

fun Float.dp(): Float = this * density + 0.5f

fun Float.convertToDp() = (this / density + 0.5f).dp

val density: Float
    get() = Resources.getSystem().displayMetrics.density

const val CARD_OFFSET = 112f // we have 2 icons in a row, so that's 56 * 2
const val ANIMATION_DURATION = 500
const val MIN_DRAG_AMOUNT = 6
