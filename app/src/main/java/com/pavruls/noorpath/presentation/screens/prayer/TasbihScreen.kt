package com.pavruls.noorpath.presentation.screens.prayer

import android.Manifest
import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission
import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.data.preferences.TasbihSettings
import com.pavruls.noorpath.presentation.components.DialogStaggerItem
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import kotlinx.coroutines.launch

data class Dhikr(
    val arabic: String,
    val bangla: String,
    val meaning: String,
    val defaultTarget: Int
)

private val allDhikr = listOf(
    Dhikr("سُبْحَانَ اللَّهِ", "সুবহানাল্লাহ", "আল্লাহ পবিত্র", 33),
    Dhikr("الْحَمْدُ لِلَّهِ", "আলহামদুলিল্লাহ", "সকল প্রশংসা আল্লাহর", 33),
    Dhikr("اللَّهُ أَكْبَرُ", "আল্লাহু আকবার", "আল্লাহ সবচেয়ে মহান", 33),
    Dhikr("لَا إِلَهَ إِلَّا اللَّهُ", "লা ইলাহা ইল্লাল্লাহ", "আল্লাহ ছাড়া কোনো ইলাহ নেই", 100),
    Dhikr("أَسْتَغْفِرُ اللَّهَ", "আস্তাগফিরুল্লাহ", "আমি আল্লাহর কাছে ক্ষমা চাই", 100),
    Dhikr("لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ", "লা হাওলা ওয়ালা কুওয়াতা ইল্লা বিল্লাহ", "আল্লাহর সাহায্য ছাড়া কোনো শক্তি নেই", 100),
    Dhikr("صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ", "সাল্লাল্লাহু আলাইহি ওয়া সাল্লাম", "নবীর উপর দরুদ", 100),
    Dhikr("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ", "সুবহানাল্লাহি ওয়া বিহামদিহি", "আল্লাহর প্রশংসাসহ পবিত্রতা", 100),
    Dhikr("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ سُبْحَانَ اللَّهِ الْعَظِيمِ", "সুবহানাল্লাহি ওয়া বিহামদিহি সুবহানাল্লাহিল আযীম", "মহান আল্লাহ পবিত্র", 100),
    Dhikr("لَا إِلَهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ", "লা ইলাহা ইল্লা আন্তা সুবহানাকা ইন্নি কুনতু মিনায যালিমীন", "ইউনুস (আ.) এর দুআ", 100)
)

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_DHIKR = 120
private const val DELAY_COUNTER = 240
private const val DELAY_TAP_BUTTON = 380

