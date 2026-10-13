package com.pavruls.noorpath.data.preferences

import android.content.Context
import android.content.SharedPreferences

object RamadanSettings {

    private const val PREF_NAME = "noorpath_ramadan"
    private const val KEY_FASTED_DAYS = "fasted_days"
    private const val KEY_TARAWEEH_RAKAT = "taraweeh_rakat"
    private const val KEY_TARAWEEH_TARGET = "taraweeh_target"

    const val TARAWEEH_DEFAULT_TARGET = 20

    // ============ রোজা ট্র্যাকার ============
    // দিন নম্বরগুলো কমা দিয়ে সেভ থাকবে: "1,2,3,4,5"
    fun getFastedDays(context: Context): Set<Int> {
        val str = getPrefs(context).getString(KEY_FASTED_DAYS, "") ?: ""
        if (str.isEmpty()) return emptySet()
        return str.split(",").mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun toggleFastedDay(context: Context, day: Int) {
        val current = getFastedDays(context).toMutableSet()
        if (current.contains(day)) current.remove(day) else current.add(day)
        getPrefs(context).edit()
            .putString(KEY_FASTED_DAYS, current.joinToString(","))
            .apply()
    }

    fun getFastedCount(context: Context): Int = getFastedDays(context).size

    fun resetFasted(context: Context) {
        getPrefs(context).edit().putString(KEY_FASTED_DAYS, "").apply()
    }

    // ============ তারাবির রাকাত ============
    fun getTaraweehRakat(context: Context): Int =
        getPrefs(context).getInt(KEY_TARAWEEH_RAKAT, 0)

    fun saveTaraweehRakat(context: Context, rakat: Int) {
        getPrefs(context).edit().putInt(KEY_TARAWEEH_RAKAT, rakat).apply()
    }

    fun getTaraweehTarget(context: Context): Int =
        getPrefs(context).getInt(KEY_TARAWEEH_TARGET, TARAWEEH_DEFAULT_TARGET)

    fun saveTaraweehTarget(context: Context, target: Int) {
        getPrefs(context).edit().putInt(KEY_TARAWEEH_TARGET, target).apply()
    }

    fun resetTaraweeh(context: Context) {
        getPrefs(context).edit().putInt(KEY_TARAWEEH_RAKAT, 0).apply()
    }

    // ============ সব রিসেট ============
    fun resetAll(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}