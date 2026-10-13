package com.pavruls.noorpath.presentation.screens.kids

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.pavruls.noorpath.presentation.components.StaggeredEntry

data class Dua(
    val id: Int,
    val emoji: String,
    val titleBn: String,
    val titleEn: String,
    val titleAr: String,
    val arabic: String,
    val pronunciationBn: String,
    val pronunciationEn: String,
    val pronunciationAr: String,
    val meaningBn: String,
    val meaningEn: String,
    val meaningAr: String,
    val color: Color
)

object DuaData {
    val allDuas = listOf(
        Dua(
            id = 1, emoji = "🍽️",
            titleBn = "খাওয়ার আগে", titleEn = "Before Eating", titleAr = "قبل الطعام",
            arabic = "بِسْمِ اللَّهِ",
            pronunciationBn = "বিসমিল্লাহ", pronunciationEn = "Bismillah", pronunciationAr = "بسم الله",
            meaningBn = "আল্লাহর নামে (শুরু করছি)",
            meaningEn = "In the name of Allah",
            meaningAr = "باسم الله",
            color = Color(0xFF4CAF50)
        ),
        Dua(
            id = 2, emoji = "🙌",
            titleBn = "খাওয়ার পরে", titleEn = "After Eating", titleAr = "بعد الطعام",
            arabic = "الْحَمْدُ لِلَّهِ",
            pronunciationBn = "আলহামদুলিল্লাহ", pronunciationEn = "Alhamdulillah", pronunciationAr = "الحمد لله",
            meaningBn = "সকল প্রশংসা আল্লাহর",
            meaningEn = "All praise is for Allah",
            meaningAr = "الحمد كله لله",
            color = Color(0xFF8BC34A)
        ),
        Dua(
            id = 3, emoji = "🌙",
            titleBn = "ঘুমানোর আগে", titleEn = "Before Sleeping", titleAr = "قبل النوم",
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            pronunciationBn = "বিসমিকাল্লাহুম্মা আমুতু ওয়া আহইয়া",
            pronunciationEn = "Bismika Allahumma amutu wa ahya",
            pronunciationAr = "باسمك اللهم أموت وأحيا",
            meaningBn = "হে আল্লাহ! আপনার নামেই আমি মরি ও বাঁচি",
            meaningEn = "O Allah! In Your name I die and live",
            meaningAr = "اللهم باسمك أموت وأحيا",
            color = Color(0xFF2196F3)
        ),
        Dua(
            id = 4, emoji = "☀️",
            titleBn = "ঘুম থেকে উঠে", titleEn = "After Waking Up", titleAr = "عند الاستيقاظ",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا",
            pronunciationBn = "আলহামদুলিল্লাহিল্লাজি আহইয়ানা বা'দা মা আমাতানা",
            pronunciationEn = "Alhamdulillahillazi ahyana ba'da ma amatana",
            pronunciationAr = "الحمد لله الذي أحيانا بعد ما أماتنا",
            meaningBn = "সকল প্রশংসা আল্লাহর, যিনি মৃত্যুর পর আমাদের জীবিত করেছেন",
            meaningEn = "All praise to Allah who gave us life after death",
            meaningAr = "الحمد لله الذي أحيانا بعد ما أماتنا",
            color = Color(0xFFFFC107)
        ),
        Dua(
            id = 5, emoji = "🚽",
            titleBn = "বাথরুমে যাওয়ার আগে", titleEn = "Before Entering Bathroom", titleAr = "قبل دخول الخلاء",
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَائِثِ",
            pronunciationBn = "আল্লাহুম্মা ইন্নি আউজুবিকা মিনাল খুবসি ওয়াল খাবাইস",
            pronunciationEn = "Allahumma inni a'uzubika minal khubsi wal khaba'is",
            pronunciationAr = "اللهم إني أعوذ بك من الخبث والخبائث",
            meaningBn = "হে আল্লাহ! আমি আপনার কাছে অপবিত্র পুরুষ ও নারী শয়তান থেকে আশ্রয় চাই",
            meaningEn = "O Allah! I seek refuge from male and female devils",
            meaningAr = "اللهم إني أعوذ بك من الخبث والخبائث",
            color = Color(0xFF9C27B0)
        ),
        Dua(
            id = 6, emoji = "🚪",
            titleBn = "বাথরুম থেকে বেরিয়ে", titleEn = "After Leaving Bathroom", titleAr = "بعد الخروج من الخلاء",
            arabic = "غُفْرَانَكَ",
            pronunciationBn = "গুফরানাকা", pronunciationEn = "Gufranaka", pronunciationAr = "غفرانك",
            meaningBn = "হে আল্লাহ! আমি আপনার ক্ষমা প্রার্থনা করি",
            meaningEn = "O Allah! I seek Your forgiveness",
            meaningAr = "اللهم إني أطلب مغفرتك",
            color = Color(0xFF673AB7)
        ),
        Dua(
            id = 7, emoji = "👨‍👩‍👧",
            titleBn = "মা-বাবার জন্য", titleEn = "For Parents", titleAr = "للوالدين",
            arabic = "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
            pronunciationBn = "রব্বির হামহুমা কামা রব্বায়ানি সগিরা",
            pronunciationEn = "Rabbir hamhuma kama rabbayani sagira",
            pronunciationAr = "رب ارحمهما كما ربياني صغيرا",
            meaningBn = "হে আমার রব! তাদের প্রতি দয়া করুন, যেভাবে তারা শৈশবে আমাকে লালন-পালন করেছেন",
            meaningEn = "My Lord! Have mercy on them as they raised me in childhood",
            meaningAr = "رب ارحمهما كما ربياني صغيرا",
            color = Color(0xFFE91E63)
        ),
        Dua(
            id = 8, emoji = "📚",
            titleBn = "জ্ঞান চাওয়ার দুআ", titleEn = "Seeking Knowledge", titleAr = "طلب العلم",
            arabic = "رَبِّ زِدْنِي عِلْمًا",
            pronunciationBn = "রব্বি যিদনি ইলমা", pronunciationEn = "Rabbi zidni ilma", pronunciationAr = "رب زدني علما",
            meaningBn = "হে আমার রব! আমার জ্ঞান বাড়িয়ে দিন",
            meaningEn = "My Lord! Increase me in knowledge",
            meaningAr = "رب زدني علما",
            color = Color(0xFF00BCD4)
        ),
        Dua(
            id = 9, emoji = "🚗",
            titleBn = "সফরের দুআ", titleEn = "Travel Dua", titleAr = "دعاء السفر",
            arabic = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا",
            pronunciationBn = "সুবহানাল্লাজি সাখখারা লানা হাজা",
            pronunciationEn = "Subhanallazi sakhkhara lana haza",
            pronunciationAr = "سبحان الذي سخر لنا هذا",
            meaningBn = "পবিত্র সেই সত্তা, যিনি এটি আমাদের বশীভূত করেছেন",
            meaningEn = "Glory to Him who has subjected this to us",
            meaningAr = "سبحان الذي سخر لنا هذا",
            color = Color(0xFFFF9800)
        ),
        Dua(
            id = 10, emoji = "🛡️",
            titleBn = "বিপদে পড়লে", titleEn = "In Trouble", titleAr = "عند الشدة",
            arabic = "لَا إِلَهَ إِلَّا اللَّهُ",
            pronunciationBn = "লা ইলাহা ইল্লাল্লাহ",
            pronunciationEn = "La ilaha illallah",
            pronunciationAr = "لا إله إلا الله",
            meaningBn = "আল্লাহ ছাড়া কোনো ইলাহ নেই",
            meaningEn = "There is no god but Allah",
            meaningAr = "لا إله إلا الله",
            color = Color(0xFFF44336)
        )
    )

