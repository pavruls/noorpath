package com.pavruls.noorpath.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OverpassResponse(
    @SerializedName("elements")
    val elements: List<OverpassElement> = emptyList()
)

data class OverpassElement(
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null,
    @SerializedName("center")
    val center: OverpassCenter? = null,
    @SerializedName("tags")
    val tags: Map<String, String>? = null
)

data class OverpassCenter(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)