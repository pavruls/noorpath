package com.pavruls.noorpath.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * একটি single row skeleton — যেকোনো list item-এর placeholder।
 * Design: circle icon + 2 lines of text + chevron placeholder
 */
@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier,
    circleSize: Dp = 48.dp,
    titleWidthFraction: Float = 0.5f,
    subtitleWidthFraction: Float = 0.7f,
    cornerRadius: Dp = 18.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.White.copy(alpha = 0.04f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon placeholder
        ShimmerCircle(size = circleSize)

        Spacer(modifier = Modifier.width(14.dp))

        // Text placeholders
        Column(modifier = Modifier.weight(1f)) {
            // Title line
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(titleWidthFraction)
                    .height(16.dp),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Subtitle line
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(subtitleWidthFraction)
                    .height(12.dp),
                shape = RoundedCornerShape(6.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Chevron placeholder
        ShimmerBox(
            modifier = Modifier.size(width = 12.dp, height = 20.dp),
            shape = RoundedCornerShape(4.dp)
        )
    }
}

/**
 * সম্পূর্ণ list skeleton — ডিফল্টভাবে ৭টি আইটেম।
 */
@Composable
fun ShimmerListSkeleton(
    itemCount: Int = 7,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalSpacing: Dp = 12.dp,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
        modifier = modifier,
        userScrollEnabled = false
    ) {
        items(count = itemCount) {
            ShimmerListItem()
        }
    }
}

/**
 * Grid skeleton — 2-কলাম grid-এর জন্য (Kids Home-এর মতো)।
 */
@Composable
fun ShimmerGridSkeleton(
    itemCount: Int = 6,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier,
        userScrollEnabled = false
    ) {
        items(count = itemCount) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.04f))
                    .padding(16.dp)
            ) {
                ShimmerBox(
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(percent = 50)
                )
            }
        }
    }
}