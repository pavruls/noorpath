package com.pavruls.noorpath.presentation.viewmodel // আপনার প্যাকেজ নেম অনুযায়ী

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavruls.noorpath.domain.model.Mosque
import com.pavruls.noorpath.domain.usecase.GetNearbyMosquesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MosqueViewModel @Inject constructor(
    private val getNearbyMosquesUseCase: GetNearbyMosquesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MosqueUiState>(MosqueUiState.Loading)
    val uiState: StateFlow<MosqueUiState> = _uiState.asStateFlow()

    fun fetchNearbyMosques(lat: Double, lng: Double) {
        viewModelScope.launch {
            _uiState.value = MosqueUiState.Loading
            getNearbyMosquesUseCase(lat, lng)
                .onSuccess { mosques ->
                    _uiState.value = MosqueUiState.Success(mosques)
                }
                .onFailure { error ->
                    _uiState.value = MosqueUiState.Error(error.message ?: "মসজিদ খুঁজে পাওয়া যায়নি")
                }
        }
    }
}

sealed interface MosqueUiState {
    object Loading : MosqueUiState
    data class Success(val mosques: List<Mosque>) : MosqueUiState
    data class Error(val message: String) : MosqueUiState
}