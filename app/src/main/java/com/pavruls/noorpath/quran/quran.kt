package com.pavruls.noorpath.quran

data class Surah(
    val id: Int,
    val name: String,
    val transliteration: String,
    val type: String,
    val totalVerses: Int
)

data class Verse(
    val id: Int,
    val globalId: Int,
    val arabic: String,
    val bengali: String,
    val english: String,
    val transliteration: String
)

object QuranConstants {
    // রেসিটার
    const val RECITER_ALAFASY = "ar.alafasy"
    const val RECITER_ABDULBASIT = "ar.abdulbasitmurattal"
    const val RECITER_SUDAIS = "ar.abdurrahmaansudais"
    const val RECITER_MINSHAWI = "ar.minshawi"

    // অডিও URL ফরম্যাট
    fun getAudioUrl(globalVerseId: Int, reciter: String = RECITER_ALAFASY): String {
        return "https://cdn.islamic.network/quran/audio/128/$reciter/$globalVerseId.mp3"
    }
}