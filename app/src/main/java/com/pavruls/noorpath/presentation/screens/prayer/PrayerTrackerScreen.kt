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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavruls.noorpath.data.local.entity.PrayerTrackerEntity
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.DialogStaggerItem
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.presentation.viewmodel.PrayerTrackerViewModel

private data class PrayerCheckItem(
    val nameKey: String,
    val arabicName: String,
    val emoji: String,
    val color: Color,
    val value: Boolean,
    val prayerKey: String
)

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_TODAY_CARD = 120
private const val DELAY_TODAY_TITLE = 240
private const val DELAY_PRAYERS_START = 320
private const val STAGGER_PRAYER = 70
private const val DELAY_WEEKLY_TITLE = 720
private const val DELAY_WEEKLY_CARD = 790
private const val DELAY_MONTHLY_TITLE = 930
private const val DELAY_MONTHLY_CARD = 1000

@Composable
fun PrayerTrackerScreen(
    onBackClick: () -> Unit,
    viewModel: PrayerTrackerViewModel = hiltViewModel()
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val todayEntry by viewModel.todayEntry.collectAsStateWithLifecycle()
    val recentDays by viewModel.recentDays.collectAsStateWithLifecycle()
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()
    val allQada by viewModel.allQada.collectAsStateWithLifecycle()

    var showQadaDialog by remember { mutableStateOf(false) }

    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    val today = todayEntry

    val prayers = listOf(
        PrayerCheckItem("fajr", "الفجر", "🌅", Color(0xFF2196F3), today?.fajr == true, "Fajr"),
        PrayerCheckItem("dhuhr", "الظهر", "☀️", Color(0xFFFF9800), today?.dhuhr == true, "Dhuhr"),
        PrayerCheckItem("asr", "العصر", "🌇", Color(0xFFE91E63), today?.asr == true, "Asr"),
        PrayerCheckItem("maghrib", "المغرب", "🌆", Color(0xFF9C27B0), today?.maghrib == true, "Maghrib"),
        PrayerCheckItem("isha", "العشاء", "🌙", Color(0xFF3F51B5), today?.isha == true, "Isha")
    )

    fun getPrayerName(key: String): String = when (key) {
        "fajr" -> AppStrings.fajr(language)
        "dhuhr" -> AppStrings.dhuhr(language)
        "asr" -> AppStrings.asr(language)
        "maghrib" -> AppStrings.maghrib(language)
        "isha" -> AppStrings.isha(language)
        else -> key
    }

    val todayCount = prayers.count { it.value }

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
                            text = AppStrings.prayerTracker(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.todayProgressLabel(language, todayCount, 5),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFD4AF37).copy(alpha = 0.2f))
                            .clickable { showQadaDialog = true }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = AppStrings.qadaLabel(language),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_TODAY_CARD
                    ) {
                        TodayProgressCard(todayCount, language)
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_TODAY_TITLE
                    ) {
                        Text(
                            text = AppStrings.todaysPrayerLabel(language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                itemsIndexed(prayers) { index, prayer ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_PRAYERS_START + index * STAGGER_PRAYER
                    ) {
                        PrayerCheckCard(
                            prayer = prayer,
                            displayName = getPrayerName(prayer.nameKey),
                            language = language,
                            onToggle = { value ->
                                viewModel.togglePrayer(prayer.prayerKey, value)
                            }
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_WEEKLY_TITLE
                    ) {
                        Text(
                            text = AppStrings.weeklyReportLabel(language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_WEEKLY_CARD
                    ) {
                        WeeklyReportCard(recentDays, language)
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_MONTHLY_TITLE
                    ) {
                        Text(
                            text = AppStrings.monthlyProgressLabel(language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_MONTHLY_CARD
                    ) {
                        MonthlyReportCard(currentMonth, language)
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }

    // ===== Qada Dialog =====
    NoorPathDialog(
        visible = showQadaDialog,
        onDismiss = { showQadaDialog = false },
        title = AppStrings.qadaDialogTitle(language),
        icon = "📋",
        iconTint = Color(0xFFD4AF37),
        confirmText = AppStrings.close(language),
        onConfirm = { showQadaDialog = false }
    ) {
        QadaDialogContent(
            allQada = allQada.map { Pair(it.prayerName, Pair(it.totalQada, it.completedQada)) },
            language = language,
            onSave = { name, total, completed -> viewModel.saveQada(name, total, completed) },
            onIncrement = { name -> viewModel.incrementQada(name) }
        )
    }
}

// ============================================================
// ===== Today Progress Card =====
// ============================================================
@Composable
private fun TodayProgressCard(todayCount: Int, language: String) {
    val percentage = (todayCount * 100) / 5

    val animatedFraction by animateFloatAsState(
        targetValue = todayCount / 5f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "todayProgress"
    )

    val iconScale by animateFloatAsState(
        targetValue = if (todayCount == 5) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "todayIconScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = 0.35f),
                        Color(0xFF4CAF50).copy(alpha = 0.25f)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 🔑 Scoped explicitly to avoid RowScope ambiguity in enclosing row-like scopes
            androidx.compose.animation.AnimatedVisibility(
                visible = true,
                enter = androidx.compose.animation.fadeIn(animationSpec = tween(300))
            ) {
                Text(
                    text = if (todayCount == 5) "🎉" else "🕌",
                    fontSize = 50.sp,
                    modifier = Modifier.scale(iconScale)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = AppStrings.timesCompleted(language, todayCount, 5),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = AppStrings.percentComplete(language, percentage),
                fontSize = 14.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

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
                        .fillMaxWidth(fraction = animatedFraction)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (todayCount == 5) Color(0xFF4CAF50)
                            else Color(0xFFD4AF37)
                        )
                )
            }

            // Success message — use androidx.compose.animation.AnimatedVisibility explicitly
            if (todayCount == 5) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = AppStrings.allPrayersDone(language),
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ============================================================
// ===== Prayer Check Card (fixed — no AnimatedVisibility inside Row) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrayerCheckCard(
    prayer: PrayerCheckItem,
    displayName: String,
    language: String,
    onToggle: (Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "prayerCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (prayer.value) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "prayerIconScale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (prayer.value) Color(0xFF4CAF50).copy(alpha = 0.2f)
                else Color.White.copy(alpha = 0.06f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    color = if (prayer.value) Color(0xFF4CAF50) else prayer.color
                ),
                onClick = { onToggle(!prayer.value) }
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Check circle — simple if/else (no AnimatedVisibility inside Row)
        Box(
            modifier = Modifier
                .size(36.dp)
                .scale(iconScale)
                .clip(CircleShape)
                .background(
                    if (prayer.value) Color(0xFF4CAF50)
                    else prayer.color.copy(alpha = 0.25f)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (prayer.value) {
                Text(
                    text = "✓",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(text = prayer.emoji, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = displayName,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = prayer.arabicName,
                fontSize = 14.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = if (prayer.value) AppStrings.prayedLabel(language)
            else AppStrings.remainingLabel(language),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (prayer.value) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.5f)
        )
    }
}

// ============================================================
// ===== Weekly Report Card =====
// ============================================================
@Composable
private fun WeeklyReportCard(recentDays: List<PrayerTrackerEntity>, language: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .padding(16.dp)
    ) {
        if (recentDays.isEmpty()) {
            Text(
                text = AppStrings.noDataYet(language),
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            Column {
                recentDays.take(7).forEachIndexed { index, day ->
                    val count = listOf(
                        day.fajr, day.dhuhr, day.asr, day.maghrib, day.isha
                    ).count { it }

                    StaggeredEntry(
                        visible = true,
                        delayMillis = 100 + index * 60
                    ) {
                        WeeklyRow(day = day, count = count)
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyRow(day: PrayerTrackerEntity, count: Int) {
    val animatedFraction by animateFloatAsState(
        targetValue = count / 5f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "weeklyRowProgress"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day.dateKey,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.width(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = animatedFraction)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (count == 5) Color(0xFF4CAF50)
                        else Color(0xFFD4AF37)
                    )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$count/5",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37)
        )
    }
}

// ============================================================
// ===== Monthly Report Card =====
// ============================================================
@Composable
private fun MonthlyReportCard(currentMonth: List<PrayerTrackerEntity>, language: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .padding(16.dp)
    ) {
        val totalDays = currentMonth.size
        val totalPrayers = currentMonth.sumOf {
            listOf(it.fajr, it.dhuhr, it.asr, it.maghrib, it.isha).count { p -> p }
        }
        val expectedPrayers = totalDays * 5
        val percentage = if (expectedPrayers > 0) (totalPrayers * 100) / expectedPrayers else 0

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatBox(AppStrings.dayLabel(language), "$totalDays", "📅")
                StatBox(AppStrings.offeredLabel(language), "$totalPrayers", "✅")
                StatBox(AppStrings.percentLabel(language), "$percentage%", "📊")
            }
        }
    }
}

@Composable
private fun StatBox(label: String, value: String, emoji: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

// ============================================================
// ===== Qada Dialog Content =====
// ============================================================
@Composable
private fun QadaDialogContent(
    allQada: List<Pair<String, Pair<Int, Int>>>,
    language: String,
    onSave: (String, Int, Int) -> Unit,
    onIncrement: (String) -> Unit
) {
    val prayerNames = listOf(
        "Fajr" to AppStrings.fajr(language),
        "Dhuhr" to AppStrings.dhuhr(language),
        "Asr" to AppStrings.asr(language),
        "Maghrib" to AppStrings.maghrib(language),
        "Isha" to AppStrings.isha(language)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        itemsIndexed(prayerNames) { index, pair ->
            val (key, banglaName) = pair
            val existing = allQada.find { it.first == key }
            var total by remember {
                mutableIntStateOf(existing?.second?.first ?: 0)
            }
            val completed = existing?.second?.second ?: 0

            DialogStaggerItem(
                visible = true,
                delayMillis = 40 + index * 60
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = banglaName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$completed / $total",
                            fontSize = 13.sp,
                            color = Color(0xFFD4AF37),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = if (total == 0) "" else total.toString(),
                            // ✅ No redundant `it ->` arrow (uses implicit `it`)
                            onValueChange = { newValue ->
                                val n = newValue.toIntOrNull() ?: 0
                                total = n
                                onSave(key, n, completed)
                            },
                            placeholder = {
                                Text(
                                    AppStrings.totalLabelShort(language),
                                    color = Color.White.copy(alpha = 0.4f)
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFD4AF37),
                                unfocusedBorderColor = Color.White.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF4CAF50).copy(alpha = 0.25f))
                                .clickable { onIncrement(key) }
                                .padding(horizontal = 12.dp, vertical = 14.dp)
                        ) {
                            Text(
                                AppStrings.plusOneLabel(language),
                                fontSize = 13.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}