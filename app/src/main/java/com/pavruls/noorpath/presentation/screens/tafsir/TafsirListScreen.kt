package com.pavruls.noorpath.presentation.screens.tafsir

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.ShimmerListSkeleton
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.tafsir.Surah
import com.pavruls.noorpath.tafsir.TafsirRepository

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 40
private const val ANIMATE_THRESHOLD = 10

@Composable
fun TafsirListScreen(
    onBackClick: () -> Unit,
    onSurahClick: (Int, String) -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    var surahs by remember { mutableStateOf<List<Surah>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        surahs = TafsirRepository.getAllSurahs(context)
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
                    Column {
                        Text(
                            text = AppStrings.tafsirTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.surahCountShort(language, surahs.size),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== Loading Shimmer =====
            if (isLoading) {
                ShimmerListSkeleton(
                    itemCount = 8,
                    contentPadding = PaddingValues(16.dp),
                    verticalSpacing = 10.dp,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // ===== Surah List (staggered) =====
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = surahs,
                        key = { _, surah -> surah.id }
                    ) { index, surah ->
                        val delay = if (index < ANIMATE_THRESHOLD) {
                            DELAY_LIST_START + index * STAGGER_STEP
                        } else 0

                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = delay
                        ) {
                            SurahCard(
                                surah = surah,
                                language = language,
                                onClick = {
                                    onSurahClick(surah.id, surah.getDisplayName(language))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Surah Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurahCard(
    surah: Surah,
    language: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "tafsirCardScale"
    )
    val numberScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "tafsirNumberScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "tafsirChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = onClick
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .scale(numberScale)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${surah.id}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = surah.getDisplayName(language),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = AppStrings.surahTransliteration(
                    language,
                    surah.transliteration,
                    surah.totalVerses
                ),
                fontSize = 12.sp,
                color = Color(0xFFD4AF37)
            )
        }
        Text(
            text = "›",
            fontSize = 26.sp,
            color = Color.White.copy(alpha = chevronAlpha)
        )
    }
}