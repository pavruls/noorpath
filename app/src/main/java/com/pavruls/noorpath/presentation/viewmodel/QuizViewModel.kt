package com.pavruls.noorpath.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavruls.noorpath.data.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: ProgressRepository
) : ViewModel() {

    fun saveScore(score: Int, total: Int) {
        viewModelScope.launch {
            repository.saveQuizScore(score, total)
        }
    }

    fun saveProgress(bookId: Int, bookTitle: String, currentPage: Int, totalPages: Int) {
        viewModelScope.launch {
            repository.saveBookProgress(bookId, bookTitle, currentPage, totalPages)
        }
    }
}