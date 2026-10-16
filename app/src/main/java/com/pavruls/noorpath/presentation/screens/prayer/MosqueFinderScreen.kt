package com.pavruls.noorpath.presentation.screens.prayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager
import com.pavruls.noorpath.domain.model.Mosque
import com.pavruls.noorpath.presentation.components.StaggeredEntry
import com.pavruls.noorpath.presentation.viewmodel.MosqueUiState
import com.pavruls.noorpath.presentation.viewmodel.MosqueViewModel

// ============ Stagger Timing ============
private const val DELAY_SHEET_TITLE = 60
private const val DELAY_LIST_START = 140
private const val STAGGER_STEP = 60

// Directions (নেভিগেশন) ফাংশন
fun navigateToMosque(context: Context, lat: Double, lng: Double, name: String) {
    val gmmIntentUri = "google.navigation:q=$lat,$lng(${Uri.encode(name)})".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        setPackage("com.google.android.apps.maps")
    }

    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.google.com/maps/dir/?api=1&destination=$lat,$lng".toUri()
        )
        context.startActivity(browserIntent)
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MosqueFinderScreen(
    viewModel: MosqueViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val language by LanguageManager.languageFlow.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // Entrance trigger for header/map
    var entryVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entryVisible = true }

    // ডিফল্ট ক্যামেরা পজিশন (ঢাকা)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(23.8103, 90.4125), 12f)
    }

    // লোকেশন পারমিশন ও মসজিদ লোড করা
    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(location.latitude, location.longitude), 14f
                    )
                    viewModel.fetchNearbyMosques(location.latitude, location.longitude)
                } else {
                    viewModel.fetchNearbyMosques(23.8103, 90.4125)
                }
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        topBar = {
            StaggeredEntry(visible = entryVisible, delayMillis = 0) {
                TopAppBar(
                    title = {
                        Text(
                            text = AppStrings.mosqueFinderTitle(language),
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {

            // ===== Map (fade in) =====
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(600, easing = FastOutSlowInEasing))
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    if (uiState is MosqueUiState.Success) {
                        (uiState as MosqueUiState.Success).mosques.forEach { mosque ->
                            Marker(
                                state = MarkerState(position = LatLng(mosque.lat, mosque.lng)),
                                title = mosque.name,
                                snippet = mosque.address
                            )
                        }
                    }
                }
            }

            // ===== Loading State (custom pulse) =====
            if (uiState is MosqueUiState.Loading) {
                PulsingLoader(modifier = Modifier.align(Alignment.Center))
            }

            // ===== Error State =====
            if (uiState is MosqueUiState.Error) {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(300)) + scaleIn(
                        initialScale = 0.85f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    Card(
                        modifier = Modifier
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = (uiState as MosqueUiState.Error).message,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            // ===== Success State =====
            if (uiState is MosqueUiState.Success) {
                val mosques = (uiState as MosqueUiState.Success).mosques

                if (mosques.isEmpty()) {
                    // Empty state card
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300)) + scaleIn(
                            initialScale = 0.85f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    ) {
                        Card(
                            modifier = Modifier.padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = AppStrings.noMosquesFound(language),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                } else {
                    // Bottom Sheet with staggered list
                    ModalBottomSheet(
                        onDismissRequest = { },
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {

                            // ===== Title =====
                            StaggeredEntry(
                                visible = true,
                                delayMillis = DELAY_SHEET_TITLE
                            ) {
                                Text(
                                    text = AppStrings.nearbyMosques(language, mosques.size),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            // ===== List with stagger =====
                            LazyColumn(contentPadding = PaddingValues(bottom = 16.dp)) {
                                itemsIndexed(mosques) { index, mosque ->
                                    StaggeredEntry(
                                        visible = true,
                                        delayMillis = DELAY_LIST_START + index * STAGGER_STEP
                                    ) {
                                        MosqueItemCard(
                                            mosque = mosque,
                                            language = language,
                                            onNavigateClick = {
                                                navigateToMosque(
                                                    context,
                                                    mosque.lat,
                                                    mosque.lng,
                                                    mosque.name
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ============================================================
// ===== Pulsing Loader (custom animated indicator) =====
// ============================================================
@Composable
private fun PulsingLoader(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "mosqueLoader")
    val ringRotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing)
        ),
        label = "ringRotation"
    )
    val pulseScale by infinite.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .size(90.dp),
        contentAlignment = Alignment.Center
    ) {
        // Pulsing outer glow
        Box(
            modifier = Modifier
                .size(80.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFD4AF37).copy(alpha = 0.35f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Rotating arc
        CircularProgressIndicator(
            modifier = Modifier
                .size(56.dp)
                .rotate(ringRotation),
            color = Color(0xFFD4AF37),
            strokeWidth = 4.dp,
            trackColor = Color(0xFFD4AF37).copy(alpha = 0.15f)
        )

        // Center icon
        Text(
            text = "🕌",
            fontSize = 24.sp
        )
    }
}

// ============================================================
// ===== Mosque Item Card (press feedback + icon bounce) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MosqueItemCard(
    mosque: Mosque,
    language: String,
    onNavigateClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "mosqueCardScale"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "mosqueIconScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .scale(cardScale),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onNavigateClick
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,   // decorative — পাশে mosque.kt name আছে
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp)
                    .scale(iconScale)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = mosque.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mosque.address ?: AppStrings.addressNotFound(language),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            NavigateButton(language = language, onClick = onNavigateClick)
        }
    }
}

// ============================================================
// ===== Navigate Button (press scale + ripple) =====
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigateButton(
    language: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "navigateButtonScale"
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier.scale(scale)
    ) {
        Icon(
            imageVector = Icons.Default.Directions,
            contentDescription = AppStrings.navigateToMosqueLabel(language),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}