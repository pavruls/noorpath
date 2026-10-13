package com.pavruls.noorpath.presentation.screens.prayer

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.pavruls.noorpath.data.preferences.PrayerDataStore
import com.pavruls.noorpath.data.preferences.RamadanSettings
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.components.StaggerTiming
import com.pavruls.noorpath.presentation.components.StaggeredEntry

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_SEHRI_IFTAAR_1 = 120
private const val DELAY_SEHRI_IFTAAR_2 = 200
private const val DELAY_TARAWEEH = 300
private const val DELAY_TRACKER_TITLE = 440
private const val DELAY_TRACKER_PROGRESS = 520
private const val DELAY_GRID_START = 600
private const val STAGGER_DAY = 25
private const val DELAY_INFO_START = 1400
private const val STAGGER_INFO = 120

@Composable
fun RamadanScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()

    var fastedDays by remember { mutableStateOf(RamadanSettings.getFastedDays(context)) }
    var taraweehRakat by remember { mutableIntStateOf(RamadanSettings.getTaraweehRakat(context)) }
    var taraweehTarget by remember { mutableIntStateOf(RamadanSettings.getTaraweehTarget(context)) }
    var showResetDialog by remember { mutableStateOf(false) }

    // Staggered entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    val sehriTime = PrayerDataStore.getFajr(context) ?: "—"
    val iftarTime = PrayerDataStore.getMaghrib(context) ?: "—"

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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = AppStrings.ramadanScreenTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.ramadanScreenSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFF5252).copy(alpha = 0.2f))
                            .clickable { showResetDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🔄", fontSize = 20.sp)
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // ===== Sehri / Iftar Row =====
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_SEHRI_IFTAAR_1
                        ) {
                            SehriIftarCard(
                                emoji = "🌙",
                                title = AppStrings.sehriLabel(language),
                                time = sehriTime,
                                hint = AppStrings.beforeFajrHint(language),
                                color = Color(0xFF2196F3),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_SEHRI_IFTAAR_2
                        ) {
                            SehriIftarCard(
                                emoji = "🌇",
                                title = AppStrings.iftarLabel(language),
                                time = iftarTime,
                                hint = AppStrings.atMaghribHint(language),
                                color = Color(0xFFFF9800),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // ===== Taraweeh Card =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_TARAWEEH
                    ) {
                        TaraweehCard(
                            currentRakat = taraweehRakat,
                            targetRakat = taraweehTarget,
                            language = language,
                            onIncrement = {
                                taraweehRakat++
                                RamadanSettings.saveTaraweehRakat(context, taraweehRakat)
                            },
                            onDecrement = {
                                if (taraweehRakat > 0) {
                                    taraweehRakat--
                                    RamadanSettings.saveTaraweehRakat(context, taraweehRakat)
                                }
                            }
                        )
                    }
                }

                // ===== Fasting Tracker Title =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_TRACKER_TITLE
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "📅", fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = AppStrings.fastingTrackerLabel(language),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = AppStrings.fastingProgressLabel(
                                            language,
                                            fastedDays.size,
                                            30
                                        ),
                                        fontSize = 12.sp,
                                        color = Color(0xFFD4AF37)
                                    )
                                }
                            }
                        }
                    }
                }

                // ===== Progress Bar =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_TRACKER_PROGRESS
                    ) {
                        FastingProgressBar(fastedCount = fastedDays.size)
                    }
                }

                // ===== 30 Day Grid =====
                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 800.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        userScrollEnabled = false
                    ) {
                        itemsIndexed((1..30).toList()) { index, day ->
                            val isFasted = fastedDays.contains(day)
                            StaggeredEntry(
                                visible = entryVisible,
                                delayMillis = DELAY_GRID_START + index * STAGGER_DAY
                            ) {
                                DayBox(
                                    day = day,
                                    isFasted = isFasted,
                                    onClick = {
                                        RamadanSettings.toggleFastedDay(context, day)
                                        fastedDays = RamadanSettings.getFastedDays(context)
                                    }
                                )
                            }
                        }
                    }
                }

                // ===== Info Cards =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_INFO_START
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            InfoCard(
                                emoji = "✨",
                                title = AppStrings.laylatulQadrTitle(language),
                                content = AppStrings.laylatulQadrContent(language)
                            )
                        }
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_INFO_START + STAGGER_INFO
                    ) {
                        InfoCard(
                            emoji = "🕌",
                            title = AppStrings.itikafTitle(language),
                            content = AppStrings.itikafContent(language)
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_INFO_START + STAGGER_INFO * 2
                    ) {
                        InfoCard(
                            emoji = "🤲",
                            title = AppStrings.ramadanDuaTitle(language),
                            content = AppStrings.iftarDuaContent(language)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // ===== Reset Dialog (Animated) =====
    NoorPathDialog(
        visible = showResetDialog,
        onDismiss = { showResetDialog = false },
        title = AppStrings.ramadanResetTitle(language),
        message = AppStrings.ramadanResetMessage(language),
        icon = "⚠️",
        iconTint = Color(0xFFFF5252),
        confirmText = AppStrings.resetYes(language),
        cancelText = AppStrings.cancel(language),
        onConfirm = {
            RamadanSettings.resetAll(context)
            fastedDays = emptySet()
            taraweehRakat = 0
            showResetDialog = false
        }
    )
}

// ============================================================
// ===== Sehri / Iftar Card =====
// ============================================================
@Composable
private fun SehriIftarCard(
    emoji: String,
    title: String,
    time: String,
    hint: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.35f),
                        color.copy(alpha = 0.15f)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = time,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = hint,
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

// ============================================================
// ===== Fasting Progress Bar (animated fill) =====
// ============================================================
@Composable
private fun FastingProgressBar(fastedCount: Int) {
    val animatedFraction by animateFloatAsState(
        targetValue = fastedCount / 30f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "fastingProgress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = animatedFraction)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFD4AF37), Color(0xFF4CAF50))
                    )
                )
        )
    }
}

