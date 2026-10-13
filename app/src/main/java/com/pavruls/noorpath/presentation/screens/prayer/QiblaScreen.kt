package com.pavruls.noorpath.presentation.screens.prayer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

private const val KAABA_LAT = 21.4225
private const val KAABA_LON = 39.8262
private const val ALIGNED_THRESHOLD = 8f  // within 8 degrees = aligned

// ============ Stagger Timing ============
private const val DELAY_HEADER = 0
private const val DELAY_COMPASS = 120
private const val DELAY_INFO_CARD_1 = 320
private const val DELAY_INFO_CARD_2 = 390
private const val DELAY_INSTRUCTIONS = 500
private const val DELAY_SENSOR_WARNING = 600

@Composable
fun QiblaScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()

    var deviceAzimuth by remember { mutableFloatStateOf(0f) }
    var qiblaBearing by remember { mutableFloatStateOf(277f) }
    var sensorAvailable by remember { mutableStateOf(true) }

    // Staggered entrance
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    DisposableEffect(Unit) {
        val sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        if (rotationSensor == null) {
            sensorAvailable = false
            onDispose { }
        } else {
            val listener = object : SensorEventListener {
                private val rotationMatrix = FloatArray(9)
                private val orientation = FloatArray(3)

                override fun onSensorChanged(event: SensorEvent?) {
                    event ?: return
                    if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                        SensorManager.getRotationMatrixFromVector(
                            rotationMatrix,
                            event.values
                        )
                        SensorManager.getOrientation(rotationMatrix, orientation)
                        val azimuthRad = orientation[0]
                        val azimuthDeg =
                            Math.toDegrees(azimuthRad.toDouble()).toFloat()
                        deviceAzimuth = (azimuthDeg + 360f) % 360f
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                listener,
                rotationSensor,
                SensorManager.SENSOR_DELAY_UI
            )

            onDispose {
                sensorManager.unregisterListener(listener)
            }
        }
    }

    LaunchedEffect(Unit) {
        val hasPermission = checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            try {
                val location = getCurrentLocation(context)
                if (location != null) {
                    qiblaBearing = calculateQiblaBearing(
                        location.latitude,
                        location.longitude
                    )
                }
            } catch (_: SecurityException) {
            } catch (_: Exception) {
            }
        }
    }

    val arrowRotation = (qiblaBearing - deviceAzimuth + 360f) % 360f

    // Smooth spring rotation — বাস্তবসম্মত feel-এর জন্য
    val animatedRotation by animateFloatAsState(
        targetValue = arrowRotation,
        animationSpec = spring(
            dampingRatio = 0.85f,
            stiffness = Spring.StiffnessLow
        ),
        label = "qiblaRotation"
    )

    val compassRotation = -deviceAzimuth

    // ⭐ Alignment detection — Qibla direction-এর সাথে মিলে গেলে
    val isAligned = remember(arrowRotation) {
        val normalized = if (arrowRotation > 180f) 360f - arrowRotation else arrowRotation
        normalized < ALIGNED_THRESHOLD
    }

    // Pulse animation — Kaaba icon bob
    val infinite = rememberInfiniteTransition(label = "qiblaInfinite")
    val kaabaBob by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "kaabaBob"
    )
    val outerPulse by infinite.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "outerPulse"
    )
    val alignedPulse by infinite.animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alignedPulse"
    )

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
                            text = AppStrings.qiblaTitle(language),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = AppStrings.qiblaSubtitle(language),
                            fontSize = 12.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // ===== Compass =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_COMPASS) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .scale(outerPulse),
                        contentAlignment = Alignment.Center
                    ) {
                        // Outer circle
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.08f),
                                            Color.White.copy(alpha = 0.03f)
                                        )
                                    )
                                )
                        )

                        // Gold ring — aligned হলে সবুজ হয়ে যাবে
                        Box(
                            modifier = Modifier
                                .size(290.dp)
                                .scale(if (isAligned) alignedPulse else 1f)
                                .clip(CircleShape)
                                .background(
                                    if (isAligned)
                                        Color(0xFF4CAF50).copy(alpha = 0.35f)
                                    else
                                        Color(0xFFD4AF37).copy(alpha = 0.15f)
                                )
                        )

                        // Compass ticks & NSEW labels (rotating with compass)
                        Box(
                            modifier = Modifier
                                .size(270.dp)
                                .rotate(compassRotation),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "N",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF5252),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 12.dp)
                            )
                            Text(
                                text = "S",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 12.dp)
                            )
                            Text(
                                text = "W",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 12.dp)
                            )
                            Text(
                                text = "E",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 12.dp)
                            )

                            for (i in 0 until 72) {
                                val angle = i * 5f
                                val isMain = i % 9 == 0
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .rotate(angle),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(top = if (isMain) 6.dp else 4.dp)
                                            .width(if (isMain) 3.dp else 1.5.dp)
                                            .height(if (isMain) 14.dp else 8.dp)
                                            .background(
                                                if (isMain) Color(0xFFD4AF37)
                                                else Color.White.copy(alpha = 0.3f)
                                            )
                                    )
                                }
                            }
                        }

                        // Qibla needle with Kaaba icon — rotates to Qibla bearing
                        Box(
                            modifier = Modifier
                                .size(270.dp)
                                .rotate(animatedRotation),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Kaaba icon with bob animation
                                Box(
                                    modifier = Modifier
                                        .padding(top = kaabaBob.dp)
                                ) {
                                    Text(text = "🕋", fontSize = 34.sp)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .width(6.dp)
                                        .height(90.dp)
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    if (isAligned) Color(0xFF4CAF50)
                                                    else Color(0xFFD4AF37),
                                                    if (isAligned) Color(0xFF4CAF50).copy(alpha = 0.5f)
                                                    else Color(0xFFD4AF37).copy(alpha = 0.5f)
                                                )
                                            )
                                        )
                                )
                            }
                        }

                        // Center dot — aligned হলে সবুজ
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .scale(if (isAligned) alignedPulse else 1f)
                                .clip(CircleShape)
                                .background(
                                    if (isAligned) Color(0xFF4CAF50)
                                    else Color(0xFFD4AF37)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0A2E1F))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Alignment indicator =====
                AnimatedVisibility(
                    visible = isAligned,
                    enter = fadeIn(animationSpec = tween(300)) +
                            androidx.compose.animation.scaleIn(
                                animationSpec = tween(300),
                                initialScale = 0.7f
                            )
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0xFF4CAF50).copy(alpha = 0.25f))
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "✓", fontSize = 20.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "কিবলার দিকে মুখ করে আছেন",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ===== Info cards (staggered) =====
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_INFO_CARD_1) {
                        InfoCard(
                            emoji = "🕋",
                            label = AppStrings.qiblaDirection(language),
                            value = "${qiblaBearing.toInt()}°",
                            color = Color(0xFFD4AF37),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_INFO_CARD_2) {
                        InfoCard(
                            emoji = "📱",
                            label = AppStrings.phoneDirection(language),
                            value = "${deviceAzimuth.toInt()}°",
                            color = Color(0xFF2196F3),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ===== Instructions card =====
                StaggeredEntry(visible = entryVisible, delayMillis = DELAY_INSTRUCTIONS) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFD4AF37).copy(alpha = 0.15f))
                            .padding(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = AppStrings.qiblaInstructionsTitle(language),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD4AF37)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = AppStrings.qiblaInstructions(language),
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Sensor warning =====
                if (!sensorAvailable) {
                    StaggeredEntry(visible = entryVisible, delayMillis = DELAY_SENSOR_WARNING) {
                        Text(
                            text = AppStrings.qiblaSensorUnavailable(language),
                            fontSize = 12.sp,
                            color = Color(0xFFFF5252),
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(
    emoji: String,
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

private fun calculateQiblaBearing(userLat: Double, userLon: Double): Float {
    val lat1 = Math.toRadians(userLat)
    val lat2 = Math.toRadians(KAABA_LAT)
    val dLon = Math.toRadians(KAABA_LON - userLon)

    val y = sin(dLon) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLon)

    val bearing = Math.toDegrees(atan2(y, x))
    return ((bearing + 360) % 360).toFloat()
}

private suspend fun getCurrentLocation(context: Context): Location? =
    suspendCancellableCoroutine { continuation ->
        try {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (continuation.isActive) continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            val fusedLocationClient =
                com.google.android.gms.location.LocationServices
                    .getFusedLocationProviderClient(context)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (continuation.isActive) continuation.resume(location)
                }
                .addOnFailureListener {
                    if (continuation.isActive) continuation.resume(null)
                }
        } catch (_: SecurityException) {
            if (continuation.isActive) continuation.resume(null)
        } catch (_: Exception) {
            if (continuation.isActive) continuation.resume(null)
        }
    }