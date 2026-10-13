package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LanguageManager {

    private const val PREF_NAME = "noorpath_language"
    private const val KEY_LANGUAGE = "app_language"

    const val LANG_BN = "bn"
    const val LANG_EN = "en"
    const val LANG_AR = "ar"

    // StateFlow — reactive
    private val _languageFlow = MutableStateFlow(LANG_BN)
    val languageFlow: StateFlow<String> = _languageFlow.asStateFlow()

    private var isInitialized = false

    fun initialize(context: Context) {
        if (isInitialized) return
        _languageFlow.value = getLanguage(context)
        isInitialized = true
    }

    fun getLanguage(context: Context): String {
        return getPrefs(context).getString(KEY_LANGUAGE, LANG_BN) ?: LANG_BN
    }

    fun saveLanguage(context: Context, lang: String) {
        getPrefs(context).edit().putString(KEY_LANGUAGE, lang).commit()
        _languageFlow.value = lang
    }

    fun getLanguageName(lang: String): String {
        return when (lang) {
            LANG_BN -> "🇧🇩 বাংলা"
            LANG_EN -> "🇬🇧 English"
            LANG_AR -> "🇸🇦 العربية"
            else -> "🇧🇩 বাংলা"
        }
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}