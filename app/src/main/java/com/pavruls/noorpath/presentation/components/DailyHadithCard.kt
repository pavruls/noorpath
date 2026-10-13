package com.pavruls.noorpath.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.hadith.DailyHadithData
import com.pavruls.noorpath.hadith.DailyHadithItem

@Composable
fun DailyHadithCard(
    onClick: () -> Unit = { }
) {
    val hadith = DailyHadithData.getTodaysHadith()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = 0.3f),
                        Color(0xFF4CAF50).copy(alpha = 0.2f)
                    )
                )
            )
            .clickable { onClick() }
            .padding(18.dp)
    ) {
        Column {
            // হেডার
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD4AF37).copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📖", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "আজকের হাদিস",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "প্রতিদিন একটি হাদিস পড়ুন",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                Text(
                    text = "›",
                    fontSize = 24.sp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // আরবি
            Text(
                text = hadith.arabic,
                fontSize = 18.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // বাংলা
            Text(
                text = hadith.bengali,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.92f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // সোর্স
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "— ${hadith.narrator}",
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = hadith.source,
                    fontSize = 10.sp,
                    color = Color(0xFFD4AF37).copy(alpha = 0.8f)
                )
            }
        }
    }
}

// সম্পূর্ণ স্ক্রিন দেখার জন্য
@Composable
fun DailyHadithFullCard(
    hadith: DailyHadithItem,
    onNewHadithClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(24.dp)
    ) {
        // আরবি
        Text(
            text = hadith.arabic,
            fontSize = 22.sp,
            color = Color(0xFFD4AF37),
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // বাংলা
        Text(
            text = hadith.bengali,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.95f),
            lineHeight = 26.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // সোর্স
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "— ${hadith.narrator}",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            Text(
                text = hadith.source,
                fontSize = 12.sp,
                color = Color(0xFFD4AF37),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // নতুন হাদিস বাটন
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFD4AF37))
                .clickable { onNewHadithClick() }
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🔄 নতুন হাদিস দেখুন",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A2E1F)
            )
        }
    }
}