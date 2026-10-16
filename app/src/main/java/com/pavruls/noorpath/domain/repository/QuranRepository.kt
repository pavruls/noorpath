package com.pavruls.noorpath.domain.repository

import android.content.Context
import com.pavruls.noorpath.quran.Surah
import com.pavruls.noorpath.quran.Verse
import org.json.JSONArray

object QuranRepository {

    private var cachedSurahs: List<Surah>? = null
    private var cachedVerses: Map<Int, List<Verse>>? = null

    // সব সূরার লিস্ট লোড
    fun loadAllSurahs(context: Context): List<Surah> {
        if (cachedSurahs != null) return cachedSurahs!!

        val result = mutableListOf<Surah>()
        try {
            val jsonString = context.assets.open("quran/quran_bn.json")
                .bufferedReader().use { it.readText() }
            val array = JSONArray(jsonString)

            for (i in 0 until array.length()) {
                val surahObj = array.getJSONObject(i)
                result.add(
                    Surah(
                        id = surahObj.getInt("id"),
                        name = surahObj.optString("name", ""),
                        transliteration = surahObj.optString("transliteration", ""),
                        type = surahObj.optString("type", ""),
                        totalVerses = surahObj.optInt("total_verses", 0)
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        cachedSurahs = result
        return result
    }

    // নির্দিষ্ট সূরার সব আয়াত (৩ ভাষায়)
    fun loadVersesForSurah(context: Context, surahId: Int): List<Verse> {
        // ক্যাশে চেক
        if (cachedVerses == null) {
            cachedVerses = buildVersesMap(context)
        }
        return cachedVerses?.get(surahId) ?: emptyList()
    }

    // ৩ ভাষার ডেটা merge করে Map<SurahId, List<Verse>> তৈরি
    private fun buildVersesMap(context: Context): Map<Int, List<Verse>> {
        val result = mutableMapOf<Int, List<Verse>>()

        try {
            // ১. আরবি লোড
            val arabicArray = loadJsonArray(context, "quran/quran.json")
            // ২. বাংলা লোড
            val bengaliArray = loadJsonArray(context, "quran/quran_bn.json")
            // ৩. ইংরেজি লোড
            val englishArray = loadJsonArray(context, "quran/quran_en.json")
            // ৪. ট্রান্সলিটারেশন লোড
            val transArray = loadJsonArray(context, "quran/quran_transliteration.json")

            if (arabicArray == null || bengaliArray == null ||
                englishArray == null || transArray == null
            ) {
                return result
            }

            // globalVerseCounter — সম্পূর্ণ কুরআনে আয়াতের ক্রমিক নম্বর
            var globalCounter = 1

            for (i in 0 until arabicArray.length()) {
                val surahId = i + 1

                val arabicSurah = arabicArray.getJSONObject(i)
                val bengaliSurah = bengaliArray.getJSONObject(i)
                val englishSurah = englishArray.getJSONObject(i)
                val transSurah = transArray.getJSONObject(i)

                val arabicVerses = arabicSurah.getJSONArray("verses")
                val bengaliVerses = bengaliSurah.getJSONArray("verses")
                val englishVerses = englishSurah.getJSONArray("verses")
                val transVerses = transSurah.getJSONArray("verses")

                val verseList = mutableListOf<Verse>()

                for (j in 0 until arabicVerses.length()) {
                    val arabicObj = arabicVerses.getJSONObject(j)
                    val bengaliObj = bengaliVerses.getJSONObject(j)
                    val englishObj = englishVerses.getJSONObject(j)
                    val transObj = transVerses.getJSONObject(j)

                    verseList.add(
                        Verse(
                            id = arabicObj.getInt("id"),
                            globalId = globalCounter,
                            arabic = arabicObj.optString("text", ""),
                            bengali = bengaliObj.optString("translation", ""),
                            english = englishObj.optString("translation", ""),
                            transliteration = transObj.optString("translation", "")
                        )
                    )
                    globalCounter++
                }

                result[surahId] = verseList
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    private fun loadJsonArray(context: Context, path: String): JSONArray? {
        return try {
            val jsonString = context.assets.open(path)
                .bufferedReader().use { it.readText() }
            JSONArray(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ক্যাশে রিসেট
    fun clearCache() {
        cachedSurahs = null
        cachedVerses = null
    }
}