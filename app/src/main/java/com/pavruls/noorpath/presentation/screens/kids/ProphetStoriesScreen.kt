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

data class ProphetStory(
    val id: Int,
    val emoji: String,
    val nameBengali: String,
    val nameEnglish: String,
    val nameArabic: String,
    val shortDescBn: String,
    val shortDescEn: String,
    val shortDescAr: String,
    val color: Color,
    val storyBengali: String,
    val storyEnglish: String,
    val storyArabic: String
)

object ProphetStoriesData {
    val allStories = listOf(
        ProphetStory(
            id = 1,
            emoji = "🌍",
            nameBengali = "হযরত আদম (আ.)",
            nameEnglish = "Prophet Adam (AS)",
            nameArabic = "النبي آدم عليه السلام",
            shortDescBn = "প্রথম মানুষ ও প্রথম নবী",
            shortDescEn = "First human & first prophet",
            shortDescAr = "أول إنسان وأول نبي",
            color = Color(0xFF4CAF50),
            storyBengali = """
                আল্লাহ তাআলা মাটি থেকে হযরত আদম (আ.)-কে সৃষ্টি করলেন। 
                তিনি ছিলেন প্রথম মানুষ এবং প্রথম নবী।
                
                আল্লাহ তাকে জান্নাতে রাখলেন। সেখানে তিনি একা ছিলেন। 
                তখন আল্লাহ তার বাম পাঁজর থেকে হাওয়া (আ.)-কে সৃষ্টি করলেন। 
                তারা দুজনে জান্নাতে সুখে থাকতেন।
                
                কিন্তু শয়তান তাদের ধোঁকা দিল। তারা নিষিদ্ধ গাছের ফল খেয়ে ফেললেন। 
                আল্লাহ তাদের ক্ষমা করলেন এবং পৃথিবীতে পাঠালেন।
                
                হযরত আদম (আ.) পৃথিবীতে আল্লাহর ইবাদত করতেন এবং 
                সন্তানদের ইসলামের পথ দেখাতেন।
            """.trimIndent(),
            storyEnglish = """
                Allah created Prophet Adam (AS) from clay. 
                He was the first human and the first prophet.
                
                Allah placed him in Paradise. He was alone there. 
                Then Allah created Hawwa (Eve) from his rib. 
                They lived happily in Paradise.
                
                But Satan deceived them. They ate from the forbidden tree. 
                Allah forgave them and sent them to Earth.
                
                Prophet Adam (AS) worshipped Allah on Earth and 
                guided his children to the path of Islam.
            """.trimIndent(),
            storyArabic = """
                خلق الله النبي آدم عليه السلام من طين.
                كان أول إنسان وأول نبي.
                
                أسكنه الله الجنة، وكان فيها وحده.
                ثم خلق الله حواء من ضلعه.
                فعاشا في الجنة بسعادة.
                
                لكن الشيطان خدعهما، فأكلا من الشجرة المحرمة.
                فغفر الله لهما وأنزلهما إلى الأرض.
                
                وعبد النبي آدم ربه في الأرض،
                وأرشد أبناءه إلى طريق الإسلام.
            """.trimIndent()
        ),
        ProphetStory(
            id = 2,
            emoji = "🚢",
            nameBengali = "হযরত নূহ (আ.)",
            nameEnglish = "Prophet Nuh (AS)",
            nameArabic = "النبي نوح عليه السلام",
            shortDescBn = "মহাপ্লাবনের নবী",
            shortDescEn = "Prophet of the great flood",
            shortDescAr = "نبي الطوفان العظيم",
            color = Color(0xFF2196F3),
            storyBengali = """
                হযরত নূহ (আ.) আল্লাহর একজন মহান নবী ছিলেন। 
                তিনি ৯৫০ বছর ধরে মানুষকে আল্লাহর ইবাদতের দাওয়াত দিয়েছিলেন।
                
                কিন্তু তার জাতি তাকে মিথ্যা বলল এবং ইবাদত করল না।
                
                আল্লাহ তাকে একটি বিশাল নৌকা তৈরি করার নির্দেশ দিলেন। 
                নূহ (আ.) অনেক কষ্টে নৌকা তৈরি করলেন।
                
                তারপর মহাপ্লাবন শুরু হলো। সবকিছু ডুবে গেল। 
                শুধু যারা ঈমান এনেছিল এবং নৌকায় উঠেছিল, তারা বেঁচে গেল।
                
                নূহ (আ.)-এর নৌকা জুদি পাহাড়ে থেমে গেল। 
                তারপর তারা নতুন করে জীবন শুরু করল।
            """.trimIndent(),
            storyEnglish = """
                Prophet Nuh (AS) was a great prophet of Allah. 
                He invited his people to worship Allah for 950 years.
                
                But his nation denied him and did not worship Allah.
                
                Allah ordered him to build a huge ark (ship). 
                Nuh (AS) built the ark with great difficulty.
                
                Then the great flood began. Everything drowned. 
                Only those who believed and boarded the ark were saved.
                
                The ark of Nuh (AS) stopped at Mount Judi. 
                Then they started a new life.
            """.trimIndent(),
            storyArabic = """
                كان النبي نوح عليه السلام نبيًا عظيمًا من أنبياء الله.
                دعا قومه إلى عبادة الله تسعمائة وخمسين سنة.
                
                لكن قومه كذبوه ولم يعبدوا الله.
                
                فأمره الله أن يصنع سفينة عظيمة.
                فصنعها نوح عليه السلام بمشقة شديدة.
                
                ثم بدأ الطوفان العظيم، وغرق كل شيء.
                ولم ينجُ إلا الذين آمنوا وركبوا السفينة.
                
                واستوت السفينة على الجودي.
                ثم بدأوا حياة جديدة.
            """.trimIndent()
        ),
        ProphetStory(
            id = 3,
            emoji = "🔥",
            nameBengali = "হযরত ইব্রাহিম (আ.)",
            nameEnglish = "Prophet Ibrahim (AS)",
            nameArabic = "النبي إبراهيم عليه السلام",
            shortDescBn = "আল্লাহর বন্ধু",
            shortDescEn = "Friend of Allah",
            shortDescAr = "خليل الله",
            color = Color(0xFFFF9800),
            storyBengali = """
                হযরত ইব্রাহিম (আ.) ছোটবেলা থেকেই আল্লাহকে ভালোবাসতেন। 
                তার জাতি মূর্তি পূজা করত। তিনি তা ঘৃণা করতেন।
                
                একদিন তিনি সব মূর্তি ভেঙে দিলেন। জাতি তাকে আগুনে নিক্ষেপ করল।
                
                আল্লাহ আদেশ দিলেন: "হে আগুন! তুমি ইব্রাহিমের জন্য শান্ত ও নিরাপদ হয়ে যাও।"
                আগুন তাকে কিছুই করতে পারল না।
                
                তিনি আল্লাহর নির্দেশে মক্কায় কাবা ঘর নির্মাণ করলেন। 
                তিনি তার ছেলে হযরত ইসমাইল (আ.)-কে কুরবানি করার স্বপ্ন দেখলেন।
                
                যখন তিনি কুরবানি করতে উদ্যত হলেন, আল্লাহ একটি দুম্বা পাঠালেন। 
                এই ঘটনাটিই ঈদুল আযহার কুরবানি হিসেবে স্মরণ করা হয়।
            """.trimIndent(),
            storyEnglish = """
                Prophet Ibrahim (AS) loved Allah from a young age. 
                His nation worshipped idols. He hated that.
                
                One day he broke all the idols. The nation threw him into fire.
                
                Allah commanded: "O fire! Be cool and safe for Ibrahim."
                The fire could not harm him at all.
                
                He built the Kaaba in Makkah by Allah's command. 
                He dreamed of sacrificing his son Prophet Ismail (AS).
                
                When he was about to sacrifice, Allah sent a ram. 
                This event is remembered as the sacrifice of Eid al-Adha.
            """.trimIndent(),
            storyArabic = """
                أحب النبي إبراهيم عليه السلام الله منذ صغره.
                وكان قومه يعبدون الأصنام، فكره ذلك.
                
                وفي يوم حطم جميع الأصنام، فألقاه القوم في النار.
                
                فأمر الله: "يا نار كوني بردًا وسلامًا على إبراهيم".
                فلم تضره النار بشيء.
                
                وبنى الكعبة في مكة بأمر الله.
                ورأى في المنام أنه يذبح ابنه إسماعيل عليه السلام.
                
                فلما هم بالذبح، أرسل الله كبشًا.
                وتُذكر هذه الحادثة كأضحية عيد الأضحى.
            """.trimIndent()
        ),
        ProphetStory(
            id = 4,
            emoji = "🌊",
            nameBengali = "হযরত মূসা (আ.)",
            nameEnglish = "Prophet Musa (AS)",
            nameArabic = "النبي موسى عليه السلام",
            shortDescBn = "সাগর বিভক্তকারী নবী",
            shortDescEn = "Prophet who split the sea",
            shortDescAr = "نبي فلق البحر",
            color = Color(0xFF9C27B0),
            storyBengali = """
                হযরত মূসা (আ.)-কে আল্লাহ ফিরাউনের হাত থেকে বাঁচাতে 
                একটি ঝুড়িতে করে নদীতে ভাসিয়ে দিলেন।
                
                ফিরাউনের স্ত্রী তাকে লালন-পালন করলেন। 
                বড় হয়ে তিনি নবী হলেন এবং ফিরাউনকে আল্লাহর পথে ডাকলেন।
                
                ফিরাউন অহংকার করে বলল: "আমিই তোমাদের প্রভু!"
                আল্লাহ মূসা (আ.)-কে সাগর বিভক্ত করার মুজিজা দিলেন।
                
                মূসা (আ.) তার লাঠি দিয়ে সাগরে আঘাত করলেন। সাগর দুভাগ হয়ে গেল।
                বনী ইসরাইল নিরাপদে পার হলো। ফিরাউন তার সেনাবাহিনীসহ ডুবে মরল।
                
                আল্লাহ তাকে তাওরাত কিতাব দান করলেন।
            """.trimIndent(),
            storyEnglish = """
                Allah saved Prophet Musa (AS) from Pharaoh by 
                placing him in a basket on the river.
                
                Pharaoh's wife raised him. 
                He grew up, became a prophet, and called Pharaoh to Allah.
                
                Pharaoh arrogantly said: "I am your lord!"
                Allah gave Musa (AS) the miracle to split the sea.
                
                Musa (AS) struck the sea with his staff. The sea split into two.
                The Children of Israel crossed safely. Pharaoh and his army drowned.
                
                Allah gave him the Torah (Tawrat).
            """.trimIndent(),
            storyArabic = """
                أنجى الله النبي موسى عليه السلام من فرعون
                بأن ألقاه في اليم في تابوت.
                
                وربته امرأة فرعون.
                ولما كبر أصبح نبيًا، ودعا فرعون إلى الله.
                
                فقال فرعون متكبرًا: "أنا ربكم الأعلى!"
                فأعطى الله موسى معجزة فلق البحر.
                
                فضرب موسى البحر بعصاه فانفلق.
                ونجا بنو إسرائيل، وغرق فرعون وجنوده.
                
                وأعطاه الله التوراة.
            """.trimIndent()
        ),
        ProphetStory(
            id = 5,
            emoji = "✨",
            nameBengali = "হযরত ঈসা (আ.)",
            nameEnglish = "Prophet Isa (AS)",
            nameArabic = "النبي عيسى عليه السلام",
            shortDescBn = "মুজিজাসম্পন্ন নবী",
            shortDescEn = "Prophet of miracles",
            shortDescAr = "نبي المعجزات",
            color = Color(0xFF00BCD4),
            storyBengali = """
                হযরত ঈসা (আ.) ছিলেন একজন মহান নবী। 
                আল্লাহ তাকে পিতা ছাড়াই মরিয়ম (আ.)-এর গর্ভে জন্ম দিলেন।
                
                তিনি শিশু অবস্থায় কথা বললেন: "আমি আল্লাহর বান্দা।"
                আল্লাহ তাকে অনেক মুজিজা দান করলেন।
                
                তিনি জন্মান্ধকে দৃষ্টি দিতেন, কুষ্ঠরোগীকে সুস্থ করতেন, 
                এবং আল্লাহর আদেশে মৃতকে জীবিত করতেন।
                
                তিনি ইঞ্জিল কিতাব পেয়েছিলেন। 
                তার জাতি তাকে হত্যা করতে চেয়েছিল, কিন্তু আল্লাহ তাকে 
                আসমানে উঠিয়ে নিলেন।
                
                তিনি কিয়ামতের আগে আবার পৃথিবীতে ফিরে আসবেন।
            """.trimIndent(),
            storyEnglish = """
                Prophet Isa (AS) was a great prophet. 
                Allah caused him to be born to Maryam (AS) without a father.
                
                He spoke as a baby: "I am a servant of Allah."
                Allah gave him many miracles.
                
                He gave sight to the blind, healed the leper, 
                and by Allah's command brought the dead back to life.
                
                He received the Gospel (Injil). 
                His nation tried to kill him, but Allah raised him to the heavens.
                
                He will return to Earth before the Day of Judgment.
            """.trimIndent(),
            storyArabic = """
                كان النبي عيسى عليه السلام نبيًا عظيمًا.
                خلقه الله من غير أب في رحم مريم عليها السلام.
                
                وتكلم في المهد: "إني عبد الله".
                وأعطاه الله معجزات كثيرة.
                
                كان يبرئ الأكمه والأبرص،
                ويحيي الموتى بإذن الله.
                
                وأوتي الإنجيل.
                وأراد قومه قتله، لكن الله رفعه إلى السماء.
                
                وسيعود إلى الأرض قبل يوم القيامة.
            """.trimIndent()
        )
    )

