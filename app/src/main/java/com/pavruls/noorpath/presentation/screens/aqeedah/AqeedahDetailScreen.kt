package com.pavruls.noorpath.presentation.screens.aqeedah

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import com.pavruls.noorpath.aqeedah.AqeedahData
import com.pavruls.noorpath.aqeedah.AqeedahSection
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_LIST_START = 120
private const val STAGGER_STEP = 60

@Composable
fun AqeedahDetailScreen(
    topicId: Int,
    topicName: String,
    onBackClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()
    val topic = AqeedahData.getTopicById(topicId)

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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = topicName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        topic?.let {
                            Text(
                                text = AppStrings.sectionsCount(language, it.sections.size),
                                fontSize = 12.sp,
                                color = Color(0xFFD4AF37)
                            )
                        }
                    }
                }
            }

            // ===== Section List (staggered) =====
            topic?.let {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    itemsIndexed(
                        items = it.sections,
                        key = { _, section -> section.id }
                    ) { index, section ->
                        StaggeredEntry(
                            visible = entryVisible,
                            delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                        ) {
                            SectionCard(section, language)
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Section Card (with press feedback) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SectionCard(section: AqeedahSection, language: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.985f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "aqeedahSectionCardScale"
    )
    val numberScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "aqeedahSectionNumberScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color(0xFFD4AF37)),
                onClick = { /* Future: bookmark/share */ }
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .scale(numberScale)
                    .clip(CircleShape)
                    .background(Color(0xFFD4AF37).copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${section.id}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = section.getTitle(language),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD4AF37)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF4CAF50).copy(alpha = 0.08f))
                .padding(14.dp)
        ) {
            Text(
                text = section.getContent(language),
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.92f),
                lineHeight = 24.sp
            )
        }

        if (section.reference.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📖 ${section.reference}",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}