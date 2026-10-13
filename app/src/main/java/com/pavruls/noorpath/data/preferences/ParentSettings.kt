package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences

object ParentSettings {

    private const val PREF_NAME = "noorpath_parent_settings"
    private const val KEY_PIN = "parent_pin"
    private const val KEY_SCREEN_TIME = "screen_time_minutes"

    private const val DEFAULT_PIN = "1234"
    private const val DEFAULT_SCREEN_TIME = 120 // ২ ঘণ্টা

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // ============ PIN ============
    fun getPin(context: Context): String {
        return getPrefs(context).getString(KEY_PIN, DEFAULT_PIN) ?: DEFAULT_PIN
    }

    fun savePin(context: Context, newPin: String) {
        getPrefs(context).edit().putString(KEY_PIN, newPin).apply()
    }

    // ============ স্ক্রিন টাইম ============
    fun getScreenTime(context: Context): Int {
        return getPrefs(context).getInt(KEY_SCREEN_TIME, DEFAULT_SCREEN_TIME)
    }

    fun saveScreenTime(context: Context, minutes: Int) {
        getPrefs(context).edit().putInt(KEY_SCREEN_TIME, minutes).apply()
    }

    fun getScreenTimeLabel(context: Context): String {
        val minutes = getScreenTime(context)
        return when {
            minutes == 0 -> "আনলিমিটেড"
            minutes < 60 -> "$minutes মিনিট"
            minutes % 60 == 0 -> "${minutes / 60} ঘণ্টা"
            else -> "${minutes / 60} ঘণ্টা ${minutes % 60} মিনিট"
        }
    }
}