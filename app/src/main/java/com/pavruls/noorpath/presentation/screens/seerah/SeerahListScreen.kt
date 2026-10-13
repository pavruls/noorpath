package com.pavruls.noorpath.presentation.screens.seerah

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.seerah.SeerahData
import com.pavruls.noorpath.seerah.SeerahTopic

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 70

@Composable
fun SeerahListScreen(
    onBackClick: () -> Unit,
    onTopicClick: (Int, String) -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

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
                    Column {
                        Text(
                            text = AppStrings.seerahFullTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.seerahFullSubtitle(
                                language,
                                SeerahData.allTopics.size
                            ),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== Topic List (staggered) =====
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = SeerahData.allTopics,
                    key = { _, topic -> topic.id }
                ) { index, topic ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                    ) {
                        SeerahTopicCard(
                            topic = topic,
                            language = language,
                            onClick = { onTopicClick(topic.id, topic.getName(language)) }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Seerah Topic Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeerahTopicCard(
    topic: SeerahTopic,
    language: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "seerahCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "seerahIconScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "seerahChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .scale(iconScale)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = topic.emoji, fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = topic.getName(language),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = AppStrings.arabicNamePeriod(
                    language,
                    topic.arabicName,
                    topic.getPeriod(language)
                ),
                fontSize = 12.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = topic.getDescription(language),
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
        Text(
            text = "›",
            fontSize = 26.sp,
            color = Color.White.copy(alpha = chevronAlpha)
        )
    }
}