package com.pavruls.noorpath.domain.model

data class Mosque(
    val id: Long,
    val name: String,
    val address: String?,
    val lat: Double,
    val lng: Double
)