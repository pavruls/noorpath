package com.pavruls.noorpath.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object PrayerScheduler {

    private const val REQUEST_CODE_BASE = 100

    // সব নামাজের অ্যালার্ম সেট করা
    fun scheduleAllPrayers(
        context: Context,
        fajr: String,
        dhuhr: String,
        asr: String,
        maghrib: String,
        isha: String
    ) {
        val prayers = listOf(
            Triple("ফজর", "الفجر", fajr),
            Triple("যোহর", "الظهر", dhuhr),
            Triple("আসর", "العصر", asr),
            Triple("মাগরিব", "المغرب", maghrib),
            Triple("ইশা", "العشاء", isha)
        )

        prayers.forEachIndexed { index, (name, arabic, time) ->
            scheduleAlarm(context, index, name, arabic, time)
        }
    }

    private fun scheduleAlarm(
        context: Context,
        requestCode: Int,
        prayerName: String,
        arabicName: String,
        timeString: String
    ) {
        val triggerTime = parseTimeToMillis(timeString) ?: return

        // যদি সময় পেরিয়ে গেছে, আগামীকালের জন্য সেট করব
        val finalTriggerTime = if (triggerTime < System.currentTimeMillis()) {
            triggerTime + 24 * 60 * 60 * 1000L
        } else {
            triggerTime
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            putExtra("prayer_name", prayerName)
            putExtra("arabic_name", arabicName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_BASE + requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            finalTriggerTime,
                            pendingIntent
                        )
                    } else {
                        alarmManager.setAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            finalTriggerTime,
                            pendingIntent
                        )
                    }
                }
                else -> {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        finalTriggerTime,
                        pendingIntent
                    )
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
}

    // "5:23 AM" → milliseconds
    private fun parseTimeToMillis(timeString: String): Long? {
        return try {
            val format = SimpleDateFormat("h:mm a", Locale.US)
            val date = format.parse(timeString) ?: return null

            val now = Calendar.getInstance()
            val prayerCal = Calendar.getInstance().apply {
                time = date
                set(Calendar.YEAR, now.get(Calendar.YEAR))
                set(Calendar.MONTH, now.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH))
            }
            prayerCal.timeInMillis
        } catch (e: Exception) {
            null
        }
    }

    // সব অ্যালার্ম বাতিল
    fun cancelAllPrayers(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (i in 0 until 5) {
            val intent = Intent(context, PrayerAlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE_BASE + i,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.cancel(pendingIntent)
        }
    }
}