// ============================================================
// ===== Taraweeh Card (with number count-up + press buttons) =====
// ============================================================
@Composable
private fun TaraweehCard(
    currentRakat: Int,
    targetRakat: Int,
    language: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    // Animated number count-up
    val animatedRakat by animateFloatAsState(
        targetValue = currentRakat.toFloat(),
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "taraweehCountUp"
    )

    // Animated progress bar
    val animatedProgress by animateFloatAsState(
        targetValue = if (targetRakat > 0)
            (currentRakat.toFloat() / targetRakat).coerceIn(0f, 1f)
        else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "taraweehProgress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = 0.3f),
                        Color(0xFF4CAF50).copy(alpha = 0.2f)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🌙", fontSize = 26.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = AppStrings.taraweehRakatLabel(language),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // ⬇️ Decrement Button (with press scale)
                TaraweehButton(
                    symbol = "−",
                    background = Color.White.copy(alpha = 0.1f),
                    onClick = onDecrement
                )

                Spacer(modifier = Modifier.width(24.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Animated count-up number
                    Text(
                        text = "${animatedRakat.toInt()}",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37)
                    )
                    Text(
                        text = "/ $targetRakat",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                // ⬆️ Increment Button (with press scale)
                TaraweehButton(
                    symbol = "+",
                    background = Color(0xFF4CAF50),
                    onClick = onIncrement
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Animated progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = animatedProgress)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (currentRakat >= targetRakat && targetRakat > 0)
                                Color(0xFF4CAF50)
                            else
                                Color(0xFFD4AF37)
                        )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (currentRakat >= targetRakat && targetRakat > 0) {
                Text(
                    text = AppStrings.taraweehComplete(language),
                    fontSize = 13.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = AppStrings.taraweehResetHint(language),
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaraweehButton(
    symbol: String,
    background: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "taraweehButtonScale"
    )

    Box(
        modifier = Modifier
            .size(56.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(background)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color.White),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============================================================
// ===== Day Box (with scale bounce on check) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DayBox(
    day: Int,
    isFasted: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "dayBoxPressScale"
    )
    // Bounce when transitioning to fasted
    val checkScale by animateFloatAsState(
        targetValue = if (isFasted) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "dayBoxCheckScale"
    )

    Box(
        modifier = Modifier
            .size(56.dp)
            .scale(pressScale * checkScale)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isFasted) Color(0xFF4CAF50)
                else Color.White.copy(alpha = 0.08f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    color = if (isFasted) Color(0xFF2E7D32) else Color(0xFFD4AF37)
                ),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isFasted) {
                Text(
                    text = "✓",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "$day",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ============================================================
// ===== Info Card =====
// ============================================================
@Composable
private fun InfoCard(
    emoji: String,
    title: String,
    content: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD4AF37).copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4AF37)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )
        }
    }
}