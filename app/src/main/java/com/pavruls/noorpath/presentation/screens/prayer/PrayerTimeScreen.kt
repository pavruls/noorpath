package com.pavruls.noorpath.presentation.screens.prayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.data.preferences.PrayerDataStore
import com.pavruls.noorpath.data.preferences.PrayerSettings
import com.pavruls.noorpath.data.remote.api.PrayerApi
import com.pavruls.noorpath.data.remote.api.PrayerTimes
import com.pavruls.noorpath.notifications.PrayerScheduler
import com.pavruls.noorpath.presentation.components.DialogStaggerItem
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.components.StaggerTiming
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.presentation.widget.IslamicCalendarWidgetUpdater
import com.pavruls.noorpath.presentation.widget.PrayerWidgetUpdater
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.coroutines.resume

// ============ ডেটা ক্লাস ============

data class PrayerItem(
    val name: String,
    val arabicName: String,
    val time: String,
    val emoji: String,
    val color: Color
)

data class NextPrayerInfo(
    val name: String,
    val arabicName: String,
    val emoji: String,
    val color: Color,
    val timeRemaining: String,
    val timeToday: String
)

private data class PrayerInfoRaw(
    val name: String,
    val arabicName: String,
    val emoji: String,
    val color: Color,
    val time: String
)

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_NEXT_CARD = 120
private const val DELAY_LOCATION = 240
private const val DELAY_LIST_TITLE = 340
private const val DELAY_PRAYERS_START = 420
private const val STAGGER_PRAYER = 70
private const val DELAY_RELOAD = 920

