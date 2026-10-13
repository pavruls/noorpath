package com.pavruls.noorpath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arabic_progress")
data class ArabicProgressEntity(
    @PrimaryKey val id: Int = 1,
    val maxReachedIndex: Int,
    val totalViewed: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)