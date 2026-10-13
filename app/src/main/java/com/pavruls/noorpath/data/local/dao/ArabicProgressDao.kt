package com.pavruls.noorpath.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavruls.noorpath.data.local.entity.ArabicProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArabicProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: ArabicProgressEntity)

    @Query("SELECT * FROM arabic_progress WHERE id = 1")
    fun getProgress(): Flow<ArabicProgressEntity?>

    @Query("DELETE FROM arabic_progress")
    suspend fun clearAll()
}