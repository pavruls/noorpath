package com.pavruls.noorpath.presentation.screens.prayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.DialogStaggerItem
import com.pavruls.noorpath.presentation.components.NoorPathDialog
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import java.util.Locale
import kotlin.math.round

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_SECTION_START = 120
private const val STAGGER_SECTION = 80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatScreen(
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

    var goldGrams by remember { mutableStateOf("") }
    var goldPricePerGram by remember { mutableStateOf("") }
    var silverGrams by remember { mutableStateOf("") }
    var silverPricePerGram by remember { mutableStateOf("") }
    var cashAmount by remember { mutableStateOf("") }
    var bankBalance by remember { mutableStateOf("") }
    var businessGoods by remember { mutableStateOf("") }
    var otherAssets by remember { mutableStateOf("") }
    var debts by remember { mutableStateOf("") }

    var showResultDialog by remember { mutableStateOf(false) }

    // Staggered entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    val goldValue = (goldGrams.toDoubleOrNull() ?: 0.0) * (goldPricePerGram.toDoubleOrNull() ?: 0.0)
    val silverValue = (silverGrams.toDoubleOrNull() ?: 0.0) * (silverPricePerGram.toDoubleOrNull() ?: 0.0)
    val cashValue = cashAmount.toDoubleOrNull() ?: 0.0
    val bankValue = bankBalance.toDoubleOrNull() ?: 0.0
    val businessValue = businessGoods.toDoubleOrNull() ?: 0.0
    val otherValue = otherAssets.toDoubleOrNull() ?: 0.0
    val debtValue = debts.toDoubleOrNull() ?: 0.0

    val totalAssets = goldValue + silverValue + cashValue + bankValue + businessValue + otherValue
    val netWealth = totalAssets - debtValue

    val silverNisabValue = 612.36 * (silverPricePerGram.toDoubleOrNull() ?: 0.0)
    val goldNisabValue = 87.48 * (goldPricePerGram.toDoubleOrNull() ?: 0.0)

    val nisab = when {
        (silverPricePerGram.toDoubleOrNull() ?: 0.0) > 0 -> silverNisabValue
        (goldPricePerGram.toDoubleOrNull() ?: 0.0) > 0 -> goldNisabValue
        else -> 0.0
    }

    val isZakatApplicable = netWealth >= nisab && nisab > 0
    val zakatAmount = if (isZakatApplicable) netWealth * 0.025 else 0.0

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
                            text = AppStrings.zakatScreenTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.zakatScreenSubtitle(language),
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
                // ===== Gold Section =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START
                    ) {
                        SectionHeader(
                            "🥇",
                            AppStrings.zakatGoldSection(language),
                            AppStrings.zakatGoldHint(language)
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            InputField(
                                label = AppStrings.zakatInputGoldGrams(language),
                                value = goldGrams,
                                onChange = { goldGrams = it },
                                modifier = Modifier.weight(1f)
                            )
                            InputField(
                                label = AppStrings.zakatInputPricePerGram(language),
                                value = goldPricePerGram,
                                onChange = { goldPricePerGram = it },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                if (goldValue > 0) {
                    item {
                        ValueRow(AppStrings.zakatGoldValue(language), goldValue)
                    }
                }

                // ===== Silver Section =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 2
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            SectionHeader(
                                "🥈",
                                AppStrings.zakatSilverSection(language),
                                AppStrings.zakatSilverHint(language)
                            )
                        }
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 3
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            InputField(
                                label = AppStrings.zakatInputSilverGrams(language),
                                value = silverGrams,
                                onChange = { silverGrams = it },
                                modifier = Modifier.weight(1f)
                            )
                            InputField(
                                label = AppStrings.zakatInputPricePerGram(language),
                                value = silverPricePerGram,
                                onChange = { silverPricePerGram = it },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                if (silverValue > 0) {
                    item {
                        ValueRow(AppStrings.zakatSilverValue(language), silverValue)
                    }
                }

                // ===== Cash Section =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 4
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            SectionHeader(
                                "💵",
                                AppStrings.zakatCashSection(language),
                                AppStrings.zakatCashHint(language)
                            )
                        }
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 5
                    ) {
                        InputField(
                            label = AppStrings.zakatInputCash(language),
                            value = cashAmount,
                            onChange = { cashAmount = it }
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 6
                    ) {
                        InputField(
                            label = AppStrings.zakatInputBank(language),
                            value = bankBalance,
                            onChange = { bankBalance = it }
                        )
                    }
                }

                // ===== Business Section =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 7
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            SectionHeader(
                                "🏪",
                                AppStrings.zakatBusinessSection(language),
                                AppStrings.zakatBusinessHint(language)
                            )
                        }
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 8
                    ) {
                        InputField(
                            label = AppStrings.zakatInputBusiness(language),
                            value = businessGoods,
                            onChange = { businessGoods = it }
                        )
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 9
                    ) {
                        InputField(
                            label = AppStrings.zakatInputOther(language),
                            value = otherAssets,
                            onChange = { otherAssets = it }
                        )
                    }
                }

                // ===== Debts Section =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 10
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            SectionHeader(
                                "📉",
                                AppStrings.zakatDebtsSection(language),
                                AppStrings.zakatDebtsHint(language)
                            )
                        }
                    }
                }

                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 11
                    ) {
                        InputField(
                            label = AppStrings.zakatInputDebts(language),
                            value = debts,
                            onChange = { debts = it }
                        )
                    }
                }

                // ===== Summary Card =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 12
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            SummaryCard(
                                totalAssets = totalAssets,
                                debts = debtValue,
                                netWealth = netWealth,
                                nisab = nisab,
                                isZakatApplicable = isZakatApplicable,
                                zakatAmount = zakatAmount,
                                language = language
                            )
                        }
                    }
                }

                // ===== View Report Button =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 13
                    ) {
                        AnimatedReportButton(
                            text = AppStrings.zakatViewReport(language),
                            onClick = { showResultDialog = true }
                        )
                    }
                }

                // ===== Info Card =====
                item {
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_SECTION_START + STAGGER_SECTION * 14
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))
                            InfoCard(language)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // ===== Detailed Report Dialog (Animated) =====
    NoorPathDialog(
        visible = showResultDialog,
        onDismiss = { showResultDialog = false },
        title = AppStrings.zakatReportTitle(language),
        icon = "📊",
        iconTint = Color(0xFFD4AF37),
        confirmText = AppStrings.close(language),
        onConfirm = { showResultDialog = false }
    ) {
        ZakatReportContent(
            goldValue = goldValue,
            silverValue = silverValue,
            cashValue = cashValue,
            bankValue = bankValue,
            businessValue = businessValue,
            otherValue = otherValue,
            totalAssets = totalAssets,
            debts = debtValue,
            netWealth = netWealth,
            nisab = nisab,
            isZakatApplicable = isZakatApplicable,
            zakatAmount = zakatAmount,
            language = language
        )
    }
}

