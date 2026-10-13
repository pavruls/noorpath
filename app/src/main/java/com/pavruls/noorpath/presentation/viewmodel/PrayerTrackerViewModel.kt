package com.pavruls.noorpath.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavruls.noorpath.data.repository.PrayerTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTrackerViewModel @Inject constructor(
    private val repository: PrayerTrackerRepository
) : ViewModel() {

    val todayKey: String = repository.todayKey()

    val todayEntry = repository.getEntryForDate(todayKey)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val recentDays = repository.getRecentDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentMonth = repository.getCurrentMonth()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allQada = repository.getAllQada()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun togglePrayer(prayerName: String, value: Boolean) {
        viewModelScope.launch {
            repository.updatePrayer(todayKey, prayerName, value)
        }
    }

    fun saveQada(prayerName: String, total: Int, completed: Int) {
        viewModelScope.launch {
            repository.saveQada(prayerName, total, completed)
        }
    }

    fun incrementQada(prayerName: String) {
        viewModelScope.launch {
            repository.incrementQadaCompleted(prayerName)
        }
    }

    fun resetAll() {
        viewModelScope.launch {
            repository.resetAll()
        }
    }
}