// ============ মূল স্ক্রিন ============

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimeScreen(
    onBackClick: () -> Unit,
    onQiblaClick: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()

    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    var prayerTimes by remember { mutableStateOf<PrayerTimes?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var reloadTrigger by remember { mutableIntStateOf(0) }
    var showMethodDialog by remember { mutableStateOf(false) }
    var currentMethod by remember { mutableIntStateOf(PrayerSettings.getMethod(context)) }

    var currentTimeMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Staggered entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            currentTimeMillis = System.currentTimeMillis()
        }
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest()
        }
    }

    LaunchedEffect(locationPermission.status.isGranted, reloadTrigger, currentMethod) {
        if (locationPermission.status.isGranted) {
            isLoading = true
            errorMessage = null
            try {
                val location = getCurrentLocation(context)
                if (location != null) {
                    prayerTimes = PrayerApi.getPrayerTimes(
                        location.latitude,
                        location.longitude,
                        method = currentMethod
                    )
                    if (prayerTimes == null) {
                        errorMessage = "Failed to load prayer times"
                    }
                } else {
                    errorMessage = "Location not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(prayerTimes) {
        prayerTimes?.let { times ->
            PrayerScheduler.scheduleAllPrayers(
                context = context,
                fajr = times.fajr,
                dhuhr = times.dhuhr,
                asr = times.asr,
                maghrib = times.maghrib,
                isha = times.isha
            )
            PrayerDataStore.savePrayerTimes(
                context = context,
                fajr = times.fajr,
                dhuhr = times.dhuhr,
                asr = times.asr,
                maghrib = times.maghrib,
                isha = times.isha
            )
            PrayerWidgetUpdater.update(
                context = context,
                fajr = times.fajr,
                dhuhr = times.dhuhr,
                asr = times.asr,
                maghrib = times.maghrib,
                isha = times.isha,
                city = times.city
            )
            try {
                IslamicCalendarWidgetUpdater.update(context)
                android.util.Log.d("IslamicCalWidget", "Update successful")
            } catch (e: Exception) {
                android.util.Log.e("IslamicCalWidget", "Error: ${e.message}", e)
            }
        }
    }

    val prayers = remember(prayerTimes, language) {
        prayerTimes?.let {
            listOf(
                PrayerItem(AppStrings.fajr(language), "الفجر", it.fajr, "🌅", Color(0xFF2196F3)),
                PrayerItem(AppStrings.sunrise(language), "الشروق", it.sunrise, "🌄", Color(0xFFFFC107)),
                PrayerItem(AppStrings.dhuhr(language), "الظهر", it.dhuhr, "☀️", Color(0xFFFF9800)),
                PrayerItem(AppStrings.asr(language), "العصر", it.asr, "🌇", Color(0xFFE91E63)),
                PrayerItem(AppStrings.maghrib(language), "المغرب", it.maghrib, "🌆", Color(0xFF9C27B0)),
                PrayerItem(AppStrings.isha(language), "العشاء", it.isha, "🌙", Color(0xFF3F51B5))
            )
        } ?: emptyList()
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
                            text = AppStrings.prayerTimeTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.prayerTimeSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable { showMethodDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⚙️", fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFD4AF37).copy(alpha = 0.55f),
                                        Color(0xFFD4AF37).copy(alpha = 0.2f)
                                    )
                                )
                            )
                            .clickable { onQiblaClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🧭", fontSize = 38.sp)
                    }
                }
            }

            // ===== Loading =====
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFFD4AF37))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = AppStrings.loadingLocation(language),
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // ===== Permission =====
            else if (!locationPermission.status.isGranted) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(text = "📍", fontSize = 80.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = AppStrings.locationPermission(language),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = AppStrings.locationPermissionDesc(language),
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(0xFFD4AF37))
                                .clickable {
                                    locationPermission.launchPermissionRequest()
                                }
                                .padding(horizontal = 32.dp, vertical = 14.dp)
                        ) {
                            Text(
                                text = AppStrings.givePermission(language),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A2E1F)
                            )
                        }
                    }
                }
            }

            // ===== Error =====
            else if (errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(text = "⚠️", fontSize = 60.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = errorMessage ?: "",
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // ===== Main Content =====
            else if (prayerTimes != null) {
                val times = prayerTimes!!

                val nextPrayer = remember(currentTimeMillis, times, language) {
                    findNextPrayer(times, language)
                }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Next prayer big card
                    nextPrayer?.let { next ->
                        item {
                            StaggeredEntry(
                                visible = entryVisible,
                                delayMillis = DELAY_NEXT_CARD
                            ) {
                                NextPrayerBigCard(next, language)
                            }
                        }
                    }

                    // Location card
                    item {
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_LOCATION
                        ) {
                            LocationCard(
                                times = times,
                                currentMethod = currentMethod,
                                language = language
                            )
                        }
                    }

                    // Today's prayers title
                    item {
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_LIST_TITLE
                        ) {
                            Text(
                                text = "🕌 ${AppStrings.todaysPrayers(language)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    // Prayer cards (staggered)
                    itemsIndexed(prayers) { index, prayer ->
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_PRAYERS_START + index * STAGGER_PRAYER
                        ) {
                            PrayerTimeCard(prayer)
                        }
                    }

                    // Reload button
                    item {
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_RELOAD
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(Color.White.copy(alpha = 0.08f))
                                    .clickable { reloadTrigger++ }
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "🔄 ${AppStrings.reload(language)}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }

    // ===== Method Dialog (Animated) =====
    NoorPathDialog(
        visible = showMethodDialog,
        onDismiss = { showMethodDialog = false },
        title = AppStrings.prayerTimeTitle(language),
        icon = "📐",
        iconTint = Color(0xFFD4AF37),
        confirmText = AppStrings.close(language),
        onConfirm = { showMethodDialog = false }
    ) {
        MethodSelectionContent(
            currentMethod = currentMethod,
            onMethodSelected = { method ->
                currentMethod = method
                PrayerSettings.saveMethod(context, method)
                showMethodDialog = false
                reloadTrigger++
            }
        )
    }
}

// ============================================================
// ===== Next Prayer Big Card (with pulse) =====
// ============================================================
@Composable
private fun NextPrayerBigCard(info: NextPrayerInfo, language: String) {
    // Subtle pulse on the time badge
    val infinite = rememberInfiniteTransition(label = "nextPrayerPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nextPrayerPulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        info.color.copy(alpha = 0.4f),
                        Color(0xFFD4AF37).copy(alpha = 0.25f)
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = AppStrings.nextPrayer(language),
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = info.emoji, fontSize = 40.sp)
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = info.name,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = info.arabicName,
                        fontSize = 22.sp,
                        color = Color(0xFFD4AF37),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pulsing time badge
            Box(
                modifier = Modifier
                    .scale(pulseScale)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black.copy(alpha = 0.25f))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "⏰ ${info.timeRemaining}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${info.timeToday}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

// ============================================================
// ===== Location Card =====
// ============================================================
@Composable
private fun LocationCard(
    times: PrayerTimes,
    currentMethod: Int,
    language: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFD4AF37).copy(alpha = 0.15f))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "📍", fontSize = 28.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = AppStrings.yourLocation(language),
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = times.city,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = times.date,
                    fontSize = 12.sp,
                    color = Color(0xFFD4AF37)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "📐 ${PrayerSettings.getMethodNameById(currentMethod)}",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

// ============================================================
// ===== Prayer Time Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrayerTimeCard(prayer: PrayerItem) {
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
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "prayerIconScale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = prayer.color),
                onClick = { /* No action currently */ }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(iconScale)
                .clip(CircleShape)
                .background(prayer.color.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = prayer.emoji, fontSize = 26.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = prayer.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = prayer.arabicName,
                fontSize = 16.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = prayer.time,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// ============================================================
// ===== Method Selection Content (staggered) =====
// ============================================================
@Composable
private fun MethodSelectionContent(
    currentMethod: Int,
    onMethodSelected: (Int) -> Unit
) {
    val methods = PrayerSettings.getAllMethods()

    LazyColumn(
        modifier = Modifier.heightIn(max = 450.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        itemsIndexed(methods) { index, pair ->
            val id = pair.first
            val name = pair.second
            val isSelected = currentMethod == id

            DialogStaggerItem(
                visible = true,
                delayMillis = 40 + index * 45
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) Color(0xFFD4AF37).copy(alpha = 0.25f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .clickable { onMethodSelected(id) }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color(0xFFD4AF37)
                                else Color.White.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Text(
                                text = "✓",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A2E1F)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = name,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = Color.White,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// ============ হেল্পার ফাংশন ============

private fun findNextPrayer(prayerTimes: PrayerTimes, language: String): NextPrayerInfo? {
    val now = Calendar.getInstance()
    val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

    val prayerData = listOf(
        PrayerInfoRaw(AppStrings.fajr(language), "الفجر", "🌅", Color(0xFF2196F3), prayerTimes.fajr),
        PrayerInfoRaw(AppStrings.dhuhr(language), "الظهر", "☀️", Color(0xFFFF9800), prayerTimes.dhuhr),
        PrayerInfoRaw(AppStrings.asr(language), "العصر", "🌇", Color(0xFFE91E63), prayerTimes.asr),
        PrayerInfoRaw(AppStrings.maghrib(language), "المغرب", "🌆", Color(0xFF9C27B0), prayerTimes.maghrib),
        PrayerInfoRaw(AppStrings.isha(language), "العشاء", "🌙", Color(0xFF3F51B5), prayerTimes.isha)
    )

    for (prayer in prayerData) {
        val prayerMinutes = parseTimeToMinutes(prayer.time)
        if (prayerMinutes > currentMinutes) {
            val diff = prayerMinutes - currentMinutes
            val hours = diff / 60
            val minutes = diff % 60
            val remaining = when (language) {
                LanguageManager.LANG_EN -> {
                    if (hours > 0) "$hours hours $minutes minutes left"
                    else "$minutes minutes left"
                }
                LanguageManager.LANG_AR -> {
                    if (hours > 0) "باقي $hours ساعات و $minutes دقائق"
                    else "باقي $minutes دقائق"
                }
                else -> {
                    if (hours > 0) "$hours ঘণ্টা $minutes মিনিট বাকি"
                    else "$minutes মিনিট বাকি"
                }
            }
            return NextPrayerInfo(
                name = prayer.name,
                arabicName = prayer.arabicName,
                emoji = prayer.emoji,
                color = prayer.color,
                timeRemaining = remaining,
                timeToday = prayer.time
            )
        }
    }

    val fajrMinutes = parseTimeToMinutes(prayerTimes.fajr)
    val diff = (24 * 60 - currentMinutes) + fajrMinutes
    val hours = diff / 60
    val minutes = diff % 60
    val remaining = when (language) {
        LanguageManager.LANG_EN -> "$hours hours $minutes minutes left"
        LanguageManager.LANG_AR -> "باقي $hours ساعات و $minutes دقائق"
        else -> "$hours ঘণ্টা $minutes মিনিট বাকি"
    }
    return NextPrayerInfo(
        name = AppStrings.fajr(language),
        arabicName = "الفجر",
        emoji = "🌅",
        color = Color(0xFF2196F3),
        timeRemaining = remaining,
        timeToday = prayerTimes.fajr
    )
}

private fun parseTimeToMinutes(timeString: String): Int {
    return try {
        val format = SimpleDateFormat("h:mm a", Locale.US)
        val date = format.parse(timeString) ?: return 0
        val cal = Calendar.getInstance().apply { time = date }
        cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
    } catch (e: Exception) {
        0
    }
}

@SuppressLint("MissingPermission")
private suspend fun getCurrentLocation(context: android.content.Context): Location? =
    suspendCancellableCoroutine { continuation ->
        try {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(location)
                    } else {
                        val defaultLocation = Location("").apply {
                            latitude = 23.8103
                            longitude = 90.4125
                        }
                        continuation.resume(defaultLocation)
                    }
                }
                .addOnFailureListener {
                    val defaultLocation = Location("").apply {
                        latitude = 23.8103
                        longitude = 90.4125
                    }
                    continuation.resume(defaultLocation)
                }
        } catch (e: Exception) {
            continuation.resume(null)
        }
    }