package com.pavruls.noorpath.domain.usecase

import com.pavruls.noorpath.data.repository.MosqueRepository
import com.pavruls.noorpath.domain.model.Mosque
import javax.inject.Inject

class GetNearbyMosquesUseCase @Inject constructor(
    private val repository: MosqueRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): Result<List<Mosque>> {
        return repository.getNearbyMosques(lat, lng)
    }
}
