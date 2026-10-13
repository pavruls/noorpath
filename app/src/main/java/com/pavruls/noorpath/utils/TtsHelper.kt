package com.pavruls.noorpath.utils

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.Locale

object TtsHelper {
    private var tts: TextToSpeech? = null
    private var isReady = false
    private var initCalled = false

    private val LOCALE_AR = Locale.forLanguageTag("ar-SA")
    private val LOCALE_BN = Locale.forLanguageTag("bn-BD")
    private val LOCALE_EN = Locale.forLanguageTag("en-US")

    fun init(context: Context) {
        if (initCalled) return
        initCalled = true
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isReady = true
            }
        }
    }

    private fun findFemaleVoice(engine: TextToSpeech, locale: Locale): Voice? {
        return try {
            val voices = engine.voices ?: return null
            // ১. একই ভাষার female voice খুঁজি
            val sameLang = voices.filter {
                it.locale.language.equals(locale.language, ignoreCase = true)
            }
            // Female keyword দিয়ে filter
            sameLang.firstOrNull { voice ->
                val name = voice.name.lowercase()
                name.contains("female") ||
                        name.contains("-f-") ||
                        name.contains("#f") ||
                        name.contains("f00") ||
                        name.contains("sfg") ||
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                                voice.name.contains("f", ignoreCase = false) && !name.contains("male"))
            } ?: sameLang.firstOrNull()
        } catch (_: Exception) {
            null
        }
    }

    private fun speak(text: String, locale: Locale) {
        val engine = tts ?: return
        if (!isReady || text.isBlank()) return

        try {
            val femaleVoice = findFemaleVoice(engine, locale)

            if (femaleVoice != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                engine.voice = femaleVoice
            } else {
                engine.language = locale
            }

            // নারীকণ্ঠের জন্য পিচ বাড়াই, রেট কমানো
            engine.setPitch(1.35f)
            engine.setSpeechRate(0.88f)
        } catch (_: Exception) {
            try {
                engine.language = locale
                engine.setPitch(1.35f)
                engine.setSpeechRate(0.88f)
            } catch (_: Exception) {}
        }

        engine.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "noor_${System.currentTimeMillis()}"
        )
    }

    fun speakArabic(text: String) = speak(text, LOCALE_AR)
    fun speakBangla(text: String) = speak(text, LOCALE_BN)
    fun speakEnglish(text: String) = speak(text, LOCALE_EN)

    fun stop() {
        try { tts?.stop() } catch (_: Exception) {}
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (_: Exception) {}
        tts = null
        isReady = false
        initCalled = false
    }
}