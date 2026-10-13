package com.pavruls.noorpath.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

/**
 * Reusable stagger entry wrapper — Reduced Motion aware.
 *
 * যখন system "Remove animations" ON থাকে, তখন:
 *   - delay skip হবে (content instantly দেখা যাবে)
 *   - animation duration ~0 হবে
 */
@Composable
fun StaggeredEntry(
    visible: Boolean,
    delayMillis: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val reduceMotion = rememberReduceMotion()
    var shown by remember { mutableStateOf(reduceMotion) }

    LaunchedEffect(visible, reduceMotion) {
        if (visible) {
            if (reduceMotion) {
                shown = true
            } else {
                if (delayMillis > 0) delay(delayMillis.toLong())
                shown = true
            }
        }
    }

    if (reduceMotion) {
        if (shown) content()
    } else {
        AnimatedVisibility(
            visible = shown,
            modifier = modifier,
            enter = fadeIn(animationSpec = tween(320, easing = FastOutSlowInEasing)) +
                    slideInVertically(
                        animationSpec = tween(400, easing = FastOutSlowInEasing),
                        initialOffsetY = { it / 4 }
                    ) +
                    scaleIn(
                        animationSpec = tween(400, easing = FastOutSlowInEasing),
                        initialScale = 0.9f
                    )
        ) {
            content()
        }
    }
}

// ============ Stagger Timing Presets ============
object StaggerTiming {
    const val LIST_START = 100
    const val GRID_START = 180
    const val STEP = 60
    const val STEP_SLOW = 80
    const val ANIMATE_THRESHOLD = 10  // প্রথম ১০টি item animate হবে
}