package com.pavruls.noorpath.presentation.widget

import android.content.Context

object PrayerWidgetUpdater {

    fun update(
        context: Context,
        fajr: String,
        dhuhr: String,
        asr: String,
        maghrib: String,
        isha: String,
        city: String
    ) {
        val data = PrayerTimeData(
            fajr = fajr,
            dhuhr = dhuhr,
            asr = asr,
            maghrib = maghrib,
            isha = isha,
            city = city
        )
        PrayerWidgetPrefs.savePrayerTimes(context, data)
        PrayerTimeWidgetProvider.refreshAllWidgets(context)
    }
}