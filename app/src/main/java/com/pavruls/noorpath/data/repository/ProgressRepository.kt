package com.pavruls.noorpath.data.repository

import com.pavruls.noorpath.data.local.dao.ArabicProgressDao
import com.pavruls.noorpath.data.local.dao.BookProgressDao
import com.pavruls.noorpath.data.local.dao.QuizDao
import com.pavruls.noorpath.data.local.entity.ArabicProgressEntity
import com.pavruls.noorpath.data.local.entity.BookProgressEntity
import com.pavruls.noorpath.data.local.entity.QuizScoreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepository @Inject constructor(
    private val quizDao: QuizDao,
    private val bookProgressDao: BookProgressDao,
    private val arabicProgressDao: ArabicProgressDao
) {

    // ============ কুইজ ============
    suspend fun saveQuizScore(score: Int, total: Int) {
        quizDao.insert(QuizScoreEntity(score = score, total = total))
    }

    fun getBestQuizScore(): Flow<QuizScoreEntity?> = quizDao.getBestScore()
    fun getQuizCount(): Flow<Int> = quizDao.getCount()
    fun getAverageQuizRatio(): Flow<Float?> = quizDao.getAverageRatio()

    // ============ বই ============
    suspend fun saveBookProgress(bookId: Int, bookTitle: String, currentPage: Int, totalPages: Int) {
        bookProgressDao.upsert(
            BookProgressEntity(
                bookId = bookId,
                bookTitle = bookTitle,
                currentPage = currentPage,
                totalPages = totalPages
            )
        )
    }

    suspend fun getBookProgress(bookId: Int): BookProgressEntity? =
        bookProgressDao.getByBookId(bookId)

    fun getBooksReadCount(): Flow<Int> = bookProgressDao.getBooksReadCount()

    // ============ আরবি হরফ ============
    suspend fun saveArabicProgress(maxReachedIndex: Int, totalViewed: Int) {
        arabicProgressDao.upsert(
            ArabicProgressEntity(
                maxReachedIndex = maxReachedIndex,
                totalViewed = totalViewed
            )
        )
    }

    fun getArabicProgress(): Flow<ArabicProgressEntity?> = arabicProgressDao.getProgress()

    // ============ সব ডেটা রিসেট ============
    suspend fun resetAllData() {
        quizDao.clearAll()
        bookProgressDao.clearAll()
        arabicProgressDao.clearAll()
    }
}