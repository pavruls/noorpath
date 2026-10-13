package com.pavruls.noorpath.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavruls.noorpath.data.local.entity.BookProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: BookProgressEntity)

    @Query("SELECT * FROM book_progress ORDER BY lastRead DESC")
    fun getAll(): Flow<List<BookProgressEntity>>

    @Query("SELECT * FROM book_progress WHERE bookId = :bookId")
    suspend fun getByBookId(bookId: Int): BookProgressEntity?

    @Query("SELECT COUNT(DISTINCT bookId) FROM book_progress")
    fun getBooksReadCount(): Flow<Int>

    @Query("DELETE FROM book_progress")
    suspend fun clearAll()
}