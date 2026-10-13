package com.pavruls.noorpath.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * NoorPath-এর standard animated bottom sheet।
 *
 * ব্যবহার:
 *   NoorPathBottomSheet(
 *       visible = showSheet,
 *       onDismiss = { showSheet = false },
 *       title = "ভাষা নির্বাচন করুন"
 *   ) {
 *       // আপনার sheet content — stagger animation স্বয়ংক্রিয়ভাবে apply হবে
 *       DialogStaggerItem(visible = true, delayMillis = 100) {
 *           LanguageOption(...)
 *       }
 *   }
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoorPathBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    title: String? = null,
    sheetContent: @Composable ColumnScope.() -> Unit
) {
    if (!visible) return

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    // Entrance trigger for inner content
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF102A1E),
        scrimColor = Color.Black.copy(alpha = 0.55f),
        dragHandle = {
            // Custom animated drag handle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 44.dp, height = 5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White.copy(alpha = 0.28f))
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ===== Title =====
            if (title != null) {
                DialogStaggerItem(
                    visible = entryVisible,
                    delayMillis = 0
                ) {
                    Text(
                        text = title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            // ===== Content =====
            sheetContent()
        }
    }
}

/**
 * Bottom Sheet-এর ভিতরের item-এর জন্য convenience wrapper।
 */
@Composable
fun BottomSheetStaggerItem(
    index: Int,
    visible: Boolean,
    content: @Composable () -> Unit
) {
    DialogStaggerItem(
        visible = visible,
        delayMillis = DialogTiming.SHEET_START + index * DialogTiming.SHEET_ITEM_STEP
    ) {
        content()
    }
}