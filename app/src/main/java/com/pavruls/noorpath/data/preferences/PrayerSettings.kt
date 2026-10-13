package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences

object PrayerSettings {

    private const val PREF_NAME = "noorpath_prayer_settings"
    private const val KEY_METHOD = "calculation_method"

    // Aladhan API-র পদ্ধতি নাম্বার:
    // 1 = University of Islamic Sciences, Karachi
    // 2 = Islamic Society of North America (ISNA)
    // 3 = Muslim World League (MWL)
    // 4 = Umm al-Qura University, Makkah
    // 5 = Egyptian General Authority of Survey
    // 7 = Institute of Geophysics, University of Tehran
    // 8 = Gulf Region
    // 9 = Kuwait
    // 10 = Qatar
    // 11 = Majlis Ugama Islam Singapura
    // 12 = Union Organization islamic de France
    // 13 = Diyanet İşleri Başkanlığı, Turkey
    // 14 = Spiritual Administration of Muslims of Russia
    // 15 = Moonsighting Committee Worldwide

    const val DEFAULT_METHOD = 1  // Karachi (দক্ষিণ এশিয়ার জন্য সবচেয়ে জনপ্রিয়)

    fun getMethod(context: Context): Int {
        return getPrefs(context).getInt(KEY_METHOD, DEFAULT_METHOD)
    }

    fun saveMethod(context: Context, method: Int) {
        getPrefs(context).edit().putInt(KEY_METHOD, method).apply()
    }

    fun getMethodName(context: Context): String {
        return getMethodNameById(getMethod(context))
    }

    fun getMethodNameById(methodId: Int): String {
        return when (methodId) {
            1 -> "করাচি (University of Islamic Sciences)"
            2 -> "উত্তর আমেরিকা (ISNA)"
            3 -> "মুসলিম ওয়ার্ল্ড লীগ"
            4 -> "উম্মুল কুরা (মক্কা)"
            5 -> "মিশর (Egyptian General Authority)"
            7 -> "তেহরান (Institute of Geophysics)"
            8 -> "উপসাগরীয় অঞ্চল"
            9 -> "কুয়েত"
            10 -> "কাতার"
            11 -> "সিঙ্গাপুর (Majlis Ugama Islam)"
            12 -> "ফ্রান্স (UOIF)"
            13 -> "তুরস্ক (Diyanet)"
            14 -> "রাশিয়া (SAMR)"
            15 -> "Moonsighting Committee"
            else -> "অজানা"
        }
    }

    fun getAllMethods(): List<Pair<Int, String>> {
        return listOf(
            1 to "করাচি (University of Islamic Sciences)",
            2 to "উত্তর আমেরিকা (ISNA)",
            3 to "মুসলিম ওয়ার্ল্ড লীগ",
            4 to "উম্মুল কুরা (মক্কা)",
            5 to "মিশর (Egyptian General Authority)",
            7 to "তেহরান (Institute of Geophysics)",
            8 to "উপসাগরীয় অঞ্চল (Gulf Region)",
            9 to "কুয়েত",
            10 to "কাতার",
            11 to "সিঙ্গাপুর (Majlis Ugama Islam)",
            12 to "ফ্রান্স (UOIF)",
            13 to "তুরস্ক (Diyanet İşleri Başkanlığı)",
            14 to "রাশিয়া (SAMR)",
            15 to "Moonsighting Committee Worldwide"
        )
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}