@Composable
fun TasbihScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    val scope = rememberCoroutineScope()

    var count by remember { mutableIntStateOf(TasbihSettings.getCount(context)) }
    var target by remember { mutableIntStateOf(TasbihSettings.getTarget(context)) }
    var total by remember { mutableIntStateOf(TasbihSettings.getTotal(context)) }
    var rounds by remember { mutableIntStateOf(TasbihSettings.getCompletedRounds(context)) }
    var selectedIndex by remember { mutableIntStateOf(TasbihSettings.getSelectedDhikr(context)) }
    var soundEnabled by remember { mutableStateOf(TasbihSettings.isSoundEnabled(context)) }
    var vibrationEnabled by remember { mutableStateOf(TasbihSettings.isVibrationEnabled(context)) }

    var showTargetDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showDhikrDialog by remember { mutableStateOf(false) }

    val currentDhikr = allDhikr.getOrElse(selectedIndex) { allDhikr[0] }

    // Staggered entrance
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    // ============ Tap Press Scale ============
    var isPressed by remember { mutableStateOf(false) }
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )

    // ============ Counter Bounce (on each tap) ============
    val counterBounce = remember { Animatable(1f) }

    // ============ Ring Glow Pulse (on each tap) ============
    val ringPulse = remember { Animatable(0f) }

    // ============ Animated Progress Fill ============
    val progressFraction by animateFloatAsState(
        targetValue = if (target > 0) count.toFloat() / target else 0f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "progressFill"
    )

    // ============ Idle Breathing on Tap Button ============
    val infinite = rememberInfiniteTransition(label = "tasbihIdle")
    val idleBreath by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idleBreath"
    )

    // ============ Tap Handler (triggers all animations) ============
    fun handleTap() {
        count++
        total++
        if (soundEnabled) playTickSound(context)
        if (vibrationEnabled) {
            try { vibrate(context) } catch (_: Exception) {}
        }
        if (count >= target) {
            rounds++
            count = 0
            if (vibrationEnabled) {
                try { vibrateLong(context) } catch (_: Exception) {}
            }
        }
        TasbihSettings.saveCount(context, count)
        TasbihSettings.saveTotal(context, total)
        TasbihSettings.saveCompletedRounds(context, rounds)

        // Animate counter bounce
        scope.launch {
            counterBounce.snapTo(0.85f)
            counterBounce.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }

        // Animate ring pulse
        scope.launch {
            ringPulse.snapTo(0f)
            ringPulse.animateTo(
                targetValue = 1f,
                animationSpec = tween(280, easing = FastOutSlowInEasing)
            )
            ringPulse.animateTo(
                targetValue = 0f,
                animationSpec = tween(420, easing = FastOutSlowInEasing)
            )
        }
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
                            text = AppStrings.tasbihTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.totalLabel(language, total, rounds),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                            .clickable { showTargetDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⚙️", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

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

            // ===== Dhikr Card =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_DHIKR) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.08f))
                        .clickable { showDhikrDialog = true }
                        .padding(14.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentDhikr.arabic,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD4AF37),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentDhikr.bangla,
                            fontSize = 15.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = currentDhikr.meaning,
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = AppStrings.changeHint(language),
                            fontSize = 10.sp,
                            color = Color(0xFFD4AF37).copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // ===== Counter Area =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_COUNTER) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Counter circle with ring glow on tap
                    Box(
                        modifier = Modifier.size(280.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Outer ring glow (pulses on tap)
                        Box(
                            modifier = Modifier
                                .size(280.dp)
                                .scale(1f + ringPulse.value * 0.15f)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFD4AF37).copy(
                                                alpha = 0.05f + ringPulse.value * 0.35f
                                            ),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )

                        // Static base circle
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFD4AF37).copy(alpha = 0.25f),
                                            Color(0xFFD4AF37).copy(alpha = 0.05f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                // Counter number with bounce animation
                                Text(
                                    text = "$count",
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD4AF37),
                                    modifier = Modifier.scale(counterBounce.value)
                                )
                                Text(
                                    text = "/ $target",
                                    fontSize = 22.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Progress bar with animated fill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction = progressFraction)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (count == 0 && rounds > 0) Color(0xFF4CAF50)
                                    else Color(0xFFD4AF37)
                                )
                        )
                    }
                }
            }

            // ===== Big Tap Button =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_TAP_BUTTON) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .scale(buttonScale * idleBreath)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))
                                )
                            )
                            .clickable {
                                isPressed = true
                                handleTap()
                                isPressed = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "📿", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = AppStrings.tapToCount(language),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // ============================================================
    // ===== Target Dialog =====
    // ============================================================
    NoorPathDialog(
        visible = showTargetDialog,
        onDismiss = { showTargetDialog = false },
        title = AppStrings.settingsTitle(language),
        icon = "⚙️",
        iconTint = Color(0xFFD4AF37),
        confirmText = AppStrings.close(language),
        onConfirm = { showTargetDialog = false }
    ) {
        TargetDialogContent(
            currentTarget = target,
            soundEnabled = soundEnabled,
            vibrationEnabled = vibrationEnabled,
            language = language,
            onTargetSelected = { newTarget ->
                target = newTarget
                TasbihSettings.saveTarget(context, newTarget)
            },
            onSoundToggle = { enabled ->
                soundEnabled = enabled
                TasbihSettings.saveSoundEnabled(context, enabled)
            },
            onVibrationToggle = { enabled ->
                vibrationEnabled = enabled
                TasbihSettings.saveVibrationEnabled(context, enabled)
            }
        )
    }

    // ============================================================
    // ===== Dhikr Selection Dialog =====
    // ============================================================
    NoorPathDialog(
        visible = showDhikrDialog,
        onDismiss = { showDhikrDialog = false },
        title = AppStrings.dhikrSelection(language),
        icon = "📿",
        iconTint = Color(0xFFD4AF37),
        confirmText = AppStrings.close(language),
        onConfirm = { showDhikrDialog = false }
    ) {
        DhikrSelectionContent(
            currentIndex = selectedIndex,
            onSelected = { index ->
                selectedIndex = index
                TasbihSettings.saveSelectedDhikr(context, index)
                target = allDhikr[index].defaultTarget
                TasbihSettings.saveTarget(context, target)
                count = 0
                TasbihSettings.saveCount(context, 0)
                showDhikrDialog = false
            }
        )
    }

    // ============================================================
    // ===== Reset Dialog =====
    // ============================================================
    NoorPathDialog(
        visible = showResetDialog,
        onDismiss = { showResetDialog = false },
        title = AppStrings.resetConfirmTitle(language),
        message = AppStrings.resetConfirmMessage(language),
        icon = "⚠️",
        iconTint = Color(0xFFFF5252),
        confirmText = AppStrings.yes(language),
        cancelText = AppStrings.cancel(language),
        onConfirm = {
            count = 0
            total = 0
            rounds = 0
            TasbihSettings.resetAll(context)
            showResetDialog = false
        }
    )
}

