package com.pavruls.noorpath.presentation.screens.kids

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_EMOJI = 120
private const val DELAY_ARABIC = 280
private const val DELAY_PRONUN = 440
private const val DELAY_MEANING = 560

@Composable
fun DuaDetailScreen(
    duaId: Int,
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val dua = DuaData.allDuas.find { it.id == duaId } ?: DuaData.allDuas.first()

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0A2E1F), Color(0xFF1B5E20))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

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

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = DuaData.getTitle(dua, language),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ===== Emoji circle (scale + infinite pulse) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_EMOJI) {
                    PulsingEmojiCircle(
                        emoji = dua.emoji,
                        color = dua.color
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ===== Arabic box (fade + glow pulse) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_ARABIC) {
                    GlowArabicBox(
                        arabic = dua.arabic,
                        color = dua.color
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== Pronunciation =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_PRONUN) {
                    InfoSection(
                        label = AppStrings.pronunciationLabel(language),
                        value = DuaData.getPronunciation(dua, language),
                        valueColor = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Meaning =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_MEANING) {
                    InfoSection(
                        label = AppStrings.meaningLabel(language),
                        value = DuaData.getMeaning(dua, language),
                        valueColor = Color.White.copy(alpha = 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

// ============================================================
// ===== Pulsing Emoji Circle =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PulsingEmojiCircle(
    emoji: String,
    color: Color
) {
    // Infinite soft pulse
    val infinite = rememberInfiniteTransition(label = "emojiPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emojiPulseScale"
    )
    val pulseGlow by infinite.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emojiPulseGlow"
    )

    // Press feedback
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "emojiPressScale"
    )

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow ring (pulsing)
        Box(
            modifier = Modifier
                .size(115.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(color.copy(alpha = pulseGlow * 0.6f))
        )

        // Inner circle with emoji
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(pressScale)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.25f))
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(color = color),
                    onClick = { /* Optional: play audio later */ }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 50.sp)
        }
    }
}

// ============================================================
// ===== Glowing Arabic Box =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GlowArabicBox(
    arabic: String,
    color: Color
) {
    // Subtle infinite glow
    val infinite = rememberInfiniteTransition(label = "arabicGlow")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.08f,
        targetValue = 0.14f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arabicGlowAlpha"
    )

    // Press feedback
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "arabicPressScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(pressScale)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = glowAlpha + 0.05f),
                        Color.White.copy(alpha = glowAlpha)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = { /* Optional: play Arabic audio */ }
            )
            .padding(24.dp)
    ) {
        Text(
            text = arabic,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37),
            textAlign = TextAlign.Center,
            lineHeight = 48.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ============================================================
// ===== Info Section =====
// ============================================================
@Composable
private fun InfoSection(
    label: String,
    value: String,
    valueColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(16.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            lineHeight = 24.sp
        )
    }
}