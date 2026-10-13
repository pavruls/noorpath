package com.pavruls.noorpath.data.sync

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncStatusManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("sync_status", Context.MODE_PRIVATE)

    private val _lastSyncTime = MutableStateFlow(getLastSyncTime())
    val lastSyncTime: StateFlow<Long> = _lastSyncTime.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    fun updateLastSyncTime(time: Long = System.currentTimeMillis()) {
        prefs.edit().putLong(KEY_LAST_SYNC, time).apply()
        _lastSyncTime.value = time
    }

    fun getLastSyncTime(): Long {
        return prefs.getLong(KEY_LAST_SYNC, 0L)
    }

    fun setSyncing(value: Boolean) {
        _isSyncing.value = value
    }

    fun getLastSyncText(): String {
        val lastSync = getLastSyncTime()
        if (lastSync == 0L) return "এখনো সিঙ্ক হয়নি"

        val diff = System.currentTimeMillis() - lastSync
        val minutes = diff / (1000 * 60)
        val hours = diff / (1000 * 60 * 60)
        val days = diff / (1000 * 60 * 60 * 24)

        return when {
            minutes < 1 -> "এইমাত্র সিঙ্ক হয়েছে"
            minutes < 60 -> "${toBanglaNumber(minutes.toInt())} মিনিট আগে"
            hours < 24 -> "${toBanglaNumber(hours.toInt())} ঘণ্টা আগে"
            else -> "${toBanglaNumber(days.toInt())} দিন আগে"
        }
    }

    private fun toBanglaNumber(n: Int): String {
        val banglaDigits = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return n.toString().map { banglaDigits[it - '0'] }.joinToString("")
    }

    companion object {
        private const val KEY_LAST_SYNC = "last_sync_time"
    }
}