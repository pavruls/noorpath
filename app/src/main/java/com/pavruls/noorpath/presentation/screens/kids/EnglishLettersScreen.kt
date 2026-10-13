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
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.utils.TtsHelper
import kotlinx.coroutines.launch

data class EnglishLetter(
    val uppercase: String,
    val lowercase: String,
    val word: String,
    val emoji: String,
    val sentence: String
)

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_CARD = 120
private const val DELAY_AUDIO = 240
private const val DELAY_WORD = 320
private const val DELAY_SENTENCE = 400
private const val DELAY_DOTS = 500
private const val DELAY_BUTTONS = 600

@Composable
fun EnglishLettersScreen(
    onBackClick: () -> Unit
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
            EnglishLetter("A", "a", "Allah", "ﷲ", "A. A for Allah. Allah is one."),
            EnglishLetter("B", "b", "Bismillah", "🤲", "B. B for Bismillah. Start with Bismillah."),
            EnglishLetter("C", "c", "Charity", "🎁", "C. C for Charity. Give charity to the poor."),
            EnglishLetter("D", "d", "Dua", "🤲", "D. D for Dua. Make dua to Allah."),
            EnglishLetter("E", "e", "Eid", "🌙", "E. E for Eid. Eid is a joyful day."),
            EnglishLetter("F", "f", "Fasting", "🌙", "F. F for Fasting. Fast in Ramadan."),
            EnglishLetter("G", "g", "Good Deeds", "✨", "G. G for Good Deeds. Always do good deeds."),
            EnglishLetter("H", "h", "Hajj", "🕋", "H. H for Hajj. Hajj is the fifth pillar."),
            EnglishLetter("I", "i", "Islam", "☪️", "I. I for Islam. Islam means peace."),
            EnglishLetter("J", "j", "Jannah", "🌸", "J. J for Jannah. Jannah is Paradise."),
            EnglishLetter("K", "k", "Kabah", "🕋", "K. K for Kabah. Kabah is in Makkah."),
            EnglishLetter("L", "l", "Laylatul Qadr", "✨", "L. L for Laylatul Qadr. It is a blessed night."),
            EnglishLetter("M", "m", "Masjid", "🕌", "M. M for Masjid. Masjid is the house of Allah."),
            EnglishLetter("N", "n", "Namaz", "🕌", "N. N for Namaz. Pray five times a day."),
            EnglishLetter("O", "o", "Obedience", "🙏", "O. O for Obedience. Obey Allah and parents."),
            EnglishLetter("P", "p", "Prophet", "🌟", "P. P for Prophet. Muhammad is the last Prophet."),
            EnglishLetter("Q", "q", "Quran", "📖", "Q. Q for Quran. Quran is the book of Allah."),
            EnglishLetter("R", "r", "Ramadan", "🌙", "R. R for Ramadan. Ramadan is the month of fasting."),
            EnglishLetter("S", "s", "Salah", "🕌", "S. S for Salah. Salah is prayer."),
            EnglishLetter("T", "t", "Tasbih", "📿", "T. T for Tasbih. Say Subhanallah."),
            EnglishLetter("U", "u", "Ummah", "👥", "U. U for Ummah. Muslims are one Ummah."),
            EnglishLetter("V", "v", "Victory", "🏆", "V. V for Victory. Allah gives victory."),
            EnglishLetter("W", "w", "Wudu", "💧", "W. W for Wudu. Make Wudu before prayer."),
            EnglishLetter("X", "x", "eXcellence", "🌟", "X. X for eXcellence. Be excellent in deeds."),
            EnglishLetter("Y", "y", "Yaqeen", "💪", "Y. Y for Yaqeen. Have faith in Allah."),
            EnglishLetter("Z", "z", "Zakat", "💰", "Z. Z for Zakat. Give Zakat every year.")
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    val current = letters[currentIndex]

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    // Letter flip / bounce animation
    val letterBounce = remember { Animatable(1f) }
    val letterFlip = remember { Animatable(0f) }

    LaunchedEffect(currentIndex) {
        TtsHelper.speakEnglish(current.sentence)

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
                            text = AppStrings.englishLetters(language),
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
                    EnglishLetterFlipCard(
                        uppercase = current.uppercase,
                        lowercase = current.lowercase,
                        bounce = letterBounce.value,
                        flip = letterFlip.value,
                        onClick = { TtsHelper.speakEnglish(current.sentence) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Audio Button with pulse =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_AUDIO) {
                    AudioButton(onClick = { TtsHelper.speakEnglish(current.sentence) })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Emoji + Word (animated) =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_WORD) {
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
                        label = "englishWord"
                    ) { idx ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF2196F3).copy(alpha = 0.15f))
                                .padding(horizontal = 24.dp, vertical = 14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = letters[idx].emoji, fontSize = 36.sp)
                                Spacer(modifier = Modifier.width(14.dp))
                                Text(
                                    text = letters[idx].word,
                                    fontSize = 22.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ===== Sentence (animated) =====
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
                        label = "englishSentence"
                    ) { idx ->
                        Text(
                            text = letters[idx].sentence,
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
                        .padding(bottom = 12.dp),
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
                        .padding(horizontal = 20.dp, vertical = 16.dp),
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
// ===== English Letter Flip Card (Uppercase + Lowercase) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnglishLetterFlipCard(
    uppercase: String,
    lowercase: String,
    bounce: Float,
    flip: Float,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "englishCardPress"
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = uppercase,
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = lowercase,
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

// ============================================================
// ===== Audio Button (with pulse ring) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AudioButton(onClick: () -> Unit) {
    val infinite = rememberInfiniteTransition(label = "englishAudioPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "englishAudioPulseScale"
    )
    val pulseAlpha by infinite.animateFloat(
        initialValue = 0.4f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "englishAudioPulseAlpha"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "englishAudioPress"
    )

    Box(
        modifier = Modifier.size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        // Pulse ring
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(Color(0xFFD4AF37).copy(alpha = pulseAlpha * 0.5f))
        )

        // Main button
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
        targetValue = if (isActive) 8f else 5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "englishDotSize"
    )
    val color by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.3f,
        animationSpec = tween(300),
        label = "englishDotAlpha"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 1.5.dp)
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
        label = "englishNavButtonScale"
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
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            } else {
                Text(
                    text = text,
                    fontSize = 15.sp,
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