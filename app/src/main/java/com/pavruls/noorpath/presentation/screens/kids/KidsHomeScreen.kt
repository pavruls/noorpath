package com.pavruls.noorpath.presentation.screens.kids

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
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
import kotlinx.coroutines.delay

data class KidsMenu(
    val emoji: String,
    val titleKey: String,
    val color: Color,
    val route: String
)

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_GRID_START = 180
private const val STAGGER_STEP = 80

@Composable
fun KidsHomeScreen(
    onBackClick: () -> Unit,
    onMenuClick: (String) -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

    val menuItems = listOf(
        KidsMenu("أ", "letters_hub", Color(0xFF4CAF50), "letters_hub"),
        KidsMenu("🤲", "duas", Color(0xFF00BCD4), "duas"),
        KidsMenu("📖", "surahs", Color(0xFF9C27B0), "surahs"),
        KidsMenu("🕌", "kids_prayer", Color(0xFFFF9800), "kids_prayer"),
        KidsMenu("📜", "prophet_stories", Color(0xFF795548), "prophet_stories"),
        KidsMenu("🎯", "quiz", Color(0xFF4CAF50), "quiz"),
        KidsMenu("📖", "daily_hadith", Color(0xFF607D8B), "daily_hadith")
    )

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
                    Spacer(modifier = Modifier.size(4.dp))
                    Column {
                        Text(
                            text = AppStrings.kidsHomeTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.kidsHomeSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== Grid with staggered items =====
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(menuItems) { index, item ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_GRID_START + index * STAGGER_STEP
                    ) {
                        KidsMenuCard(
                            item = item,
                            title = getMenuTitle(item.titleKey, language),
                            onClick = { onMenuClick(item.route) }
                        )
                    }
                }
            }
        }
    }
}

// ============ Stagger Wrapper (reusable) ============
@Composable
private fun StaggeredEntry(
    visible: Boolean,
    delayMillis: Int,
    content: @Composable () -> Unit
) {
    var shown by remember { mutableStateOf(false) }
    LaunchedEffect(visible) {
        if (visible) {
            delay(delayMillis.toLong())
            shown = true
        }
    }
    AnimatedVisibility(
        visible = shown,
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

private fun getMenuTitle(key: String, lang: String): String {
    return when (key) {
        "letters_hub" -> AppStrings.lettersHubTitle(lang)
        "duas" -> AppStrings.duas(lang)
        "surahs" -> AppStrings.smallSurahs(lang)
        "kids_prayer" -> AppStrings.kidsPrayer(lang)
        "prophet_stories" -> AppStrings.prophetStories(lang)
        "quiz" -> AppStrings.quiz(lang)
        "daily_hadith" -> AppStrings.dailyHadith(lang)
        else -> key
    }
}

// ============ Menu Card with Press Feedback ============
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KidsMenuCard(
    item: KidsMenu,
    title: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "kidsCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "kidsIconScale"
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f)
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
                onClick = onClick
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
                Text(
                    text = item.emoji,
                    fontSize = when (item.emoji) {
                        "أ", "অ", "A" -> 32.sp
                        else -> 28.sp
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 22.sp
            )
        }
    }
}