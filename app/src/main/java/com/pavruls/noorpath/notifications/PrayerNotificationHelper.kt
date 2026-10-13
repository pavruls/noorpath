package com.pavruls.noorpath.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.pavruls.noorpath.MainActivity
import com.pavruls.noorpath.R

object PrayerNotificationHelper {

    private const val CHANNEL_ID = "prayer_time_channel"
    private const val CHANNEL_NAME = "নামাজের সময়"
    private const val CHANNEL_DESC = "প্রতিটি ওয়াক্তের নোটিফিকেশন"

    // চ্যানেল তৈরি (Android 8+)
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC
                enableVibration(true)
                enableLights(true)
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    // নোটিফিকেশন দেখানো
    fun showPrayerNotification(
        context: Context,
        prayerName: String,
        arabicName: String
    ) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("🕌 $prayerName এর সময় হয়েছে")
            .setContentText("$arabicName — নামাজের জন্য প্রস্তুত হোন")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$arabicName — নামাজের জন্য প্রস্তুত হোন। আল্লাহ আপনার নামাজ কবুল করুন।")
            )
            .build()

        try {
            NotificationManagerCompat.from(context)
                .notify(prayerName.hashCode(), notification)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}