package com.pavruls.noorpath.data.repository

import com.pavruls.noorpath.data.local.dao.PrayerTrackerDao
import com.pavruls.noorpath.data.local.entity.PrayerTrackerEntity
import com.pavruls.noorpath.data.local.entity.QadaEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrayerTrackerRepository @Inject constructor(
    private val dao: PrayerTrackerDao
) {

    fun todayKey(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }

    fun getEntryForDate(dateKey: String): Flow<PrayerTrackerEntity?> =
        dao.getByDateFlow(dateKey)

    fun getRecentDays(): Flow<List<PrayerTrackerEntity>> = dao.getRecentDays(7)

    fun getCurrentMonth(): Flow<List<PrayerTrackerEntity>> {
        val monthPrefix = SimpleDateFormat("yyyy-MM", Locale.US).format(Date())
        return dao.getMonth(monthPrefix)
    }

    suspend fun updatePrayer(
        dateKey: String,
        prayerName: String,
        value: Boolean
    ) {
        val current = dao.getByDate(dateKey) ?: PrayerTrackerEntity(dateKey = dateKey)
        val updated = when (prayerName) {
            "Fajr" -> current.copy(fajr = value)
            "Dhuhr" -> current.copy(dhuhr = value)
            "Asr" -> current.copy(asr = value)
            "Maghrib" -> current.copy(maghrib = value)
            "Isha" -> current.copy(isha = value)
            else -> current
        }
        dao.upsert(updated)
    }

    suspend fun resetAll() {
        dao.clearAll()
        dao.clearAllQada()
    }

    fun getAllQada(): Flow<List<QadaEntity>> = dao.getAllQada()

    suspend fun saveQada(prayerName: String, total: Int, completed: Int) {
        val existing = dao.getQadaByPrayer(prayerName)
        if (existing != null) {
            dao.upsertQada(existing.copy(totalQada = total, completedQada = completed))
        } else {
            dao.upsertQada(QadaEntity(prayerName = prayerName, totalQada = total, completedQada = completed))
        }
    }

    suspend fun incrementQadaCompleted(prayerName: String) {
        val existing = dao.getQadaByPrayer(prayerName) ?: return
        if (existing.completedQada < existing.totalQada) {
            dao.upsertQada(existing.copy(completedQada = existing.completedQada + 1))
        }
    }
}