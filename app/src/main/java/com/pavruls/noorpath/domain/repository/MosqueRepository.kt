package com.pavruls.noorpath.domain.repository

import com.pavruls.noorpath.domain.model.Mosque

interface MosqueRepository {
    suspend fun getNearbyMosques(lat: Double, lng: Double): Result<List<Mosque>>
}
