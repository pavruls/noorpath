package com.pavruls.noorpath.data.remote.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PrayerTimes(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val date: String,
    val city: String
)

object PrayerApi {

    suspend fun getPrayerTimes(
        latitude: Double,
        longitude: Double,
        method: Int = 1
    ): PrayerTimes? = withContext(Dispatchers.IO) {
        try {
            val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
            val urlString = "https://api.aladhan.com/v1/timings/$today" +
                    "?latitude=$latitude&longitude=$longitude&method=$method"

            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            connection.disconnect()

            val json = JSONObject(response)
            val data = json.getJSONObject("data")
            val timings = data.getJSONObject("timings")
            val dateInfo = data.getJSONObject("date")
            val readableDate = dateInfo.getString("readable")
            val meta = data.getJSONObject("meta")
            val timezone = meta.getString("timezone")

            PrayerTimes(
                fajr = formatTime(timings.getString("Fajr")),
                sunrise = formatTime(timings.getString("Sunrise")),
                dhuhr = formatTime(timings.getString("Dhuhr")),
                asr = formatTime(timings.getString("Asr")),
                maghrib = formatTime(timings.getString("Maghrib")),
                isha = formatTime(timings.getString("Isha")),
                date = readableDate,
                city = timezone
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun formatTime(time24: String): String {
        return try {
            // "05:23" বা "05:23 (BDT)" ফরম্যাট থেকে শুধু সময় নিন
            val cleanTime = time24.split(" ").first()
            val parts = cleanTime.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1]
            val amPm = if (hour < 12) "AM" else "PM"
            val hour12 = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }
            "$hour12:$minute $amPm"
        } catch (e: Exception) {
            time24
        }
    }
}