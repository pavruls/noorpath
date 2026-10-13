package com.pavruls.noorpath.presentation.screens.kids

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.presentation.viewmodel.ArabicViewModel
import com.pavruls.noorpath.utils.TtsHelper
import kotlinx.coroutines.launch

data class ArabicLetter(
    val arabic: String,
    val nameBn: String, val nameEn: String, val nameAr: String,
    val pronunBn: String, val pronunEn: String, val pronunAr: String,
    val sentenceBn: String, val sentenceEn: String, val sentenceAr: String
) {
    fun getName(lang: String) = when (lang) {
        LanguageManager.LANG_EN -> nameEn
        LanguageManager.LANG_AR -> nameAr
        else -> nameBn
    }
    fun getPronun(lang: String) = when (lang) {
        LanguageManager.LANG_EN -> pronunEn
        LanguageManager.LANG_AR -> pronunAr
        else -> pronunBn
    }
    fun getSentence(lang: String) = when (lang) {
        LanguageManager.LANG_EN -> sentenceEn
        LanguageManager.LANG_AR -> sentenceAr
        else -> sentenceBn
    }
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_CARD = 120
private const val DELAY_AUDIO = 240
private const val DELAY_NAME = 320
private const val DELAY_PRONUN = 400
private const val DELAY_SENTENCE = 480
private const val DELAY_DOTS = 560
private const val DELAY_BUTTONS = 640

@Composable
fun ArabicLettersScreen(
    onBackClick: () -> Unit,
    viewModel: ArabicViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        TtsHelper.init(context)
        onDispose { TtsHelper.stop() }
    }

    val letters = remember {
        listOf(
            ArabicLetter("ا", "আলিফ", "Alif", "ألف", "আ", "A", "أ", "আলিফ। আল্লাহ এক।", "Alif. Allah is one.", "ألف. الله واحد."),
            ArabicLetter("ب", "বা", "Ba", "باء", "বা", "Ba", "با", "বা। বিসমিল্লাহ।", "Ba. Bismillah.", "باء. بسم الله."),
            ArabicLetter("ت", "তা", "Ta", "تاء", "তা", "Ta", "تا", "তা। তাওহিদ।", "Ta. Tawhid.", "تاء. التوحيد."),
            ArabicLetter("ث", "ছা", "Tha", "ثاء", "ছা", "Tha", "ثا", "ছা। ছবর।", "Tha. Sabr (Patience).", "ثاء. الصبر."),
            ArabicLetter("ج", "জিম", "Jim", "جيم", "জি", "Ji", "جي", "জিম। জান্নাত।", "Jim. Jannah.", "جيم. الجنة."),
            ArabicLetter("ح", "হা", "Ha", "حاء", "হা", "Ha", "حا", "হা। হজ।", "Ha. Hajj.", "حاء. الحج."),
            ArabicLetter("خ", "খা", "Kha", "خاء", "খা", "Kha", "خا", "খা। খুতবা।", "Kha. Khutbah.", "خاء. الخطبة."),
            ArabicLetter("د", "দাল", "Dal", "دال", "দা", "Da", "دا", "দাল। দান।", "Dal. Charity.", "دال. الصدقة."),
            ArabicLetter("ذ", "যাল", "Zal", "ذال", "যা", "Za", "ذا", "যাল। যাকাত।", "Zal. Zakat.", "ذال. الزكاة."),
            ArabicLetter("ر", "রা", "Ra", "راء", "রা", "Ra", "را", "রা। রমজান।", "Ra. Ramadan.", "راء. رمضان."),
            ArabicLetter("ز", "যা", "Za", "زاي", "যা", "Za", "زا", "যা। যিকির।", "Za. Dhikr.", "زاي. الذكر."),
            ArabicLetter("س", "সিন", "Sin", "سين", "সি", "Si", "سي", "সিন। সালাম।", "Sin. Salam.", "سين. السلام."),
            ArabicLetter("ش", "শিন", "Shin", "شين", "শি", "Shi", "شي", "শিন। শুকরিয়া।", "Shin. Gratitude.", "شين. الشكر."),
            ArabicLetter("ص", "সাদ", "Sad", "صاد", "সা", "Sa", "صا", "সাদ। সাদাকা।", "Sad. Sadaqah.", "صاد. الصدقة."),
            ArabicLetter("ض", "দাদ", "Dad", "ضاد", "দা", "Da", "ضا", "দাদ। দুআ।", "Dad. Dua.", "ضاد. الدعاء."),
            ArabicLetter("ط", "তা", "Ta", "طاء", "তা", "Ta", "طا", "তা। তাওয়াফ।", "Ta. Tawaf.", "طاء. الطواف."),
            ArabicLetter("ظ", "যা", "Zha", "ظاء", "যা", "Zha", "ظا", "যা। যোহর।", "Zha. Dhuhr.", "ظاء. الظهر."),
            ArabicLetter("ع", "আইন", "Ain", "عين", "আ", "A", "ع", "আইন। ইলম।", "Ain. Knowledge.", "عين. العلم."),
            ArabicLetter("غ", "গাইন", "Ghain", "غين", "গা", "Gha", "غا", "গাইন। গোসল।", "Ghain. Ghusl.", "غين. الغسل."),
            ArabicLetter("ف", "ফা", "Fa", "فاء", "ফা", "Fa", "فا", "ফা। ফজর।", "Fa. Fajr.", "فاء. الفجر."),
            ArabicLetter("ق", "কাফ", "Qaf", "قاف", "কা", "Qa", "قا", "কাফ। কুরআন।", "Qaf. Quran.", "قاف. القرآن."),
            ArabicLetter("ك", "কাফ", "Kaf", "كاف", "কা", "Ka", "كا", "কাফ। কাবা।", "Kaf. Kaaba.", "كاف. الكعبة."),
            ArabicLetter("ل", "লাম", "Lam", "لام", "লা", "La", "لا", "লাম। লা ইলাহা ইল্লাল্লাহ।", "Lam. La ilaha illallah.", "لام. لا إله إلا الله."),
            ArabicLetter("م", "মিম", "Mim", "ميم", "মি", "Mi", "مي", "মিম। মসজিদ।", "Mim. Masjid.", "ميم. المسجد."),
            ArabicLetter("ن", "নুন", "Nun", "نون", "নু", "Nu", "نو", "নুন। নামাজ।", "Nun. Salah.", "نون. الصلاة."),
            ArabicLetter("و", "ওয়াও", "Waw", "واو", "ও", "Wa", "وا", "ওয়াও। ওজু।", "Waw. Wudu.", "واو. الوضوء."),
            ArabicLetter("ه", "হা", "Ha", "هاء", "হা", "Ha", "ها", "হা। হালাল।", "Ha. Halal.", "هاء. الحلال."),
            ArabicLetter("ي", "ইয়া", "Ya", "ياء", "ইয়া", "Ya", "يا", "ইয়া। ইয়াকিন।", "Ya. Yaqeen.", "ياء. اليقين.")
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    val current = letters[currentIndex]

    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    val letterBounce = remember { Animatable(1f) }
    val letterFlip = remember { Animatable(0f) }

    LaunchedEffect(currentIndex, language) {
        viewModel.saveProgress(currentIndex, currentIndex + 1)
        TtsHelper.speakArabic(current.sentenceBn)

        scope.launch {
            letterFlip.snapTo(0f)
            letterBounce.snapTo(0.7f)
            letterFlip.animateTo(
                targetValue = 1f,
                animationSpec = tween(220, easing = FastOutSlowInEasing)
            )
            letterBounce.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
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
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    Column {
                        Text(
                            text = AppStrings.arabicLetters(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${currentIndex + 1} / ${letters.size}",
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // ===== Letter Card with flip =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_CARD) {
                    LetterFlipCard(
                        letter = current.arabic,
                        bounce = letterBounce.value,
                        flip = letterFlip.value,
                        onClick = { TtsHelper.speakArabic(current.sentenceBn) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Audio Button with pulse =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_AUDIO) {
                    AudioButton(onClick = { TtsHelper.speakArabic(current.sentenceBn) })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Name =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_NAME) {
                    AnimatedContent<Int>(
                        targetState = currentIndex,
                        transitionSpec = {
                            (fadeIn(tween(280)) + slideInHorizontally(
                                animationSpec = tween(320, easing = FastOutSlowInEasing),
                                initialOffsetX = { it / 3 }
                            )).togetherWith(
                                fadeOut(tween(180)) + slideOutHorizontally(
                                    animationSpec = tween(220),
                                    targetOffsetX = { -it / 3 }
                                )
                            )
                        },
                        label = "letterName"
                    ) { idx ->
                        Text(
                            text = letters[idx].getName(language),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ===== Pronunciation =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_PRONUN) {
                    AnimatedContent<Int>(
                        targetState = currentIndex,
                        transitionSpec = {
                            (fadeIn(tween(280)) + slideInHorizontally(
                                animationSpec = tween(320, easing = FastOutSlowInEasing),
                                initialOffsetX = { it / 3 }
                            )).togetherWith(
                                fadeOut(tween(180)) + slideOutHorizontally(
                                    animationSpec = tween(220),
                                    targetOffsetX = { -it / 3 }
                                )
                            )
                        },
                        label = "letterPronun"
                    ) { idx ->
                        Text(
                            text = "${AppStrings.pronunciationFullLabel(language)}: ${letters[idx].getPronun(language)}",
                            fontSize = 18.sp,
                            color = Color(0xFFD4AF37),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ===== Sentence =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_SENTENCE) {
                    AnimatedContent<Int>(
                        targetState = currentIndex,
                        transitionSpec = {
                            (fadeIn(tween(300)) + slideInHorizontally(
                                animationSpec = tween(340, easing = FastOutSlowInEasing),
                                initialOffsetX = { it / 4 }
                            )).togetherWith(
                                fadeOut(tween(180)) + slideOutHorizontally(
                                    animationSpec = tween(220),
                                    targetOffsetX = { -it / 4 }
                                )
                            )
                        },
                        label = "letterSentence"
                    ) { idx ->
                        Text(
                            text = letters[idx].getSentence(language),
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp),
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // ===== Progress Dots =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_DOTS) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(letters.size) { i ->
                        ProgressDot(isActive = i == currentIndex)
                    }
                }
            }

            // ===== Prev / Next Buttons =====
            StaggeredEntry(visible = entryVisible, delayMillis = DELAY_BUTTONS) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val canGoPrev = currentIndex > 0
                    NavButton(
                        text = AppStrings.previousLabel(language),
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        iconOnLeft = true,
                        enabled = canGoPrev,
                        onClick = { if (canGoPrev) currentIndex-- }
                    )

                    val canGoNext = currentIndex < letters.size - 1
                    NavButton(
                        text = AppStrings.nextLabel(language),
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        iconOnLeft = false,
                        enabled = canGoNext,
                        onClick = { if (canGoNext) currentIndex++ }
                    )
                }
            }
        }
    }
}

// ============================================================
// ===== Letter Flip Card =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LetterFlipCard(
    letter: String,
    bounce: Float,
    flip: Float,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "letterCardPress"
    )

    Box(
        modifier = Modifier
            .size(240.dp)
            .scale(pressScale * bounce)
            .graphicsLayer {
                rotationY = (1f - flip) * 90f
            }
            .clip(RoundedCornerShape(40.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = 160.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37)
        )
    }
}

// ============================================================
// ===== Audio Button =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AudioButton(onClick: () -> Unit) {
    val infinite = rememberInfiniteTransition(label = "audioPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "audioPulseScale"
    )
    val pulseAlpha by infinite.animateFloat(
        initialValue = 0.4f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "audioPulseAlpha"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "audioButtonPress"
    )

    Box(
        modifier = Modifier.size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = pulseAlpha * 0.5f))
        )

        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(pressScale)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = 0.25f))
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(color = Color(0xFFD4AF37)),
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🔊", fontSize = 26.sp)
        }
    }
}

// ============================================================
// ===== Progress Dot =====
// ============================================================
@Composable
private fun ProgressDot(isActive: Boolean) {
    val size by animateFloatAsState(
        targetValue = if (isActive) 10f else 6f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "dotSize"
    )
    val color by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.3f,
        animationSpec = tween(300),
        label = "dotAlpha"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .size(size.dp)
            .clip(CircleShape)
            .background(
                if (isActive) Color(0xFFD4AF37)
                else Color.White.copy(alpha = color)
            )
    )
}

// ============================================================
// ===== Nav Button =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavButton(
    text: String,
    icon: ImageVector,
    iconOnLeft: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "navButtonScale"
    )

    val textColor = if (enabled) Color(0xFF0A2E1F) else Color.White.copy(alpha = 0.4f)
    val bgColor = if (enabled) Color(0xFFD4AF37) else Color.White.copy(alpha = 0.1f)

    Box(
        modifier = Modifier
            .width(140.dp)
            .height(60.dp)
            .scale(scale)
            .clip(RoundedCornerShape(30.dp))
            .background(bgColor)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (iconOnLeft) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = textColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            } else {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = textColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}