package com.pavruls.noorpath.presentation.screens.dailyhadith

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.hadith.DailyHadithData
import com.pavruls.noorpath.hadith.DailyHadithItem
import com.pavruls.noorpath.presentation.components.DailyHadithFullCard
import com.pavruls.noorpath.presentation.components.StaggeredEntry

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_TODAY_CARD = 120
private const val DELAY_LIST_TITLE = 320
private const val DELAY_LIST_START = 420
private const val STAGGER_ITEM = 60

@Composable
fun DailyHadithScreen(
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()
    var currentHadith by remember { mutableStateOf(DailyHadithData.getTodaysHadith()) }

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
                            text = AppStrings.dailyHadith(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.dailyHadithCollectionSubtitle(
                                language,
                                DailyHadithData.allHadiths.size
                            ),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== Content List =====
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ===== Today's Featured Card =====
                item(key = "today_card") {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_TODAY_CARD
                    ) {
                        DailyHadithFullCard(
                            hadith = currentHadith,
                            onNewHadithClick = {
                                currentHadith = DailyHadithData.getRandomHadith()
                            }
                        )
                    }
                }

                // ===== All Hadiths Title =====
                item(key = "all_hadiths_title") {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_TITLE
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = AppStrings.allHadithLabel(language),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // ===== Hadith List (staggered) =====
                itemsIndexed(
                    items = DailyHadithData.allHadiths,
                    key = { _, hadith -> hadith.id }
                ) { index, hadith ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_START + index * STAGGER_ITEM
                    ) {
                        HadithListItem(hadith = hadith)
                    }
                }

                // ===== Bottom Spacer =====
                item(key = "bottom_spacer") {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

// ============================================================
// ===== Hadith List Item (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HadithListItem(hadith: DailyHadithItem) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "hadithItemCardScale"
    )
    val numberScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "hadithItemNumberScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = { /* Future: open detail */ }
            )
            .padding(14.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .scale(numberScale)
                        .clip(CircleShape)
                        .background(Color(0xFFD4AF37).copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${hadith.id}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = hadith.source,
                    fontSize = 11.sp,
                    color = Color(0xFFD4AF37)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hadith.arabic,
                fontSize = 15.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = hadith.bengali,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "— ${hadith.narrator}",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}