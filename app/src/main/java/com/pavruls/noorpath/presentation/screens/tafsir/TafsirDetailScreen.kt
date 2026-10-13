package com.pavruls.noorpath.presentation.screens.tafsir

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.ShimmerListSkeleton
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.tafsir.TafsirRepository
import com.pavruls.noorpath.tafsir.Verse

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 60
private const val ANIMATE_THRESHOLD = 8

@Composable
fun TafsirDetailScreen(
    surahId: Int,
    surahName: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    var verses by remember { mutableStateOf<List<Verse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }

    LaunchedEffect(surahId) {
        isLoading = true
        verses = TafsirRepository.getVersesForSurah(context, surahId)
        isLoading = false
        entryVisible = true
    }

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
            StaggeredEntry(visible = entryVisible || isLoading, delayMillis = DELAY_HEADER) {
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = surahName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (!isLoading) {
                            Text(
                                text = AppStrings.verseCount(language, verses.size),
                                fontSize = 12.sp,
                                color = Color(0xFFD4AF37)
                            )
                        }
                    }
                }
            }

            // ===== Loading Shimmer =====
            if (isLoading) {
                ShimmerListSkeleton(
                    itemCount = 6,
                    contentPadding = PaddingValues(16.dp),
                    verticalSpacing = 14.dp,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // ===== Verse List (staggered) =====
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    itemsIndexed(
                        items = verses,
                        key = { _, verse -> verse.id }
                    ) { index, verse ->
                        val delay = if (index < ANIMATE_THRESHOLD) {
                            DELAY_LIST_START + index * STAGGER_STEP
                        } else 0

                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = delay
                        ) {
                            VerseCard(verse, language)
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Verse Card (with Arabic glow pulse) =====
// ============================================================
@Composable
private fun VerseCard(verse: Verse, language: String) {
    // Subtle infinite glow on Arabic section
    val infinite = rememberInfiniteTransition(label = "tafsirArabicGlow")
    val arabicGlow by infinite.animateFloat(
        initialValue = 0.08f,
        targetValue = 0.16f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tafsirArabicGlowAlpha"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .padding(16.dp)
    ) {
        // ===== Verse number =====
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD4AF37).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${verse.id}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = AppStrings.verseNumber(language, verse.id),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ===== Arabic (with subtle glow) =====
        if (verse.arabic.isNotBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFD4AF37).copy(alpha = arabicGlow))
                    .padding(14.dp)
            ) {
                Text(
                    text = verse.arabic,
                    fontSize = 22.sp,
                    color = Color(0xFFD4AF37),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // ===== Bengali translation =====
        if (language == LanguageManager.LANG_BN && verse.bengali.isNotBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF4CAF50).copy(alpha = 0.1f))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🇧🇩", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = AppStrings.bengaliTranslation(language),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = verse.bengali,
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.92f),
                    lineHeight = 24.sp
                )
            }
        }

        // ===== English translation =====
        if (language == LanguageManager.LANG_EN && verse.english.isNotBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF2196F3).copy(alpha = 0.1f))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🇬🇧", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = AppStrings.englishLabel(language),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = verse.english,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 22.sp
                )
            }
        }
    }
}