    fun getTitle(dua: Dua, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> dua.titleEn
        LanguageManager.LANG_AR -> dua.titleAr
        else -> dua.titleBn
    }

    fun getPronunciation(dua: Dua, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> dua.pronunciationEn
        LanguageManager.LANG_AR -> dua.pronunciationAr
        else -> dua.pronunciationBn
    }

    fun getMeaning(dua: Dua, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> dua.meaningEn
        LanguageManager.LANG_AR -> dua.meaningAr
        else -> dua.meaningBn
    }
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 60

@Composable
fun DuasScreen(
    onBackClick: () -> Unit,
    onDuaClick: (Int) -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

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

                    Spacer(modifier = Modifier.size(4.dp))

                    Column {
                        Text(
                            text = AppStrings.duasTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.duasCount(language, DuaData.allDuas.size),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== List with staggered items =====
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = DuaData.allDuas,
                    key = { _, dua -> dua.id }
                ) { index, dua ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                    ) {
                        DuaCard(
                            dua = dua,
                            language = language,
                            onClick = { onDuaClick(dua.id) }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Dua Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DuaCard(
    dua: Dua,
    language: String,
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
        label = "duaCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "duaIconScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "duaChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = dua.color),
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(iconScale)
                .clip(CircleShape)
                .background(dua.color.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = dua.emoji, fontSize = 26.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = DuaData.getTitle(dua, language),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dua.arabic,
                fontSize = 15.sp,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Start
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = AppStrings.openLabel(language),
            tint = Color.White.copy(alpha = chevronAlpha),
            modifier = Modifier.size(28.dp)
        )
    }
}