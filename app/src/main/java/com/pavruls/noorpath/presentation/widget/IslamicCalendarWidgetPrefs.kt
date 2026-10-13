package com.pavruls.noorpath.presentation.widget

import android.content.Context

data class IslamicCalendarData(
    val hijriDate: String = "—",
    val gregorianDate: String = "—",
    val event1Emoji: String = "🌙",
    val event1Name: String = "—",
    val event1Countdown: String = "—",
    val event2Emoji: String = "🌙",
    val event2Name: String = "—",
    val event2Countdown: String = "—",
    val event3Emoji: String = "🌙",
    val event3Name: String = "—",
    val event3Countdown: String = "—"
)

object IslamicCalendarWidgetPrefs {

    private const val PREFS_NAME = "islamic_calendar_widget_prefs"
    private const val KEY_HIJRI_DATE = "hijri_date"
    private const val KEY_GREGORIAN_DATE = "gregorian_date"
    private const val KEY_EVENT1_EMOJI = "event1_emoji"
    private const val KEY_EVENT1_NAME = "event1_name"
    private const val KEY_EVENT1_COUNTDOWN = "event1_countdown"
    private const val KEY_EVENT2_EMOJI = "event2_emoji"
    private const val KEY_EVENT2_NAME = "event2_name"
    private const val KEY_EVENT2_COUNTDOWN = "event2_countdown"
    private const val KEY_EVENT3_EMOJI = "event3_emoji"
    private const val KEY_EVENT3_NAME = "event3_name"
    private const val KEY_EVENT3_COUNTDOWN = "event3_countdown"

    fun saveData(context: Context, data: IslamicCalendarData) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_HIJRI_DATE, data.hijriDate)
            .putString(KEY_GREGORIAN_DATE, data.gregorianDate)
            .putString(KEY_EVENT1_EMOJI, data.event1Emoji)
            .putString(KEY_EVENT1_NAME, data.event1Name)
            .putString(KEY_EVENT1_COUNTDOWN, data.event1Countdown)
            .putString(KEY_EVENT2_EMOJI, data.event2Emoji)
            .putString(KEY_EVENT2_NAME, data.event2Name)
            .putString(KEY_EVENT2_COUNTDOWN, data.event2Countdown)
            .putString(KEY_EVENT3_EMOJI, data.event3Emoji)
            .putString(KEY_EVENT3_NAME, data.event3Name)
            .putString(KEY_EVENT3_COUNTDOWN, data.event3Countdown)
            .apply()
    }

    fun getData(context: Context): IslamicCalendarData {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return IslamicCalendarData(
            hijriDate = prefs.getString(KEY_HIJRI_DATE, "—") ?: "—",
            gregorianDate = prefs.getString(KEY_GREGORIAN_DATE, "—") ?: "—",
            event1Emoji = prefs.getString(KEY_EVENT1_EMOJI, "🌙") ?: "🌙",
            event1Name = prefs.getString(KEY_EVENT1_NAME, "—") ?: "—",
            event1Countdown = prefs.getString(KEY_EVENT1_COUNTDOWN, "—") ?: "—",
            event2Emoji = prefs.getString(KEY_EVENT2_EMOJI, "🌙") ?: "🌙",
            event2Name = prefs.getString(KEY_EVENT2_NAME, "—") ?: "—",
            event2Countdown = prefs.getString(KEY_EVENT2_COUNTDOWN, "—") ?: "—",
            event3Emoji = prefs.getString(KEY_EVENT3_EMOJI, "🌙") ?: "🌙",
            event3Name = prefs.getString(KEY_EVENT3_NAME, "—") ?: "—",
            event3Countdown = prefs.getString(KEY_EVENT3_COUNTDOWN, "—") ?: "—"
        )
    }
}