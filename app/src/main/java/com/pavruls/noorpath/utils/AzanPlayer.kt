package com.pavruls.noorpath.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

object AzanPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun playAzan(context: Context, isFajr: Boolean = false) {
        stopAzan()

        try {
            val fileName = if (isFajr) "azan_fajr.mp3" else "azan.mp3"

            // assets থেকে ফাইল লোড
            val afd = context.assets.openFd("audio/azan/$fileName")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )

                setOnPreparedListener {
                    it.start()
                    Log.d("AzanPlayer", "Azan started: $fileName")
                }

                setOnCompletionListener {
                    Log.d("AzanPlayer", "Azan completed")
                    stopAzan()
                }

                setOnErrorListener { _, what, extra ->
                    Log.e("AzanPlayer", "Error: what=$what extra=$extra")
                    true
                }

                prepareAsync()
            }

            // কম্পন
            vibrate(context)

        } catch (e: Exception) {
            Log.e("AzanPlayer", "Failed to play azan: ${e.message}")
            e.printStackTrace()
        }
    }

    fun stopAzan() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying == true

    private fun vibrate(context: Context) {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                manager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            val pattern = longArrayOf(0, 500, 300, 500, 300, 500)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern, -1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}