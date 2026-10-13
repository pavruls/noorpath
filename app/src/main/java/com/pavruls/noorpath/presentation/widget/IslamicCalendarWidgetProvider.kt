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

class IslamicCalendarWidgetProvider : AppWidgetProvider() {

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
        const val ACTION_REFRESH = "com.pavruls.noorpath.ISLAMIC_CALENDAR_WIDGET_REFRESH"

        fun refreshAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, IslamicCalendarWidgetProvider::class.java)
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
            val data = IslamicCalendarWidgetPrefs.getData(context)
            val views = RemoteViews(context.packageName, R.layout.widget_islamic_calendar)

            // হিজরি ও গ্রেগরিয়ান তারিখ
            views.setTextViewText(R.id.hijri_date, data.hijriDate)
            views.setTextViewText(R.id.gregorian_date, data.gregorianDate)

            // ইভেন্ট ১
            views.setTextViewText(R.id.event1_emoji, data.event1Emoji)
            views.setTextViewText(R.id.event1_name, data.event1Name)
            views.setTextViewText(R.id.event1_countdown, data.event1Countdown)

            // ইভেন্ট ২
            views.setTextViewText(R.id.event2_emoji, data.event2Emoji)
            views.setTextViewText(R.id.event2_name, data.event2Name)
            views.setTextViewText(R.id.event2_countdown, data.event2Countdown)

            // ইভেন্ট ৩
            views.setTextViewText(R.id.event3_emoji, data.event3Emoji)
            views.setTextViewText(R.id.event3_name, data.event3Name)
            views.setTextViewText(R.id.event3_countdown, data.event3Countdown)

            // Widget-এ ট্যাপ করলে অ্যাপ খুলবে
            val openAppIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.hijri_date, pendingIntent)
            views.setOnClickPendingIntent(R.id.gregorian_date, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}