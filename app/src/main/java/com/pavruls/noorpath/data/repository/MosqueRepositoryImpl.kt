package com.pavruls.noorpath.data.repository

import com.pavruls.noorpath.data.remote.api.OverpassApi
import com.pavruls.noorpath.domain.model.Mosque
import javax.inject.Inject
import javax.inject.Named

class MosqueRepositoryImpl @Inject constructor(
    @Named("Api1") private val api1: OverpassApi,
    @Named("Api2") private val api2: OverpassApi,
    @Named("Api3") private val api3: OverpassApi
) : MosqueRepository {

    override suspend fun getNearbyMosques(lat: Double, lng: Double): Result<List<Mosque>> {
        val radius = 8000 // ২ কিমি
        val query = buildOverpassQuery(lat, lng, radius)

        // ⬇️ প্রথমে মিরর ১ এ চেষ্টা, ফেইল করলে মিরর ২, তারপর মিরর ৩
        val apis = listOf(api1, api2, api3)
        var lastError: Exception? = null

        for (api in apis) {
            try {
                val response = api.getNearbyMosques(query)
                val mosques = response.elements.mapNotNull { element ->
                    val mosqueLat = element.lat ?: element.center?.lat ?: return@mapNotNull null
                    val mosqueLng = element.lon ?: element.center?.lon ?: return@mapNotNull null
                    val name = element.tags?.get("name") ?: "নাম অজানা মসজিদ"
                    val address = element.tags?.get("addr:full")
                        ?: element.tags?.get("addr:street")

                    Mosque(
                        id = element.id,
                        name = name,
                        address = address,
                        lat = mosqueLat,
                        lng = mosqueLng
                    )
                }
                return Result.success(mosques)
            } catch (e: Exception) {
                lastError = e
                // এই মিরর ফেইল করেছে, পরেরটায় চেষ্টা করব
                continue
            }
        }

        return Result.failure(lastError ?: Exception("সব সার্ভারে ব্যর্থ"))
    }
    private fun buildOverpassQuery(lat: Double, lng: Double, radius: Int): String {
        return """
        [out:json][timeout:40];
        (
          node["amenity"="place_of_worship"]["religion"="muslim"](around:$radius,$lat,$lng);
          way["amenity"="place_of_worship"]["religion"="muslim"](around:$radius,$lat,$lng);
        );
        out center;
    """.trimIndent()
    }
}