package com.pavruls.noorpath.presentation.widget

import android.content.Context
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.Locale

object IslamicCalendarWidgetUpdater {

    // হিজরি মাসের বাংলা নাম
    private val hijriMonthsBangla = listOf(
        "মুহাররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
        "জামাদিউল আউয়াল", "জামাদিউস সানি", "রজব", "শাবান",
        "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ"
    )

    // গ্রেগরিয়ান মাসের বাংলা নাম
    private val gregorianMonthsBangla = listOf(
        "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল",
        "মে", "জুন", "জুলাই", "আগস্ট",
        "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"
    )

    // ইসলামিক ইভেন্ট (হিজরি মাস, দিন, নাম, ইমোজি)
    private data class IslamicEvent(
        val hijriMonth: Int,
        val hijriDay: Int,
        val name: String,
        val emoji: String
    )

    private val events = listOf(
        IslamicEvent(1, 1, "মুহাররম (নতুন বছর)", "🎊"),
        IslamicEvent(1, 10, "আশুরা", "🕌"),
        IslamicEvent(3, 12, "ঈদে মিলাদুন্নবী", "🌹"),
        IslamicEvent(7, 27, "শবে মিরাজ", "✨"),
        IslamicEvent(8, 15, "শবে বরাত", "🌕"),
        IslamicEvent(9, 1, "রমজান শুরু", "🌙"),
        IslamicEvent(9, 27, "শবে কদর", "⭐"),
        IslamicEvent(10, 1, "ঈদুল ফিতর", "🎉"),
        IslamicEvent(12, 10, "ঈদুল আযহা", "🐐"),
        IslamicEvent(12, 9, "আরাফার দিন", "🕋")
    )

    /**
     * Widget আপডেট করার মূল ফাংশন
     */
    fun update(context: Context) {
        val today = LocalDate.now()
        val hijriToday = HijrahDate.from(today)

        // হিজরি তারিখ
        val hijriDay = hijriToday.get(ChronoField.DAY_OF_MONTH)
        val hijriMonth = hijriToday.get(ChronoField.MONTH_OF_YEAR)
        val hijriYear = hijriToday.get(ChronoField.YEAR)
        val hijriMonthName = hijriMonthsBangla.getOrElse(hijriMonth - 1) { "" }
        val hijriDateText = "${toBanglaNumber(hijriDay)} $hijriMonthName ${toBanglaNumber(hijriYear)} হিজরি"

        // গ্রেগরিয়ান তারিখ
        val gregorianDay = today.dayOfMonth
        val gregorianMonth = today.monthValue
        val gregorianYear = today.year
        val gregorianMonthName = gregorianMonthsBangla.getOrElse(gregorianMonth - 1) { "" }
        val gregorianDateText = "${toBanglaNumber(gregorianDay)} $gregorianMonthName ${toBanglaNumber(gregorianYear)}"

        // পরবর্তী ৩টি ইভেন্ট খুঁজে বের করি
        val upcomingEvents = findUpcomingEvents(hijriToday)

        val data = IslamicCalendarData(
            hijriDate = hijriDateText,
            gregorianDate = gregorianDateText,
            event1Emoji = upcomingEvents.getOrNull(0)?.emoji ?: "🌙",
            event1Name = upcomingEvents.getOrNull(0)?.name ?: "—",
            event1Countdown = upcomingEvents.getOrNull(0)?.countdown ?: "—",
            event2Emoji = upcomingEvents.getOrNull(1)?.emoji ?: "🌙",
            event2Name = upcomingEvents.getOrNull(1)?.name ?: "—",
            event2Countdown = upcomingEvents.getOrNull(1)?.countdown ?: "—",
            event3Emoji = upcomingEvents.getOrNull(2)?.emoji ?: "🌙",
            event3Name = upcomingEvents.getOrNull(2)?.name ?: "—",
            event3Countdown = upcomingEvents.getOrNull(2)?.countdown ?: "—"
        )

        IslamicCalendarWidgetPrefs.saveData(context, data)
        IslamicCalendarWidgetProvider.refreshAllWidgets(context)
    }

    private data class EventInfo(val name: String, val emoji: String, val countdown: String)

    /**
     * হিজরি তারিখ থেকে পরবর্তী ইভেন্টগুলো খুঁজে বের করে এবং কত দিন বাকি সেটি গণনা করে
     */
    private fun findUpcomingEvents(hijriToday: HijrahDate): List<EventInfo> {
        val today = LocalDate.now()
        val result = mutableListOf<Pair<IslamicEvent, LocalDate>>()

        // বর্তমান বছর এবং পরের বছরের ইভেন্টগুলো দেখি
        val hijriYear = hijriToday.get(ChronoField.YEAR)

        for (event in events) {
            // বর্তমান হিজরি বছরে ইভেন্ট
            val eventDate = getEventDate(hijriYear, event)
            if (eventDate != null && !eventDate.isBefore(today)) {
                result.add(event to eventDate)
            } else {
                // পরের হিজরি বছরে ইভেন্ট
                val nextYearEvent = getEventDate(hijriYear + 1, event)
                if (nextYearEvent != null) {
                    result.add(event to nextYearEvent)
                }
            }
        }

        // তারিখ অনুযায়ী সাজাই এবং প্রথম ৩টি নেই
        return result
            .sortedBy { it.second }
            .take(3)
            .map { (event, date) ->
                val daysUntil = ChronoUnit.DAYS.between(today, date)
                val countdownText = when {
                    daysUntil == 0L -> "আজ!"
                    daysUntil == 1L -> "আগামীকাল"
                    daysUntil < 30 -> "${toBanglaNumber(daysUntil.toInt())} দিন বাকি"
                    else -> "${toBanglaNumber((daysUntil / 30).toInt())} মাস বাকি"
                }
                EventInfo(
                    name = event.name,
                    emoji = event.emoji,
                    countdown = countdownText
                )
            }
    }

    /**
     * একটি নির্দিষ্ট হিজরি বছরে ইভেন্টের গ্রেগরিয়ান তারিখ বের করে
     */
    private fun getEventDate(hijriYear: Int, event: IslamicEvent): LocalDate? {
        return try {
            val hijriDate = HijrahDate.of(hijriYear, event.hijriMonth, event.hijriDay)
            LocalDate.from(hijriDate)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * ইংরেজি সংখ্যাকে বাংলা সংখ্যায় রূপান্তর
     */
    private fun toBanglaNumber(n: Int): String {
        val banglaDigits = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return n.toString().map { banglaDigits[it - '0'] }.joinToString("")
    }
}