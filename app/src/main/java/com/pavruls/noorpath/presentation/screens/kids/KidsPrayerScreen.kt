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

data class PrayerTopic(
    val id: Int,
    val emoji: String,
    val titleBn: String,
    val titleEn: String,
    val titleAr: String,
    val subtitleBn: String,
    val subtitleEn: String,
    val subtitleAr: String,
    val color: Color
)

data class PrayerStep(
    val stepNumber: Int,
    val title: String,
    val arabic: String,
    val pronunciation: String,
    val meaning: String
)

object PrayerData {
    val allTopics = listOf(
        PrayerTopic(
            id = 1, emoji = "💧",
            titleBn = "ওজু করার নিয়ম", titleEn = "Rules of Wudu", titleAr = "أحكام الوضوء",
            subtitleBn = "৯টি ধাপ", subtitleEn = "9 steps", subtitleAr = "٩ خطوات",
            color = Color(0xFF2196F3)
        ),
        PrayerTopic(
            id = 2, emoji = "🌅",
            titleBn = "ফজরের নামাজ", titleEn = "Fajr Prayer", titleAr = "صلاة الفجر",
            subtitleBn = "২ রাকাত ফরজ", subtitleEn = "2 Rakats Fard", subtitleAr = "ركعتان فرض",
            color = Color(0xFFFF9800)
        ),
        PrayerTopic(
            id = 3, emoji = "☀️",
            titleBn = "যোহরের নামাজ", titleEn = "Dhuhr Prayer", titleAr = "صلاة الظهر",
            subtitleBn = "৪ রাকাত ফরজ", subtitleEn = "4 Rakats Fard", subtitleAr = "٤ ركعات فرض",
            color = Color(0xFFFFC107)
        ),
        PrayerTopic(
            id = 4, emoji = "🌇",
            titleBn = "আসরের নামাজ", titleEn = "Asr Prayer", titleAr = "صلاة العصر",
            subtitleBn = "৪ রাকাত ফরজ", subtitleEn = "4 Rakats Fard", subtitleAr = "٤ ركعات فرض",
            color = Color(0xFFFF5722)
        ),
        PrayerTopic(
            id = 5, emoji = "🌆",
            titleBn = "মাগরিবের নামাজ", titleEn = "Maghrib Prayer", titleAr = "صلاة المغرب",
            subtitleBn = "৩ রাকাত ফরজ", subtitleEn = "3 Rakats Fard", subtitleAr = "٣ ركعات فرض",
            color = Color(0xFF9C27B0)
        ),
        PrayerTopic(
            id = 6, emoji = "🌙",
            titleBn = "ইশার নামাজ", titleEn = "Isha Prayer", titleAr = "صلاة العشاء",
            subtitleBn = "৪ রাকাত ফরজ", subtitleEn = "4 Rakats Fard", subtitleAr = "٤ ركعات فرض",
            color = Color(0xFF3F51B5)
        ),
        PrayerTopic(
            id = 7, emoji = "🕌",
            titleBn = "নামাজের ধাপ", titleEn = "Steps of Prayer", titleAr = "خطوات الصلاة",
            subtitleBn = "শুরু থেকে শেষ", subtitleEn = "Start to end", subtitleAr = "من البداية للنهاية",
            color = Color(0xFF4CAF50)
        )
    )

    val wuduSteps = listOf(
        PrayerStep(1, "নিয়ত ও বিসমিল্লাহ", "بِسْمِ اللَّهِ", "বিসমিল্লাহ", "আল্লাহর নামে শুরু করছি"),
        PrayerStep(2, "দুই হাত কব্জি পর্যন্ত ধোয়া", "—", "—", "৩ বার ডান, ৩ বার বাম হাত ধোয়া"),
        PrayerStep(3, "কুলি করা", "—", "—", "৩ বার কুলি করা"),
        PrayerStep(4, "নাক পরিষ্কার করা", "—", "—", "৩ বার নাকে পানি দেওয়া ও পরিষ্কার করা"),
        PrayerStep(5, "মুখ ধোয়া", "—", "—", "৩ বার মুখ ধোয়া"),
        PrayerStep(6, "হাত কনুই পর্যন্ত ধোয়া", "—", "—", "৩ বার ডান, ৩ বার বাম"),
        PrayerStep(7, "মাথা মাসেহ করা", "—", "—", "একবার ভেজা হাতে মাথা মাসেহ"),
        PrayerStep(8, "কান মাসেহ করা", "—", "—", "দুই কান মাসেহ করা"),
        PrayerStep(9, "পা টাখনু পর্যন্ত ধোয়া", "—", "—", "৩ বার ডান, ৩ বার বাম")
    )

