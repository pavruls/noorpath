package com.pavruls.noorpath.presentation.screens.quran

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
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
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
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.ShimmerListSkeleton
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.quran.QuranConstants
import com.pavruls.noorpath.quran.QuranRepository
import com.pavruls.noorpath.quran.Verse

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 50
private const val ANIMATE_THRESHOLD = 8

@Composable
fun QuranDetailScreen(
    surahId: Int,
    surahName: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    var verses by remember { mutableStateOf<List<Verse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var currentlyPlaying by remember { mutableStateOf(-1) }

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                androidx.media3.common.AudioAttributes.Builder()
                    .setContentType(androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(androidx.media3.common.C.USAGE_MEDIA)
                    .build(),
                true
            )
        }
    }

    LaunchedEffect(surahId) {
        isLoading = true
        verses = QuranRepository.loadVersesForSurah(context, surahId)
        isLoading = false
        entryVisible = true
    }

    DisposableEffect(exoPlayer) {
        val listener = object : androidx.media3.common.Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == androidx.media3.common.Player.STATE_ENDED) {
                    currentlyPlaying = -1
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    fun playVerse(verse: Verse) {
        // Same verse tap করলে stop
        if (currentlyPlaying == verse.id) {
            exoPlayer.stop()
            currentlyPlaying = -1
            return
        }
        val url = QuranConstants.getAudioUrl(verse.globalId)
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        currentlyPlaying = verse.id
    }

    fun stopAudio() {
        exoPlayer.stop()
        currentlyPlaying = -1
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
            StaggeredEntry(visible = entryVisible || isLoading, delayMillis = DELAY_HEADER) {
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
                            text = surahName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (!isLoading) {
                            Text(
                                text = AppStrings.verseCount(language, verses.size),
                                fontSize = 12.sp,
                                color = Color(0xFFD4AF37)
                            )
                        }
                    }
                }
            }

            // ===== Loading Shimmer =====
            if (isLoading) {
                ShimmerListSkeleton(
                    itemCount = 6,
                    contentPadding = PaddingValues(16.dp),
                    verticalSpacing = 14.dp,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            // bottom padding দেব যাতে mini player content-কে ঢাকে না
                            bottom = if (currentlyPlaying != -1) 100.dp else 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        itemsIndexed(
                            items = verses,
                            key = { _, verse -> verse.id }
                        ) { index, verse ->
                            val delay = if (index < ANIMATE_THRESHOLD) {
                                DELAY_LIST_START + index * STAGGER_STEP
                            } else 0

                            StaggeredEntry(
                                visible = entryVisible,
                                delayMillis = delay
                            ) {
                                VerseCard(
                                    verse = verse,
                                    language = language,
                                    isPlaying = currentlyPlaying == verse.id,
                                    onPlayClick = { playVerse(verse) }
                                )
                            }
                        }
                    }

                    // ===== Mini Audio Player (bottom, floating) =====
                    androidx.compose.animation.AnimatedVisibility(
                        visible = currentlyPlaying != -1,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        ) + fadeIn(tween(300)),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(220, easing = FastOutSlowInEasing)
                        ) + fadeOut(tween(180)),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    ) {
                        MiniAudioPlayer(
                            surahName = surahName,
                            verseNumber = currentlyPlaying,
                            language = language,
                            onStop = { stopAudio() }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Verse Card (with playing highlight + pulsing play button) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerseCard(
    verse: Verse,
    language: String,
    isPlaying: Boolean,
    onPlayClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.99f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "verseCardScale"
    )

    // Glow background when playing
    val infinite = rememberInfiniteTransition(label = "versePlaying")
    val playingGlow by infinite.animateFloat(
        initialValue = 0.10f,
        targetValue = 0.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "playingGlow"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isPlaying) Color(0xFF4CAF50).copy(alpha = playingGlow)
                else Color.White.copy(alpha = 0.06f)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isPlaying) Color(0xFF4CAF50)
                        else Color(0xFFD4AF37).copy(alpha = 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${verse.id}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = AppStrings.verseNumber(language, verse.id),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f)
            )

            // Play button with pulse ring when playing
            PlayButton(
                isPlaying = isPlaying,
                language = language,
                onClick = onPlayClick
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (verse.arabic.isNotBlank()) {
            Text(
                text = verse.arabic,
                fontSize = 24.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold,
                lineHeight = 44.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (verse.bengali.isNotBlank()) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "🇧🇩",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = verse.bengali,
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.95f),
                    lineHeight = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (verse.english.isNotBlank()) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "🇬🇧",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = verse.english,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    lineHeight = 22.sp
                )
            }
        }
    }
}

// ============================================================
// ===== Play Button (pulse ring when playing) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayButton(
    isPlaying: Boolean,
    language: String,
    onClick: () -> Unit
) {
    val infinite = rememberInfiniteTransition(label = "playPulse")
    val ringScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = if (isPlaying) 1.4f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "playRingScale"
    )
    val ringAlpha by infinite.animateFloat(
        initialValue = if (isPlaying) 0.5f else 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "playRingAlpha"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "playPressScale"
    )

    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        // Pulse ring when playing
        if (isPlaying) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .scale(ringScale)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50).copy(alpha = ringAlpha))
            )
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .scale(pressScale)
                .clip(CircleShape)
                .background(
                    if (isPlaying) Color(0xFFE53935)
                    else Color(0xFFD4AF37)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(
                        color = if (isPlaying) Color(0xFF4CAF50) else Color(0xFFD4AF37)
                    ),
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Stop
                else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying)
                    AppStrings.stopAudio(language)
                else
                    AppStrings.playAudio(language),
                tint = if (isPlaying) Color.White else Color(0xFF0A2E1F),
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

// ============================================================
// ===== Mini Audio Player (bottom floating) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MiniAudioPlayer(
    surahName: String,
    verseNumber: Int,
    language: String,
    onStop: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val stopScale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "miniStopScale"
    )

    // Sound wave pulse on the icon
    val infinite = rememberInfiniteTransition(label = "miniPlayer")
    val iconPulse by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "miniIconPulse"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF102A1E))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Pulsing sound icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .scale(iconPulse)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50).copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🔊", fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = AppStrings.nowPlaying(language),
                    fontSize = 11.sp,
                    color = Color(0xFFD4AF37),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$surahName • ${AppStrings.verseNumber(language, verseNumber)}",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Stop button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .scale(stopScale)
                    .clip(CircleShape)
                    .background(Color(0xFFE53935))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple(color = Color.White),
                        onClick = onStop
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = AppStrings.stopAudio(language),
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}