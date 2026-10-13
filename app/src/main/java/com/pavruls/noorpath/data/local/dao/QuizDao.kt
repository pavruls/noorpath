package com.pavruls.noorpath.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pavruls.noorpath.data.local.entity.QuizScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Insert
    suspend fun insert(score: QuizScoreEntity)

    @Query("SELECT * FROM quiz_scores ORDER BY timestamp DESC")
    fun getAll(): Flow<List<QuizScoreEntity>>

    @Query("SELECT * FROM quiz_scores ORDER BY score DESC LIMIT 1")
    fun getBestScore(): Flow<QuizScoreEntity?>

    @Query("SELECT COUNT(*) FROM quiz_scores")
    fun getCount(): Flow<Int>

    @Query("SELECT AVG(CAST(score AS FLOAT) / total) FROM quiz_scores")
    fun getAverageRatio(): Flow<Float?>

    @Query("DELETE FROM quiz_scores")
    suspend fun clearAll()
}