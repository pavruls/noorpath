package com.pavruls.noorpath.presentation.screens.mode

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.pavruls.noorpath.presentation.components.NoorPathBottomBar
import kotlinx.coroutines.delay
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_CIRCLE_1 = 100
private const val DELAY_CIRCLE_2 = 200
private const val DELAY_CALENDAR = 300
private const val DELAY_TITLE = 420
private const val DELAY_NAME = 480
private const val DELAY_SELECT = 540
private const val DELAY_CARDS_START = 620
private const val STAGGER_STEP = 70

@Composable
fun ModeSelectionScreen(
    onKidsClick: () -> Unit,
    onBooksClick: () -> Unit,
    onParentClick: () -> Unit,
    onPrayerHubClick: () -> Unit,
    onQiblaClick: () -> Unit,
    onTasbihClick: () -> Unit,
    onDailyHadithClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onQuranClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAssistantClick: () -> Unit
) {
    var currentTime by remember { mutableStateOf(Date()) }
    val language by LanguageManager.languageFlow.collectAsState()

    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Date()
            delay(1000L)
        }
    }

    val timeString = getTimeString(currentTime)
    val banglaDate = getBanglaDate(currentTime)
    val englishDate = getEnglishDateInBangla(currentTime)
    val hijriDate = getHijriDateInBangla()

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NoorPathBottomBar(
                selectedTab = 0,
                onHomeClick = { },
                onPrayerClick = onPrayerHubClick,
                onQuranClick = onBooksClick,
                onTasbihClick = onTasbihClick,
                onMoreClick = onSettingsClick
            )
        }
    ) { innerPadding ->
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
                    .padding(innerPadding)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // ===== Search + AI + Settings (Top-right) =====
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_HEADER) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Search Icon
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .clickable { onSearchClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = AppStrings.searchTitle(language),
                                    tint = Color.White
                                )
                            }

                            // 🤖 AI Assistant Icon
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(
                                                Color(0xFF00BCD4).copy(alpha = 0.45f),
                                                Color(0xFF00BCD4).copy(alpha = 0.15f)
                                            )
                                        )
                                    )
                                    .clickable { onAssistantClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🤖", fontSize = 22.sp)
                            }

                            // Settings Icon
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .clickable { onSettingsClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "⚙️", fontSize = 22.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ===== Circle Cards (Time + Qibla) =====
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CIRCLE_1) {
                        CircleCard(
                            emoji = "🕐",
                            topText = AppStrings.time(language),
                            mainText = timeString,
                            bottomText = AppStrings.now(language),
                            color = Color(0xFF2196F3),
                            size = 120.dp,
                            onClick = { }
                        )
                    }
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CIRCLE_2) {
                        CircleCard(
                            emoji = "🧭",
                            topText = AppStrings.qibla(language),
                            mainText = AppStrings.direction(language),
                            bottomText = AppStrings.find(language),
                            color = Color(0xFFD4AF37),
                            size = 120.dp,
                            onClick = onQiblaClick
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== Calendars =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CALENDAR) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFD4AF37).copy(alpha = 0.15f),
                                        Color(0xFF4CAF50).copy(alpha = 0.1f)
                                    )
                                )
                            )
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(text = "🇧🇩", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = banglaDate, fontSize = 15.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(text = "📅", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = englishDate, fontSize = 15.sp, color = Color.White.copy(alpha = 0.9f), fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(text = "🌙", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = hijriDate, fontSize = 15.sp, color = Color(0xFFD4AF37), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ===== نور + App Name =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_TITLE) {
                    Text(text = "نُور", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD4AF37))
                }
                Spacer(modifier = Modifier.height(4.dp))
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_NAME) {
                    Text(
                        text = AppStrings.appName(language),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_SELECT) {
                    Text(
                        text = AppStrings.selectMode(language),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ===== Mode Cards =====

                // 1. Kids Mode
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START) {
                    ModeCard(
                        emoji = "🧒",
                        title = AppStrings.kidsMode(language),
                        subtitle = AppStrings.kidsModeDesc(language),
                        color = Color(0xFF4CAF50),
                        onClick = onKidsClick
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                // 2. Book Mode
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START + STAGGER_STEP) {
                    ModeCard(
                        emoji = "📚",
                        title = AppStrings.bookMode(language),
                        subtitle = AppStrings.bookModeDesc(language),
                        color = Color(0xFFD4AF37),
                        onClick = onBooksClick
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                // 3. Complete Quran
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START + STAGGER_STEP * 2) {
                    ModeCard(
                        emoji = "📖",
                        title = AppStrings.quran(language),
                        subtitle = AppStrings.quranDesc(language),
                        color = Color(0xFF4CAF50),
                        onClick = onQuranClick
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                // 4. নামাজ ও ইবাদত Hub
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START + STAGGER_STEP * 3) {
                    ModeCard(
                        emoji = "🕌",
                        title = AppStrings.prayerHubTitle(language),
                        subtitle = AppStrings.prayerHubShortDesc(language),
                        color = Color(0xFF2196F3),
                        onClick = onPrayerHubClick
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                // 5. 🤖 ইসলামিক সহকারী (নতুন AI card)
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START + STAGGER_STEP * 4) {
                    ModeCard(
                        emoji = "🤖",
                        title = AppStrings.assistantTitle(language),
                        subtitle = AppStrings.assistantSubtitle(language),
                        color = Color(0xFF00BCD4),
                        onClick = onAssistantClick
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                // 6. Daily Hadith
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START + STAGGER_STEP * 5) {
                    ModeCard(
                        emoji = "📖",
                        title = AppStrings.dailyHadith(language),
                        subtitle = AppStrings.dailyHadithDesc(language),
                        color = Color(0xFF795548),
                        onClick = onDailyHadithClick
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                // 7. Parent Lock
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARDS_START + STAGGER_STEP * 6) {
                    ModeCard(
                        emoji = "🔒",
                        title = AppStrings.parentLock(language),
                        subtitle = AppStrings.parentLockDesc(language),
                        color = Color(0xFF9C27B0),
                        onClick = onParentClick
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

// ============ Reusable Stagger Wrapper ============
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
        enter = fadeIn(animationSpec = tween(350, easing = FastOutSlowInEasing)) +
                slideInVertically(
                    animationSpec = tween(420, easing = FastOutSlowInEasing),
                    initialOffsetY = { it / 4 }
                ) +
                scaleIn(
                    animationSpec = tween(420, easing = FastOutSlowInEasing),
                    initialScale = 0.92f
                )
    ) {
        content()
    }
}

// ============ Circle Card ============
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CircleCard(
    emoji: String,
    topText: String,
    mainText: String,
    bottomText: String,
    color: Color,
    size: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "circleScale"
    )

    Box(
        modifier = Modifier
            .size(size)
            .scale(pressScale)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.4f), color.copy(alpha = 0.12f))
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = color),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = emoji, fontSize = 26.sp)
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = topText,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = mainText,
                fontSize = if (mainText.length > 5) 14.sp else 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = bottomText,
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

// ============ Mode Card ============
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModeCard(
    emoji: String,
    title: String,
    subtitle: String,
    color: Color,
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
        label = "modeCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "modeIconScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "chevronAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = color),
                onClick = onClick
            )
            .padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .scale(iconScale)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 30.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = subtitle, fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
            }
            Text(
                text = "›",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = chevronAlpha)
            )
        }
    }
}

