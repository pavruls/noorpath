package com.pavruls.noorpath.presentation.widget

import android.content.Context

data class PrayerTimeData(
    val fajr: String = "—",
    val dhuhr: String = "—",
    val asr: String = "—",
    val maghrib: String = "—",
    val isha: String = "—",
    val city: String = "—"
)

object PrayerWidgetPrefs {

    private const val PREFS_NAME = "prayer_widget_prefs"
    private const val KEY_FAJR = "fajr"
    private const val KEY_DHUHR = "dhuhr"
    private const val KEY_ASR = "asr"
    private const val KEY_MAGHRIB = "maghrib"
    private const val KEY_ISHA = "isha"
    private const val KEY_CITY = "city"

    fun savePrayerTimes(context: Context, data: PrayerTimeData) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_FAJR, data.fajr)
            .putString(KEY_DHUHR, data.dhuhr)
            .putString(KEY_ASR, data.asr)
            .putString(KEY_MAGHRIB, data.maghrib)
            .putString(KEY_ISHA, data.isha)
            .putString(KEY_CITY, data.city)
            .apply()
    }

    fun getPrayerTimes(context: Context): PrayerTimeData {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return PrayerTimeData(
            fajr = prefs.getString(KEY_FAJR, "—") ?: "—",
            dhuhr = prefs.getString(KEY_DHUHR, "—") ?: "—",
            asr = prefs.getString(KEY_ASR, "—") ?: "—",
            maghrib = prefs.getString(KEY_MAGHRIB, "—") ?: "—",
            isha = prefs.getString(KEY_ISHA, "—") ?: "—",
            city = prefs.getString(KEY_CITY, "—") ?: "—"
        )
    }
}