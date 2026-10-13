package com.pavruls.noorpath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_progress")
data class BookProgressEntity(
    @PrimaryKey val bookId: Int,
    val bookTitle: String,
    val currentPage: Int,
    val totalPages: Int,
    val lastRead: Long = System.currentTimeMillis()
)