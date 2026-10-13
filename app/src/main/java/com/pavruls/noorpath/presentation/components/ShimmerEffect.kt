package com.pavruls.noorpath.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Shimmer brush — shimmer effect-এর core।
 * @param baseColor — skeleton-এর base রঙ (সাধারণত gray বা white-alpha)
 * @param highlightColor — shimmer-এর highlight রঙ (উজ্জ্বল)
 */
@Composable
fun shimmerBrush(
    baseColor: Color = Color.White.copy(alpha = 0.06f),
    highlightColor: Color = Color.White.copy(alpha = 0.18f)
): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    return Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(translateAnim - 400f, translateAnim - 400f),
        end = Offset(translateAnim, translateAnim)
    )
}

/**
 * Shimmer box — যেকোনো আকৃতির placeholder।
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    baseColor: Color = Color.White.copy(alpha = 0.06f),
    highlightColor: Color = Color.White.copy(alpha = 0.18f)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = shimmerBrush(
                    baseColor = baseColor,
                    highlightColor = highlightColor
                )
            )
    )
}

/**
 * Shimmer circle — avatar-like placeholder।
 */
@Composable
fun ShimmerCircle(
    size: Dp,
    modifier: Modifier = Modifier,
    baseColor: Color = Color.White.copy(alpha = 0.06f),
    highlightColor: Color = Color.White.copy(alpha = 0.18f)
) {
    ShimmerBox(
        modifier = modifier.size(size),
        shape = RoundedCornerShape(percent = 50),
        baseColor = baseColor,
        highlightColor = highlightColor
    )
}