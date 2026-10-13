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

data class BanglaLetter(
    val letter: String,
    val word: String,
    val emoji: String,
    val sentenceBn: String,
    val sentenceEn: String,
    val sentenceAr: String
) {
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
private const val DELAY_WORD = 320
private const val DELAY_SENTENCE = 400
private const val DELAY_DOTS = 500
private const val DELAY_BUTTONS = 600

@Composable
fun BanglaLettersScreen(
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
            BanglaLetter("অ", "অজু", "💧",
                "অ, অজু। নামাজের আগে অজু করতে হয়।",
                "O, Oju. Wudu is performed before prayer.",
                "أ، الوضوء. يتوضأ قبل الصلاة."),
            BanglaLetter("আ", "আল্লাহ", "ﷲ",
                "আ, আল্লাহ। আল্লাহ এক।",
                "Aa, Allah. Allah is one.",
                "آ، الله. الله واحد."),
            BanglaLetter("ই", "ইমান", "💚",
                "ই, ইমান। ইমান আনা প্রত্যেক মুসলিমের কর্তব্য।",
                "I, Iman. Faith is the duty of every Muslim.",
                "إ، الإيمان. الإيمان واجب على كل مسلم."),
            BanglaLetter("ঈ", "ঈদ", "🌙",
                "ঈ, ঈদ। ঈদ মুসলিমদের আনন্দের দিন।",
                "Ee, Eid. Eid is a joyful day for Muslims.",
                "إي، العيد. العيد يوم فرح للمسلمين."),
            BanglaLetter("উ", "উম্মত", "👥",
                "উ, উম্মত। আমরা সবাই এক উম্মত।",
                "U, Ummah. We are all one Ummah.",
                "أو، الأمة. كلنا أمة واحدة."),
            BanglaLetter("ঊ", "ঊষা", "🌅",
                "ঊ, ঊষা। ফজরের সময় ঊষা হয়।",
                "Uu, Usha (Dawn). Dawn comes at Fajr time.",
                "أو، الفجر. يطلع الفجر في وقت صلاة الفجر."),
            BanglaLetter("ঋ", "ঋণ", "🤝",
                "ঋ, ঋণ। ঋণ পরিশোধ করা কর্তব্য।",
                "Ri, Rin (Debt). Paying debt is a duty.",
                "ري، الدين. سداد الدين واجب."),
            BanglaLetter("এ", "একত্ব", "☝️",
                "এ, একত্ব। আল্লাহর একত্বে বিশ্বাস করা ফরজ।",
                "E, Ekotwo (Oneness). Believing in Allah's oneness is obligatory.",
                "إي، التوحيد. الإيمان بوحدانية الله فرض."),
            BanglaLetter("ঐ", "ঐক্য", "🤲",
                "ঐ, ঐক্য। মুসলিমদের ঐক্য জরুরি।",
                "Oi, Oikko (Unity). Muslim unity is essential.",
                "أوي، الوحدة. وحدة المسلمين ضرورية."),
            BanglaLetter("ও", "ওজু", "💧",
                "ও, ওজু। নামাজের আগে ওজু করতে হয়।",
                "O, Oju. Wudu is performed before prayer.",
                "أو، الوضوء. يتوضأ قبل الصلاة."),
            BanglaLetter("ঔ", "ঔদার্য", "🎁",
                "ঔ, ঔদার্য। ঔদার্য একটি উত্তম গুণ।",
                "Ou, Oudarjo (Generosity). Generosity is a noble quality.",
                "أو، الكرم. الكرم خلق حميد."),
            BanglaLetter("ক", "কুরআন", "📖",
                "ক, কুরআন। কুরআন আল্লাহর কিতাব।",
                "Ka, Quran. Quran is the book of Allah.",
                "ك، القرآن. القرآن كتاب الله."),
            BanglaLetter("খ", "খুতবা", "🕌",
                "খ, খুতবা। জুমার দিন খুতবা শুনতে হয়।",
                "Kha, Khutbah. Listen to Khutbah on Friday.",
                "خ، الخطبة. يسمع الخطبة يوم الجمعة."),
            BanglaLetter("গ", "গোসল", "🚿",
                "গ, গোসল। নাপাক হলে গোসল করতে হয়।",
                "Ga, Ghusl. Ghusl is needed after impurity.",
                "غ، الغسل. يجب الغسل بعد الجنابة."),
            BanglaLetter("ঘ", "ঘুষ", "❌",
                "ঘ, ঘুষ। ঘুষ দেওয়া-নেওয়া হারাম।",
                "Gha, Ghush (Bribe). Giving or taking a bribe is forbidden.",
                "غ، الرشوة. أخذ الرشوة وإعطاؤها حرام."),
            BanglaLetter("চ", "চরিত্র", "💎",
                "চ, চরিত্র। উত্তম চরিত্র মুমিনের অলংকার।",
                "Cha, Choritro (Character). Good character is a believer's ornament.",
                "تش، الأخلاق. الأخلاق الحسنة زينة المؤمن."),
            BanglaLetter("ছ", "ছবর", "🧘",
                "ছ, ছবর। বিপদে ছবর করা উচিত।",
                "Chha, Sabr. Be patient in adversity.",
                "ص، الصبر. الصبر عند البلاء."),
            BanglaLetter("জ", "জান্নাত", "🌸",
                "জ, জান্নাত। জান্নাত মুমিনের চিরস্থায়ী ঠিকানা।",
                "Ja, Jannah. Jannah is the eternal home of believers.",
                "ج، الجنة. الجنة دار المؤمنين الخالدة."),
            BanglaLetter("ঝ", "ঝগড়া", "⚠️",
                "ঝ, ঝগড়া। ঝগড়া করা ভালো নয়।",
                "Jha, Jhogra (Quarrel). Quarreling is not good.",
                "جه، المشاجرة. المشاجرة ليست حسنة."),
            BanglaLetter("ট", "টাকা", "💰",
                "ট, টাকা। হালাল উপায়ে টাকা আয় করা উচিত।",
                "Ta, Taka (Money). Earn money through halal means.",
                "ط، المال. يجب كسب المال بطريقة حلال."),
            BanglaLetter("ঠ", "ঠিক", "✅",
                "ঠ, ঠিক। সবসময় সত্য কথা বলা উচিত।",
                "Tha, Thik (Right). Always speak the truth.",
                "ث، الصواب. قل الحق دائمًا."),
            BanglaLetter("ড", "ডুআ", "🤲",
                "ড, ডুআ। আল্লাহর কাছে ডুআ করা যায়।",
                "Da, Dua. Make dua to Allah.",
                "د، الدعاء. ادعُ الله."),
            BanglaLetter("ঢ", "ঢাল", "🛡️",
                "ঢ, ঢাল। ঈমান আমাদের ঢাল।",
                "Dha, Dhal (Shield). Faith is our shield.",
                "ض، الدرع. الإيمان درعنا."),
            BanglaLetter("ত", "তাওহিদ", "☝️",
                "ত, তাওহিদ। তাওহিদ ইসলামের মূল ভিত্তি।",
                "Ta, Tawhid. Tawhid is the foundation of Islam.",
                "ت، التوحيد. التوحيد أساس الإسلام."),
            BanglaLetter("থ", "থামা", "🛑",
                "থ, থামা। নামাজে সব কাজ থেকে থামতে হয়।",
                "Tha, Thama (Stop). Stop all actions for prayer.",
                "ث، التوقف. توقف عن كل شيء للصلاة."),
            BanglaLetter("দ", "দান", "🤝",
                "দ, দান। গরিবকে দান করা উচিত।",
                "Da, Dan (Charity). Give charity to the poor.",
                "د، الصدقة. تصدق على الفقراء."),
            BanglaLetter("ধ", "ধৈর্য", "🧘",
                "ধ, ধৈর্য। ধৈর্যশীলরা আল্লাহর প্রিয়।",
                "Dha, Dhoirjo (Patience). The patient are dear to Allah.",
                "ذ، الصبر. الصابرون أحباء الله."),
            BanglaLetter("ন", "নামাজ", "🕌",
                "ন, নামাজ। দিনে পাঁচ ওয়াক্ত নামাজ ফরজ।",
                "Na, Namaz. Five prayers are obligatory daily.",
                "ن، الصلاة. خمس صلوات مفروضة يوميًا."),
            BanglaLetter("প", "পবিত্রতা", "✨",
                "প, পবিত্রতা। পবিত্রতা ঈমানের অর্ধেক।",
                "Pa, Pobitrota (Purity). Purity is half of faith.",
                "ب، الطهارة. الطهارة شطر الإيمان."),
            BanglaLetter("ফ", "ফজর", "🌅",
                "ফ, ফজর। ফজরের নামাজ খুব গুরুত্বপূর্ণ।",
                "Fa, Fajr. Fajr prayer is very important.",
                "ف، الفجر. صلاة الفجر مهمة جدًا."),
            BanglaLetter("ব", "বরকত", "🌟",
                "ব, বরকত। বিসমিল্লাহ বললে বরকত হয়।",
                "Ba, Barakah. Saying Bismillah brings blessing.",
                "ب، البركة. البسملة تجلب البركة."),
            BanglaLetter("ভ", "ভালোবাসা", "❤️",
                "ভ, ভালোবাসা। আল্লাহর জন্য ভালোবাসা ইবাদত।",
                "Bha, Bhalobasha (Love). Love for Allah is worship.",
                "ب، الحب. الحب في الله عبادة."),
            BanglaLetter("ম", "মসজিদ", "🕌",
                "ম, মসজিদ। মসজিদ আল্লাহর ঘর।",
                "Ma, Masjid. Masjid is the house of Allah.",
                "م، المسجد. المسجد بيت الله."),
            BanglaLetter("য", "যাকাত", "💰",
                "য, যাকাত। যাকাত ইসলামের পাঁচ স্তম্ভের একটি।",
                "Za, Zakat. Zakat is one of the five pillars.",
                "ز، الزكاة. الزكاة أحد أركان الإسلام الخمسة."),
            BanglaLetter("র", "রমজান", "🌙",
                "র, রমজান। রমজানে রোজা রাখতে হয়।",
                "Ra, Ramadan. Fast during Ramadan.",
                "ر، رمضان. الصيام في رمضان."),
            BanglaLetter("ল", "লা ইলাহা ইল্লাল্লাহ", "☝️",
                "ল, লা ইলাহা ইল্লাল্লাহ। এটি তাওহিদের ঘোষণা।",
                "La, La ilaha illallah. It is the declaration of Tawhid.",
                "ل، لا إله إلا الله. كلمة التوحيد."),
            BanglaLetter("শ", "শুকরিয়া", "🙏",
                "শ, শুকরিয়া। আল্লাহর নিয়ামতের জন্য শুকরিয়া।",
                "Sha, Shukriya (Thanks). Thank Allah for His blessings.",
                "ش، الشكر. اشكر الله على نعمه."),
            BanglaLetter("স", "সাদাকা", "🤲",
                "স, সাদাকা। সাদাকা গুনাহ মিটায়।",
                "Sa, Sadaqah. Sadaqah removes sins.",
                "ص، الصدقة. الصدقة تكفر الذنوب."),
            BanglaLetter("হ", "হজ", "🕋",
                "হ, হজ। সামর্থ্যবানদের হজ করা ফরজ।",
                "Ha, Hajj. Hajj is obligatory for those who can afford it.",
                "ح، الحج. الحج فرض على المستطيع.")
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

    LaunchedEffect(currentIndex, language) {
        TtsHelper.speakBangla(current.sentenceBn)

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
                            text = AppStrings.banglaLetters(language),
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
                    BanglaLetterFlipCard(
                        letter = current.letter,
                        bounce = letterBounce.value,
                        flip = letterFlip.value,
                        onClick = { TtsHelper.speakBangla(current.sentenceBn) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Audio Button with pulse =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_AUDIO) {
                    AudioButton(onClick = { TtsHelper.speakBangla(current.sentenceBn) })
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
                        label = "banglaWord"
                    ) { idx ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF4CAF50).copy(alpha = 0.15f))
                                .padding(horizontal = 24.dp, vertical = 14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = letters[idx].emoji, fontSize = 36.sp)
                                Spacer(modifier = Modifier.width(14.dp))
                                Text(
                                    text = letters[idx].word,
                                    fontSize = 24.sp,
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
                        label = "banglaSentence"
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

            // ===== Progress Dots (smooth grow) =====
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
// ===== Bangla Letter Flip Card =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BanglaLetterFlipCard(
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
        label = "banglaCardPress"
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
            fontSize = 140.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37)
        )
    }
}

// ============================================================
// ===== Audio Button (with pulse ring) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AudioButton(onClick: () -> Unit) {
    val infinite = rememberInfiniteTransition(label = "banglaAudioPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "banglaAudioPulseScale"
    )
    val pulseAlpha by infinite.animateFloat(
        initialValue = 0.4f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "banglaAudioPulseAlpha"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "banglaAudioPress"
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
        label = "banglaDotSize"
    )
    val color by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.3f,
        animationSpec = tween(300),
        label = "banglaDotAlpha"
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
        label = "banglaNavButtonScale"
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