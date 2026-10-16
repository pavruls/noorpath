package com.pavruls.noorpath.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object SocialLinks {

    // 🔵 Facebook
    fun openFacebook(context: Context) {
        val username = "pavruls9730" // আপনার দেওয়া আইডি
        val url = "https://www.facebook.com/$username"
        val appUri = Uri.parse("fb://facewebmodal/f?href=$url") // Facebook App
        val webUri = Uri.parse(url) // Browser fallback
        openAppOrBrowser(context, appUri, webUri, "Facebook")
    }

    // 🟣 Instagram
    fun openInstagram(context: Context) {
        val username = "pavruls" // আপনার দেওয়া ইউজারনেম
        val url = "https://www.instagram.com/$username"
        val appUri = Uri.parse("instagram://user?username=$username") // Instagram App
        val webUri = Uri.parse(url) // Browser fallback
        openAppOrBrowser(context, appUri, webUri, "Instagram")
    }

    // 🟢 Email
    fun sendEmail(context: Context) {
        val email = "amara2023alam@gmail.com" // আপনার দেওয়া ইমেইল
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            // চাইলে সাবজেক্ট ও বডি যোগ করতে পারেন:
            // putExtra(Intent.EXTRA_SUBJECT, "NoorPath App Feedback")
            // putExtra(Intent.EXTRA_TEXT, "আসসালামু আলাইকুম...")
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "কোনো ইমেইল অ্যাপ পাওয়া যায়নি", Toast.LENGTH_SHORT).show()
        }
    }

    // 🔷 Website
    fun openWebsite(context: Context) {
        val url = "https://pavruls.github.io/noorpath/" // আপনার দেওয়া ওয়েবসাইট
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "কোনো ব্রাউজার পাওয়া যায়নি", Toast.LENGTH_SHORT).show()
        }
    }

    // ===== Helper: অ্যাপ থাকলে অ্যাপ খুলবে, না থাকলে ব্রাউজার =====
    private fun openAppOrBrowser(context: Context, appUri: Uri, webUri: Uri, appName: String) {
        val intent = Intent(Intent.ACTION_VIEW, appUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // অ্যাপ ইনস্টল করা নেই, তাই ব্রাউজারে খুলবে
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(webIntent)
            } catch (e2: ActivityNotFoundException) {
                Toast.makeText(context, "$appName অ্যাপ বা ব্রাউজার পাওয়া যায়নি", Toast.LENGTH_SHORT).show()
            }
        }
    }
}