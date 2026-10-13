package com.pavruls.noorpath.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager

@Composable
fun NoorPathBottomBar(
    selectedTab: Int,
    onHomeClick: () -> Unit,
    onPrayerClick: () -> Unit,
    onQuranClick: () -> Unit,
    onTasbihClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

    NavigationBar(
        containerColor = Color(0xFF04170C),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onHomeClick,
            icon = { BottomNavContent("🏠", AppStrings.navHome(language), selectedTab == 0) },
            label = null,
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = onPrayerClick,
            icon = { BottomNavContent("🕌", AppStrings.navPrayer(language), selectedTab == 1) },
            label = null,
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = onQuranClick,
            icon = { BottomNavContent("📖", AppStrings.navQuran(language), selectedTab == 2) },
            label = null,
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = onTasbihClick,
            icon = { BottomNavContent("📿", AppStrings.navTasbih(language), selectedTab == 3) },
            label = null,
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = selectedTab == 4,
            onClick = onMoreClick,
            icon = { BottomNavContent("⚙️", AppStrings.navMore(language), selectedTab == 4) },
            label = null,
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
    }
}

@Composable
private fun BottomNavContent(
    emoji: String,
    label: String,
    isSelected: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) Color(0xFFD4AF37).copy(alpha = 0.25f)
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 18.sp)
        }
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color(0xFFD4AF37) else Color.White.copy(alpha = 0.6f)
        )
    }
}