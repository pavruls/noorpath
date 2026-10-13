package com.pavruls.noorpath.presentation.screens.kids

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.presentation.viewmodel.QuizViewModel
import kotlinx.coroutines.launch

data class QuizQuestion(
    val questionBn: String,
    val questionEn: String,
    val questionAr: String,
    val optionsBn: List<String>,
    val optionsEn: List<String>,
    val optionsAr: List<String>,
    val correctIndex: Int
) {
    fun getQuestion(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> questionEn
        LanguageManager.LANG_AR -> questionAr
        else -> questionBn
    }

    fun getOptions(lang: String): List<String> = when (lang) {
        LanguageManager.LANG_EN -> optionsEn
        LanguageManager.LANG_AR -> optionsAr
        else -> optionsBn
    }
}

object QuizData {
    val allQuestions = listOf(
        QuizQuestion(
            questionBn = "আরবি হরফ 'ا' এর নাম কী?",
            questionEn = "What is the name of Arabic letter 'ا'?",
            questionAr = "ما اسم الحرف العربي 'ا'؟",
            optionsBn = listOf("বা", "আলিফ", "তা", "ছা"),
            optionsEn = listOf("Ba", "Alif", "Ta", "Tha"),
            optionsAr = listOf("با", "ألف", "تا", "ثا"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "খাওয়ার আগে কোন দুআ পড়তে হয়?",
            questionEn = "Which dua is recited before eating?",
            questionAr = "أي دعاء يقال قبل الطعام؟",
            optionsBn = listOf("আলহামদুলিল্লাহ", "বিসমিল্লাহ", "সুবহানাল্লাহ", "আল্লাহু আকবার"),
            optionsEn = listOf("Alhamdulillah", "Bismillah", "Subhanallah", "Allahu Akbar"),
            optionsAr = listOf("الحمد لله", "بسم الله", "سبحان الله", "الله أكبر"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "সূরা আল-ইখলাসে কতটি আয়াত আছে?",
            questionEn = "How many verses are in Surah Al-Ikhlas?",
            questionAr = "كم عدد آيات سورة الإخلاص؟",
            optionsBn = listOf("৩টি", "৪টি", "৫টি", "৬টি"),
            optionsEn = listOf("3", "4", "5", "6"),
            optionsAr = listOf("٣", "٤", "٥", "٦"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "দিনে কত ওয়াক্ত নামাজ ফরজ?",
            questionEn = "How many obligatory prayers per day?",
            questionAr = "كم عدد الصلوات المفروضة في اليوم؟",
            optionsBn = listOf("৩ ওয়াক্ত", "৪ ওয়াক্ত", "৫ ওয়াক্ত", "৬ ওয়াক্ত"),
            optionsEn = listOf("3 times", "4 times", "5 times", "6 times"),
            optionsAr = listOf("٣ مرات", "٤ مرات", "٥ مرات", "٦ مرات"),
            correctIndex = 2
        ),
        QuizQuestion(
            questionBn = "প্রথম নবী কে ছিলেন?",
            questionEn = "Who was the first prophet?",
            questionAr = "من هو أول نبي؟",
            optionsBn = listOf("হযরত নূহ (আ.)", "হযরত আদম (আ.)", "হযরত ইব্রাহিম (আ.)", "হযরত মূসা (আ.)"),
            optionsEn = listOf("Prophet Nuh (AS)", "Prophet Adam (AS)", "Prophet Ibrahim (AS)", "Prophet Musa (AS)"),
            optionsAr = listOf("نوح عليه السلام", "آدم عليه السلام", "إبراهيم عليه السلام", "موسى عليه السلام"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "আরবি হরফ 'ب' এর উচ্চারণ কী?",
            questionEn = "What is the pronunciation of Arabic letter 'ب'?",
            questionAr = "ما نطق الحرف العربي 'ب'؟",
            optionsBn = listOf("তা", "বা", "জি", "হা"),
            optionsEn = listOf("Ta", "Ba", "Ji", "Ha"),
            optionsAr = listOf("تا", "با", "جيم", "حا"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "নামাজে প্রথমে কী বলতে হয়?",
            questionEn = "What is said first in prayer?",
            questionAr = "ما يقال أولاً في الصلاة؟",
            optionsBn = listOf("আসসালামু আলাইকুম", "আল্লাহু আকবার", "সুবহানাল্লাহ", "আলহামদুলিল্লাহ"),
            optionsEn = listOf("Assalamu Alaikum", "Allahu Akbar", "Subhanallah", "Alhamdulillah"),
            optionsAr = listOf("السلام عليكم", "الله أكبر", "سبحان الله", "الحمد لله"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "নূহ (আ.) এর সাথে জাহাজে কে ছিলেন?",
            questionEn = "Who was on the ark with Nuh (AS)?",
            questionAr = "من كان مع نوح عليه السلام في السفينة؟",
            optionsBn = listOf("শুধু মানুষ", "শুধু পশু", "মুমিন ও পশু-পাখি জোড়া", "শুধু পাখি"),
            optionsEn = listOf("Only humans", "Only animals", "Believers & pairs of animals", "Only birds"),
            optionsAr = listOf("البشر فقط", "الحيوانات فقط", "المؤمنون وأزواج من الحيوانات", "الطيور فقط"),
            correctIndex = 2
        ),
        QuizQuestion(
            questionBn = "কুরআনের প্রথম সূরা কী?",
            questionEn = "What is the first Surah of the Quran?",
            questionAr = "ما أول سورة في القرآن؟",
            optionsBn = listOf("সূরা আল-ইখলাস", "সূরা আল-ফাতিহা", "সূরা আন-নাস", "সূরা আল-ফালাক"),
            optionsEn = listOf("Surah Al-Ikhlas", "Surah Al-Fatiha", "Surah An-Nas", "Surah Al-Falaq"),
            optionsAr = listOf("الإخلاص", "الفاتحة", "الناس", "الفلق"),
            correctIndex = 1
        ),
        QuizQuestion(
            questionBn = "দিনে কতবার ওজু করতে হয়?",
            questionEn = "How many times is Wudu performed daily?",
            questionAr = "كم مرة يتوضأ في اليوم؟",
            optionsBn = listOf("২ বার", "৩ বার", "৫ বার", "যতবার নামাজ পড়বে"),
            optionsEn = listOf("2 times", "3 times", "5 times", "Before every prayer"),
            optionsAr = listOf("مرتين", "٣ مرات", "٥ مرات", "قبل كل صلاة"),
            correctIndex = 3
        )
    )
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_QUESTION = 80
private const val DELAY_OPTIONS_START = 240
private const val STAGGER_OPTION = 70
private const val DELAY_NEXT_BUTTON = 220

@Composable
fun QuizScreen(
    onBackClick: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val questions = remember { QuizData.allQuestions }
    var currentQuestion by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var showFeedback by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }

    // Entrance trigger
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    // Question-change trigger — increments on each question
    var questionKey by remember { mutableIntStateOf(0) }
    var questionVisible by remember { mutableStateOf(false) }

    LaunchedEffect(currentQuestion, isFinished) {
        if (!isFinished) {
            questionVisible = false
            kotlinx.coroutines.delay(60)
            questionKey++
            questionVisible = true
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
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = AppStrings.quizTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (!isFinished) {
                            Text(
                                text = AppStrings.questionNumber(
                                    language,
                                    currentQuestion + 1,
                                    questions.size
                                ),
                                fontSize = 12.sp,
                                color = Color(0xFFD4AF37)
                            )
                        }
                    }

                    if (!isFinished) {
                        ScoreBadge(score = score)
                    }
                }
            }

            // ===== Content =====
            if (isFinished) {
                ResultScreen(
                    score = score,
                    total = questions.size,
                    language = language,
                    onRestart = {
                        currentQuestion = 0
                        score = 0
                        selectedOption = null
                        showFeedback = false
                        isFinished = false
                    }
                )
            } else {
                val question = questions[currentQuestion]
                val options = question.getOptions(language)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // ===== Question Card =====
                    StaggeredEntry(visible = questionVisible, delayMillis = DELAY_QUESTION) {
                        QuestionCard(question = question.getQuestion(language))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ===== Options (staggered) =====
                    options.forEachIndexed { index, option ->
                        val isCorrect = index == question.correctIndex
                        val isSelected = selectedOption == index

                        StaggeredEntry(
                            visible = questionVisible,
                            delayMillis = DELAY_OPTIONS_START + index * STAGGER_OPTION
                        ) {
                            OptionRow(
                                index = index,
                                option = option,
                                isCorrect = isCorrect,
                                isSelected = isSelected,
                                showFeedback = showFeedback,
                                language = language,
                                onClick = {
                                    if (!showFeedback) {
                                        selectedOption = index
                                        showFeedback = true
                                        if (isCorrect) score++
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // ===== Next / Result button =====
                    if (showFeedback) {
                        val isLast = currentQuestion == questions.size - 1
                        StaggeredEntry(
                            visible = true,
                            delayMillis = DELAY_NEXT_BUTTON
                        ) {
                            NextButton(
                                text = if (isLast)
                                    AppStrings.seeResultButton(language)
                                else
                                    AppStrings.nextQuestionButton(language),
                                onClick = {
                                    if (isLast) {
                                        isFinished = true
                                        viewModel.saveScore(score, questions.size)
                                    } else {
                                        currentQuestion++
                                        selectedOption = null
                                        showFeedback = false
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ============================================================
// ===== Score Badge (pulse on increment) =====
// ============================================================
@Composable
private fun ScoreBadge(score: Int) {
    val scoreScale = remember { Animatable(1f) }
    LaunchedEffect(score) {
        if (score > 0) {
            scoreScale.snapTo(0.7f)
            scoreScale.animateTo(
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
            .scale(scoreScale.value)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFD4AF37).copy(alpha = 0.2f))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = "⭐ $score",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD4AF37)
        )
    }
}

// ============================================================
// ===== Question Card =====
// ============================================================
@Composable
private fun QuestionCard(question: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(24.dp)
    ) {
        Column {
            Text(text = "❓", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = question,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 28.sp
            )
        }
    }
}

// ============================================================
// ===== Option Row (with correct/wrong feedback animation) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OptionRow(
    index: Int,
    option: String,
    isCorrect: Boolean,
    isSelected: Boolean,
    showFeedback: Boolean,
    language: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Press feedback
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed && !showFeedback) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "optionPressScale"
    )

    // Background color
    val bgColor by animateColorAsState(
        targetValue = when {
            showFeedback && isCorrect -> Color(0xFF4CAF50)
            showFeedback && isSelected && !isCorrect -> Color(0xFFE53935)
            else -> Color.White.copy(alpha = 0.08f)
        },
        animationSpec = tween(300),
        label = "bgColor"
    )

    // Icon bounce on correct/wrong
    val iconBounce = remember { Animatable(1f) }
    LaunchedEffect(showFeedback, isCorrect, isSelected) {
        if (showFeedback && (isCorrect || isSelected)) {
            iconBounce.snapTo(0.5f)
            iconBounce.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    // Wrong option shake
    val shakeRotation = remember { Animatable(0f) }
    LaunchedEffect(showFeedback, isCorrect, isSelected) {
        if (showFeedback && isSelected && !isCorrect) {
            // Shake sequence
            shakeRotation.snapTo(0f)
            shakeRotation.animateTo(6f, tween(60))
            shakeRotation.animateTo(-6f, tween(60))
            shakeRotation.animateTo(4f, tween(60))
            shakeRotation.animateTo(-4f, tween(60))
            shakeRotation.animateTo(0f, tween(60))
        }
    }

    val shouldHighlight = showFeedback && (isSelected || isCorrect)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(pressScale)
            .rotate(shakeRotation.value)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                enabled = !showFeedback,
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .scale(iconBounce.value)
                .clip(CircleShape)
                .background(
                    if (shouldHighlight) Color.White.copy(alpha = 0.3f)
                    else Color(0xFFD4AF37).copy(alpha = 0.25f)
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                showFeedback && isCorrect -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = AppStrings.correctAnswer(language),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                showFeedback && isSelected && !isCorrect -> {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = AppStrings.wrongAnswer(language),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                else -> {
                    Text(
                        text = "${index + 1}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (shouldHighlight) Color.White else Color(0xFFD4AF37)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = option,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

// ============================================================
// ===== Next Button (press scale + ripple) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NextButton(text: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "nextButtonScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .height(60.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFD4AF37))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFF0A2E1F)),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0A2E1F)
        )
    }
}

// ============================================================
// ===== Result Screen (bounce + pulse + count-up) =====
// ============================================================
@Composable
private fun ResultScreen(
    score: Int,
    total: Int,
    language: String,
    onRestart: () -> Unit
) {
    val percentage = if (total > 0) (score * 100) / total else 0

    val emoji: String
    val message: String
    when {
        percentage >= 80 -> {
            emoji = "🏆"
            message = AppStrings.resultExcellent(language)
        }
        percentage >= 60 -> {
            emoji = "🎉"
            message = AppStrings.resultVeryGood(language)
        }
        percentage >= 40 -> {
            emoji = "👍"
            message = AppStrings.resultGood(language)
        }
        else -> {
            emoji = "📚"
            message = AppStrings.resultNeedMore(language)
        }
    }

    // Emoji spring bounce on entry
    val emojiScale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        emojiScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    // Infinite pulse on emoji after entry
    val infinite = rememberInfiniteTransition(label = "resultPulse")
    val pulseScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "resultPulseScale"
    )

    // Score count-up animation
    val animatedScore = remember { Animatable(0f) }
    LaunchedEffect(score) {
        animatedScore.animateTo(
            targetValue = score.toFloat(),
            animationSpec = tween(900, easing = FastOutSlowInEasing)
        )
    }

    // Percentage count-up
    val animatedPercentage = remember { Animatable(0f) }
    LaunchedEffect(percentage) {
        kotlinx.coroutines.delay(300)
        animatedPercentage.animateTo(
            targetValue = percentage.toFloat(),
            animationSpec = tween(900, easing = FastOutSlowInEasing)
        )
    }

    // Entrance stagger via StaggeredEntry
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Emoji (bounce in + pulse)
        StaggeredEntry(visible = visible, delayMillis = 0) {
            Text(
                text = emoji,
                fontSize = 100.sp,
                modifier = Modifier.scale(emojiScale.value * pulseScale)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Message
        StaggeredEntry(visible = visible, delayMillis = 200) {
            Text(
                text = message,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Score card (with count-up)
        StaggeredEntry(visible = visible, delayMillis = 400) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.08f))
                    .padding(horizontal = 40.dp, vertical = 28.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${animatedScore.value.toInt()} / $total",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = AppStrings.correctAnswersLabel(language),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${animatedPercentage.value.toInt()}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Try again button
        StaggeredEntry(visible = visible, delayMillis = 600) {
            NextButton(
                text = AppStrings.tryAgainButton(language),
                onClick = onRestart
            )
        }
    }
}