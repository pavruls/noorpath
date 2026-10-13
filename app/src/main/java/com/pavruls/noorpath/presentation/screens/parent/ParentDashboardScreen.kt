package com.pavruls.noorpath.presentation.screens.parent

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.data.preferences.ParentSettings
import com.pavruls.noorpath.presentation.components.DialogStaggerItem
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.presentation.viewmodel.ParentViewModel

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_WELCOME = 120
private const val DELAY_ACTIVITY_TITLE = 280
private const val DELAY_STATS_ROW_1 = 360
private const val DELAY_STATS_ROW_2 = 440
private const val DELAY_STATS_ROW_3 = 520
private const val DELAY_SETTINGS_TITLE = 640
private const val DELAY_SETTINGS_START = 720
private const val STAGGER_SETTINGS = 70

@Composable
fun ParentDashboardScreen(
    onBackClick: () -> Unit,
    viewModel: ParentViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()

    val quizCount by viewModel.quizCount.collectAsStateWithLifecycle()
    val bestScore by viewModel.bestScore.collectAsStateWithLifecycle()
    val averageRatio by viewModel.averageRatio.collectAsStateWithLifecycle()
    val booksRead by viewModel.booksRead.collectAsStateWithLifecycle()
    val arabicProgress by viewModel.arabicProgress.collectAsStateWithLifecycle()

    var showScreenTimeDialog by remember { mutableStateOf(false) }
    var showPinDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showResetDoneDialog by remember { mutableStateOf(false) }

    var screenTimeLabel by remember {
        mutableStateOf(ParentSettings.getScreenTimeLabel(context))
    }

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
                    Column {
                        Text(
                            text = AppStrings.parentDashboardTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.parentDashboardSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // ===== Welcome Card =====
                item(key = "welcome") {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_WELCOME) {
                        WelcomeCard(language)
                    }
                }

                // ===== Activity Title =====
                item(key = "activity_title") {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_ACTIVITY_TITLE) {
                        Text(
                            text = AppStrings.todayActivitiesLabel(language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                // ===== Stats Row 1 =====
                item(key = "stats_row_1") {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_STATS_ROW_1) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val bestScoreText = if (bestScore != null)
                                "${bestScore!!.score}/${bestScore!!.total}"
                            else "—"

                            StatCard(
                                emoji = "🎯",
                                value = bestScoreText,
                                label = AppStrings.bestQuizScore(language),
                                color = Color(0xFF2196F3),
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                emoji = "📝",
                                value = "$quizCount",
                                label = AppStrings.quizzesPlayed(language),
                                color = Color(0xFFFF9800),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // ===== Stats Row 2 =====
                item(key = "stats_row_2") {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_STATS_ROW_2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val avgText = averageRatio?.let {
                                "${(it * 100).toInt()}%"
                            } ?: "—"

                            StatCard(
                                emoji = "📈",
                                value = avgText,
                                label = AppStrings.averageScore(language),
                                color = Color(0xFF4CAF50),
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                emoji = "📚",
                                value = "$booksRead",
                                label = AppStrings.booksReadLabel(language),
                                color = Color(0xFF9C27B0),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // ===== Stats Row 3 =====
                item(key = "stats_row_3") {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_STATS_ROW_3) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val letterText = arabicProgress?.let {
                                "${it.maxReachedIndex + 1}/28"
                            } ?: "—"

                            StatCard(
                                emoji = "أ",
                                value = letterText,
                                label = AppStrings.arabicLettersLabel(language),
                                color = Color(0xFFE91E63),
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                emoji = "👀",
                                value = arabicProgress?.let { "${it.totalViewed}" } ?: "—",
                                label = AppStrings.viewedLabel(language),
                                color = Color(0xFF00BCD4),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // ===== Settings Title =====
                item(key = "settings_title") {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_SETTINGS_TITLE) {
                        Text(
                            text = AppStrings.settingsLabel(language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                // ===== Settings Rows =====
                item(key = "settings_row_1") {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SETTINGS_START
                    ) {
                        SettingsRow(
                            emoji = "⏰",
                            title = AppStrings.dailyScreenTime(language),
                            subtitle = AppStrings.currentLabel(language, screenTimeLabel),
                            onClick = { showScreenTimeDialog = true }
                        )
                    }
                }

                item(key = "settings_row_2") {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SETTINGS_START + STAGGER_SETTINGS
                    ) {
                        SettingsRow(
                            emoji = "🔒",
                            title = AppStrings.changePinLabel(language),
                            subtitle = AppStrings.setNewPinLabel(language),
                            onClick = { showPinDialog = true }
                        )
                    }
                }

                item(key = "settings_row_3") {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SETTINGS_START + STAGGER_SETTINGS * 2
                    ) {
                        SettingsRow(
                            emoji = "🔄",
                            title = AppStrings.resetAllDataLabel(language),
                            subtitle = AppStrings.deleteChildProgress(language),
                            onClick = { showResetDialog = true }
                        )
                    }
                }

                item(key = "bottom_spacer") {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    // ============================================================
    // ===== All Dialogs — আগের মতোই অপরিবর্তিত =====
    // ============================================================
    NoorPathDialog(
        visible = showScreenTimeDialog,
        onDismiss = { showScreenTimeDialog = false },
        title = AppStrings.dailyScreenTime(language),
        icon = "⏰",
        iconTint = Color(0xFF2196F3),
        confirmText = AppStrings.saveLabel(language),
        cancelText = AppStrings.cancel(language),
        onConfirm = { showScreenTimeDialog = false }
    ) {
        ScreenTimeContent(
            currentMinutes = ParentSettings.getScreenTime(context),
            language = language,
            onMinutesChange = { minutes ->
                ParentSettings.saveScreenTime(context, minutes)
                screenTimeLabel = ParentSettings.getScreenTimeLabel(context)
            }
        )
    }

    NoorPathDialog(
        visible = showPinDialog,
        onDismiss = { showPinDialog = false },
        title = AppStrings.changePinLabel(language),
        icon = "🔒",
        iconTint = Color(0xFF9C27B0),
        confirmText = AppStrings.saveLabel(language),
        cancelText = AppStrings.cancel(language),
        onConfirm = { showPinDialog = false }
    ) {
        ChangePinContent(
            currentPin = ParentSettings.getPin(context),
            language = language,
            onValid = { newPin ->
                ParentSettings.savePin(context, newPin)
                showPinDialog = false
            }
        )
    }

    NoorPathDialog(
        visible = showResetDialog,
        onDismiss = { showResetDialog = false },
        title = AppStrings.resetAllDataTitle(language),
        message = AppStrings.resetAllDataMessage(language),
        icon = "⚠️",
        iconTint = Color(0xFFFF5252),
        confirmText = AppStrings.yesDeleteAll(language),
        cancelText = AppStrings.cancel(language),
        onConfirm = {
            viewModel.resetAllData()
            showResetDialog = false
            showResetDoneDialog = true
        }
    )

    NoorPathDialog(
        visible = showResetDoneDialog,
        onDismiss = { showResetDoneDialog = false },
        title = AppStrings.successLabel(language),
        message = AppStrings.allDataDeletedMessage(language),
        icon = "✅",
        iconTint = Color(0xFF4CAF50),
        confirmText = AppStrings.okLabel(language),
        onConfirm = { showResetDoneDialog = false }
    )
}

// ============================================================
// ===== Welcome Card (with subtle glow pulse) =====
// ============================================================
@Composable
private fun WelcomeCard(language: String) {
    val infinite = rememberInfiniteTransition(label = "welcomeGlow")
    val glowAlpha by infinite.animateFloat(
        initialValue = 0.28f,
        targetValue = 0.40f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "welcomeGlowAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = glowAlpha),
                        Color(0xFF4CAF50).copy(alpha = glowAlpha)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👨‍👩‍👧", fontSize = 32.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = AppStrings.assalamuAlaikum(language),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = AppStrings.seeChildProgress(language),
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ============================================================
// ===== Stat Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatCard(
    emoji: String,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(150),
        label = "statCardScale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = color),
                onClick = { /* Future: could show detail */ }
            )
            .padding(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

// ============================================================
// ===== Settings Row (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsRow(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(150),
        label = "settingsRowScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = tween(150),
        label = "settingsRowIconScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "settingsRowChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .scale(iconScale)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
        Text(
            text = "›",
            fontSize = 24.sp,
            color = Color.White.copy(alpha = chevronAlpha)
        )
    }
}

// ============================================================
// ===== Screen Time Content (staggered options) =====
// ============================================================
@Composable
private fun ScreenTimeContent(
    currentMinutes: Int,
    language: String,
    onMinutesChange: (Int) -> Unit
) {
    val options = listOf(
        30 to AppStrings.minutesLabel(language, 30),
        60 to AppStrings.oneHour(language),
        90 to AppStrings.oneAndHalfHour(language),
        120 to AppStrings.twoHours(language),
        180 to AppStrings.threeHours(language),
        240 to AppStrings.fourHours(language),
        0 to AppStrings.unlimitedLabel(language)
    )

    var selected by remember { mutableStateOf(currentMinutes) }

    LaunchedEffect(selected) {
        onMinutesChange(selected)
    }

    Column {
        Text(
            text = AppStrings.selectScreenTimePrompt(language),
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(12.dp))

        options.forEachIndexed { index, (minutes, label) ->
            DialogStaggerItem(
                visible = true,
                delayMillis = 40 + index * 55
            ) {
                val isSelected = selected == minutes
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) Color(0xFFD4AF37).copy(alpha = 0.3f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .clickable { selected = minutes }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
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
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0A2E1F)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = label,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Change PIN Content (staggered fields) =====
// ============================================================
@Composable
private fun ChangePinContent(
    currentPin: String,
    language: String,
    onValid: (String) -> Unit
) {
    var oldPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column {
        DialogStaggerItem(visible = true, delayMillis = 0) {
            PinField(
                label = AppStrings.currentPinLabel(language),
                value = oldPin,
                onValueChange = {
                    if (it.length <= 4 && it.all { c -> c.isDigit() }) oldPin = it
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        DialogStaggerItem(visible = true, delayMillis = 80) {
            PinField(
                label = AppStrings.newPinLabel(language),
                value = newPin,
                onValueChange = {
                    if (it.length <= 4 && it.all { c -> c.isDigit() }) newPin = it
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        DialogStaggerItem(visible = true, delayMillis = 160) {
            PinField(
                label = AppStrings.newPinAgainLabel(language),
                value = confirmPin,
                onValueChange = {
                    if (it.length <= 4 && it.all { c -> c.isDigit() }) confirmPin = it
                }
            )
        }

        LaunchedEffect(oldPin, newPin, confirmPin) {
            error = ""
            if (oldPin.length == 4 && newPin.length == 4 && confirmPin.length == 4) {
                when {
                    oldPin != currentPin -> error = AppStrings.wrongCurrentPin(language)
                    newPin != confirmPin -> error = AppStrings.pinMismatch(language)
                    else -> onValid(newPin)
                }
            }
        }

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            DialogStaggerItem(visible = true, delayMillis = 0) {
                Text(
                    text = "⚠️ $error",
                    fontSize = 13.sp,
                    color = Color(0xFFFF5252),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PinField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label, color = Color.White.copy(alpha = 0.7f))
        },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFFD4AF37),
            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
            cursorColor = Color(0xFFD4AF37)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}