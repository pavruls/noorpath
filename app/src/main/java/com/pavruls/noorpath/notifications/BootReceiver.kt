package com.pavruls.noorpath.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.pavruls.noorpath.data.preferences.PrayerDataStore

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.d("BootReceiver", "Boot received: $action")

        // শুধু নির্দিষ্ট action-এর জন্য কাজ করবে
        if (action != Intent.ACTION_BOOT_COMPLETED &&
            action != "android.intent.action.QUICKBOOT_POWERON" &&
            action != "android.intent.action.LOCKED_BOOT_COMPLETED"
        ) {
            return
        }

        try {
            // সংরক্ষিত নামাজের সময় চেক
            if (!PrayerDataStore.hasData(context)) {
                Log.d("BootReceiver", "No prayer data found")
                return
            }

            val fajr = PrayerDataStore.getFajr(context) ?: return
            val dhuhr = PrayerDataStore.getDhuhr(context) ?: return
            val asr = PrayerDataStore.getAsr(context) ?: return
            val maghrib = PrayerDataStore.getMaghrib(context) ?: return
            val isha = PrayerDataStore.getIsha(context) ?: return

            // সব অ্যালার্ম আবার সেট করা
            PrayerScheduler.scheduleAllPrayers(
                context = context,
                fajr = fajr,
                dhuhr = dhuhr,
                asr = asr,
                maghrib = maghrib,
                isha = isha
            )

            Log.d("BootReceiver", "Prayer alarms re-scheduled successfully")

        } catch (e: Exception) {
            Log.e("BootReceiver", "Error in onReceive: ${e.message}")
            e.printStackTrace()
        }
    }
}