// ============================================================
// ===== Animated Report Button =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedReportButton(text: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "reportButtonScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFD4AF37))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFF0A2E1F)),
                onClick = onClick
            )
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0A2E1F)
        )
    }
}

// ============================================================
// ===== Section Header =====
// ============================================================
@Composable
private fun SectionHeader(emoji: String, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

// ============================================================
// ===== Input Field =====
// ============================================================
@Composable
private fun InputField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d*$"))) {
                onChange(input)
            }
        },
        label = {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFFD4AF37),
            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
            cursorColor = Color(0xFFD4AF37)
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// ============================================================
// ===== Value Row (animated appear) =====
// ============================================================
@Composable
private fun ValueRow(label: String, value: Double) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300, easing = FastOutSlowInEasing)) +
                expandVertically(
                    animationSpec = tween(350, easing = FastOutSlowInEasing),
                    expandFrom = Alignment.Top
                ),
        exit = fadeOut(animationSpec = tween(200)) +
                shrinkVertically(animationSpec = tween(250))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF4CAF50).copy(alpha = 0.1f))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
            Text(
                text = "৳ ${formatNumber(value)}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

// ============================================================
// ===== Summary Card =====
// ============================================================
@Composable
private fun SummaryCard(
    totalAssets: Double,
    debts: Double,
    netWealth: Double,
    nisab: Double,
    isZakatApplicable: Boolean,
    zakatAmount: Double,
    language: String
) {
    val animatedZakat by animateFloatAsState(
        targetValue = zakatAmount.toFloat(),
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "zakatCountUp"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = 0.3f),
                        Color(0xFF4CAF50).copy(alpha = 0.25f)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "📊", fontSize = 26.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = AppStrings.zakatSummaryTitle(language),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            SummaryRow(AppStrings.zakatTotalAssets(language), totalAssets, Color.White)
            SummaryRow(AppStrings.zakatDebtsMinus(language), debts, Color(0xFFFF5252))
            DividerLine()
            SummaryRow(AppStrings.zakatNetWealth(language), netWealth, Color(0xFF4CAF50), bold = true)
            SummaryRow(AppStrings.zakatNisab(language), nisab, Color(0xFF2196F3))

            Spacer(modifier = Modifier.height(12.dp))

            if (isZakatApplicable) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF4CAF50).copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = AppStrings.zakatObligated(language),
                            fontSize = 13.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "৳ ${formatNumber(animatedZakat.toDouble())}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD4AF37)
                        )
                        Text(
                            text = AppStrings.zakatToPay(language),
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFF9800).copy(alpha = 0.25f))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = AppStrings.zakatNotObligated(language),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = AppStrings.zakatBelowNisab(language),
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: Double, color: Color, bold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.85f),
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "৳ ${formatNumber(value)}",
            fontSize = 13.sp,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Medium,
            color = color
        )
    }
}

