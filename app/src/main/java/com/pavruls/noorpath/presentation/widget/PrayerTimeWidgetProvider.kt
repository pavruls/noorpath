package com.pavruls.noorpath.presentation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.pavruls.noorpath.MainActivity
import com.pavruls.noorpath.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PrayerTimeWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH) {
            refreshAllWidgets(context)
        }
    }

    companion object {
        const val ACTION_REFRESH = "com.pavruls.noorpath.PRAYER_WIDGET_REFRESH"

        fun refreshAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, PrayerTimeWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
            for (appWidgetId in appWidgetIds) {
                updateWidget(context, appWidgetManager, appWidgetId)
            }
        }

        private fun updateWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val data = PrayerWidgetPrefs.getPrayerTimes(context)
            val views = RemoteViews(context.packageName, R.layout.widget_prayer_time)

            // পরবর্তী নামাজ বের করি
            val nextInfo = calculateNextPrayer(data)

            views.setTextViewText(R.id.next_prayer_label, "পরবর্তী: ${nextInfo.name}")
            views.setTextViewText(R.id.next_prayer_time, nextInfo.time)
            views.setTextViewText(R.id.widget_location, data.city)

            views.setTextViewText(R.id.time_fajr, data.fajr)
            views.setTextViewText(R.id.time_dhuhr, data.dhuhr)
            views.setTextViewText(R.id.time_asr, data.asr)
            views.setTextViewText(R.id.time_maghrib, data.maghrib)
            views.setTextViewText(R.id.time_isha, data.isha)

            val openAppIntent = Intent(context, MainActivity::class.java)
            val openAppPendingIntent = PendingIntent.getActivity(
                context, 0, openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.next_prayer_label, openAppPendingIntent)
            views.setOnClickPendingIntent(R.id.next_prayer_time, openAppPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private data class NextInfo(val name: String, val time: String)

        private fun calculateNextPrayer(data: PrayerTimeData): NextInfo {
            val now = Calendar.getInstance()
            val currentMin = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

            val prayers = listOf(
                "ফজর" to data.fajr,
                "জোহর" to data.dhuhr,
                "আসর" to data.asr,
                "মাগরিব" to data.maghrib,
                "ইশা" to data.isha
            )

            for ((name, time) in prayers) {
                val min = parseToMinutes(time)
                if (min > currentMin) return NextInfo(name, time)
            }

            // সব পার হলে আগামীকাল ফজর
            return NextInfo("ফজর", data.fajr)
        }

        private fun parseToMinutes(timeString: String): Int {
            return try {
                val format = SimpleDateFormat("h:mm a", Locale.US)
                val date = format.parse(timeString) ?: return 0
                val cal = Calendar.getInstance().apply { time = date }
                cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
            } catch (e: Exception) {
                0
            }
        }
    }
}