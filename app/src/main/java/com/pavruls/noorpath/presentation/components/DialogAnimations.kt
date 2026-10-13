package com.pavruls.noorpath.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.delay

/**
 * Dialog / BottomSheet-এর ভিতরের content stagger-এর জন্য।
 */
@Composable
fun DialogStaggerItem(
    visible: Boolean,
    delayMillis: Int,
    modifier: Modifier = Modifier,
    fromBottom: Boolean = true,
    content: @Composable () -> Unit
) {
    var shown by remember { mutableStateOf(false) }
    LaunchedEffect(visible) {
        if (visible) {
            if (delayMillis > 0) delay(delayMillis.toLong())
            shown = true
        }
    }
    AnimatedVisibility(
        visible = shown,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(280, easing = FastOutSlowInEasing)) +
                (if (fromBottom) {
                    slideInVertically(
                        animationSpec = tween(360, easing = FastOutSlowInEasing),
                        initialOffsetY = { it / 3 }
                    )
                } else {
                    slideInHorizontally(
                        animationSpec = tween(360, easing = FastOutSlowInEasing),
                        initialOffsetX = { it / 4 }
                    )
                }) +
                scaleIn(
                    animationSpec = tween(340, easing = FastOutSlowInEasing),
                    initialScale = 0.92f
                )
    ) {
        content()
    }
}

/**
 * Icon scale-in with spring bounce — Dialog-এর icon-এর জন্য।
 */
@Composable
fun DialogIconEntrance(
    visible: Boolean,
    delayMillis: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var shown by remember { mutableStateOf(false) }
    LaunchedEffect(visible) {
        if (visible) {
            if (delayMillis > 0) delay(delayMillis.toLong())
            shown = true
        }
    }
    AnimatedVisibility(
        visible = shown,
        modifier = modifier,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialScale = 0.4f
        ) + fadeIn(animationSpec = tween(200))
    ) {
        content()
    }
}

/**
 * Press feedback scale — button বা clickable item-এর জন্য।
 */
@Composable
fun rememberPressScale(
    isPressed: Boolean,
    pressedScale: Float = 0.96f
): Float {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "pressScale"
    )
    return scale
}

// Timing constants for Dialog/BottomSheet
object DialogTiming {
    const val ICON = 80
    const val TITLE = 200
    const val MESSAGE = 280
    const val ACTIONS = 380
    const val SHEET_ITEM_STEP = 70
    const val SHEET_START = 120
}