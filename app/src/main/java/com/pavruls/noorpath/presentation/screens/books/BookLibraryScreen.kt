package com.pavruls.noorpath.presentation.screens.books

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggerTiming
import com.pavruls.noorpath.presentation.components.StaggeredEntry

data class BookCategory(
    val id: Int,
    val emoji: String,
    val titleKey: String,
    val descKey: String,
    val englishName: String,
    val color: Color,
    val bookCount: Int
)

object BookData {
    val categories = listOf(
        BookCategory(1, "📖", "tafsir", "tafsir_desc", "Tafsir", Color(0xFF4CAF50), 5),
        BookCategory(2, "📚", "hadith", "hadith_desc", "Hadith", Color(0xFFE91E63), 6),
        BookCategory(3, "⚖️", "fiqh", "fiqh_desc", "Fiqh", Color(0xFF9C27B0), 6),
        BookCategory(4, "🌟", "seerah", "seerah_desc", "Seerah", Color(0xFFFF9800), 8),
        BookCategory(5, "🕌", "aqeedah", "aqeedah_desc", "Aqeedah", Color(0xFF00BCD4), 6),
        BookCategory(6, "💎", "akhlaq", "akhlaq_desc", "Akhlaq", Color(0xFF795548), 7)
    )
}

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_GRID_START = 140
private const val STAGGER_ITEM = 80

@Composable
fun BookLibraryScreen(
    onBackClick: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    onHadithClick: () -> Unit,
    onTafsirClick: () -> Unit,
    onFiqhClick: () -> Unit,
    onSeerahClick: () -> Unit,
    onAqeedahClick: () -> Unit,
    onAkhlaqClick: () -> Unit
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
                            text = AppStrings.bookLibrary(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.bookLibrarySubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ===== Category Grid (staggered) =====
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = BookData.categories,
                    key = { _, category -> category.id }
                ) { index, category ->
                    StaggeredEntry(
                        visible = entryVisible,
                        delayMillis = DELAY_GRID_START + index * STAGGER_ITEM
                    ) {
                        CategoryCard(
                            category = category,
                            title = getCategoryTitle(category.titleKey, language),
                            description = getCategoryDesc(category.descKey, language),
                            onClick = {
                                when (category.id) {
                                    1 -> onTafsirClick()
                                    2 -> onHadithClick()
                                    3 -> onFiqhClick()
                                    4 -> onSeerahClick()
                                    5 -> onAqeedahClick()
                                    6 -> onAkhlaqClick()
                                    else -> onCategoryClick(category.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun getCategoryTitle(key: String, lang: String): String {
    return when (key) {
        "tafsir" -> AppStrings.tafsir(lang)
        "hadith" -> AppStrings.hadith(lang)
        "fiqh" -> AppStrings.fiqh(lang)
        "seerah" -> AppStrings.seerah(lang)
        "aqeedah" -> AppStrings.aqeedah(lang)
        "akhlaq" -> AppStrings.akhlaq(lang)
        else -> key
    }
}

private fun getCategoryDesc(key: String, lang: String): String {
    return when (key) {
        "tafsir_desc" -> AppStrings.tafsirDesc(lang)
        "hadith_desc" -> AppStrings.hadithDesc(lang)
        "fiqh_desc" -> AppStrings.fiqhDesc(lang)
        "seerah_desc" -> AppStrings.seerahDesc(lang)
        "aqeedah_desc" -> AppStrings.aqeedahDesc(lang)
        "akhlaq_desc" -> AppStrings.akhlaqDesc(lang)
        else -> key
    }
}

// ============================================================
// ===== Category Card (with press feedback + icon bounce) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryCard(
    category: BookCategory,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "categoryCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "categoryIconScale"
    )
    // Count badge subtle pulse on press
    val countScale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "categoryCountScale"
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .scale(cardScale)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        category.color.copy(alpha = 0.85f),
                        category.color.copy(alpha = 0.55f)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = category.color),
                onClick = onClick
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Emoji circle with bounce
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .scale(iconScale)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = category.emoji, fontSize = 28.sp)
                }

                // Count badge
                Box(
                    modifier = Modifier
                        .scale(countScale)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.25f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${category.bookCount}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    lineHeight = 16.sp,
                    maxLines = 2
                )
            }
        }
    }
}