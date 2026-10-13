package com.pavruls.noorpath.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavruls.noorpath.data.local.entity.PrayerTrackerEntity
import com.pavruls.noorpath.data.local.entity.QadaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: PrayerTrackerEntity)

    @Query("SELECT * FROM prayer_tracker WHERE dateKey = :dateKey")
    suspend fun getByDate(dateKey: String): PrayerTrackerEntity?

    @Query("SELECT * FROM prayer_tracker WHERE dateKey = :dateKey")
    fun getByDateFlow(dateKey: String): Flow<PrayerTrackerEntity?>

    @Query("SELECT * FROM prayer_tracker ORDER BY dateKey DESC LIMIT :days")
    fun getRecentDays(days: Int): Flow<List<PrayerTrackerEntity>>

    @Query("SELECT * FROM prayer_tracker WHERE dateKey LIKE :monthPrefix || '%' ORDER BY dateKey ASC")
    fun getMonth(monthPrefix: String): Flow<List<PrayerTrackerEntity>>

    @Query("DELETE FROM prayer_tracker")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQada(qada: QadaEntity)

    @Query("SELECT * FROM qada_prayers")
    fun getAllQada(): Flow<List<QadaEntity>>

    @Query("SELECT * FROM qada_prayers WHERE prayerName = :prayerName LIMIT 1")
    suspend fun getQadaByPrayer(prayerName: String): QadaEntity?

    @Query("DELETE FROM qada_prayers")
    suspend fun clearAllQada()
}