// ============ Helpers ============
private val banglaDigits = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

private fun toBanglaNumber(n: Int): String {
    return n.toString().map { banglaDigits[it - '0'] }.joinToString("")
}

private fun getTimeString(date: Date): String {
    val cal = Calendar.getInstance().apply { time = date }
    val hour24 = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }
    val amPm = if (hour24 < 12) "AM" else "PM"
    return "$hour12:${String.format("%02d", minute)} $amPm"
}

private val banglaMonths = listOf(
    "বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়", "শ্রাবণ", "ভাদ্র", "আশ্বিন",
    "কার্তিক", "অগ্রহায়ণ", "পৌষ", "মাঘ", "ফাল্গুন", "চৈত্র"
)
private val banglaMonthDays = listOf(31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30, 30)

private fun getBanglaDate(date: Date): String {
    return try {
        val cal = Calendar.getInstance().apply { time = date }
        val gYear = cal.get(Calendar.YEAR)
        val gMonth = cal.get(Calendar.MONTH)
        val gDay = cal.get(Calendar.DAY_OF_MONTH)
        val monthDay = (gMonth + 1) * 100 + gDay
        val banglaYear = if (monthDay < 414) gYear - 594 else gYear - 593
        val startYear = if (monthDay < 414) gYear - 1 else gYear
        val boishakhCal = Calendar.getInstance().apply {
            set(startYear, Calendar.APRIL, 14, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val currentCal = Calendar.getInstance().apply {
            set(gYear, gMonth, gDay, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val daysDiff = ((currentCal.timeInMillis - boishakhCal.timeInMillis) / (1000L * 60 * 60 * 24)).toInt()
        var remaining = daysDiff
        var monthIdx = 0
        while (monthIdx < banglaMonthDays.size && remaining >= banglaMonthDays[monthIdx]) {
            remaining -= banglaMonthDays[monthIdx]
            monthIdx++
        }
        if (monthIdx >= banglaMonths.size) monthIdx = banglaMonths.size - 1
        val banglaDay = remaining + 1
        "${toBanglaNumber(banglaDay)} ${banglaMonths[monthIdx]} ${toBanglaNumber(banglaYear)} বঙ্গাব্দ"
    } catch (e: Exception) {
        "—"
    }
}

private val englishMonthsBangla = listOf(
    "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল",
    "মে", "জুন", "জুলাই", "আগস্ট",
    "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"
)

private fun getEnglishDateInBangla(date: Date): String {
    return try {
        val cal = Calendar.getInstance().apply { time = date }
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        "${toBanglaNumber(day)} ${englishMonthsBangla[month]} ${toBanglaNumber(year)}"
    } catch (e: Exception) {
        "—"
    }
}

private val hijriMonthsBangla = listOf(
    "মুহাররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
    "জামাদিউল আউয়াল", "জামাদিউস সানি", "রজব", "শাবান",
    "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ"
)

private fun getHijriDateInBangla(): String {
    return try {
        val hijri = HijrahDate.now()
        val day = hijri.get(ChronoField.DAY_OF_MONTH)
        val monthIdx = hijri.get(ChronoField.MONTH_OF_YEAR) - 1
        val year = hijri.get(ChronoField.YEAR)
        val monthName = hijriMonthsBangla.getOrElse(monthIdx) { "" }
        val suffix = when {
            day == 1 -> "লা"
            day == 2 || day == 3 -> "রা"
            day == 4 -> "ঠা"
            day in 5..18 -> "ই"
            day == 20 -> "শে"
            day in 21..31 -> "শে"
            else -> "ই"
        }
        "${toBanglaNumber(day)}$suffix $monthName ${toBanglaNumber(year)} হিজরি"
    } catch (e: Exception) {
        "—"
    }
}