package com.pavruls.noorpath.di

import android.content.Context
import androidx.room.Room
import com.pavruls.noorpath.data.local.dao.ArabicProgressDao
import com.pavruls.noorpath.data.local.dao.BookProgressDao
import com.pavruls.noorpath.data.local.dao.PrayerTrackerDao
import com.pavruls.noorpath.data.local.dao.QuizDao
import com.pavruls.noorpath.data.local.database.NoorPathDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoorPathDatabase(
        @ApplicationContext context: Context
    ): NoorPathDatabase {
        return Room.databaseBuilder(
            context,
            NoorPathDatabase::class.java,
            "noorpath_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideQuizDao(database: NoorPathDatabase): QuizDao {
        return database.quizDao()
    }

    @Provides
    @Singleton
    fun provideBookProgressDao(database: NoorPathDatabase): BookProgressDao {
        return database.bookProgressDao()
    }

    @Provides
    @Singleton
    fun provideArabicProgressDao(database: NoorPathDatabase): ArabicProgressDao {
        return database.arabicProgressDao()
    }

    @Provides
    @Singleton
    fun providePrayerTrackerDao(database: NoorPathDatabase): PrayerTrackerDao {
        return database.prayerTrackerDao()
    }
}