package com.pavruls.noorpath.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavruls.noorpath.data.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.pavruls.noorpath.presentation.widget.PrayerWidgetUpdater
@HiltViewModel
class ParentViewModel @Inject constructor(
    private val repository: ProgressRepository,

) : ViewModel() {

    val quizCount = repository.getQuizCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val bestScore = repository.getBestQuizScore()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val averageRatio = repository.getAverageQuizRatio()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val booksRead = repository.getBooksReadCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val arabicProgress = repository.getArabicProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun resetAllData() {
        viewModelScope.launch {
            repository.resetAllData()
        }
    }
}