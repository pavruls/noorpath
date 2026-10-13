package com.pavruls.noorpath.presentation.screens.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.data.preferences.ThemeSettings
import com.pavruls.noorpath.data.sync.SyncStatusManager
import com.pavruls.noorpath.data.sync.SyncWorker
import com.pavruls.noorpath.presentation.components.DialogStaggerItem
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.viewmodel.AuthUiState
import com.pavruls.noorpath.presentation.viewmodel.AuthViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var currentMode by remember { mutableIntStateOf(ThemeSettings.getDarkMode(context)) }
    var showThemeDialog by remember { mutableStateOf(false) }
    val language by LanguageManager.languageFlow.collectAsState()
    var showLanguageDialog by remember { mutableStateOf(false) }

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

            // ===== Top App Bar =====
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
                        text = AppStrings.settings(language),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = AppStrings.appSettings(language),
                        fontSize = 12.sp,
                        color = Color(0xFFD4AF37)
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ===== Appearance =====
                item {
                    Text(
                        text = AppStrings.appearance(language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                item {
                    SettingsItem(
                        emoji = when (currentMode) {
                            ThemeSettings.MODE_DARK -> "🌙"
                            ThemeSettings.MODE_LIGHT -> "☀️"
                            else -> "⚙️"
                        },
                        title = AppStrings.theme(language),
                        subtitle = getThemeName(language, currentMode),
                        onClick = { showThemeDialog = true }
                    )
                }
                item {
                    SettingsItem(
                        emoji = "🌐",
                        title = AppStrings.language(language),
                        subtitle = LanguageManager.getLanguageName(language),
                        onClick = { showLanguageDialog = true }
                    )
                }

                // ===== Cloud Backup =====
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "☁️ ক্লাউড ব্যাকআপ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                item {
                    CloudBackupSection()
                }

                // ===== About =====
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = AppStrings.aboutApp(language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                item {
                    SettingsItem(
                        emoji = "📱",
                        title = AppStrings.version(language),
                        subtitle = "NoorPath v1.0",
                        onClick = { }
                    )
                }

                item {
                    SettingsItem(
                        emoji = "📖",
                        title = AppStrings.aboutUs(language),
                        subtitle = AppStrings.aboutUsDesc(language),
                        onClick = { }
                    )
                }
            }
        }
    }

    // ============================================================
    // ===== Theme Selection Dialog (Animated) =====
    // ============================================================
    NoorPathDialog(
        visible = showThemeDialog,
        onDismiss = { showThemeDialog = false },
        title = AppStrings.themeSelection(language),
        icon = "🎨",
        iconTint = Color(0xFFD4AF37),
        confirmText = AppStrings.close(language),
        onConfirm = { showThemeDialog = false }
    ) {
        ThemeSelectionContent(
            currentMode = currentMode,
            language = language,
            onModeSelected = { mode ->
                currentMode = mode
                ThemeSettings.saveDarkMode(context, mode)
                showThemeDialog = false
            }
        )
    }

    // ============================================================
    // ===== Language Selection Dialog (Animated) =====
    // ============================================================
    NoorPathDialog(
        visible = showLanguageDialog,
        onDismiss = { showLanguageDialog = false },
        title = AppStrings.languageSelection(language),
        icon = "🌐",
        iconTint = Color(0xFF2196F3),
        confirmText = AppStrings.close(language),
        onConfirm = { showLanguageDialog = false }
    ) {
        LanguageSelectionContent(
            currentLanguage = language,
            onLanguageSelected = { lang ->
                LanguageManager.saveLanguage(context, lang)
                showLanguageDialog = false
            }
        )
    }
}

