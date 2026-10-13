package com.pavruls.noorpath.data.sync

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pavruls.noorpath.data.local.dao.PrayerTrackerDao
import com.pavruls.noorpath.data.local.entity.PrayerTrackerEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSyncManager @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val prayerTrackerDao: PrayerTrackerDao
) {

    private fun userCollection(collectionName: String) = firestore
        .collection("users")
        .document(auth.currentUser?.uid ?: "unknown")
        .collection(collectionName)

    // ============================================
    // Prayer Tracker Sync
    // ============================================

    suspend fun syncPrayerTrackerToCloud(): Result<Int> {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.d("FirestoreSync", "ব্যবহারকারী লগইন করা নেই")
            return Result.failure(Exception("লগইন করা নেই"))
        }

        return try {
            // ১০ বছরের ডেটা (কার্যত সব) Flow থেকে first() দিয়ে আনি
            val allData = prayerTrackerDao.getRecentDays(3650).first()
            var uploadedCount = 0

            allData.forEach { entity ->
                val data = mapOf(
                    "dateKey" to entity.dateKey,
                    "fajr" to entity.fajr,
                    "dhuhr" to entity.dhuhr,
                    "asr" to entity.asr,
                    "maghrib" to entity.maghrib,
                    "isha" to entity.isha,
                    "lastUpdated" to entity.lastUpdated
                )
                userCollection("prayer_tracker")
                    .document(entity.dateKey)
                    .set(data)
                    .await()
                uploadedCount++
            }

            Log.d("FirestoreSync", "$uploadedCount টি এন্ট্রি আপলোড হয়েছে")
            Result.success(uploadedCount)
        } catch (e: Exception) {
            Log.e("FirestoreSync", "Sync ব্যর্থ: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun syncPrayerTrackerFromCloud(): Result<Int> {
        val userId = auth.currentUser?.uid
            ?: return Result.failure(Exception("লগইন করা নেই"))

        return try {
            val snapshot = userCollection("prayer_tracker").get().await()
            var downloadedCount = 0

            snapshot.documents.forEach { doc ->
                val data = doc.data ?: return@forEach
                val entity = PrayerTrackerEntity(
                    dateKey = data["dateKey"] as? String ?: doc.id,
                    fajr = data["fajr"] as? Boolean ?: false,
                    dhuhr = data["dhuhr"] as? Boolean ?: false,
                    asr = data["asr"] as? Boolean ?: false,
                    maghrib = data["maghrib"] as? Boolean ?: false,
                    isha = data["isha"] as? Boolean ?: false,
                    lastUpdated = (data["lastUpdated"] as? Long)
                        ?: System.currentTimeMillis()
                )
                prayerTrackerDao.upsert(entity)
                downloadedCount++
            }

            Log.d("FirestoreSync", "$downloadedCount টি এন্ট্রি ডাউনলোড হয়েছে")
            Result.success(downloadedCount)
        } catch (e: Exception) {
            Log.e("FirestoreSync", "Download ব্যর্থ: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun fullSync(): Result<String> {
        return try {
            val uploadResult = syncPrayerTrackerToCloud()
            val downloadResult = syncPrayerTrackerFromCloud()

            val msg = "আপলোড: ${uploadResult.getOrNull() ?: 0}, ডাউনলোড: ${downloadResult.getOrNull() ?: 0}"
            Result.success(msg)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}