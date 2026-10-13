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
private const val DELAY_HERO_CARD = 120
private const val DELAY_STORY_SECTION = 340

@Composable
fun ProphetStoryDetailScreen(
    storyId: Int,
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val story = ProphetStoriesData.allStories.find { it.id == storyId }
        ?: ProphetStoriesData.allStories.first()

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
                            text = ProphetStoriesData.getStoryName(story, language),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = ProphetStoriesData.getStoryShortDesc(story, language),
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

                // ===== Hero Card (with pulse glow + bouncing emoji) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_HERO_CARD) {
                    StoryHeroCard(
                        emoji = story.emoji,
                        title = ProphetStoriesData.getStoryName(story, language),
                        subtitle = ProphetStoriesData.getStoryShortDesc(story, language),
                        color = story.color
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== Story Section (fade + slide up) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_STORY_SECTION) {
                    StorySection(
                        label = getStoryLabel(language),
                        content = ProphetStoriesData.getStoryText(story, language),
                        accentColor = story.color
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ============================================================
// ===== Story Hero Card (pulse glow + bounce emoji) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StoryHeroCard(
    emoji: String,
    title: String,
    subtitle: String,
    color: Color
) {
    // Soft infinite glow on background
    val infinite = rememberInfiniteTransition(label = "storyHeroGlow")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.18f,
        targetValue = 0.30f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heroGlowAlpha"
    )
    // Subtle bob on emoji circle
    val emojiBob by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emojiBob"
    )

    // Press feedback on emoji circle
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "heroEmojiPressScale"
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
            // Emoji circle with bob + press
            Box(
                modifier = Modifier
                    .padding(top = emojiBob.dp)
                    .size(80.dp)
                    .scale(pressScale)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.3f))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple(color = color),
                        onClick = { /* Future: audio */ }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 40.sp)
            }
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
                text = subtitle,
                fontSize = 14.sp,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Center
            )
        }
    }
}

// ============================================================
// ===== Story Section =====
// ============================================================
@Composable
private fun StorySection(
    label: String,
    content: String,
    accentColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .padding(18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(accentColor)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = content,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f),
            lineHeight = 22.sp
        )
    }
}

// ============================================================
// ===== Story Label Helper =====
// ============================================================
private fun getStoryLabel(lang: String): String = when (lang) {
    LanguageManager.LANG_EN -> "🇬🇧 English"
    LanguageManager.LANG_AR -> "🇸🇦 العربية"
    else -> "🇧🇩 বাংলা"
}