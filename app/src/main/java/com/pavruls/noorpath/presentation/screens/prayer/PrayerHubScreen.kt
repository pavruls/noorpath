package com.pavruls.noorpath.presentation.screens.prayer

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.pavruls.noorpath.presentation.components.StaggerTiming
import com.pavruls.noorpath.presentation.components.StaggeredEntry

data class PrayerHubItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color,
    val onClick: () -> Unit
)

@Composable
fun PrayerHubScreen(
    onBackClick: () -> Unit,
    onPrayerTimeClick: () -> Unit,
    onQiblaClick: () -> Unit,
    onTrackerClick: () -> Unit,
    onTasbihClick: () -> Unit,
    onZakatClick: () -> Unit,
    onRamadanClick: () -> Unit,
    onMosqueFinderClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    val items = listOf(
        PrayerHubItem(
            emoji = "🕌",
            title = AppStrings.prayerTime(language),
            subtitle = AppStrings.prayerTimeDesc(language),
            color = Color(0xFF2196F3)
        ) { onPrayerTimeClick() },
        PrayerHubItem(
            emoji = "🧭",
            title = AppStrings.qibla(language),
            subtitle = AppStrings.directionToQibla(language),
            color = Color(0xFFD4AF37)
        ) { onQiblaClick() },
        PrayerHubItem(
            emoji = "📊",
            title = AppStrings.prayerTracker(language),
            subtitle = AppStrings.prayerTrackerDesc(language),
            color = Color(0xFFFF5722)
        ) { onTrackerClick() },
        PrayerHubItem(
            emoji = "📿",
            title = AppStrings.tasbih(language),
            subtitle = AppStrings.tasbihDesc(language),
            color = Color(0xFF009688)
        ) { onTasbihClick() },
        PrayerHubItem(
            emoji = "💰",
            title = AppStrings.zakat(language),
            subtitle = AppStrings.zakatDesc(language),
            color = Color(0xFF4CAF50)
        ) { onZakatClick() },
        PrayerHubItem(
            emoji = "🌙",
            title = AppStrings.ramadan(language),
            subtitle = AppStrings.ramadanDesc(language),
            color = Color(0xFF673AB7)
        ) { onRamadanClick() },
        PrayerHubItem(
            emoji = "🗺️",
            title = AppStrings.mosqueFinderTitle(language),
            subtitle = AppStrings.mosqueFinderSubtitle(language),
            color = Color(0xFF00BCD4)
        ) { onMosqueFinderClick() }
    )

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
            StaggeredEntry(visible = entryVisible, delayMillis = 0) {
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
                            text = AppStrings.prayerHubTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.prayerHubSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== Grid =====
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(items) { index, item ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = StaggerTiming.GRID_START + index * StaggerTiming.STEP_SLOW
                    ) {
                        PrayerHubCard(item = item)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrayerHubCard(item: PrayerHubItem) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "hubCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "hubIconScale"
    )

    Box(
        modifier = Modifier
            .aspectRatio(0.95f)
            .scale(cardScale)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        item.color.copy(alpha = 0.85f),
                        item.color.copy(alpha = 0.55f)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = item.color),
                onClick = item.onClick
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .scale(iconScale)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.emoji, fontSize = 30.sp)
            }

            Column {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subtitle,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.75f),
                    lineHeight = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}