    fun getStoryName(story: ProphetStory, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> story.nameEnglish
        LanguageManager.LANG_AR -> story.nameArabic
        else -> story.nameBengali
    }

    fun getStoryShortDesc(story: ProphetStory, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> story.shortDescEn
        LanguageManager.LANG_AR -> story.shortDescAr
        else -> story.shortDescBn
    }

    fun getStoryText(story: ProphetStory, lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> story.storyEnglish
        LanguageManager.LANG_AR -> story.storyArabic
        else -> story.storyBengali
    }
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 80

@Composable
fun ProphetStoriesScreen(
    onBackClick: () -> Unit,
    onStoryClick: (Int) -> Unit
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
                            text = AppStrings.prophetStoriesTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.prophetStoriesCount(language, ProphetStoriesData.allStories.size),
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
                    items = ProphetStoriesData.allStories,
                    key = { _, story -> story.id }
                ) { index, story ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                    ) {
                        ProphetStoryCard(
                            story = story,
                            language = language,
                            onClick = { onStoryClick(story.id) }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Prophet Story Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProphetStoryCard(
    story: ProphetStory,
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
        label = "storyCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "storyIconScale"
    )
    val chevronAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 0.5f,
        animationSpec = tween(150),
        label = "storyChevronAlpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = story.color),
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
                .background(story.color.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = story.emoji, fontSize = 26.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ProphetStoriesData.getStoryName(story, language),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ProphetStoriesData.getStoryShortDesc(story, language),
                fontSize = 12.sp,
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