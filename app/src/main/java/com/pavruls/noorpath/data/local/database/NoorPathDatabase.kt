package com.pavruls.noorpath.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavruls.noorpath.data.local.dao.ArabicProgressDao
import com.pavruls.noorpath.data.local.dao.BookProgressDao
import com.pavruls.noorpath.data.local.dao.PrayerTrackerDao
import com.pavruls.noorpath.data.local.dao.QuizDao
import com.pavruls.noorpath.data.local.entity.ArabicProgressEntity
import com.pavruls.noorpath.data.local.entity.BookProgressEntity
import com.pavruls.noorpath.data.local.entity.PrayerTrackerEntity
import com.pavruls.noorpath.data.local.entity.QadaEntity
import com.pavruls.noorpath.data.local.entity.QuizScoreEntity

@Database(
    entities = [
        QuizScoreEntity::class,
        BookProgressEntity::class,
        ArabicProgressEntity::class,
        PrayerTrackerEntity::class,
        QadaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class NoorPathDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
    abstract fun bookProgressDao(): BookProgressDao
    abstract fun arabicProgressDao(): ArabicProgressDao
    abstract fun prayerTrackerDao(): PrayerTrackerDao
}