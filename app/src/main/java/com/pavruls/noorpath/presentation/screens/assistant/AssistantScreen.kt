package com.pavruls.noorpath.presentation.screens.assistant

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.search.IslamicAssistant
import com.pavruls.noorpath.search.SearchSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ============ Chat Model ============
sealed class ChatMessage {
    data class User(val text: String) : ChatMessage()
    data class Assistant(
        val response: IslamicAssistant.Response
    ) : ChatMessage()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantScreen(
    onBackClick: () -> Unit,
    onResultClick: (source: SearchSource, meta: String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    val scope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current

    var inputText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val messages = remember { mutableStateListOf<ChatMessage>() }

    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    fun sendMessage() {
        val query = inputText.trim()
        if (query.isEmpty() || isSearching) return

        messages.add(ChatMessage.User(query))
        inputText = ""
        isSearching = true
        keyboard?.hide()

        scope.launch {
            delay(180)
            val response = withContext(Dispatchers.IO) {
                IslamicAssistant.ask(context, query, language)
            }
            messages.add(ChatMessage.Assistant(response))
            isSearching = false
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            // ===== Header =====
            StaggeredEntry(visible = entryVisible, delayMillis = 0) {
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = AppStrings.assistantTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.assistantSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            // ===== Chat Area =====
            Box(modifier = Modifier.weight(1f)) {
                if (messages.isEmpty()) {
                    WelcomeState(
                        language = language,
                        visible = entryVisible,
                        onSuggestionClick = { suggestion ->
                            inputText = suggestion
                            sendMessage()
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        itemsIndexed(messages) { _, message ->
                            when (message) {
                                is ChatMessage.User -> {
                                    UserBubble(text = message.text, language = language)
                                }
                                is ChatMessage.Assistant -> {
                                    AssistantResponseBlock(
                                        response = message.response,
                                        language = language,
                                        onResultClick = onResultClick
                                    )
                                }
                            }
                        }

                        if (isSearching) {
                            item(key = "searching_indicator") {
                                SearchingIndicator(language = language)
                            }
                        }
                    }
                }
            }

            // ===== Disclaimer =====
            DisclaimerBar(language = language)

            // ===== Input Bar =====
            InputBar(
                value = inputText,
                onValueChange = { inputText = it },
                onSend = { sendMessage() },
                isSearching = isSearching,
                language = language
            )
        }
    }
}

// ============================================================
// ===== Welcome State (with suggestions) =====
// ============================================================
@Composable
private fun WelcomeState(
    language: String,
    visible: Boolean,
    onSuggestionClick: (String) -> Unit
) {
    val suggestions = when (language) {
        LanguageManager.LANG_EN -> listOf(
            "What is Fajr prayer?",
            "What is Zakat?",
            "Tell me about Prophet Adam",
            "Dua before eating"
        )
        LanguageManager.LANG_AR -> listOf(
            "ما هي صلاة الفجر؟",
            "ما هي الزكاة؟",
            "أخبرني عن النبي آدم",
            "دعاء قبل الطعام"
        )
        else -> listOf(
            "ফজরের নামাজ কী?",
            "যাকাত কী?",
            "হযরত আদম (আ.) সম্পর্কে বলুন",
            "খাওয়ার আগের দুআ"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StaggeredEntry(visible = visible, delayMillis = 0) {
            Text(text = "🤖", fontSize = 72.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        StaggeredEntry(visible = visible, delayMillis = 150) {
            Text(
                text = AppStrings.assistantTitle(language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        StaggeredEntry(visible = visible, delayMillis = 300) {
            Text(
                text = AppStrings.assistantSubtitle(language),
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        suggestions.forEachIndexed { index, suggestion ->
            StaggeredEntry(visible = visible, delayMillis = 450 + index * 80) {
                SuggestionChip(
                    text = suggestion,
                    onClick = { onSuggestionClick(suggestion) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuggestionChip(text: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "💡", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

// ============================================================
// ===== User Bubble =====
// ============================================================
@Composable
private fun UserBubble(text: String, language: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = AppStrings.assistantYouAsked(language),
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 4.dp
                    )
                )
                .background(Color(0xFFD4AF37).copy(alpha = 0.9f))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0A2E1F)
            )
        }
    }
}

// ============================================================
// ===== Assistant Response Block =====
// ============================================================
@Composable
private fun AssistantResponseBlock(
    response: IslamicAssistant.Response,
    language: String,
    onResultClick: (SearchSource, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Header bubble
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 20.dp
                    )
                )
                .background(Color.White.copy(alpha = 0.08f))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🤖", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    if (response.totalCount > 0) {
                        Text(
                            text = AppStrings.assistantFoundResults(language, response.totalCount),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = AppStrings.assistantNoResults(language),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF9800)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (response.totalCount > 0) {
            val groupedOrder = listOf(
                SearchSource.QURAN,
                SearchSource.HADITH,
                SearchSource.DUA,
                SearchSource.ALL
            )

            groupedOrder.forEach { source ->
                val items = response.groupedBySource[source] ?: return@forEach
                if (items.isEmpty()) return@forEach

                SourceSection(
                    source = source,
                    items = items.take(3),
                    language = language,
                    onResultClick = onResultClick
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun SourceSection(
    source: SearchSource,
    items: List<IslamicAssistant.Result>,
    language: String,
    onResultClick: (SearchSource, String) -> Unit
) {
    val emoji: String
    val label: String
    val color: Color
    when (source) {
        SearchSource.QURAN -> {
            emoji = "📖"
            label = AppStrings.searchFilterQuran(language)
            color = Color(0xFF4CAF50)
        }
        SearchSource.HADITH -> {
            emoji = "📚"
            label = AppStrings.searchFilterHadith(language)
            color = Color(0xFF2196F3)
        }
        SearchSource.DUA -> {
            emoji = "🤲"
            label = AppStrings.searchFilterDua(language)
            color = Color(0xFFD4AF37)
        }
        else -> {
            emoji = "🔍"
            label = AppStrings.searchFilterAll(language)
            color = Color.Gray
        }
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = emoji, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        items.forEach { item ->
            ResultMiniCard(
                item = item,
                color = color,
                onClick = { onResultClick(source, item.meta) }
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultMiniCard(
    item: IslamicAssistant.Result,
    color: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = color),
                onClick = onClick
            )
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = item.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            if (item.arabic.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.arabic,
                    fontSize = 16.sp,
                    color = Color(0xFFD4AF37),
                    fontWeight = FontWeight.Medium,
                    lineHeight = 28.sp,
                    maxLines = 3
                )
            }
            if (item.snippet.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.snippet,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 18.sp,
                    maxLines = 3
                )
            }
        }
    }
}

// ============================================================
// ===== Searching Indicator =====
// ============================================================
@Composable
private fun SearchingIndicator(language: String) {
    val infinite = rememberInfiniteTransition(label = "searchingAssistant")
    val rotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing)
        ),
        label = "searchingRotation"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🤖",
            fontSize = 24.sp,
            modifier = Modifier.rotate(rotation)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = AppStrings.assistantSearching(language),
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

// ============================================================
// ===== Disclaimer Bar =====
// ============================================================
@Composable
private fun DisclaimerBar(language: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF9800).copy(alpha = 0.12f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = AppStrings.assistantDisclaimer(language),
            fontSize = 10.sp,
            color = Color(0xFFFFB74D),
            lineHeight = 14.sp
        )
    }
}

// ============================================================
// ===== Input Bar =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isSearching: Boolean,
    language: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF102A1E))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = AppStrings.assistantHint(language),
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSend() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFD4AF37),
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                cursorColor = Color(0xFFD4AF37)
            ),
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (value.isBlank() || isSearching)
                        Color.White.copy(alpha = 0.1f)
                    else
                        Color(0xFFD4AF37)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(color = Color(0xFF0A2E1F)),
                    enabled = value.isNotBlank() && !isSearching,
                    onClick = onSend
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = AppStrings.sendLabel(language),
                tint = if (value.isBlank() || isSearching)
                    Color.White.copy(alpha = 0.4f)
                else
                    Color(0xFF0A2E1F)
            )
        }
    }
}