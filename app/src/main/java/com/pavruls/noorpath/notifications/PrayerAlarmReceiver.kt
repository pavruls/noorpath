package com.pavruls.noorpath.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.pavruls.noorpath.utils.AzanPlayer

class PrayerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("prayer_name") ?: "নামাজ"
        val arabicName = intent.getStringExtra("arabic_name") ?: ""
        val isFajr = prayerName == "ফজর"

        Log.d("PrayerAlarmReceiver", "Prayer time: $prayerName")

        // আজান বাজানো
        AzanPlayer.playAzan(context, isFajr = isFajr)

        // নোটিফিকেশন দেখানো
        PrayerNotificationHelper.showPrayerNotification(
            context = context,
            prayerName = prayerName,
            arabicName = arabicName
        )
    }
}