@Composable
private fun DividerLine() {
    Spacer(modifier = Modifier.height(6.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White.copy(alpha = 0.2f))
    )
    Spacer(modifier = Modifier.height(6.dp))
}

// ============================================================
// ===== Info Card =====
// ============================================================
@Composable
private fun InfoCard(language: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = AppStrings.zakatInfoTitle(language),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = AppStrings.zakatInfoContent(language),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.75f),
                lineHeight = 20.sp
            )
        }
    }
}

// ============================================================
// ===== Report Dialog Content (staggered rows) =====
// ============================================================
@Composable
private fun ZakatReportContent(
    goldValue: Double,
    silverValue: Double,
    cashValue: Double,
    bankValue: Double,
    businessValue: Double,
    otherValue: Double,
    totalAssets: Double,
    debts: Double,
    netWealth: Double,
    nisab: Double,
    isZakatApplicable: Boolean,
    zakatAmount: Double,
    language: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        DialogStaggerItem(visible = true, delayMillis = 0) {
            ReportRow(AppStrings.zakatReportGold(language), goldValue)
        }
        DialogStaggerItem(visible = true, delayMillis = 40) {
            ReportRow(AppStrings.zakatReportSilver(language), silverValue)
        }
        DialogStaggerItem(visible = true, delayMillis = 80) {
            ReportRow(AppStrings.zakatReportCash(language), cashValue)
        }
        DialogStaggerItem(visible = true, delayMillis = 120) {
            ReportRow(AppStrings.zakatReportBank(language), bankValue)
        }
        DialogStaggerItem(visible = true, delayMillis = 160) {
            ReportRow(AppStrings.zakatReportBusiness(language), businessValue)
        }
        DialogStaggerItem(visible = true, delayMillis = 200) {
            ReportRow(AppStrings.zakatReportOther(language), otherValue)
        }

        Spacer(modifier = Modifier.height(8.dp))
        ReportDivider()
        Spacer(modifier = Modifier.height(8.dp))

        DialogStaggerItem(visible = true, delayMillis = 260) {
            ReportRow(
                AppStrings.zakatReportTotalAssets(language),
                totalAssets,
                bold = true
            )
        }
        DialogStaggerItem(visible = true, delayMillis = 300) {
            ReportRow(
                AppStrings.zakatReportTotalDebts(language),
                -debts,
                color = Color(0xFFFF5252)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        ReportDivider()
        Spacer(modifier = Modifier.height(8.dp))

        DialogStaggerItem(visible = true, delayMillis = 360) {
            ReportRow(
                AppStrings.zakatReportNetWealth(language),
                netWealth,
                color = Color(0xFF4CAF50),
                bold = true
            )
        }
        DialogStaggerItem(visible = true, delayMillis = 400) {
            ReportRow(
                AppStrings.zakatReportNisab(language),
                nisab,
                color = Color(0xFF2196F3)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isZakatApplicable) {
            DialogStaggerItem(visible = true, delayMillis = 460) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF4CAF50).copy(alpha = 0.2f))
                        .padding(12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = AppStrings.zakatReportZakat25(language),
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "৳ ${formatNumber(zakatAmount)}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }
        } else {
            DialogStaggerItem(visible = true, delayMillis = 460) {
                Text(
                    text = AppStrings.zakatReportNotObligated(language),
                    fontSize = 12.sp,
                    color = Color(0xFFFF9800),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun ReportRow(
    label: String,
    value: Double,
    color: Color = Color.White,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.85f),
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "৳ ${formatNumber(value)}",
            fontSize = 13.sp,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Medium,
            color = color
        )
    }
}

@Composable
private fun ReportDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White.copy(alpha = 0.2f))
    )
}

// ============================================================
// ===== Number Formatter (fixed) =====
// ============================================================
private fun formatNumber(value: Double): String {
    return try {
        val rounded = round(value * 100.0) / 100.0
        if (rounded == rounded.toLong().toDouble()) {
            rounded.toLong().toString()
        } else {
            String.format(Locale.US, "%.2f", rounded)
        }
    } catch (_: Exception) {
        "0"
    }
}