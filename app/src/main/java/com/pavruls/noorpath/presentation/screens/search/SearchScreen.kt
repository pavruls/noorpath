package com.pavruls.noorpath.presentation.screens.search

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.search.SearchHit
import com.pavruls.noorpath.search.SearchRepository
import com.pavruls.noorpath.search.SearchSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_SEARCH_BAR = 100
private const val DELAY_CHIPS = 220
private const val STAGGER_CHIP = 60
private const val DELAY_RESULTS_START = 160
private const val STAGGER_RESULT = 55
private const val ANIMATE_RESULTS_THRESHOLD = 10

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onResultClick: (SearchHit) -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()

    var query by remember { mutableStateOf("") }
    var source by remember { mutableStateOf(SearchSource.ALL) }
    var results by remember { mutableStateOf<List<SearchHit>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    // Results key — প্রতিবার নতুন result আসলে bump হবে, যাতে StaggeredEntry আবার trigger হয়
    var resultsKey by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) { SearchRepository.preload(context) }
    }

    LaunchedEffect(query, source) {
        if (query.length < 2) {
            results = emptyList()
            return@LaunchedEffect
        }
        isSearching = true
        delay(180)  // debounce: typed characters coalesce
        val hits = withContext(Dispatchers.IO) {
            SearchRepository.search(query, source)
        }
        results = hits
        resultsKey++
        isSearching = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0A2E1F), Color(0xFF1B5E20))
                )
            )
    ) {
        Column(Modifier.fillMaxSize()) {

            // ===== Header =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_HEADER) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = AppStrings.back(language),
                            tint = Color.White
                        )
                    }
                    Text(
                        text = AppStrings.searchTitle(language),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // ===== Search Bar =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_SEARCH_BAR) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = {
                        Text(
                            AppStrings.searchHint(language),
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFFD4AF37)
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = AppStrings.close(language),
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFD4AF37),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color(0xFFD4AF37)
                    )
                )
            }

            Spacer(Modifier.height(12.dp))

            // ===== Filter Chips (staggered) =====
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = SearchSource.values().toList(),
                    key = { _, s -> s.name }
                ) { index, s ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_CHIPS + index * STAGGER_CHIP
                    ) {
                        FilterChip(
                            label = labelFor(s, language),
                            selected = source == s,
                            onClick = { source = s }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ===== Content States =====
            when {
                query.length < 2 -> {
                    HintState(
                        text = AppStrings.searchTypeAtLeast(language),
                        visible = entryVisible
                    )
                }

                isSearching -> {
                    SearchPulseLoader()
                }

                results.isEmpty() -> {
                    EmptyState(
                        language = language,
                        visible = entryVisible
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(
                            items = results,
                            key = { _, hit -> "${hit.source}_${hit.title}_${hit.meta}" }
                        ) { index, hit ->
                            val delay = if (index < ANIMATE_RESULTS_THRESHOLD) {
                                DELAY_RESULTS_START + index * STAGGER_RESULT
                            } else 0

                            // `resultsKey` ব্যবহার করে re-trigger on new search
                            StaggeredEntry(
                                visible = true,
                                delayMillis = delay
                            ) {
                                ResultCard(hit = hit, onClick = { onResultClick(hit) })
                            }
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Search Pulse Loader =====
// ============================================================
@Composable
private fun SearchPulseLoader() {
    val infinite = rememberInfiniteTransition(label = "searchLoader")
    val ringRotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing)
        ),
        label = "searchRingRotation"
    )
    val pulseScale by infinite.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "searchPulseScale"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFD4AF37).copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    )
            )

            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp)
                    .rotate(ringRotation),
                color = Color(0xFFD4AF37),
                strokeWidth = 4.dp,
                trackColor = Color(0xFFD4AF37).copy(alpha = 0.15f)
            )

            Text(text = "🔍", fontSize = 26.sp)
        }
    }
}

// ============================================================
// ===== Hint State (fade in) =====
// ============================================================
@Composable
private fun HintState(text: String, visible: Boolean) {
    StaggeredEntry(visible = visible, delayMillis = DELAY_CHIPS + 300) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "⌨️", fontSize = 48.sp)
                Spacer(Modifier.height(12.dp))
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ============================================================
// ===== Empty State (pulse + fade) =====
// ============================================================
@Composable
private fun EmptyState(language: String, visible: Boolean) {
    val infinite = rememberInfiniteTransition(label = "emptyPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emptyPulseScale"
    )

    StaggeredEntry(visible = visible, delayMillis = DELAY_RESULTS_START) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "🔍",
                    fontSize = 64.sp,
                    modifier = Modifier.scale(pulseScale)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = AppStrings.searchNoResults(language),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ============================================================
// ===== Label Helper =====
// ============================================================
private fun labelFor(s: SearchSource, lang: String): String = when (s) {
    SearchSource.ALL -> AppStrings.searchFilterAll(lang)
    SearchSource.QURAN -> AppStrings.searchFilterQuran(lang)
    SearchSource.HADITH -> AppStrings.searchFilterHadith(lang)
    SearchSource.DUA -> AppStrings.searchFilterDua(lang)
}

// ============================================================
// ===== Filter Chip (press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "chipScale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (selected) Color(0xFFD4AF37)
                else Color.White.copy(alpha = 0.1f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Color(0xFF0A2E1F) else Color.White,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 13.sp
        )
    }
}

// ============================================================
// ===== Result Card (press feedback + icon bounce) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultCard(hit: SearchHit, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "resultCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "resultIconScale"
    )

    val emoji = when (hit.source) {
        SearchSource.QURAN -> "📖"
        SearchSource.HADITH -> "📚"
        SearchSource.DUA -> "🤲"
        else -> "🔍"
    }
    val accent = when (hit.source) {
        SearchSource.QURAN -> Color(0xFF4CAF50)
        SearchSource.HADITH -> Color(0xFF2196F3)
        SearchSource.DUA -> Color(0xFFD4AF37)
        else -> Color.Gray
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.07f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = accent),
                onClick = onClick
            )
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = emoji,
                fontSize = 18.sp,
                modifier = Modifier.scale(iconScale)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = hit.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = accent
            )
        }
        if (hit.arabic.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = hit.arabic,
                fontSize = 18.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                maxLines = 3
            )
        }
        if (hit.snippet.isNotBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = hit.snippet,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 20.sp,
                maxLines = 3
            )
        }
    }
}