package com.pavruls.noorpath.presentation.screens.parent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.data.preferences.ParentSettings
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import kotlinx.coroutines.launch

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LOCK = 120
private const val DELAY_PIN_LABEL = 240
private const val DELAY_DOTS = 320
private const val DELAY_NUMPAD_START = 440
private const val STAGGER_NUMPAD_ROW = 80

@Composable
fun ParentPinScreen(
    onBackClick: () -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    var enteredPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    // Shake animation on wrong PIN
    val shakeRotation = remember { Animatable(0f) }
    // Success bounce
    val successScale = remember { Animatable(1f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0A2E1F), Color(0xFF1B5E20))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ===== Header =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_HEADER) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = AppStrings.back(language),
                            tint = Color.White
                        )
                    }
                    Text(
                        text = AppStrings.parentProtectionTitle(language),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ===== Lock Icon (pulse + success bounce) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_LOCK) {
                    LockIcon(successScale = successScale.value)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== PIN Prompt Label =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_PIN_LABEL) {
                    Text(
                        text = AppStrings.enterPinLabel(language),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // ===== PIN Dots (with shake on error + bounce per digit) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_DOTS) {
                    Box(
                        modifier = Modifier.rotate(shakeRotation.value)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            repeat(4) { index ->
                                PinDot(
                                    filled = index < enteredPin.length,
                                    isError = errorMessage.isNotEmpty()
                                )
                            }
                        }
                    }
                }

                // ===== Error Message =====
                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage,
                        fontSize = 14.sp,
                        color = Color(0xFFFF5252),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ===== Number Pad (staggered rows) =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val rows = listOf(
                    listOf("1", "2", "3"),
                    listOf("4", "5", "6"),
                    listOf("7", "8", "9"),
                    listOf("", "0", "⌫")
                )

                rows.forEachIndexed { rowIndex, row ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_NUMPAD_START + rowIndex * STAGGER_NUMPAD_ROW
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            row.forEach { digit ->
                                if (digit.isEmpty()) {
                                    Spacer(modifier = Modifier.weight(1f))
                                } else {
                                    PinButton(
                                        digit = digit,
                                        modifier = Modifier.weight(1f),
                                        onClick = {
                                            errorMessage = ""
                                            when (digit) {
                                                "⌫" -> {
                                                    if (enteredPin.isNotEmpty()) {
                                                        enteredPin = enteredPin.dropLast(1)
                                                    }
                                                }
                                                else -> {
                                                    if (enteredPin.length < 4) {
                                                        enteredPin += digit
                                                        if (enteredPin.length == 4) {
                                                            val savedPin = ParentSettings.getPin(context)
                                                            if (enteredPin == savedPin) {
                                                                // Success bounce
                                                                scope.launch {
                                                                    successScale.animateTo(
                                                                        targetValue = 1.2f,
                                                                        animationSpec = spring(
                                                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                                                            stiffness = Spring.StiffnessMedium
                                                                        )
                                                                    )
                                                                    successScale.animateTo(
                                                                        targetValue = 1f,
                                                                        animationSpec = spring(
                                                                            dampingRatio = Spring.DampingRatioMediumBouncy
                                                                        )
                                                                    )
                                                                }
                                                                onSuccess()
                                                            } else {
                                                                errorMessage = AppStrings.wrongPin(language)
                                                                enteredPin = ""
                                                                // Shake animation
                                                                scope.launch {
                                                                    shakeRotation.snapTo(0f)
                                                                    shakeRotation.animateTo(10f, tween(60))
                                                                    shakeRotation.animateTo(-10f, tween(60))
                                                                    shakeRotation.animateTo(8f, tween(60))
                                                                    shakeRotation.animateTo(-8f, tween(60))
                                                                    shakeRotation.animateTo(5f, tween(60))
                                                                    shakeRotation.animateTo(-5f, tween(60))
                                                                    shakeRotation.animateTo(0f, tween(60))
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Lock Icon (infinite pulse + success bounce) =====
// ============================================================
@Composable
private fun LockIcon(successScale: Float) {
    val infinite = rememberInfiniteTransition(label = "lockPulse")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.18f,
        targetValue = 0.30f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "lockGlowAlpha"
    )

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing glow
        Box(
            modifier = Modifier
                .size(115.dp)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = glowAlpha))
        )

        // Inner circle with lock emoji
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(successScale)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🔒", fontSize = 50.sp)
        }
    }
}

// ============================================================
// ===== PIN Dot (bounce when filled + error color) =====
// ============================================================
@Composable
private fun PinDot(filled: Boolean, isError: Boolean) {
    val scale by animateFloatAsState(
        targetValue = if (filled) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "pinDotScale"
    )

    val dotColor by animateFloatAsState(
        targetValue = when {
            isError && !filled -> 1f
            filled -> 0.9f
            else -> 0.2f
        },
        animationSpec = tween(200),
        label = "pinDotColorAlpha"
    )

    Box(
        modifier = Modifier
            .size(20.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                when {
                    isError && !filled -> Color(0xFFFF5252).copy(alpha = dotColor)
                    filled -> Color(0xFFD4AF37).copy(alpha = dotColor)
                    else -> Color.White.copy(alpha = dotColor)
                }
            )
    )
}

// ============================================================
// ===== Pin Button (press scale + ripple) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PinButton(
    digit: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "pinButtonScale"
    )

    val bgColor = if (digit == "⌫") {
        Color(0xFFFF5252).copy(alpha = 0.15f)
    } else {
        Color.White.copy(alpha = 0.08f)
    }

    Box(
        modifier = modifier
            .aspectRatio(1.5f)
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    color = if (digit == "⌫") Color(0xFFFF5252) else Color(0xFFD4AF37)
                ),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit,
            fontSize = if (digit == "⌫") 28.sp else 32.sp,
            fontWeight = FontWeight.Bold,
            color = if (digit == "⌫") Color(0xFFFF5252) else Color.White
        )
    }
}