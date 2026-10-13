package com.pavruls.noorpath.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    // ============ Entry Animation States ============
    val logoScale = remember { Animatable(0.5f) }
    val logoAlpha = remember { Animatable(0f) }
    val bookScale = remember { Animatable(0f) }
    val arabicAlpha = remember { Animatable(0f) }
    val arabicOffset = remember { Animatable(24f) }
    val nameAlpha = remember { Animatable(0f) }
    val nameOffset = remember { Animatable(24f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val exitAlpha = remember { Animatable(1f) }

    // ============ Continuous Animations ============
    val infinite = rememberInfiniteTransition(label = "splash_infinite")
    val glowPulse by infinite.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowPulse"
    )
    val ringRotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(24000, easing = LinearEasing)
        ),
        label = "ringRotation"
    )

    // ============ Orchestrated Sequence ============
    LaunchedEffect(Unit) {
        // Stage 1: Logo scale + fade (parallel)
        launch { logoScale.animateTo(1f, tween(650, easing = FastOutSlowInEasing)) }
        launch { logoAlpha.animateTo(1f, tween(500)) }

        // Stage 2: Book spring bounce (slight delay)
        launch {
            delay(250)
            bookScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )
        }

        // Stage 3: Arabic text — fade + slide up
        launch {
            delay(500)
            arabicAlpha.animateTo(1f, tween(400))
            arabicOffset.animateTo(0f, tween(400, easing = FastOutSlowInEasing))
        }

        // Stage 4: App name — fade + slide up
        launch {
            delay(700)
            nameAlpha.animateTo(1f, tween(380))
            nameOffset.animateTo(0f, tween(420, easing = FastOutSlowInEasing))
        }

        // Stage 5: Subtitle
        launch {
            delay(900)
            subtitleAlpha.animateTo(1f, tween(380))
        }

        // Hold the polished look, then exit
        delay(1800)
        exitAlpha.animateTo(0f, tween(400, easing = FastOutSlowInEasing))
        onFinished()
    }

    // ============ UI ============
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(exitAlpha.value)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0A2E1F), Color(0xFF1B5E20))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ===== Logo Badge =====
            Box(
                modifier = Modifier
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value),
                contentAlignment = Alignment.Center
            ) {
                // Pulsing golden glow
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .scale(glowPulse)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFD4AF37).copy(alpha = 0.35f),
                                    Color(0xFFD4AF37).copy(alpha = 0.10f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Rotating golden ring
                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .rotate(ringRotation)
                        .background(
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color(0xFFD4AF37).copy(alpha = 0.05f),
                                    Color(0xFFD4AF37).copy(alpha = 0.70f),
                                    Color(0xFFD4AF37).copy(alpha = 0.05f),
                                    Color(0xFFD4AF37).copy(alpha = 0.70f),
                                    Color(0xFFD4AF37).copy(alpha = 0.05f)
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Dark inner circle (creates the ring illusion + badge base)
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF14532D),
                                    Color(0xFF082418)
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // Book — springs in
                Text(
                    text = "📖",
                    fontSize = 100.sp,
                    modifier = Modifier.scale(bookScale.value)
                )
            }

            Spacer(modifier = Modifier.height(44.dp))

            // ===== Arabic: نُور =====
            Text(
                text = "نُور",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37),
                modifier = Modifier
                    .alpha(arabicAlpha.value)
                    .offset(y = arabicOffset.value.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // ===== App Name =====
            Text(
                text = "নূরপাঠ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .alpha(nameAlpha.value)
                    .offset(y = nameOffset.value.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // ===== Subtitle =====
            Text(
                text = "আলোর পথে আপনাকে স্বাগতম",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.78f),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(subtitleAlpha.value)
            )
        }
    }
}

// ============ Preview ============
@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "Splash — Preview")
@Composable
private fun SplashScreenPreview() {
    SplashScreen(onFinished = {})
}