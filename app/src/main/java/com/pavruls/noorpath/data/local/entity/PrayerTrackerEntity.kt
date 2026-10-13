package com.pavruls.noorpath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_tracker")
data class PrayerTrackerEntity(
    @PrimaryKey val dateKey: String,
    val fajr: Boolean = false,
    val dhuhr: Boolean = false,
    val asr: Boolean = false,
    val maghrib: Boolean = false,
    val isha: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "qada_prayers")
data class QadaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val prayerName: String,
    val totalQada: Int = 0,
    val completedQada: Int = 0
)