// ============ Target Dialog Content ============
@Composable
private fun TargetDialogContent(
    currentTarget: Int,
    soundEnabled: Boolean,
    vibrationEnabled: Boolean,
    language: String,
    onTargetSelected: (Int) -> Unit,
    onSoundToggle: (Boolean) -> Unit,
    onVibrationToggle: (Boolean) -> Unit
) {
    val targets = listOf(10, 33, 66, 99, 100, 500, 1000)

    Column {
        DialogStaggerItem(visible = true, delayMillis = 0) {
            Column {
                Text(
                    text = AppStrings.targetLabel(language),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4AF37)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(targets) { t ->
                        val isSelected = currentTarget == t
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) Color(0xFFD4AF37)
                                    else Color.White.copy(alpha = 0.1f)
                                )
                                .clickable { onTargetSelected(t) }
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "$t",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color(0xFF0A2E1F) else Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        DialogStaggerItem(visible = true, delayMillis = 80) {
            ToggleRow(
                emoji = "🔊",
                label = AppStrings.soundLabel(language),
                enabled = soundEnabled,
                onToggle = { onSoundToggle(!soundEnabled) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DialogStaggerItem(visible = true, delayMillis = 140) {
            ToggleRow(
                emoji = "📳",
                label = AppStrings.vibrationLabel(language),
                enabled = vibrationEnabled,
                onToggle = { onVibrationToggle(!vibrationEnabled) }
            )
        }
    }
}

@Composable
private fun ToggleRow(
    emoji: String,
    label: String,
    enabled: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable { onToggle() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(
                    if (enabled) Color(0xFF4CAF50)
                    else Color.White.copy(alpha = 0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (enabled) {
                Text(
                    text = "✓",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ============ Dhikr Selection Content ============
@Composable
private fun DhikrSelectionContent(
    currentIndex: Int,
    onSelected: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.heightIn(max = 400.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(allDhikr) { index, dhikr ->
            val isSelected = currentIndex == index

            DialogStaggerItem(
                visible = true,
                delayMillis = 40 + index * 50
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) Color(0xFFD4AF37).copy(alpha = 0.25f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .clickable { onSelected(index) }
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = dhikr.arabic,
                            fontSize = 18.sp,
                            color = Color(0xFFD4AF37),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        if (isSelected) {
                            Text(
                                text = "✓",
                                fontSize = 18.sp,
                                color = Color(0xFF4CAF50),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dhikr.bangla,
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = dhikr.meaning,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

// ============ Helpers ============
private var toneGenerator: ToneGenerator? = null

private fun playTickSound(context: Context) {
    try {
        if (toneGenerator == null) {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 60)
        }
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 50)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@RequiresPermission(Manifest.permission.VIBRATE)
private fun vibrate(context: Context) {
    try {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(30)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@RequiresPermission(Manifest.permission.VIBRATE)
private fun vibrateLong(context: Context) {
    try {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100, 100, 100, 100, 200), -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 100, 100, 100, 100, 200), -1)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}