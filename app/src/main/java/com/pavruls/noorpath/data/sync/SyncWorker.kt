package com.pavruls.noorpath.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val syncManager: FirestoreSyncManager,
    private val syncStatusManager: SyncStatusManager,  // ⬅️ নতুন
    private val auth: FirebaseAuth
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        if (auth.currentUser == null) {
            Log.d("SyncWorker", "ব্যবহারকারী লগইন নেই — Sync স্কিপ")
            return Result.success()
        }

        // Sync শুরু
        syncStatusManager.setSyncing(true)

        return try {
            val result = syncManager.fullSync()
            if (result.isSuccess) {
                Log.d("SyncWorker", "Sync সফল: ${result.getOrNull()}")
                // সফল হলে last sync time সেভ করি
                syncStatusManager.updateLastSyncTime()
                syncStatusManager.setSyncing(false)
                Result.success()
            } else {
                Log.e("SyncWorker", "Sync ব্যর্থ", result.exceptionOrNull())
                syncStatusManager.setSyncing(false)
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e("SyncWorker", "Sync এ এরর", e)
            syncStatusManager.setSyncing(false)
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "noorpath_sync"
    }
}