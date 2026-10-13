package com.pavruls.noorpath.hadith

import com.pavruls.noorpath.data.preferences.LanguageManager

data class Hadith(
    val id: Int,
    val arabic: String,
    val bengali: String,
    val english: String
)

data class HadithBook(
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val arabicName: String,
    val fileNamePrefix: String
) {
    fun getName(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> nameEn
        LanguageManager.LANG_AR -> nameAr
        else -> nameBn
    }
}

object HadithBookData {
    val allBooks = listOf(
        HadithBook(
            nameBn = "সহীহ বুখারী",
            nameEn = "Sahih Bukhari",
            nameAr = "صحيح البخاري",
            arabicName = "صحيح البخاري",
            fileNamePrefix = "bukhari"
        ),
        HadithBook(
            nameBn = "সহীহ মুসলিম",
            nameEn = "Sahih Muslim",
            nameAr = "صحيح مسلم",
            arabicName = "صحيح مسلم",
            fileNamePrefix = "muslim"
        ),
        HadithBook(
            nameBn = "সুনানে আবু দাউদ",
            nameEn = "Sunan Abu Dawud",
            nameAr = "سنن أبي داود",
            arabicName = "سنن أبي داود",
            fileNamePrefix = "abudawud"
        ),
        HadithBook(
            nameBn = "সুনানে তিরমিজি",
            nameEn = "Sunan Tirmidhi",
            nameAr = "سنن الترمذي",
            arabicName = "سنن الترمذي",
            fileNamePrefix = "tirmidhi"
        ),
        HadithBook(
            nameBn = "সুনানে নাসাঈ",
            nameEn = "Sunan Nasai",
            nameAr = "سنن النسائي",
            arabicName = "سنن النسائي",
            fileNamePrefix = "nasai"
        ),
        HadithBook(
            nameBn = "সুনানে ইবনে মাজাহ",
            nameEn = "Sunan Ibn Majah",
            nameAr = "سنن ابن ماجه",
            arabicName = "سنن ابن ماجه",
            fileNamePrefix = "ibnmajah"
        )
    )
}