package com.pavruls.noorpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.pavruls.noorpath.data.preferences.ThemeSettings
import com.pavruls.noorpath.presentation.navigation.NavGraph
import com.pavruls.noorpath.ui.theme.NoorPathTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.collectAsState
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoorPathRoot()
        }
    }
}

@Composable
fun NoorPathRoot() {
    val context = LocalContext.current

    // StateFlow observe করছি - theme পরিবর্তন হলে সাথে সাথে update হবে
    val mode by ThemeSettings.darkModeFlow.collectAsState()

    val isDark = when (mode) {
        ThemeSettings.MODE_DARK -> true
        ThemeSettings.MODE_LIGHT -> false
        else -> isSystemInDarkTheme()
    }

    NoorPathTheme(darkTheme = isDark) {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}