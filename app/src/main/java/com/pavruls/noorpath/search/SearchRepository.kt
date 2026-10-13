package com.pavruls.noorpath.search

import android.content.Context
import org.json.JSONArray

enum class SearchSource { ALL, QURAN, HADITH, DUA }

data class SearchHit(
    val source: SearchSource,
    val title: String,
    val arabic: String,
    val snippet: String,
    val meta: String
)

object SearchRepository {

    private var quranList: List<SearchHit> = emptyList()
    private var hadithList: List<SearchHit> = emptyList()
    private var duaList: List<SearchHit> = emptyList()
    private var loaded = false

    fun preload(context: Context) {
        if (loaded) return
        quranList = loadQuran(context)
        hadithList = loadHadith(context)
        duaList = loadDuas(context)
        loaded = true
    }

    fun search(query: String, source: SearchSource): List<SearchHit> {
        val q = query.trim()
        if (q.length < 2) return emptyList()
        val nq = normalize(q)
        val result = mutableListOf<SearchHit>()
        if (source == SearchSource.ALL || source == SearchSource.QURAN)
            result += quranList.filter { matches(it, q, nq) }
        if (source == SearchSource.ALL || source == SearchSource.HADITH)
            result += hadithList.filter { matches(it, q, nq) }
        if (source == SearchSource.ALL || source == SearchSource.DUA)
            result += duaList.filter { matches(it, q, nq) }
        return result.take(120)
    }

    private fun matches(hit: SearchHit, original: String, normQ: String): Boolean {
        return normalize(hit.arabic).contains(normQ) ||
                hit.snippet.lowercase().contains(original.lowercase()) ||
                hit.title.lowercase().contains(original.lowercase())
    }

    private fun normalize(t: String): String = t
        .replace(Regex("[\u064B-\u065F\u0670\u06D6-\u06ED]"), "")
        .replace("أ", "ا").replace("إ", "ا").replace("آ", "ا")
        .replace("ى", "ي").replace("ة", "ه")
        .replace("ؤ", "و").replace("ئ", "ي")
        .lowercase().trim()

    private fun loadQuran(context: Context): List<SearchHit> {
        val out = mutableListOf<SearchHit>()
        try {
            val json = tryReadAsset(context, "quran/quran_bn.json")
                ?: tryReadAsset(context, "tafsir/quran_bn.json")
                ?: return out

            val enMap = mutableMapOf<String, String>()
            val enJson = tryReadAsset(context, "quran/quran_en.json")
                ?: tryReadAsset(context, "tafsir/quran_en.json")
            if (enJson != null) {
                val enArr = JSONArray(enJson)
                for (i in 0 until enArr.length()) {
                    val s = enArr.getJSONObject(i)
                    val sid = s.optInt("id")
                    val vs = s.optJSONArray("verses") ?: continue
                    for (j in 0 until vs.length()) {
                        val v = vs.getJSONObject(j)
                        enMap["$sid:${v.optInt("id")}"] =
                            v.optString("translation", v.optString("english", ""))
                    }
                }
            }

            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val s = arr.getJSONObject(i)
                val sid = s.optInt("id")
                val nameBn = s.optString("translation", s.optString("name_bn", ""))
                val nameEn = s.optString("transliteration", s.optString("name_en", ""))
                val verses = s.optJSONArray("verses") ?: continue
                for (j in 0 until verses.length()) {
                    val v = verses.getJSONObject(j)
                    val vid = v.optInt("id")
                    val arabic = v.optString("text", v.optString("arabic", ""))
                    val bangla = v.optString("translation", v.optString("bengali", ""))
                    val english = enMap["$sid:$vid"] ?: ""
                    val snippet = if (bangla.isNotBlank()) bangla else english
                    if (arabic.isBlank() && snippet.isBlank()) continue
                    out.add(
                        SearchHit(
                            source = SearchSource.QURAN,
                            title = "$nameBn ($nameEn) — $vid",
                            arabic = arabic,
                            snippet = snippet,
                            meta = "$sid:$vid"
                        )
                    )
                }
            }
        } catch (e: Exception) { e.printStackTrace() }
        return out
    }

    private fun loadHadith(context: Context): List<SearchHit> {
        val out = mutableListOf<SearchHit>()
        val books = listOf(
            "bukhari" to "সহীহ বুখারী",
            "muslim" to "সহীহ মুসলিম",
            "abu_dawud" to "আবু দাউদ",
            "tirmidhi" to "তিরমিযী",
            "nasai" to "নাসাঈ",
            "ibn_majah" to "ইবনে মাজাহ"
        )
        for ((file, name) in books) {
            val json = tryReadAsset(context, "hadith/$file.json") ?: continue
            try {
                val arr = JSONArray(json)
                for (i in 0 until arr.length()) {
                    val h = arr.getJSONObject(i)
                    val no = h.optInt("id", h.optInt("hadith_no", i + 1))
                    val arabic = h.optString("arabic", h.optString("text_ar", ""))
                    val bangla = h.optString("bengali",
                        h.optString("bn", h.optString("translation", "")))
                    val english = h.optString("english",
                        h.optString("en", h.optString("translation_en", "")))
                    val snippet = if (bangla.isNotBlank()) bangla else english
                    if (arabic.isBlank() && snippet.isBlank()) continue
                    out.add(
                        SearchHit(
                            source = SearchSource.HADITH,
                            title = "$name — হাদিস $no",
                            arabic = arabic,
                            snippet = snippet,
                            meta = "$file:$no"
                        )
                    )
                }
            } catch (_: Exception) {}
        }
        return out
    }

    private fun loadDuas(context: Context): List<SearchHit> {
        val out = mutableListOf<SearchHit>()
        val json = tryReadAsset(context, "duas/duas.json")
            ?: tryReadAsset(context, "dua/duas.json")
            ?: tryReadAsset(context, "duas.json")
            ?: return out
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val d = arr.getJSONObject(i)
                val id = d.optString("id", "$i")
                val titleBn = d.optString("title_bn", d.optString("title", ""))
                val titleEn = d.optString("title_en", "")
                val arabic = d.optString("arabic", "")
                val bangla = d.optString("bangla", d.optString("bengali", ""))
                val english = d.optString("english", "")
                val title = titleBn.ifBlank { titleEn }.ifBlank { "দুআ ${i + 1}" }
                val snippet = if (bangla.isNotBlank()) bangla else english
                if (arabic.isBlank() && snippet.isBlank()) continue
                out.add(
                    SearchHit(
                        source = SearchSource.DUA,
                        title = title,
                        arabic = arabic,
                        snippet = snippet,
                        meta = id
                    )
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
        return out
    }

    private fun tryReadAsset(context: Context, path: String): String? {
        return try {
            context.assets.open(path).bufferedReader().use { it.readText() }
        } catch (_: Exception) { null }
    }
}