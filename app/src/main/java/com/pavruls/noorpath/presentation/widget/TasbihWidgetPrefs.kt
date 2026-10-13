package com.pavruls.noorpath.presentation.widget

import android.content.Context

object TasbihWidgetPrefs {

    private const val PREFS_NAME = "tasbih_widget_prefs"
    private const val KEY_COUNT = "tasbih_count"

    fun getCount(context: Context): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_COUNT, 0)
    }

    fun incrementCount(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val newCount = prefs.getInt(KEY_COUNT, 0) + 1
        prefs.edit().putInt(KEY_COUNT, newCount).apply()
        return newCount
    }

    fun resetCount(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putInt(KEY_COUNT, 0).apply()
    }
}