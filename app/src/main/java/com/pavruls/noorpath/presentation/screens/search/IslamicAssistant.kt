package com.pavruls.noorpath.search

import android.content.Context
import com.pavruls.noorpath.data.preferences.LanguageManager

/**
 * RAG-like Islamic Assistant — কোনো AI API নেই।
 *
 * কী করে:
 *   1. ইউজারের প্রশ্ন থেকে stopword বাদ দিয়ে keyword বের করে
 *   2. অ্যাপের SearchRepository-তে সার্চ করে
 *   3. relevance score অনুযায়ী সাজায়
 *   4. Source অনুযায়ী group করে
 *
 * 100% Safe — কোনো নতুন কনটেন্ট তৈরি হয় না।
 */
object IslamicAssistant {

    // ============ Data Models ============
    data class Result(
        val source: SearchSource,
        val title: String,
        val arabic: String,
        val snippet: String,
        val meta: String,
        val score: Int
    )

    data class Response(
        val originalQuery: String,
        val results: List<Result>,
        val groupedBySource: Map<SearchSource, List<Result>>,
        val totalCount: Int
    )

    // ============ Stopwords ============
    private val stopwordsBn = setOf(
        "কি", "কী", "কে", "কেন", "কোথায়", "কখন", "কিভাবে", "কীভাবে",
        "একটি", "একটা", "এর", "এই", "সেই", "ও", "আর", "বা", "এবং",
        "আমি", "আমার", "আমাকে", "আমরা", "আমাদের",
        "তুমি", "তোমার", "তোমাকে", "আপনি", "আপনার", "আপনাকে",
        "হলো", "হয়", "হয়েছে", "ছিল", "ছিলেন",
        "মানে", "সম্পর্কে", "বলতে", "বলেন", "জানতে", "চাই",
        "কিছু", "কোনো", "সব", "সবকিছু", "নাকি",
        "কত", "কতটি", "কতগুলো", "কোনটি", "কোন"
    )

    private val stopwordsEn = setOf(
        "what", "who", "when", "where", "why", "how",
        "is", "are", "was", "were", "be", "been", "being",
        "the", "a", "an", "and", "or", "of", "for", "to", "in", "on", "at",
        "about", "tell", "me", "please", "can", "you", "i", "we", "they",
        "this", "that", "these", "those", "it", "its", "my", "your", "our"
    )

    private val stopwordsAr = setOf(
        "ما", "من", "متى", "أين", "لماذا", "كيف",
        "هل", "في", "على", "عن", "إلى", "و", "أو",
        "هذا", "هذه", "ذلك", "تلك", "الذي", "التي",
        "أنا", "أنت", "هو", "هي", "نحن", "هم",
        "أخبرني", "قل", "لي", "حول"
    )

    // ============ Public API ============
    suspend fun ask(
        context: Context,
        question: String,
        language: String
    ): Response {
        val cleaned = cleanQuery(question)
        val keywords = extractKeywords(cleaned, language)

        if (keywords.isEmpty()) {
            return Response(
                originalQuery = question,
                results = emptyList(),
                groupedBySource = emptyMap(),
                totalCount = 0
            )
        }

        // Search the app's DB
        val hits = SearchRepository.search(cleaned, SearchSource.ALL)

        // Score and sort
        val scored = hits.map { hit ->
            Result(
                source = hit.source,
                title = hit.title,
                arabic = hit.arabic,
                snippet = hit.snippet,
                meta = hit.meta,
                score = computeScore(hit, keywords, cleaned)
            )
        }.sortedByDescending { it.score }

        val grouped = scored.groupBy { it.source }

        return Response(
            originalQuery = question,
            results = scored,
            groupedBySource = grouped,
            totalCount = scored.size
        )
    }

    // ============ Internals ============
    private fun cleanQuery(query: String): String {
        return query
            .trim()
            .replace(Regex("\\s+"), " ")
            .replace(Regex("[?!।,،;:]+$"), "")
            .trim()
    }

    private fun extractKeywords(query: String, language: String): List<String> {
        if (query.isBlank()) return emptyList()

        val stopwords = when (language) {
            LanguageManager.LANG_EN -> stopwordsEn
            LanguageManager.LANG_AR -> stopwordsAr
            else -> stopwordsBn
        }

        return query
            .split(Regex("\\s+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .filter { it.lowercase() !in stopwords }
            .filter { it.length >= 2 }
    }

    private fun computeScore(
        hit: SearchHit,
        keywords: List<String>,
        originalQuery: String
    ): Int {
        var score = 0
        val titleLower = hit.title.lowercase()
        val arabicLower = hit.arabic.lowercase()
        val snippetLower = hit.snippet.lowercase()
        val queryLower = originalQuery.lowercase()

        // Exact query match
        if (titleLower.contains(queryLower)) score += 100
        if (snippetLower.contains(queryLower)) score += 60

        // Keyword hits
        keywords.forEach { kw ->
            val kl = kw.lowercase()
            if (titleLower.contains(kl)) score += 30
            if (snippetLower.contains(kl)) score += 10
            if (arabicLower.contains(kl)) score += 15
        }

        // Multi-keyword bonus
        val matchedKeywords = keywords.count { kw ->
            val kl = kw.lowercase()
            titleLower.contains(kl) || snippetLower.contains(kl)
        }
        if (matchedKeywords >= 2) score += matchedKeywords * 15

        // Source priority
        score += when (hit.source) {
            SearchSource.QURAN -> 20
            SearchSource.HADITH -> 15
            SearchSource.DUA -> 10
            else -> 5
        }

        return score
    }
}