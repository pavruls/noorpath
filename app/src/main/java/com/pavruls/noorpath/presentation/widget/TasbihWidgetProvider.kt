package com.pavruls.noorpath.presentation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.pavruls.noorpath.R

class TasbihWidgetProvider : AppWidgetProvider() {

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

        when (intent.action) {
            ACTION_INCREMENT -> {
                TasbihWidgetPrefs.incrementCount(context)
                refreshAllWidgets(context)
            }
            ACTION_RESET -> {
                TasbihWidgetPrefs.resetCount(context)
                refreshAllWidgets(context)
            }
        }
    }

    private fun refreshAllWidgets(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context, TasbihWidgetProvider::class.java)
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
        val count = TasbihWidgetPrefs.getCount(context)

        val views = RemoteViews(context.packageName, R.layout.widget_tasbih)
        views.setTextViewText(R.id.tasbih_count, toBanglaNumber(count))

        // Increment action
        val incrementIntent = Intent(context, TasbihWidgetProvider::class.java).apply {
            action = ACTION_INCREMENT
        }
        val incrementPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            incrementIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // পুরো widget বা tap button — clickable area
        views.setOnClickPendingIntent(R.id.tasbih_tap_button, incrementPendingIntent)
        views.setOnClickPendingIntent(R.id.tasbih_count, incrementPendingIntent)

        // Reset action
        val resetIntent = Intent(context, TasbihWidgetProvider::class.java).apply {
            action = ACTION_RESET
        }
        val resetPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            resetIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.tasbih_reset_button, resetPendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun toBanglaNumber(n: Int): String {
        val banglaDigits = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return n.toString().map { banglaDigits[it - '0'] }.joinToString("")
    }

    companion object {
        const val ACTION_INCREMENT = "com.pavruls.noorpath.TASBIH_INCREMENT"
        const val ACTION_RESET = "com.pavruls.noorpath.TASBIH_RESET"
    }
}