    val namazSteps = listOf(
        PrayerStep(1, "নিয়ত করা", "—", "—", "মনে মনে ইবাদতের নিয়ত করা"),
        PrayerStep(2, "তাকবিরে তাহরিমা", "اللَّهُ أَكْبَرُ", "আল্লাহু আকবার", "আল্লাহ সবচেয়ে বড়"),
        PrayerStep(3, "সানা পড়া", "سُبْحَانَكَ اللَّهُمَّ", "সুবহানাকাল্লাহুম্মা", "হে আল্লাহ! আপনার প্রশংসা"),
        PrayerStep(4, "সূরা আল-ফাতিহা পড়া", "—", "—", "প্রতিটি রাকাতে পড়তে হয়"),
        PrayerStep(5, "রুকু করা", "سُبْحَانَ رَبِّيَ الْعَظِيمِ", "সুবহানা রব্বিয়াল আযিম", "পবিত্র আমার মহান রব"),
        PrayerStep(6, "সিজদা করা", "سُبْحَانَ رَبِّيَ الْأَعْلَى", "সুবহানা রব্বিয়াল আ'লা", "পবিত্র আমার মহান রব"),
        PrayerStep(7, "তাশাহহুদ পড়া", "التَّحِيَّاتُ لِلَّهِ", "আত্তাহিয়্যাতু লিল্লাহ", "সকল মৌখিক ইবাদত আল্লাহর"),
        PrayerStep(8, "দরুদ পড়া", "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ", "আল্লাহুম্মা সাল্লি আলা মুহাম্মাদ", "হে আল্লাহ! মুহাম্মদের উপর দরুদ পাঠান"),
        PrayerStep(9, "সালাম ফিরানো", "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ", "আসসালামু আলাইকুম ওয়া রাহমাতুল্লাহ", "আপনাদের উপর শান্তি বর্ষিত হোক")
    )

    fun getStepsForTopic(topicId: Int): List<PrayerStep> {
        return when (topicId) {
            1 -> wuduSteps
            2, 3, 4, 5, 6 -> namazSteps
            7 -> namazSteps
            else -> emptyList()
        }
    }

    fun getTopicTitle(topic: PrayerTopic, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> topic.titleEn
        LanguageManager.LANG_AR -> topic.titleAr
        else -> topic.titleBn
    }

    fun getTopicSubtitle(topic: PrayerTopic, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> topic.subtitleEn
        LanguageManager.LANG_AR -> topic.subtitleAr
        else -> topic.subtitleBn
    }
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 60

@Composable
fun KidsPrayerScreen(
    onBackClick: () -> Unit,
    onTopicClick: (Int) -> Unit
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
                            text = AppStrings.kidsPrayerTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.kidsPrayerSubtitle(language),
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
                    items = PrayerData.allTopics,
                    key = { _, topic -> topic.id }
                ) { index, topic ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                    ) {
                        PrayerTopicCard(
                            topic = topic,
                            language = language,
                            onClick = { onTopicClick(topic.id) }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Prayer Topic Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrayerTopicCard(
    topic: PrayerTopic,
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
        label = "prayerTopicCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "prayerTopicIconScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "prayerTopicChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = topic.color),
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
                .background(topic.color.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = topic.emoji, fontSize = 26.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = PrayerData.getTopicTitle(topic, language),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = PrayerData.getTopicSubtitle(topic, language),
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.6f)
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