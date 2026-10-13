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
private const val DELAY_ARABIC_BOX = 120
private const val DELAY_VERSES_START = 280
private const val STAGGER_VERSE = 80

@Composable
fun SurahDetailScreen(
    surahId: Int,
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val surah = SurahData.allSurahs.find { it.id == surahId } ?: SurahData.allSurahs.first()

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
                            text = surah.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.surahMeaningWithVerses(
                                language,
                                surah.meaning,
                                surah.totalVerses
                            ),
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
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ===== Arabic Header Box (with glow + pulse) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_ARABIC_BOX) {
                    GlowArabicHeaderBox(
                        arabicName = surah.arabicName,
                        surahNumber = surah.number,
                        language = language,
                        color = surah.color
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== Verse Cards (wave stagger) =====
                surah.verses.forEachIndexed { index, verse ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_VERSES_START + index * STAGGER_VERSE
                    ) {
                        VerseCard(
                            verse = verse,
                            accentColor = surah.color,
                            language = language
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ============================================================
// ===== Glowing Arabic Header Box =====
// ============================================================
@Composable
private fun GlowArabicHeaderBox(
    arabicName: String,
    surahNumber: String,
    language: String,
    color: Color
) {
    // Soft infinite glow
    val infinite = rememberInfiniteTransition(label = "surahHeaderGlow")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.20f,
        targetValue = 0.32f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "surahHeaderGlowAlpha"
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
            Text(
                text = arabicName,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = AppStrings.surahNumberLabel(language, surahNumber),
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

// ============================================================
// ===== Verse Card (with number pulse + press) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerseCard(
    verse: SurahVerse,
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
        label = "verseCardScale"
    )
    val numberScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "verseNumberScale"
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
        // আয়াত নম্বর
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .scale(numberScale)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${verse.number}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // আরবি
        Text(
            text = verse.arabic,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37),
            textAlign = TextAlign.Right,
            lineHeight = 42.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        // উচ্চারণ
        Text(
            text = "${AppStrings.pronunciationLabel(language)}: ${verse.pronunciation}",
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.85f),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // অর্থ
        Text(
            text = "${AppStrings.meaningLabel(language)}: ${verse.meaning}",
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.7f),
            lineHeight = 20.sp
        )
    }
}