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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry

// সূরার ডেটা মডেল
data class SurahVerse(
    val number: Int,
    val arabic: String,
    val pronunciation: String,
    val meaning: String
)

data class Surah(
    val id: Int,
    val number: String,
    val name: String,
    val arabicName: String,
    val meaning: String,
    val totalVerses: Int,
    val color: Color,
    val verses: List<SurahVerse>
)

// সব সূরার ডেটা
object SurahData {
    val allSurahs = listOf(
        Surah(
            id = 1,
            number = "১",
            name = "সূরা আল-ফাতিহা",
            arabicName = "الْفَاتِحَة",
            meaning = "সূচনা",
            totalVerses = 7,
            color = Color(0xFF4CAF50),
            verses = listOf(
                SurahVerse(
                    1,
                    "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                    "বিসমিল্লাহির রাহমানির রাহিম",
                    "পরম করুণাময় ও অসীম দয়ালু আল্লাহর নামে"
                ),
                SurahVerse(
                    2,
                    "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                    "আলহামদুলিল্লাহি রব্বিল আলামিন",
                    "সকল প্রশংসা আল্লাহর, যিনি সমস্ত জগতের রব"
                ),
                SurahVerse(
                    3,
                    "الرَّحْمَٰنِ الرَّحِيمِ",
                    "আর-রাহমানির রাহিম",
                    "পরম করুণাময় ও অসীম দয়ালু"
                ),
                SurahVerse(
                    4,
                    "مَالِكِ يَوْمِ الدِّينِ",
                    "মালিকি ইয়াওমিদ্দিন",
                    "বিচার দিনের মালিক"
                ),
                SurahVerse(
                    5,
                    "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
                    "ইয়্যাকা না'বুদু ওয়া ইয়্যাকা নাসতাঈন",
                    "আমরা শুধু আপনার ইবাদত করি এবং শুধু আপনারই সাহায্য চাই"
                ),
                SurahVerse(
                    6,
                    "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
                    "ইহদিনাস সিরাতাল মুস্তাকিম",
                    "আমাদের সরল পথ দেখান"
                ),
                SurahVerse(
                    7,
                    "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
                    "সিরাতাল্লাজিনা আন'আমতা আলাইহিম, গাইরিল মাগদুবি আলাইহিম ওয়ালাদ্দোয়াল্লিন",
                    "তাদের পথ, যাদের প্রতি আপনি অনুগ্রহ করেছেন; তাদের পথ নয় যারা অভিশপ্ত এবং যারা পথভ্রষ্ট"
                )
            )
        ),
        Surah(
            id = 2,
            number = "১১২",
            name = "সূরা আল-ইখলাস",
            arabicName = "الْإِخْلَاص",
            meaning = "একনিষ্ঠতা",
            totalVerses = 4,
            color = Color(0xFF2196F3),
            verses = listOf(
                SurahVerse(
                    1,
                    "قُلْ هُوَ اللَّهُ أَحَدٌ",
                    "কুল হুওয়াল্লাহু আহাদ",
                    "বলুন, তিনি আল্লাহ, এক ও অদ্বিতীয়"
                ),
                SurahVerse(
                    2,
                    "اللَّهُ الصَّمَدُ",
                    "আল্লাহুস সমাদ",
                    "আল্লাহ অমুখাপেক্ষী"
                ),
                SurahVerse(
                    3,
                    "لَمْ يَلِدْ وَلَمْ يُولَدْ",
                    "লাম ইয়ালিদ ওয়া লাম ইউলাদ",
                    "তিনি কাউকে জন্ম দেননি এবং কেউ তাকে জন্ম দেয়নি"
                ),
                SurahVerse(
                    4,
                    "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
                    "ওয়া লাম ইয়াকুল্লাহু কুফুওয়ান আহাদ",
                    "এবং তার সমতুল্য কেউ নেই"
                )
            )
        ),
        Surah(
            id = 3,
            number = "১১৩",
            name = "সূরা আল-ফালাক",
            arabicName = "الْفَلَق",
            meaning = "প্রভাত",
            totalVerses = 5,
            color = Color(0xFF9C27B0),
            verses = listOf(
                SurahVerse(
                    1,
                    "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ",
                    "কুল আউজু বিরব্বিল ফালাক",
                    "বলুন, আমি আশ্রয় চাই প্রভাতের রবের কাছে"
                ),
                SurahVerse(
                    2,
                    "مِن شَرِّ مَا خَلَقَ",
                    "মিন শাররি মা খালাক",
                    "তিনি যা সৃষ্টি করেছেন তার অনিষ্ট থেকে"
                ),
                SurahVerse(
                    3,
                    "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ",
                    "ওয়া মিন শাররি গাসিকিন ইজা ওয়াকাব",
                    "এবং অন্ধকার রাতের অনিষ্ট থেকে, যখন তা ছেয়ে যায়"
                ),
                SurahVerse(
                    4,
                    "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ",
                    "ওয়া মিন শাররিন নাফফাসাতি ফিল উকাদ",
                    "এবং গ্রন্থিতে ফুঁ দিয়ে জাদুকারীদের অনিষ্ট থেকে"
                ),
                SurahVerse(
                    5,
                    "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ",
                    "ওয়া মিন শাররি হাসিদিন ইজা হাসাদ",
                    "এবং হিংসুকের অনিষ্ট থেকে, যখন সে হিংসা করে"
                )
            )
        ),
        Surah(
            id = 4,
            number = "১১৪",
            name = "সূরা আন-নাস",
            arabicName = "النَّاس",
            meaning = "মানবজাতি",
            totalVerses = 6,
            color = Color(0xFFFF9800),
            verses = listOf(
                SurahVerse(
                    1,
                    "قُلْ أَعُوذُ بِرَبِّ النَّاسِ",
                    "কুল আউজু বিরব্বিন নাস",
                    "বলুন, আমি আশ্রয় চাই মানুষের রবের কাছে"
                ),
                SurahVerse(
                    2,
                    "مَلِكِ النَّاسِ",
                    "মালিকিন নাস",
                    "মানুষের অধিপতির কাছে"
                ),
                SurahVerse(
                    3,
                    "إِلَٰهِ النَّاسِ",
                    "ইলাহিন নাস",
                    "মানুষের উপাস্যের কাছে"
                ),
                SurahVerse(
                    4,
                    "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ",
                    "মিন শাররিল ওয়াসওয়াসিল খান্নাস",
                    "কুমন্ত্রণাদাতার অনিষ্ট থেকে, যে পিছিয়ে যায়"
                ),
                SurahVerse(
                    5,
                    "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ",
                    "আল্লাজি ইউওয়াসবিসু ফি সুদুরিন নাস",
                    "যে মানুষের অন্তরে কুমন্ত্রণা দেয়"
                ),
                SurahVerse(
                    6,
                    "مِنَ الْجِنَّةِ وَالنَّاسِ",
                    "মিনাল জিন্নাতি ওয়ান নাস",
                    "জিন ও মানুষের মধ্য থেকে"
                )
            )
        )
    )
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 70

@Composable
fun SurahsScreen(
    onBackClick: () -> Unit,
    onSurahClick: (Int) -> Unit
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
                            text = AppStrings.smallSurahs(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.surahsCount(language, SurahData.allSurahs.size),
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
                    items = SurahData.allSurahs,
                    key = { _, surah -> surah.id }
                ) { index, surah ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                    ) {
                        SurahCard(
                            surah = surah,
                            language = language,
                            onClick = { onSurahClick(surah.id) }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Surah Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurahCard(
    surah: Surah,
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
        label = "surahCardScale"
    )
    val numberScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "surahNumberScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "surahChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = surah.color),
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // নম্বর সার্কেল
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(numberScale)
                .clip(CircleShape)
                .background(surah.color.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = surah.number,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = surah.color
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        // সূরার তথ্য
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = surah.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = AppStrings.surahMeaningWithVerses(language, surah.meaning, surah.totalVerses),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = surah.arabicName,
                fontSize = 18.sp,
                color = Color(0xFFD4AF37)
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