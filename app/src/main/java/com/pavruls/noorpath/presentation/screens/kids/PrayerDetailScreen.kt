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
import androidx.compose.foundation.layout.width
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
private const val DELAY_TOPIC_CARD = 120
private const val DELAY_STEPS_START = 300
private const val STAGGER_STEP_MS = 70

@Composable
fun PrayerDetailScreen(
    topicId: Int,
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val topic = PrayerData.allTopics.find { it.id == topicId }
        ?: PrayerData.allTopics.first()
    val steps = PrayerData.getStepsForTopic(topicId)

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

                    Column {
                        Text(
                            text = PrayerData.getTopicTitle(topic, language),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = PrayerData.getTopicSubtitle(topic, language),
                            fontSize = 11.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                // ===== Topic Header Card (with glow) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_TOPIC_CARD) {
                    TopicHeaderCard(
                        emoji = topic.emoji,
                        title = PrayerData.getTopicTitle(topic, language),
                        stepCountLabel = AppStrings.prayerStepCount(language, steps.size),
                        color = topic.color
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== Step Cards (wave stagger) =====
                steps.forEachIndexed { index, step ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_STEPS_START + index * STAGGER_STEP_MS
                    ) {
                        PrayerStepCard(
                            step = step,
                            accentColor = topic.color,
                            language = language
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ============================================================
// ===== Topic Header Card (glow pulse) =====
// ============================================================
@Composable
private fun TopicHeaderCard(
    emoji: String,
    title: String,
    stepCountLabel: String,
    color: Color
) {
    // Soft infinite glow
    val infinite = rememberInfiniteTransition(label = "topicHeaderGlow")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.20f,
        targetValue = 0.32f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "topicHeaderGlowAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(color.copy(alpha = glowAlpha))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 60.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stepCountLabel,
                fontSize = 13.sp,
                color = Color(0xFFD4AF37)
            )
        }
    }
}

// ============================================================
// ===== Prayer Step Card (press + number bounce) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrayerStepCard(
    step: PrayerStep,
    accentColor: Color,
    language: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.985f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "stepCardScale"
    )
    val numberScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "stepNumberScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = accentColor),
                onClick = { /* Future: audio play */ }
            )
            .padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .scale(numberScale)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${step.stepNumber}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = step.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }

        if (step.arabic != "—") {
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = step.arabic,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Right,
                lineHeight = 38.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (step.pronunciation != "—") {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${AppStrings.pronunciationLabel(language)}: ${step.pronunciation}",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = step.meaning,
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.7f),
            lineHeight = 20.sp
        )
    }
}