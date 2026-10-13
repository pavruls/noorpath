package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeSettings {

    private const val PREF_NAME = "noorpath_theme"
    private const val KEY_DARK_MODE = "dark_mode"

    const val MODE_SYSTEM = 0
    const val MODE_LIGHT = 1
    const val MODE_DARK = 2

    // ============ StateFlow for reactive updates ============
    private val _darkModeFlow = MutableStateFlow(MODE_SYSTEM)
    val darkModeFlow: StateFlow<Int> = _darkModeFlow.asStateFlow()

    private var isInitialized = false

    // অ্যাপ চালু হলে একবার কল করতে হবে
    fun initialize(context: Context) {
        if (isInitialized) return
        _darkModeFlow.value = getDarkMode(context)
        isInitialized = true
    }

    fun getDarkMode(context: Context): Int {
        return getPrefs(context).getInt(KEY_DARK_MODE, MODE_SYSTEM)
    }

    fun saveDarkMode(context: Context, mode: Int) {
        // commit() ব্যবহার করছি যাতে সাথে সাথে ডিস্কে সেভ হয়
        getPrefs(context).edit().putInt(KEY_DARK_MODE, mode).commit()
        // StateFlow আপডেট করছি - এতে UI সাথে সাথে পরিবর্তন হবে
        _darkModeFlow.value = mode
    }

    fun getModeName(mode: Int): String {
        return when (mode) {
            MODE_SYSTEM -> "সিস্টেম ডিফল্ট"
            MODE_LIGHT -> "লাইট মোড"
            MODE_DARK -> "ডার্ক মোড"
            else -> "সিস্টেম ডিফল্ট"
        }
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}