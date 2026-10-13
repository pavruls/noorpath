package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences

object TasbihSettings {

    private const val PREF_NAME = "noorpath_tasbih"
    private const val KEY_COUNT = "current_count"
    private const val KEY_TARGET = "target"
    private const val KEY_TOTAL = "total_count"
    private const val KEY_SELECTED_DHIKR = "selected_dhikr"
    private const val KEY_SOUND = "sound_enabled"
    private const val KEY_VIBRATION = "vibration_enabled"
    private const val KEY_ROUNDS = "completed_rounds"

    const val DEFAULT_TARGET = 33

    fun getCount(context: Context): Int =
        getPrefs(context).getInt(KEY_COUNT, 0)

    fun saveCount(context: Context, count: Int) {
        getPrefs(context).edit().putInt(KEY_COUNT, count).apply()
    }

    fun getTarget(context: Context): Int =
        getPrefs(context).getInt(KEY_TARGET, DEFAULT_TARGET)

    fun saveTarget(context: Context, target: Int) {
        getPrefs(context).edit().putInt(KEY_TARGET, target).apply()
    }

    fun getTotal(context: Context): Int =
        getPrefs(context).getInt(KEY_TOTAL, 0)

    fun saveTotal(context: Context, total: Int) {
        getPrefs(context).edit().putInt(KEY_TOTAL, total).apply()
    }

    fun getSelectedDhikr(context: Context): Int =
        getPrefs(context).getInt(KEY_SELECTED_DHIKR, 0)

    fun saveSelectedDhikr(context: Context, index: Int) {
        getPrefs(context).edit().putInt(KEY_SELECTED_DHIKR, index).apply()
    }

    fun isSoundEnabled(context: Context): Boolean =
        getPrefs(context).getBoolean(KEY_SOUND, true)

    fun saveSoundEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_SOUND, enabled).apply()
    }

    fun isVibrationEnabled(context: Context): Boolean =
        getPrefs(context).getBoolean(KEY_VIBRATION, true)

    fun saveVibrationEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_VIBRATION, enabled).apply()
    }

    fun getCompletedRounds(context: Context): Int =
        getPrefs(context).getInt(KEY_ROUNDS, 0)

    fun saveCompletedRounds(context: Context, rounds: Int) {
        getPrefs(context).edit().putInt(KEY_ROUNDS, rounds).apply()
    }

    fun resetCount(context: Context) {
        getPrefs(context).edit()
            .putInt(KEY_COUNT, 0)
            .apply()
    }

    fun resetAll(context: Context) {
        getPrefs(context).edit()
            .putInt(KEY_COUNT, 0)
            .putInt(KEY_TOTAL, 0)
            .putInt(KEY_ROUNDS, 0)
            .apply()
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}