// ============================================================
// ===== Theme Selection Content (Staggered) =====
// ============================================================
@Composable
private fun ThemeSelectionContent(
    currentMode: Int,
    language: String,
    onModeSelected: (Int) -> Unit
) {
    val options = listOf(
        Triple(ThemeSettings.MODE_SYSTEM, "⚙️", AppStrings.systemDefault(language)),
        Triple(ThemeSettings.MODE_LIGHT, "☀️", AppStrings.lightMode(language)),
        Triple(ThemeSettings.MODE_DARK, "🌙", AppStrings.darkMode(language))
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEachIndexed { index, (mode, emoji, name) ->
            DialogStaggerItem(
                visible = true,
                delayMillis = 40 + index * 70
            ) {
                val isSelected = currentMode == mode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) Color(0xFFD4AF37).copy(alpha = 0.25f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .clickable { onModeSelected(mode) }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = emoji, fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = name,
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD4AF37)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A2E1F)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Language Selection Content (Staggered) =====
// ============================================================
@Composable
private fun LanguageSelectionContent(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    val languages = listOf(
        LanguageManager.LANG_BN to "🇧🇩 বাংলা",
        LanguageManager.LANG_EN to "🇬🇧 English",
        LanguageManager.LANG_AR to "🇸🇦 العربية"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        languages.forEachIndexed { index, (langCode, langName) ->
            DialogStaggerItem(
                visible = true,
                delayMillis = 40 + index * 80
            ) {
                val isSelected = currentLanguage == langCode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) Color(0xFFD4AF37).copy(alpha = 0.25f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .clickable { onLanguageSelected(langCode) }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = langName,
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD4AF37)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A2E1F)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Cloud Backup Section (Unchanged) =====
// ============================================================
@Composable
private fun CloudBackupSection(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user by authViewModel.currentUser.collectAsState()
    val uiState by authViewModel.uiState.collectAsState()

    val syncStatusManager = remember { SyncStatusManager(context) }
    val isSyncing by syncStatusManager.isSyncing.collectAsState()

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> {
                Toast.makeText(context, "সফলভাবে সাইন-ইন হয়েছে!", Toast.LENGTH_SHORT).show()
                triggerSync(context)
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD4AF37).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "☁️", fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ক্লাউড সিঙ্ক",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = if (user == null) "সাইন-ইন করা হয়নি" else (user?.email ?: "অজানা"),
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            if (isSyncing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color(0xFFD4AF37),
                    strokeWidth = 2.dp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (user == null) {
            Text(
                text = "Google অ্যাকাউন্টে সাইন-ইন করলে আপনার নামাজের হিসাব, তাসবিহ কাউন্ট — সব ক্লাউডে সেভ হবে। ফোন বদলালেও কিছু হারাবে না।",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { authViewModel.signInWithGoogle() },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is AuthUiState.Loading
            ) {
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color(0xFF0A2E1F),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("সাইন-ইন হচ্ছে...", color = Color(0xFF0A2E1F))
                } else {
                    Text("Google দিয়ে সাইন-ইন করুন", color = Color(0xFF0A2E1F))
                }
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "✅", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ডেটা ক্লাউডে সিঙ্ক হচ্ছে",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD4AF37)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🕐", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "শেষ সিঙ্ক: ${syncStatusManager.getLastSyncText()}",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    triggerSync(context)
                    Toast.makeText(context, "সিঙ্ক শুরু হয়েছে...", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSyncing
            ) {
                if (isSyncing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color(0xFF0A2E1F),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("সিঙ্ক হচ্ছে...", color = Color(0xFF0A2E1F))
                } else {
                    Text("🔄 এখনই সিঙ্ক করুন", color = Color(0xFF0A2E1F))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { authViewModel.signOut() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("সাইন আউট", color = Color.White)
            }
        }
    }
}

// ============================================================
// ===== Helper Functions =====
// ============================================================
private fun triggerSync(context: android.content.Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val request = OneTimeWorkRequestBuilder<SyncWorker>()
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueue(request)
}

private fun getThemeName(lang: String, mode: Int): String = when (mode) {
    ThemeSettings.MODE_DARK -> AppStrings.darkMode(lang)
    ThemeSettings.MODE_LIGHT -> AppStrings.lightMode(lang)
    else -> AppStrings.systemDefault(lang)
}

@Composable
private fun SettingsItem(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        Text(text = "›", fontSize = 24.sp, color = Color.White.copy(alpha = 0.5f))
    }
}