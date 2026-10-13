package com.pavruls.noorpath.hadith

import android.content.Context
import org.json.JSONObject

object HadithRepository {

    fun loadHadiths(context: Context, filePrefix: String): List<Hadith> {
        // ⚠️ তিন ভাষার ফাইলেই টেক্সট কী = "text"
        val arabicMap = loadLanguageMap(context, "ara-$filePrefix.min.json")
        val bengaliMap = loadLanguageMap(context, "ben-$filePrefix.min.json")
        val englishMap = loadLanguageMap(context, "eng-$filePrefix.min.json")

        val allNumbers = (arabicMap.keys + bengaliMap.keys + englishMap.keys).toSortedSet()

        val result = mutableListOf<Hadith>()
        for (num in allNumbers) {
            val arabicText = arabicMap[num] ?: ""
            val bengaliText = bengaliMap[num] ?: ""
            val englishText = englishMap[num] ?: ""

            if (arabicText.isBlank() && bengaliText.isBlank() && englishText.isBlank()) continue

            result.add(
                Hadith(
                    id = num,
                    arabic = arabicText,
                    bengali = bengaliText,
                    english = englishText
                )
            )
        }
        return result
    }

    private fun loadLanguageMap(
        context: Context,
        fileName: String
    ): Map<Int, String> {
        val map = mutableMapOf<Int, String>()
        try {
            val jsonString = context.assets.open("hadith/$fileName")
                .bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val hadithsArray = jsonObject.optJSONArray("hadiths") ?: return map

            for (i in 0 until hadithsArray.length()) {
                val item = hadithsArray.getJSONObject(i)
                val number = item.optInt("hadithnumber", -1)
                if (number <= 0) continue

                // ✅ সঠিক কী = "text"
                val text = item.optString("text", "").trim()
                if (text.isNotEmpty()) {
                    map[number] = text
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}