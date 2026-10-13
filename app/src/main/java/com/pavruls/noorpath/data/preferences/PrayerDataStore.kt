package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences

object PrayerDataStore {

    private const val PREF_NAME = "noorpath_prayer_times"
    private const val KEY_FAJR = "fajr"
    private const val KEY_DHUHR = "dhuhr"
    private const val KEY_ASR = "asr"
    private const val KEY_MAGHRIB = "maghrib"
    private const val KEY_ISHA = "isha"
    private const val KEY_LAST_UPDATE = "last_update"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // সব সময় সেভ করা
    fun savePrayerTimes(
        context: Context,
        fajr: String,
        dhuhr: String,
        asr: String,
        maghrib: String,
        isha: String
    ) {
        getPrefs(context).edit()
            .putString(KEY_FAJR, fajr)
            .putString(KEY_DHUHR, dhuhr)
            .putString(KEY_ASR, asr)
            .putString(KEY_MAGHRIB, maghrib)
            .putString(KEY_ISHA, isha)
            .putLong(KEY_LAST_UPDATE, System.currentTimeMillis())
            .apply()
    }

    fun getFajr(context: Context): String? = getPrefs(context).getString(KEY_FAJR, null)
    fun getDhuhr(context: Context): String? = getPrefs(context).getString(KEY_DHUHR, null)
    fun getAsr(context: Context): String? = getPrefs(context).getString(KEY_ASR, null)
    fun getMaghrib(context: Context): String? = getPrefs(context).getString(KEY_MAGHRIB, null)
    fun getIsha(context: Context): String? = getPrefs(context).getString(KEY_ISHA, null)

    fun getLastUpdate(context: Context): Long =
        getPrefs(context).getLong(KEY_LAST_UPDATE, 0L)

    // সব ডেটা আছে কিনা চেক
    fun hasData(context: Context): Boolean {
        return getFajr(context) != null && getDhuhr(context) != null
    }

    // সব ডেটা মুছা
    fun clearAll(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}