package com.pavruls.noorpath.tafsir

import com.pavruls.noorpath.data.preferences.LanguageManager

data class Surah(
    val id: Int,
    val name: String,
    val transliteration: String,
    val type: String,
    val totalVerses: Int
) {
    fun getDisplayName(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> transliteration
        LanguageManager.LANG_AR -> name
        else -> name
    }
}

data class Verse(
    val id: Int,
    val arabic: String,
    val bengali: String,
    val english: String = ""
)