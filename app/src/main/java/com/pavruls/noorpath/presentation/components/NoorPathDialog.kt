package com.pavruls.noorpath.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * NoorPath-এর standard animated dialog।
 *
 * ব্যবহার:
 *   NoorPathDialog(
 *       visible = showDialog,
 *       onDismiss = { showDialog = false },
 *       icon = "🗑️",
 *       iconTint = Color(0xFFE53935),
 *       title = "রিসেট করবেন?",
 *       message = "আপনার সব তাসবিহ কাউন্ট মুছে যাবে।",
 *       confirmText = "রিসেট",
 *       cancelText = "বাতিল",
 *       onConfirm = { /* action */ }
 *   )
 */
@Composable
fun NoorPathDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String? = null,
    icon: String? = null,
    iconTint: Color = Color(0xFFD4AF37),
    confirmText: String? = null,
    cancelText: String? = null,
    onConfirm: (() -> Unit)? = null,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    // ✅ নতুন: custom content (message-এর বদলে কিছু দেখাতে চাইলে)
    content: (@Composable () -> Unit)? = null
) {
    if (!visible) return

    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    Dialog(
        onDismissRequest = {
            if (dismissOnBackPress || dismissOnClickOutside) onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF102A1E),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // ===== Icon =====
                if (icon != null) {
                    DialogIconEntrance(
                        visible = entryVisible,
                        delayMillis = DialogTiming.ICON
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(
                                    color = iconTint.copy(alpha = 0.18f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = icon, fontSize = 36.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }

                // ===== Title =====
                DialogStaggerItem(
                    visible = entryVisible,
                    delayMillis = DialogTiming.TITLE
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                // ===== Custom Content OR Message =====
                if (content != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    DialogStaggerItem(
                        visible = entryVisible,
                        delayMillis = DialogTiming.MESSAGE
                    ) {
                        content()
                    }
                } else if (message != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    DialogStaggerItem(
                        visible = entryVisible,
                        delayMillis = DialogTiming.MESSAGE
                    ) {
                        Text(
                            text = message,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.75f),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }

                // ===== Buttons =====
                if (confirmText != null || cancelText != null) {
                    Spacer(modifier = Modifier.height(22.dp))
                    DialogStaggerItem(
                        visible = entryVisible,
                        delayMillis = DialogTiming.ACTIONS,
                        fromBottom = false
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (cancelText != null)
                                Arrangement.spacedBy(10.dp) else Arrangement.Center
                        ) {
                            if (cancelText != null) {
                                AnimatedActionButton(
                                    text = cancelText,
                                    onClick = onDismiss,
                                    isPrimary = false,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (confirmText != null && onConfirm != null) {
                                AnimatedActionButton(
                                    text = confirmText,
                                    onClick = onConfirm,
                                    isPrimary = true,
                                    modifier = if (cancelText != null) Modifier.weight(1f)
                                    else Modifier.fillMaxWidth(0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
/**
 * Button with press scale — Dialog-এর ভিতরে ব্যবহার।
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedActionButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = rememberPressScale(isPressed = isPressed, pressedScale = 0.96f)

    val containerColor = if (isPrimary) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.08f)
    val contentColor = if (isPrimary) Color.White else Color.White.copy(alpha = 0.85f)

    if (isPrimary) {
        Button(
            onClick = onClick,
            modifier = modifier.scale(scale),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            shape = RoundedCornerShape(14.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
    } else {
        TextButton(
            onClick = onClick,
            modifier = modifier.scale(scale),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            Text(text = text, fontWeight = FontWeight.Medium, fontSize = 15.sp)
        }
    }
}