package com.pavruls.noorpath.domain.repository

import android.content.Context
import com.pavruls.noorpath.tafsir.Surah
import com.pavruls.noorpath.tafsir.Verse
import org.json.JSONArray

object TafsirRepository {

    fun getAllSurahs(context: Context): List<Surah> {
        val list = mutableListOf<Surah>()
        try {
            val json = context.assets.open("tafsir/quran_bn.json")
                .bufferedReader().use { it.readText() }
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val s = arr.getJSONObject(i)
                list.add(
                    Surah(
                        id = s.optInt("id"),
                        name = s.optString("name", ""),
                        transliteration = s.optString("transliteration", ""),
                        type = s.optString("type", ""),
                        totalVerses = s.optInt("total_verses", 0)
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun getVersesForSurah(context: Context, surahId: Int): List<Verse> {
        val bnMap = loadVerseMap(context, "tafsir/quran_bn.json", surahId)
        val enMap = loadVerseMap(context, "tafsir/quran_en.json", surahId)

        val allIds = (bnMap.keys + enMap.keys).toSortedSet()

        return allIds.map { vid ->
            Verse(
                id = vid,
                arabic = bnMap[vid]?.first ?: enMap[vid]?.first ?: "",
                bengali = bnMap[vid]?.second ?: "",
                english = enMap[vid]?.second ?: ""
            )
        }
    }

    // Map: verseId -> (arabicText, translation)
    private fun loadVerseMap(
        context: Context,
        path: String,
        surahId: Int
    ): Map<Int, Pair<String, String>> {
        val map = mutableMapOf<Int, Pair<String, String>>()
        try {
            val json = context.assets.open(path)
                .bufferedReader().use { it.readText() }
            val arr = JSONArray(json)

            for (i in 0 until arr.length()) {
                val s = arr.getJSONObject(i)
                if (s.optInt("id") != surahId) continue

                val verses = s.optJSONArray("verses") ?: return map
                for (j in 0 until verses.length()) {
                    val v = verses.getJSONObject(j)
                    val vid = v.optInt("id")
                    map[vid] = Pair(
                        v.optString("text", ""),
                        v.optString("translation", "")
                    )
                }
                break
            }
        } catch (e: Exception) {
            // quran_en.json না থাকলে চুপচাপ empty return করবে
            e.printStackTrace()
        }
        return map
    }
}