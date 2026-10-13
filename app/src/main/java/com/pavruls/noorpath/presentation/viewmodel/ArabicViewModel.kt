package com.pavruls.noorpath.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavruls.noorpath.data.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArabicViewModel @Inject constructor(
    private val repository: ProgressRepository
) : ViewModel() {

    fun saveProgress(maxIndex: Int, totalViewed: Int) {
        viewModelScope.launch {
            repository.saveArabicProgress(maxIndex, totalViewed)
        }
    }
}