package com.pavruls.noorpath.data.preferences

object AppStrings {

    // ============ Common ============
    fun back(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Back"
        LanguageManager.LANG_AR -> "رجوع"
        else -> "ফিরে যান"
    }

    fun close(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Close"
        LanguageManager.LANG_AR -> "إغلاق"
        else -> "বন্ধ করুন"
    }

    fun cancel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Cancel"
        LanguageManager.LANG_AR -> "إلغاء"
        else -> "বাতিল"
    }

    fun sectionsCountForTopic(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count sections"
        LanguageManager.LANG_AR -> "$count أقسام"
        else -> "${count}টি অংশ"
    }

    fun yes(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Yes"
        LanguageManager.LANG_AR -> "نعم"
        else -> "হ্যাঁ"
    }

    fun no(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "No"
        LanguageManager.LANG_AR -> "لا"
        else -> "না"
    }

    fun reset(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Reset"
        LanguageManager.LANG_AR -> "إعادة تعيين"
        else -> "রিসেট"
    }

    fun resetConfirm(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Are you sure?"
        LanguageManager.LANG_AR -> "هل أنت متأكد؟"
        else -> "আপনি কি নিশ্চিত?"
    }

    fun loading(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Loading..."
        LanguageManager.LANG_AR -> "جارٍ التحميل..."
        else -> "লোড হচ্ছে..."
    }

    // ============ Mode Selection ============
    fun appName(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "NoorPath"
        LanguageManager.LANG_AR -> "نور باث"
        else -> "নূরপাঠ"
    }

    fun selectMode(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Which mode do you want to use?"
        LanguageManager.LANG_AR -> "أي وضع تريد استخدامه؟"
        else -> "আপনি কোন মোড ব্যবহার করতে চান?"
    }

    fun kidsMode(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Kids Mode"
        LanguageManager.LANG_AR -> "وضع الأطفال"
        else -> "শিশু মোড"
    }

    fun kidsModeDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic letters, Dua, Surah & Stories"
        LanguageManager.LANG_AR -> "الحروف العربية والأدعية والسور والقصص"
        else -> "আরবি হরফ, দুআ, সূরা ও গল্প"
    }

    fun bookMode(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Book Mode"
        LanguageManager.LANG_AR -> "وضع الكتب"
        else -> "বই মোড"
    }

    fun bookModeDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tafsir, Hadith, Fiqh & Seerah"
        LanguageManager.LANG_AR -> "التفسير والحديث والفقه والسيرة"
        else -> "তাফসির, হাদিস, ফিকহ ও সিরাত"
    }

    fun quran(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Complete Quran"
        LanguageManager.LANG_AR -> "القرآن الكريم"
        else -> "সম্পূর্ণ কুরআন"
    }

    fun quranDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic + Bengali + English + Audio"
        LanguageManager.LANG_AR -> "عربي + بنغالي + إنجليزي + صوت"
        else -> "আরবি + বাংলা + English + অডিও"
    }

    fun prayerTime(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer Time"
        LanguageManager.LANG_AR -> "أوقات الصلاة"
        else -> "নামাজ ও ইবাদাত"
    }

    fun prayerTimeDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "5 prayer times via GPS"
        LanguageManager.LANG_AR -> "أوقات الصلوات الخمس عبر GPS"
        else -> "GPS অনুযায়ী ৫ ওয়াক্তের সময়"
    }

    fun prayerTracker(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer Tracker"
        LanguageManager.LANG_AR -> "متابعة الصلاة"
        else -> "নামাজ ট্র্যাকার"
    }

    fun prayerTrackerDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Daily prayer & Qada calculation"
        LanguageManager.LANG_AR -> "الصلاة اليومية وحساب القضاء"
        else -> "দৈনিক নামাজ ও কাজা হিসাব"
    }

    fun tasbih(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tasbih Counter"
        LanguageManager.LANG_AR -> "عداد التسبيح"
        else -> "তাসবিহ কাউন্টার"
    }

    fun tasbihDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Digital counter • 33/99/100"
        LanguageManager.LANG_AR -> "عداد رقمي • ٣٣/٩٩/١٠٠"
        else -> "ডিজিটাল কাউন্টার • ৩৩/৯৯/১০০"
    }

    fun dailyHadith(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Daily Hadith"
        LanguageManager.LANG_AR -> "الحديث اليومي"
        else -> "দৈনিক হাদিস"
    }

    fun dailyHadithDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Read a Hadith every day"
        LanguageManager.LANG_AR -> "اقرأ حديثًا كل يوم"
        else -> "প্রতিদিন একটি হাদিস পড়ুন"
    }

    fun zakat(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Zakat Calculator"
        LanguageManager.LANG_AR -> "حاسبة الزكاة"
        else -> "যাকাত ক্যালকুলেটর"
    }

    fun zakatDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Calculate with gold, silver, cash"
        LanguageManager.LANG_AR -> "احسب بالذهب والفضة والنقد"
        else -> "স্বর্ণ, রূপা, নগদ দিয়ে হিসাব"
    }

    fun ramadan(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Ramadan Section"
        LanguageManager.LANG_AR -> "قسم رمضان"
        else -> "রমজান সেকশন"
    }

    fun ramadanDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Sehri, Iftar, Fasting & Taraweeh"
        LanguageManager.LANG_AR -> "السحور والإفطار والصيام والتراويح"
        else -> "সেহরি, ইফতার, রোজা ও তারাবি"
    }

    fun parentLock(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Parent Protection"
        LanguageManager.LANG_AR -> "حماية الوالدين"
        else -> "অভিভাবক সুরক্ষা"
    }

    fun parentLockDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "PIN protected dashboard"
        LanguageManager.LANG_AR -> "لوحة محمية برقم سري"
        else -> "PIN দিয়ে সুরক্ষিত ড্যাশবোর্ড"
    }

    // ============ Circle Cards ============
    fun time(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Time"
        LanguageManager.LANG_AR -> "الوقت"
        else -> "সময়"
    }

    fun now(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Now"
        LanguageManager.LANG_AR -> "الآن"
        else -> "এখন"
    }

    fun qibla(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Qibla"
        LanguageManager.LANG_AR -> "القبلة"
        else -> "কিবলা"
    }

    fun direction(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Direction"
        LanguageManager.LANG_AR -> "الاتجاه"
        else -> "দিক"
    }

    fun find(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Find"
        LanguageManager.LANG_AR -> "تحديد"
        else -> "নির্ণয়"
    }

    // ============ Settings ============
    fun settings(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Settings"
        LanguageManager.LANG_AR -> "الإعدادات"
        else -> "সেটিংস"
    }

    fun appSettings(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "App settings"
        LanguageManager.LANG_AR -> "إعدادات التطبيق"
        else -> "অ্যাপের সেটিংস"
    }

    fun appearance(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🎨 Appearance"
        LanguageManager.LANG_AR -> "🎨 المظهر"
        else -> "🎨 চেহারা"
    }

    fun theme(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Theme"
        LanguageManager.LANG_AR -> "الثيم"
        else -> "থিম"
    }

    fun language(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Language"
        LanguageManager.LANG_AR -> "اللغة"
        else -> "ভাষা"
    }

    fun aboutApp(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "ℹ️ About"
        LanguageManager.LANG_AR -> "ℹ️ عن التطبيق"
        else -> "ℹ️ অ্যাপ সম্পর্কে"
    }

    fun version(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Version"
        LanguageManager.LANG_AR -> "الإصدار"
        else -> "সংস্করণ"
    }

    fun aboutUs(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "About Us"
        LanguageManager.LANG_AR -> "من نحن"
        else -> "আমাদের সম্পর্কে"
    }

    fun aboutUsDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Islamic education for children & book library"
        LanguageManager.LANG_AR -> "التعليم الإسلامي للأطفال ومكتبة الكتب"
        else -> "শিশুদের ইসলামিক শিক্ষা ও বইঘর"
    }

    fun themeSelection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Theme Selection"
        LanguageManager.LANG_AR -> "اختيار الثيم"
        else -> "থিম নির্বাচন"
    }

    fun languageSelection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Language Selection"
        LanguageManager.LANG_AR -> "اختيار اللغة"
        else -> "ভাষা নির্বাচন"
    }

    fun systemDefault(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "System Default"
        LanguageManager.LANG_AR -> "افتراضي النظام"
        else -> "সিস্টেম ডিফল্ট"
    }

    fun lightMode(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Light Mode"
        LanguageManager.LANG_AR -> "الوضع الفاتح"
        else -> "লাইট মোড"
    }

    fun darkMode(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Dark Mode"
        LanguageManager.LANG_AR -> "الوضع الداكن"
        else -> "ডার্ক মোড"
    }

    fun userExperience(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "User Experience"
        LanguageManager.LANG_AR -> "تجربة المستخدم"
        else -> "ব্যবহারকারীর অভিজ্ঞতা"
    }

    fun aboutAppDescription(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "NoorPath — Al-Quran with Bengali translation and word-by-word meaning"
        LanguageManager.LANG_AR -> "نور باث — القرآن الكريم مع الترجمة البنغالية والمعنى كلمة بكلمة"
        else -> "প্রিয় পাঠক, NoorPath — বাংলা অনুবাদ ও শব্দে-শব্দে অর্থসহ আল-কুরআন"
    }

    // ============ Kids Home ============
    fun kidsHomeTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Kids Mode"
        LanguageManager.LANG_AR -> "وضع الأطفال"
        else -> "শিশু মোড"
    }

    fun kidsHomeSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Let's start learning! 🌟"
        LanguageManager.LANG_AR -> "هيا نبدأ التعلم! 🌟"
        else -> "শেখা শুরু করি! 🌟"
    }

    fun arabicLetters(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic Letters"
        LanguageManager.LANG_AR -> "الحروف العربية"
        else -> "আরবি হরফ"
    }

    fun banglaLetters(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Bengali Letters"
        LanguageManager.LANG_AR -> "الحروف البنغالية"
        else -> "বাংলা অক্ষর"
    }

    fun englishLetters(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "English Letters"
        LanguageManager.LANG_AR -> "الحروف الإنجليزية"
        else -> "English Letters"
    }

    fun duas(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Duas"
        LanguageManager.LANG_AR -> "الأدعية"
        else -> "দুআ"
    }

    fun smallSurahs(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Short Surahs"
        LanguageManager.LANG_AR -> "سور قصيرة"
        else -> "ছোট সূরা"
    }

    fun kidsPrayer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer Learning"
        LanguageManager.LANG_AR -> "تعلم الصلاة"
        else -> "নামাজ শিক্ষা"
    }

    fun prophetStories(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prophet Stories"
        LanguageManager.LANG_AR -> "قصص الأنبياء"
        else -> "নবীদের গল্প"
    }

    fun quiz(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Quiz"
        LanguageManager.LANG_AR -> "اختبار"
        else -> "কুইজ"
    }

    // ============ Book Library ============
    fun bookLibrary(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Book Library"
        LanguageManager.LANG_AR -> "مكتبة الكتب"
        else -> "বই লাইব্রেরি"
    }

    fun bookLibrarySubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Islamic Knowledge Treasury"
        LanguageManager.LANG_AR -> "خزانة المعرفة الإسلامية"
        else -> "ইসলামিক জ্ঞান ভাণ্ডার"
    }

    fun tafsir(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tafsir"
        LanguageManager.LANG_AR -> "التفسير"
        else -> "তাফসির"
    }

    fun tafsirDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Quran Explanation"
        LanguageManager.LANG_AR -> "شرح القرآن"
        else -> "কুরআনের ব্যাখ্যা"
    }

    fun hadith(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Hadith"
        LanguageManager.LANG_AR -> "الحديث"
        else -> "হাদিস"
    }

    fun hadithDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "6 Major Hadith Books"
        LanguageManager.LANG_AR -> "٦ كتب حديث رئيسية"
        else -> "৬টি প্রধান হাদিস গ্রন্থ"
    }

    fun fiqh(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Fiqh"
        LanguageManager.LANG_AR -> "الفقه"
        else -> "ফিকহ"
    }

    fun fiqhDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Islamic Law"
        LanguageManager.LANG_AR -> "الشريعة الإسلامية"
        else -> "ইসলামিক আইন"
    }

    fun seerah(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Seerah"
        LanguageManager.LANG_AR -> "السيرة"
        else -> "সিরাত"
    }

    fun seerahDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prophet's Biography"
        LanguageManager.LANG_AR -> "سيرة النبي"
        else -> "নবীর জীবনী"
    }

    fun aqeedah(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Aqeedah"
        LanguageManager.LANG_AR -> "العقيدة"
        else -> "আকিদা"
    }

    fun aqeedahDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Islamic Belief"
        LanguageManager.LANG_AR -> "الإيمان الإسلامي"
        else -> "ইসলামিক বিশ্বাস"
    }

    fun akhlaq(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Akhlaq"
        LanguageManager.LANG_AR -> "الأخلاق"
        else -> "আখলাক"
    }

    fun akhlaqDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Character & Morality"
        LanguageManager.LANG_AR -> "الطابع والأخلاق"
        else -> "চরিত্র ও নৈতিকতা"
    }

    // ============ Prayer Time ============
    fun prayerTimeTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer Time"
        LanguageManager.LANG_AR -> "أوقات الصلاة"
        else -> "নামাজের সময়"
    }

    fun prayerTimeSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Accurate time via GPS"
        LanguageManager.LANG_AR -> "الوقت الدقيق عبر GPS"
        else -> "GPS অনুযায়ী সঠিক সময়"
    }

    fun loadingLocation(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Finding location..."
        LanguageManager.LANG_AR -> "جارٍ البحث عن الموقع..."
        else -> "লোকেশন খুঁজছি..."
    }

    fun locationPermission(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Location Permission Required"
        LanguageManager.LANG_AR -> "إذن الموقع مطلوب"
        else -> "লোকেশন পারমিশন প্রয়োজন"
    }

    fun locationPermissionDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Your location is needed to know accurate prayer times"
        LanguageManager.LANG_AR -> "موقعك مطلوب لمعرفة أوقات الصلاة الدقيقة"
        else -> "সঠিক নামাজের সময় জানতে\nআপনার লোকেশন প্রয়োজন"
    }

    fun givePermission(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Give Permission"
        LanguageManager.LANG_AR -> "منح الإذن"
        else -> "পারমিশন দিন"
    }

    fun yourLocation(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Your Location"
        LanguageManager.LANG_AR -> "موقعك"
        else -> "আপনার অবস্থান"
    }

    fun todaysPrayers(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Today's Prayer Times"
        LanguageManager.LANG_AR -> "أوقات صلاة اليوم"
        else -> "আজকের ওয়াক্তসমূহ"
    }

    fun reload(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Reload"
        LanguageManager.LANG_AR -> "إعادة تحميل"
        else -> "আবার লোড করুন"
    }

    fun nextPrayer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Next Prayer"
        LanguageManager.LANG_AR -> "الصلاة القادمة"
        else -> "পরবর্তী নামাজ"
    }

    fun fajr(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Fajr"
        LanguageManager.LANG_AR -> "الفجر"
        else -> "ফজর"
    }

    fun sunrise(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Sunrise"
        LanguageManager.LANG_AR -> "الشروق"
        else -> "সূর্যোদয়"
    }

    fun dhuhr(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Dhuhr"
        LanguageManager.LANG_AR -> "الظهر"
        else -> "যোহর"
    }

    fun asr(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Asr"
        LanguageManager.LANG_AR -> "العصر"
        else -> "আসর"
    }

    fun maghrib(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Maghrib"
        LanguageManager.LANG_AR -> "المغرب"
        else -> "মাগরিব"
    }

    fun isha(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Isha"
        LanguageManager.LANG_AR -> "العشاء"
        else -> "ইশা"
    }

    // ============ Bottom Navigation ============
    fun navHome(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Home"
        LanguageManager.LANG_AR -> "الرئيسية"
        else -> "হোম"
    }

    fun navPrayer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer"
        LanguageManager.LANG_AR -> "الصلاة"
        else -> "নামাজ"
    }

    fun navQuran(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Quran"
        LanguageManager.LANG_AR -> "القرآن"
        else -> "কুরআন"
    }

    fun navTasbih(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tasbih"
        LanguageManager.LANG_AR -> "التسبيح"
        else -> "তাসবিহ"
    }

    fun navMore(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "More"
        LanguageManager.LANG_AR -> "المزيد"
        else -> "আরও"
    }

    // ============ Tasbih Screen ============
    fun tasbihTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tasbih Counter"
        LanguageManager.LANG_AR -> "عداد التسبيح"
        else -> "তাসবিহ কাউন্টার"
    }

    fun totalLabel(lang: String, total: Int, rounds: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Total: $total • Rounds: $rounds"
        LanguageManager.LANG_AR -> "المجموع: $total • الجولات: $rounds"
        else -> "মোট: $total • রাউন্ড: $rounds"
    }

    fun tapToCount(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tap to count"
        LanguageManager.LANG_AR -> "اضغط للعد"
        else -> "ট্যাপ করুন"
    }

    fun changeHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Tap to change 🔄"
        LanguageManager.LANG_AR -> "اضغط للتغيير 🔄"
        else -> "পরিবর্তন করতে ট্যাপ করুন 🔄"
    }

    fun settingsTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Settings"
        LanguageManager.LANG_AR -> "الإعدادات"
        else -> "সেটিংস"
    }

    fun targetLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Select target"
        LanguageManager.LANG_AR -> "اختر الهدف"
        else -> "টার্গেট নির্বাচন করুন"
    }

    fun soundLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Sound"
        LanguageManager.LANG_AR -> "الصوت"
        else -> "সাউন্ড"
    }

    fun vibrationLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Vibration"
        LanguageManager.LANG_AR -> "الاهتزاز"
        else -> "কম্পন"
    }

    fun dhikrSelection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Dhikr Selection"
        LanguageManager.LANG_AR -> "اختيار الذكر"
        else -> "ধিকর নির্বাচন"
    }

    fun resetConfirmTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Reset?"
        LanguageManager.LANG_AR -> "إعادة تعيين؟"
        else -> "রিসেট করবেন?"
    }

    fun resetConfirmMessage(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "All counts (current, total, rounds) will be deleted.\n\nAre you sure?"
        LanguageManager.LANG_AR -> "سيتم حذف جميع العدادات (الحالي، الإجمالي، الجولات).\n\nهل أنت متأكد؟"
        else -> "সব কাউন্ট (বর্তমান, মোট, রাউন্ড) মুছে যাবে।\n\nআপনি কি নিশ্চিত?"
    }

    // ============ Hadith Screen ============
    fun hadithCollection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Hadith Collection"
        LanguageManager.LANG_AR -> "مجموعة الحديث"
        else -> "হাদিস কালেকশন"
    }

    fun hadithCollectionSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "6 Major Hadith Books"
        LanguageManager.LANG_AR -> "٦ كتب حديث رئيسية"
        else -> "৬টি প্রধান হাদিস গ্রন্থ"
    }

    fun hadithCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Hadiths"
        LanguageManager.LANG_AR -> "$count حديث"
        else -> "${count}টি হাদিস"
    }

    fun hadithNumber(lang: String, num: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Hadith $num"
        LanguageManager.LANG_AR -> "حديث $num"
        else -> "হাদিস $num"
    }

    fun arabicLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic"
        LanguageManager.LANG_AR -> "عربي"
        else -> "আরবি"
    }

    fun bengaliLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Bengali"
        LanguageManager.LANG_AR -> "بنغالي"
        else -> "বাংলা"
    }

    fun englishLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "English"
        LanguageManager.LANG_AR -> "إنجليزي"
        else -> "English"
    }

    // ============ Tafsir/Quran Detail ============
    fun completeQuran(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Complete Quran"
        LanguageManager.LANG_AR -> "القرآن الكريم"
        else -> "সম্পূর্ণ কুরআন"
    }

    fun surahCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Surahs • Arabic + Bengali + English"
        LanguageManager.LANG_AR -> "$count سورة • عربي + بنغالي + إنجليزي"
        else -> "${count}টি সূরা • আরবি + বাংলা + English"
    }

    fun verseCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count verses"
        LanguageManager.LANG_AR -> "$count آية"
        else -> "${count}টি আয়াত"
    }

    fun verseNumber(lang: String, num: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Verse $num"
        LanguageManager.LANG_AR -> "آية $num"
        else -> "আয়াত $num"
    }

    fun listen(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Listen"
        LanguageManager.LANG_AR -> "استمع"
        else -> "শুনুন"
    }

    fun stop(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Stop"
        LanguageManager.LANG_AR -> "إيقاف"
        else -> "থামান"
    }

    // ============ Qibla ============
    fun qiblaTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Qibla Direction"
        LanguageManager.LANG_AR -> "اتجاه القبلة"
        else -> "কিবলা দিক"
    }

    fun qiblaSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Rotate phone — arrow shows Kaaba"
        LanguageManager.LANG_AR -> "أدر الهاتف — السهم يشير إلى الكعبة"
        else -> "ফোন ঘোরান — তীর কাবার দিক দেখাবে"
    }

    fun qiblaDirection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Qibla"
        LanguageManager.LANG_AR -> "القبلة"
        else -> "কিবলা"
    }

    fun phoneDirection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Phone Direction"
        LanguageManager.LANG_AR -> "اتجاه الهاتف"
        else -> "ফোনের দিক"
    }

    fun qiblaInstructions(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Hold the phone flat and rotate. The golden arrow with 🕋 will point towards Kaaba. When the arrow is straight up, you are facing the Qibla."
        LanguageManager.LANG_AR -> "امسك الهاتف بشكل مسطح وأدره. السهم الذهبي مع 🕋 سيشير نحو الكعبة. عندما يكون السهم مستقيمًا للأعلى، فأنت تواجه القبلة."
        else -> "ফোনটি সমতলে ধরে ঘোরান। সোনালী তীর ও 🕋 কাবার দিকে ইশারা করবে। তীর সরাসরি উপরে থাকলে আপনি কিবলার দিকে মুখ করে আছেন।"
    }

    fun qiblaInstructionsTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🧭 Instructions"
        LanguageManager.LANG_AR -> "🧭 الإرشادات"
        else -> "🧭 নির্দেশনা"
    }

    fun qiblaSensorUnavailable(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "⚠️ This device does not have a compass sensor. Please determine direction using the degrees below."
        LanguageManager.LANG_AR -> "⚠️ هذا الجهاز لا يحتوي على مستشعر البوصلة. يرجى تحديد الاتجاه باستخدام الدرجات أدناه."
        else -> "⚠️ এই ডিভাইসে কম্পাস সেন্সর নেই। অনুগ্রহ করে নিচের ডিগ্রি দেখে দিক নির্ণয় করুন।"
    }

    fun qiblaDirectionHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "You are facing the Qibla"
        LanguageManager.LANG_AR -> "أنت تواجه القبلة"
        else -> "কিবলার দিকে মুখ করে আছেন"
    }

    fun directionToQibla(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Direction to Qibla"
        LanguageManager.LANG_AR -> "اتجاه القبلة"
        else -> "কিবলার দিক নির্ণয়"
    }

    // ============ Tafsir Screen ============
    fun tafsirTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Complete Quran"
        LanguageManager.LANG_AR -> "القرآن الكريم"
        else -> "সম্পূর্ণ কুরআন"
    }

    fun surahCountShort(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Surahs • Arabic + Bengali"
        LanguageManager.LANG_AR -> "$count سورة • عربي + بنغالي"
        else -> "${count}টি সূরা • আরবি + বাংলা"
    }

    fun surahTransliteration(lang: String, transliteration: String, verses: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$transliteration • $verses verses"
        LanguageManager.LANG_AR -> "$transliteration • $verses آية"
        else -> "$transliteration • $verses আয়াত"
    }

    fun bengaliTranslation(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Bengali Translation"
        LanguageManager.LANG_AR -> "الترجمة البنغالية"
        else -> "বাংলা অনুবাদ"
    }

    // ============ Fiqh Screen ============
    fun fiqhEducation(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Fiqh Learning"
        LanguageManager.LANG_AR -> "تعلم الفقه"
        else -> "ফিকহ শিক্ষা"
    }

    fun fiqhTopicCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Topics • Masala"
        LanguageManager.LANG_AR -> "$count موضوع • مسألة"
        else -> "${count}টি বিষয় • মাসআলা"
    }

    fun masalaCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Masalas"
        LanguageManager.LANG_AR -> "$count مسألة"
        else -> "${count}টি মাসআলা"
    }

    fun masalaCountWithDesc(lang: String, count: Int, desc: String): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Masalas • $desc"
        LanguageManager.LANG_AR -> "$count مسألة • $desc"
        else -> "${count}টি মাসআলা • $desc"
    }

    fun referenceLabel(lang: String, reference: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📖 Reference: $reference"
        LanguageManager.LANG_AR -> "📖 المرجع: $reference"
        else -> "📖 রেফারেন্স: $reference"
    }

    fun dailyHadithKids(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Daily Hadith"
        LanguageManager.LANG_AR -> "الحديث اليومي"
        else -> "দৈনিক হাদিস"
    }

    fun duasTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Learning Duas"
        LanguageManager.LANG_AR -> "تعلم الأدعية"
        else -> "দুআ শেখা"
    }

    fun duasCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Duas"
        LanguageManager.LANG_AR -> "$count دعاء"
        else -> "${count}টি দুআ"
    }

    fun openLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Open"
        LanguageManager.LANG_AR -> "فتح"
        else -> "খুলুন"
    }

    fun pronunciationLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Pronunciation"
        LanguageManager.LANG_AR -> "النطق"
        else -> "উচ্চারণ"
    }

    fun meaningLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Meaning"
        LanguageManager.LANG_AR -> "المعنى"
        else -> "অর্থ"
    }

    // ============ Kids Prayer ============
    fun kidsPrayerTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer Learning"
        LanguageManager.LANG_AR -> "تعلم الصلاة"
        else -> "নামাজ শিক্ষা"
    }

    fun kidsPrayerSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Learn step by step"
        LanguageManager.LANG_AR -> "تعلم خطوة بخطوة"
        else -> "ধাপে ধাপে শিখি"
    }

    fun prayerStepCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count steps"
        LanguageManager.LANG_AR -> "$count خطوة"
        else -> "${count}টি ধাপ"
    }

    // ============ Prophet Stories ============
    fun prophetStoriesTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prophet Stories"
        LanguageManager.LANG_AR -> "قصص الأنبياء"
        else -> "নবীদের গল্প"
    }

    fun prophetStoriesCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count stories"
        LanguageManager.LANG_AR -> "$count قصة"
        else -> "${count}টি গল্প"
    }

    fun storySectionBengali(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🇧🇩 Bengali"
        LanguageManager.LANG_AR -> "🇧🇩 بنغالي"
        else -> "🇧🇩 বাংলা"
    }

    fun storySectionEnglish(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🇬🇧 English"
        LanguageManager.LANG_AR -> "🇬🇧 إنجليزي"
        else -> "🇬🇧 English"
    }

    // ============ Kids Quiz ============
    fun quizTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Quiz"
        LanguageManager.LANG_AR -> "اختبار"
        else -> "কুইজ"
    }

    fun questionNumber(lang: String, current: Int, total: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Question $current / $total"
        LanguageManager.LANG_AR -> "السؤال $current / $total"
        else -> "প্রশ্ন $current / $total"
    }

    fun nextQuestionButton(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Next Question →"
        LanguageManager.LANG_AR -> "السؤال التالي ←"
        else -> "পরের প্রশ্ন →"
    }

    fun seeResultButton(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "See Result 🎉"
        LanguageManager.LANG_AR -> "شاهد النتيجة 🎉"
        else -> "ফলাফল দেখুন 🎉"
    }

    fun correctAnswer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Correct"
        LanguageManager.LANG_AR -> "صحيح"
        else -> "সঠিক"
    }

    fun wrongAnswer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Wrong"
        LanguageManager.LANG_AR -> "خطأ"
        else -> "ভুল"
    }

    fun resultExcellent(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Excellent!"
        LanguageManager.LANG_AR -> "ممتاز!"
        else -> "অসাধারণ!"
    }

    fun resultVeryGood(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Very Good!"
        LanguageManager.LANG_AR -> "جيد جدًا!"
        else -> "খুব ভালো!"
    }

    fun resultGood(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Good Try!"
        LanguageManager.LANG_AR -> "محاولة جيدة!"
        else -> "ভালো চেষ্টা!"
    }

    fun resultNeedMore(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Need More Learning!"
        LanguageManager.LANG_AR -> "تحتاج إلى مزيد من التعلم!"
        else -> "আরও শিখতে হবে!"
    }

    fun correctAnswersLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Correct Answers"
        LanguageManager.LANG_AR -> "الإجابات الصحيحة"
        else -> "সঠিক উত্তর"
    }

    fun tryAgainButton(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🔄 Try Again"
        LanguageManager.LANG_AR -> "🔄 حاول مرة أخرى"
        else -> "🔄 আবার চেষ্টা করুন"
    }

    // ============ Kids Letters ============
    fun pronunciationFullLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Pronunciation"
        LanguageManager.LANG_AR -> "النطق"
        else -> "উচ্চারণ"
    }

    fun previousLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Previous"
        LanguageManager.LANG_AR -> "السابق"
        else -> "আগের"
    }

    fun nextLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Next"
        LanguageManager.LANG_AR -> "التالي"
        else -> "পরের"
    }

    // ============ Prayer Tracker ============
    fun todayProgressLabel(lang: String, count: Int, total: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Today's progress: $count / $total"
        LanguageManager.LANG_AR -> "تقدم اليوم: $count / $total"
        else -> "আজকের অগ্রগতি: $count / $total"
    }

    fun qadaLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📋 Qada"
        LanguageManager.LANG_AR -> "📋 القضاء"
        else -> "📋 কাজা"
    }

    fun todaysPrayerLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🕌 Today's Prayers"
        LanguageManager.LANG_AR -> "🕌 صلوات اليوم"
        else -> "🕌 আজকের নামাজ"
    }

    fun weeklyReportLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📊 Last 7 Days Report"
        LanguageManager.LANG_AR -> "📊 تقرير آخر ٧ أيام"
        else -> "📊 শেষ ৭ দিনের রিপোর্ট"
    }

    fun monthlyProgressLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📅 This Month's Progress"
        LanguageManager.LANG_AR -> "📅 تقدم هذا الشهر"
        else -> "📅 এই মাসের অগ্রগতি"
    }

    fun timesCompleted(lang: String, count: Int, total: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count / $total prayers offered"
        LanguageManager.LANG_AR -> "$count / $total صلاة مؤداة"
        else -> "$count / $total ওয়াক্ত আদায়"
    }

    fun percentComplete(lang: String, percent: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$percent% complete"
        LanguageManager.LANG_AR -> "$percent% مكتمل"
        else -> "$percent% সম্পন্ন"
    }

    fun allPrayersDone(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Alhamdulillah! All prayers completed."
        LanguageManager.LANG_AR -> "الحمد لله! تمت جميع الصلوات."
        else -> "আলহামদুলিল্লাহ! সব নামাজ আদায় হয়েছে।"
    }

    fun prayedLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "✅ Done"
        LanguageManager.LANG_AR -> "✅ أديت"
        else -> "✅ আদায়"
    }

    fun remainingLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "⭕ Due"
        LanguageManager.LANG_AR -> "⭕ متبقية"
        else -> "⭕ বাকি"
    }

    fun noDataYet(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "No data yet"
        LanguageManager.LANG_AR -> "لا توجد بيانات بعد"
        else -> "এখনো কোনো ডেটা নেই"
    }

    fun dayLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Days"
        LanguageManager.LANG_AR -> "أيام"
        else -> "দিন"
    }

    fun offeredLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Offered"
        LanguageManager.LANG_AR -> "مؤداة"
        else -> "আদায়"
    }

    fun percentLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Percent"
        LanguageManager.LANG_AR -> "النسبة"
        else -> "শতকরা"
    }

    fun qadaDialogTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Qada Prayers"
        LanguageManager.LANG_AR -> "صلوات القضاء"
        else -> "কাজা নামাজ"
    }

    fun totalLabelShort(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Total"
        LanguageManager.LANG_AR -> "المجموع"
        else -> "মোট"
    }

    fun plusOneLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "+1"
        LanguageManager.LANG_AR -> "+١"
        else -> "+১"
    }

    // ============ Daily Hadith ============
    fun dailyHadithCollectionSubtitle(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Hadith Collection"
        LanguageManager.LANG_AR -> "مجموعة $count حديث"
        else -> "${count}টি হাদিসের সংগ্রহ"
    }

    fun allHadithLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📚 All Hadiths"
        LanguageManager.LANG_AR -> "📚 جميع الأحاديث"
        else -> "📚 সব হাদিস"
    }

    // ============ Zakat Screen ============
    fun zakatScreenTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Zakat Calculator"
        LanguageManager.LANG_AR -> "حاسبة الزكاة"
        else -> "যাকাত ক্যালকুলেটর"
    }

    fun zakatScreenSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Calculate your Zakat"
        LanguageManager.LANG_AR -> "احسب زكاتك"
        else -> "আপনার যাকাত হিসাব করুন"
    }

    fun zakatGoldSection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🥇 Gold"
        LanguageManager.LANG_AR -> "🥇 الذهب"
        else -> "🥇 স্বর্ণ (Gold)"
    }

    fun zakatGoldHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "7.5 tola = 87.48 gram Nisab"
        LanguageManager.LANG_AR -> "٧.٥ تولا = ٨٧.٤٨ غرام نصاب"
        else -> "৭.৫ তোলা = ৮৭.৪৮ গ্রাম নিসাব"
    }

    fun zakatSilverSection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🥈 Silver"
        LanguageManager.LANG_AR -> "🥈 الفضة"
        else -> "🥈 রূপা (Silver)"
    }

    fun zakatSilverHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "52.5 tola = 612.36 gram Nisab"
        LanguageManager.LANG_AR -> "٥٢.٥ تولا = ٦١٢.٣٦ غرام نصاب"
        else -> "৫২.৫ তোলা = ৬১২.৩৬ গ্রাম নিসাব"
    }

    fun zakatCashSection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "💵 Cash & Bank"
        LanguageManager.LANG_AR -> "💵 النقد والبنك"
        else -> "💵 নগদ ও ব্যাংক"
    }

    fun zakatCashHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Money in hand & bank"
        LanguageManager.LANG_AR -> "المال في اليد والبنك"
        else -> "হাতে ও ব্যাংকে থাকা টাকা"
    }

    fun zakatBusinessSection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🏪 Business & Other"
        LanguageManager.LANG_AR -> "🏪 التجارة وأخرى"
        else -> "🏪 ব্যবসা ও অন্যান্য"
    }

    fun zakatBusinessHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Business goods, shares, property"
        LanguageManager.LANG_AR -> "بضاعة، أسهم، عقارات"
        else -> "ব্যবসার পণ্য, শেয়ার, সম্পত্তি"
    }

    fun zakatDebtsSection(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📉 Debts (Deducted)"
        LanguageManager.LANG_AR -> "📉 الديون (تُخصم)"
        else -> "📉 ঋণ (বাদ যাবে)"
    }

    fun zakatDebtsHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Debts that must be paid"
        LanguageManager.LANG_AR -> "الديون الواجب سدادها"
        else -> "যে ঋণ পরিশোধ করতে হবে"
    }

    fun zakatInputGoldGrams(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Gold (gram)"
        LanguageManager.LANG_AR -> "الذهب (غرام)"
        else -> "স্বর্ণ (গ্রাম)"
    }

    fun zakatInputPricePerGram(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Price/gram (৳)"
        LanguageManager.LANG_AR -> "السعر/غرام (৳)"
        else -> "দাম/গ্রাম (৳)"
    }

    fun zakatInputSilverGrams(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Silver (gram)"
        LanguageManager.LANG_AR -> "الفضة (غرام)"
        else -> "রূপা (গ্রাম)"
    }

    fun zakatInputCash(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Cash in hand (৳)"
        LanguageManager.LANG_AR -> "النقد في اليد (৳)"
        else -> "হাতে নগদ (৳)"
    }

    fun zakatInputBank(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Bank balance (৳)"
        LanguageManager.LANG_AR -> "رصيد البنك (৳)"
        else -> "ব্যাংক ব্যালেন্স (৳)"
    }

    fun zakatInputBusiness(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Business goods value (৳)"
        LanguageManager.LANG_AR -> "قيمة البضاعة (৳)"
        else -> "ব্যবসার পণ্যের মূল্য (৳)"
    }

    fun zakatInputOther(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Other assets (৳)"
        LanguageManager.LANG_AR -> "أصول أخرى (৳)"
        else -> "অন্যান্য সম্পদ (৳)"
    }

    fun zakatInputDebts(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Total debts (৳)"
        LanguageManager.LANG_AR -> "إجمالي الديون (৳)"
        else -> "মোট ঋণ (৳)"
    }

    fun zakatGoldValue(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Gold value"
        LanguageManager.LANG_AR -> "قيمة الذهب"
        else -> "স্বর্ণের মূল্য"
    }

    fun zakatSilverValue(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Silver value"
        LanguageManager.LANG_AR -> "قيمة الفضة"
        else -> "রূপার মূল্য"
    }

    fun zakatSummaryTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Summary"
        LanguageManager.LANG_AR -> "الملخص"
        else -> "সারসংক্ষেপ"
    }

    fun zakatTotalAssets(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Total Assets"
        LanguageManager.LANG_AR -> "إجمالي الأصول"
        else -> "মোট সম্পদ"
    }

    fun zakatDebtsMinus(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Debts (-)"
        LanguageManager.LANG_AR -> "الديون (-)"
        else -> "ঋণ (-)"
    }

    fun zakatNetWealth(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Net Wealth"
        LanguageManager.LANG_AR -> "صافي الثروة"
        else -> "নিট সম্পদ"
    }

    fun zakatNisab(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Nisab"
        LanguageManager.LANG_AR -> "النصاب"
        else -> "নিসাব"
    }

    fun zakatObligated(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "✅ Zakat is obligatory"
        LanguageManager.LANG_AR -> "✅ الزكاة واجبة"
        else -> "✅ যাকাত ফরজ হয়েছে"
    }

    fun zakatNotObligated(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "⭕ Zakat is not obligatory"
        LanguageManager.LANG_AR -> "⭕ الزكاة غير واجبة"
        else -> "⭕ যাকাত ফরজ হয়নি"
    }

    fun zakatBelowNisab(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Your wealth is below Nisab"
        LanguageManager.LANG_AR -> "ثروتك أقل من النصاب"
        else -> "আপনার সম্পদ নিসাবের চেয়ে কম"
    }

    fun zakatToPay(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Zakat to pay (2.5%)"
        LanguageManager.LANG_AR -> "الزكاة الواجبة (٢.٥%)"
        else -> "যাকাত দিতে হবে (২.৫%)"
    }

    fun zakatViewReport(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📊 View Detailed Report"
        LanguageManager.LANG_AR -> "📊 عرض التقرير التفصيلي"
        else -> "📊 বিস্তারিত রিপোর্ট দেখুন"
    }

    fun zakatInfoTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "ℹ️ Nisab Information"
        LanguageManager.LANG_AR -> "ℹ️ معلومات النصاب"
        else -> "ℹ️ নিসাবের তথ্য"
    }

    fun zakatInfoContent(lang: String): String = when (lang) {
        LanguageManager.LANG_EN ->
            "• Gold Nisab: 7.5 tola (87.48 grams)\n" +
                    "• Silver Nisab: 52.5 tola (612.36 grams)\n" +
                    "• Zakat rate: 2.5% (1/40)\n" +
                    "• Zakat is due after one lunar year passes.\n" +
                    "• Silver Nisab is commonly used as it is lower."
        LanguageManager.LANG_AR ->
            "• نصاب الذهب: ٧.٥ تولا (٨٧.٤٨ غرام)\n" +
                    "• نصاب الفضة: ٥٢.٥ تولا (٦١٢.٣٦ غرام)\n" +
                    "• نسبة الزكاة: ٢.٥%\n" +
                    "• تجب الزكاة بعد مرور سنة هجرية.\n" +
                    "• يُستخدم نصاب الفضة عادة لأنه أقل."
        else ->
            "• স্বর্ণের নিসাব: ৭.৫ তোলা (৮৭.৪৮ গ্রাম)\n" +
                    "• রূপার নিসাব: ৫২.৫ তোলা (৬১২.৩৬ গ্রাম)\n" +
                    "• যাকাতের হার: ২.৫% (৪০ ভাগের ১ ভাগ)\n" +
                    "• নিসাব পূর্ণ হওয়ার পর এক বছর অতিক্রম হলে যাকাত ফরজ হয়।\n" +
                    "• সাধারণত রূপার নিসাব হিসাবে সস্তা হওয়ায় সেটাই ব্যবহার করা হয়।"
    }

    fun zakatReportTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Detailed Zakat Report"
        LanguageManager.LANG_AR -> "تقرير الزكاة التفصيلي"
        else -> "যাকাতের বিস্তারিত রিপোর্ট"
    }

    fun zakatReportGold(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🥇 Gold"
        LanguageManager.LANG_AR -> "🥇 الذهب"
        else -> "🥇 স্বর্ণ"
    }

    fun zakatReportSilver(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🥈 Silver"
        LanguageManager.LANG_AR -> "🥈 الفضة"
        else -> "🥈 রূপা"
    }

    fun zakatReportCash(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "💵 Cash in hand"
        LanguageManager.LANG_AR -> "💵 النقد"
        else -> "💵 হাতে নগদ"
    }

    fun zakatReportBank(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🏦 Bank"
        LanguageManager.LANG_AR -> "🏦 البنك"
        else -> "🏦 ব্যাংক"
    }

    fun zakatReportBusiness(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🏪 Business goods"
        LanguageManager.LANG_AR -> "🏪 البضاعة"
        else -> "🏪 ব্যবসার পণ্য"
    }

    fun zakatReportOther(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📦 Other assets"
        LanguageManager.LANG_AR -> "📦 أصول أخرى"
        else -> "📦 অন্যান্য সম্পদ"
    }

    fun zakatReportTotalAssets(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "💎 Total Assets"
        LanguageManager.LANG_AR -> "💎 إجمالي الأصول"
        else -> "💎 মোট সম্পদ"
    }

    fun zakatReportTotalDebts(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📉 Total Debts"
        LanguageManager.LANG_AR -> "📉 إجمالي الديون"
        else -> "📉 মোট ঋণ"
    }

    fun zakatReportNetWealth(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "✅ Net Wealth"
        LanguageManager.LANG_AR -> "✅ صافي الثروة"
        else -> "✅ নিট সম্পদ"
    }

    fun zakatReportNisab(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📐 Nisab"
        LanguageManager.LANG_AR -> "📐 النصاب"
        else -> "📐 নিসাব"
    }

    fun zakatReportZakat25(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Zakat (2.5%)"
        LanguageManager.LANG_AR -> "الزكاة (٢.٥%)"
        else -> "যাকাত (২.৫%)"
    }

    fun zakatReportNotObligated(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "⚠️ Your wealth is below Nisab, so Zakat is not obligatory."
        LanguageManager.LANG_AR -> "⚠️ ثروتك أقل من النصاب، فالزكاة غير واجبة."
        else -> "⚠️ আপনার সম্পদ নিসাবের চেয়ে কম, তাই যাকাত ফরজ হয়নি।"
    }

    // ============ Ramadan Screen ============
    fun ramadanScreenTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Ramadan Section"
        LanguageManager.LANG_AR -> "قسم رمضان"
        else -> "রমজান সেকশন"
    }

    fun ramadanScreenSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Fasting, Taraweeh & Worship"
        LanguageManager.LANG_AR -> "الصيام والتراويح والعبادة"
        else -> "রোজা, তারাবি ও ইবাদত"
    }

    fun sehriLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Sehri"
        LanguageManager.LANG_AR -> "السحور"
        else -> "সেহরি"
    }

    fun iftarLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Iftar"
        LanguageManager.LANG_AR -> "الإفطار"
        else -> "ইফতার"
    }

    fun beforeFajrHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Before Fajr"
        LanguageManager.LANG_AR -> "قبل الفجر"
        else -> "ফজরের আগে"
    }

    fun atMaghribHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "At Maghrib"
        LanguageManager.LANG_AR -> "عند المغرب"
        else -> "মাগরিবে"
    }

    fun taraweehRakatLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Taraweeh Rakats"
        LanguageManager.LANG_AR -> "ركعات التراويح"
        else -> "তারাবির রাকাত"
    }

    fun taraweehComplete(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "🎉 MashaAllah! Taraweeh complete"
        LanguageManager.LANG_AR -> "🎉 ما شاء الله! تمت التراويح"
        else -> "🎉 মাশাআল্লাহ! তারাবি সম্পন্ন"
    }

    fun taraweehResetHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Press 🔄 on top right to reset"
        LanguageManager.LANG_AR -> "اضغط 🔄 في الأعلى لإعادة التعيين"
        else -> "রিসেট করতে ডান দিকের 🔄 বাটন চাপুন"
    }

    fun fastingTrackerLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Fasting Tracker"
        LanguageManager.LANG_AR -> "متابعة الصيام"
        else -> "রোজা ট্র্যাকার"
    }

    fun fastingProgressLabel(lang: String, done: Int, total: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$done / $total days fasted"
        LanguageManager.LANG_AR -> "$done / $total يومًا من الصيام"
        else -> "$done / $total দিন রোজা রাখা হয়েছে"
    }

    fun laylatulQadrTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Laylatul Qadr"
        LanguageManager.LANG_AR -> "ليلة القدر"
        else -> "লাইলাতুল কদর"
    }

    fun laylatulQadrContent(lang: String): String = when (lang) {
        LanguageManager.LANG_EN ->
            "Seek Laylatul Qadr in the odd nights of the last ten days (21, 23, 25, 27, 29).\n\n" +
                    "Allah says: \"Laylatul Qadr is better than a thousand months.\" (Surah Al-Qadr: 3)\n\n" +
                    "Dua of this night: اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي"
        LanguageManager.LANG_AR ->
            "اطلب ليلة القدر في الليالي الوترية من العشر الأواخر (٢١، ٢٣، ٢٥، ٢٧، ٢٩).\n\n" +
                    "قال تعالى: \"ليلة القدر خير من ألف شهر\" (القدر: ٣)\n\n" +
                    "دعاء هذه الليلة: اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي"
        else ->
            "রমজানের শেষ দশকের বিজোড় রাতে (২১, ২৩, ২৫, ২৭, ২৯) লাইলাতুল কদর তালাশ করতে বলা হয়েছে।\n\n" +
                    "আল্লাহ বলেন: \"লাইলাতুল কদর হাজার মাসের চেয়ে উত্তম।\" (সূরা কদর: ৩)\n\n" +
                    "এই রাতের দুআ: اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي"
    }

    fun itikafTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Itikaf"
        LanguageManager.LANG_AR -> "الاعتكاف"
        else -> "ইতিকাফ"
    }

    fun itikafContent(lang: String): String = when (lang) {
        LanguageManager.LANG_EN ->
            "Itikaf in the mosque.kt during the last ten days of Ramadan is Sunnah.\n\n" +
                    "• Stay in the mosque.kt with intention\n" +
                    "• Refrain from worldly matters\n" +
                    "• Engage in worship and Quran recitation\n" +
                    "• Stay awake at night in worship"
        LanguageManager.LANG_AR ->
            "الاعتكاف في المسجد في العشر الأواخر من رمضان سنة.\n\n" +
                    "• البقاء في المسجد بنية\n" +
                    "• الابتعاد عن أمور الدنيا\n" +
                    "• الاشتغال بالعبادة وتلاوة القرآن\n" +
                    "• إحياء الليل بالعبادة"
        else ->
            "রমজানের শেষ দশ দিন মসজিদে ইতিকাফ করা সুন্নত।\n\n" +
                    "• নিয়ত করে মসজিদে অবস্থান করা\n" +
                    "• দুনিয়ার কাজ থেকে বিরত থাকা\n" +
                    "• ইবাদত ও কুরআন তিলাওয়াত করা\n" +
                    "• রাত জাগরণ করে ইবাদত করা"
    }

    fun ramadanDuaTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Important Ramadan Duas"
        LanguageManager.LANG_AR -> "أدعية رمضان المهمة"
        else -> "রমজানের গুরুত্বপূর্ণ দুআ"
    }

    fun iftarDuaContent(lang: String): String = when (lang) {
        LanguageManager.LANG_EN ->
            "Iftar Dua:\nاللَّهُمَّ لَكَ صُمْتُ وَبِكَ آمَنْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ\n" +
                    "\"Allahumma laka sumtu wa bika amantu wa ala rizqika aftartu\"\n\n" +
                    "Meaning: O Allah! I fasted for You, believed in You, and broke my fast with Your provision."
        LanguageManager.LANG_AR ->
            "دعاء الإفطار:\nاللَّهُمَّ لَكَ صُمْتُ وَبِكَ آمَنْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ\n\n" +
                    "المعنى: اللهم لك صمت وبك آمنت وعلى رزقك أفطرت."
        else ->
            "ইফতারের দুআ:\nاللَّهُمَّ لَكَ صُمْتُ وَبِكَ آمَنْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ\n" +
                    "\"আল্লাহুম্মা লাকা সুমতু ওয়া বিকা আমানতু ওয়া আলা রিযকিকা আফতারতু\"\n\n" +
                    "অর্থ: হে আল্লাহ! আপনার জন্য আমি রোজা রেখেছি, আপনার উপর ঈমান এনেছি এবং আপনার দেওয়া রিজিক দিয়ে ইফতার করছি।"
    }

    fun ramadanResetTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Reset?"
        LanguageManager.LANG_AR -> "إعادة تعيين؟"
        else -> "রিসেট করবেন?"
    }

    fun ramadanResetMessage(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Fasting tracker and Taraweeh rakats will be deleted.\n\nAre you sure?"
        LanguageManager.LANG_AR -> "سيتم حذف متابعة الصيام وركعات التراويح.\n\nهل أنت متأكد؟"
        else -> "রোজা ট্র্যাকার ও তারাবির রাকাত সব মুছে যাবে।\n\nআপনি কি নিশ্চিত?"
    }

    fun resetYes(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Yes, Reset"
        LanguageManager.LANG_AR -> "نعم، إعادة تعيين"
        else -> "হ্যাঁ, রিসেট করুন"
    }

    // ============ Parent PIN ============
    fun parentProtectionTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Parent Protection"
        LanguageManager.LANG_AR -> "حماية الوالدين"
        else -> "অভিভাবক সুরক্ষা"
    }

    fun enterPinLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Enter PIN Code"
        LanguageManager.LANG_AR -> "أدخل الرمز السري"
        else -> "PIN কোড দিন"
    }

    fun wrongPin(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Wrong PIN! Try again."
        LanguageManager.LANG_AR -> "الرمز خاطئ! حاول مرة أخرى."
        else -> "ভুল PIN! আবার চেষ্টা করুন।"
    }

    // ============ Parent Dashboard ============
    fun parentDashboardTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Parent Dashboard"
        LanguageManager.LANG_AR -> "لوحة الوالدين"
        else -> "অভিভাবক ড্যাশবোর্ড"
    }

    fun parentDashboardSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "View your child's progress"
        LanguageManager.LANG_AR -> "شاهد تقدم طفلك"
        else -> "সন্তানের অগ্রগতি দেখুন"
    }

    fun assalamuAlaikum(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Assalamu Alaikum"
        LanguageManager.LANG_AR -> "السلام عليكم"
        else -> "আসসালামু আলাইকুম"
    }

    fun seeChildProgress(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "See your child's progress"
        LanguageManager.LANG_AR -> "شاهد تقدم طفلك"
        else -> "আপনার সন্তানের অগ্রগতি দেখুন"
    }

    fun todayActivitiesLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "📊 Today's Activities"
        LanguageManager.LANG_AR -> "📊 أنشطة اليوم"
        else -> "📊 আজকের কার্যক্রম"
    }

    fun bestQuizScore(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Best Quiz Score"
        LanguageManager.LANG_AR -> "أفضل نتيجة اختبار"
        else -> "সেরা কুইজ স্কোর"
    }

    fun quizzesPlayed(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Quizzes Played"
        LanguageManager.LANG_AR -> "اختبارات"
        else -> "কুইজ খেলা"
    }

    fun averageScore(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Average Score"
        LanguageManager.LANG_AR -> "متوسط النتيجة"
        else -> "গড় স্কোর"
    }

    fun booksReadLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Books Read"
        LanguageManager.LANG_AR -> "كتب مقروءة"
        else -> "বই পড়া"
    }

    fun arabicLettersLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic Letters"
        LanguageManager.LANG_AR -> "الحروف العربية"
        else -> "আরবি হরফ"
    }

    fun viewedLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Viewed"
        LanguageManager.LANG_AR -> "تمت المشاهدة"
        else -> "দেখা হয়েছে"
    }

    fun settingsLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "⚙️ Settings"
        LanguageManager.LANG_AR -> "⚙️ الإعدادات"
        else -> "⚙️ সেটিংস"
    }

    fun dailyScreenTime(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Daily Screen Time"
        LanguageManager.LANG_AR -> "وقت الشاشة اليومي"
        else -> "দৈনিক স্ক্রিন টাইম"
    }

    fun currentLabel(lang: String, value: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Current: $value"
        LanguageManager.LANG_AR -> "الحالي: $value"
        else -> "বর্তমান: $value"
    }

    fun changePinLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Change PIN"
        LanguageManager.LANG_AR -> "تغيير الرمز السري"
        else -> "PIN পরিবর্তন"
    }

    fun setNewPinLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Set a new PIN"
        LanguageManager.LANG_AR -> "تعيين رمز سري جديد"
        else -> "নতুন PIN সেট করুন"
    }

    fun resetAllDataLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Reset All Data"
        LanguageManager.LANG_AR -> "إعادة تعيين جميع البيانات"
        else -> "সব ডেটা রিসেট"
    }

    fun deleteChildProgress(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Delete child's progress"
        LanguageManager.LANG_AR -> "حذف تقدم الطفل"
        else -> "শিশুর অগ্রগতি মুছে ফেলুন"
    }

    fun saveLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Save"
        LanguageManager.LANG_AR -> "حفظ"
        else -> "সেভ করুন"
    }

    fun okLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "OK"
        LanguageManager.LANG_AR -> "موافق"
        else -> "ঠিক আছে"
    }

    fun selectScreenTimePrompt(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Select daily limit for your child:"
        LanguageManager.LANG_AR -> "اختر الحد اليومي لطفلك:"
        else -> "সন্তানের জন্য দৈনিক সময়সীমা নির্বাচন করুন:"
    }

    fun minutesLabel(lang: String, mins: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$mins minutes"
        LanguageManager.LANG_AR -> "$mins دقيقة"
        else -> "${mins} মিনিট"
    }

    fun oneHour(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "1 hour"
        LanguageManager.LANG_AR -> "ساعة واحدة"
        else -> "১ ঘণ্টা"
    }

    fun oneAndHalfHour(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "1.5 hours"
        LanguageManager.LANG_AR -> "ساعة ونصف"
        else -> "১.৫ ঘণ্টা"
    }

    fun twoHours(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "2 hours"
        LanguageManager.LANG_AR -> "ساعتان"
        else -> "২ ঘণ্টা"
    }

    fun threeHours(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "3 hours"
        LanguageManager.LANG_AR -> "٣ ساعات"
        else -> "৩ ঘণ্টা"
    }

    fun fourHours(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "4 hours"
        LanguageManager.LANG_AR -> "٤ ساعات"
        else -> "৪ ঘণ্টা"
    }

    fun unlimitedLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Unlimited"
        LanguageManager.LANG_AR -> "غير محدود"
        else -> "আনলিমিটেড"
    }

    fun currentPinLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Current PIN"
        LanguageManager.LANG_AR -> "الرمز الحالي"
        else -> "বর্তমান PIN"
    }

    fun newPinLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "New PIN (4 digits)"
        LanguageManager.LANG_AR -> "رمز جديد (٤ أرقام)"
        else -> "নতুন PIN (৪ ডিজিট)"
    }

    fun newPinAgainLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "New PIN again"
        LanguageManager.LANG_AR -> "الرمز الجديد مرة أخرى"
        else -> "নতুন PIN আবার"
    }

    fun wrongCurrentPin(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Current PIN is wrong!"
        LanguageManager.LANG_AR -> "الرمز الحالي خاطئ!"
        else -> "বর্তমান PIN ভুল!"
    }

    fun pinMustBe4(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "New PIN must be 4 digits"
        LanguageManager.LANG_AR -> "يجب أن يكون الرمز ٤ أرقام"
        else -> "নতুন PIN ৪ ডিজিটের হতে হবে"
    }

    fun pinMismatch(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "New PINs don't match"
        LanguageManager.LANG_AR -> "الرموز الجديدة غير متطابقة"
        else -> "নতুন PIN দুবার এক হয়নি"
    }

    fun resetAllDataTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Reset all data?"
        LanguageManager.LANG_AR -> "إعادة تعيين جميع البيانات؟"
        else -> "সব ডেটা রিসেট?"
    }

    fun resetAllDataMessage(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "All quiz scores, reading records, and Arabic progress will be deleted.\n\nAre you sure?"
        LanguageManager.LANG_AR -> "سيتم حذف جميع درجات الاختبار والسجلات وتقدم اللغة العربية.\n\nهل أنت متأكد؟"
        else -> "সব কুইজ স্কোর, পড়া বই ও আরবি হরফের প্রগ্রেস মুছে যাবে।\n\nআপনি কি নিশ্চিত?"
    }

    fun yesDeleteAll(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Yes, Delete All"
        LanguageManager.LANG_AR -> "نعم، احذف الكل"
        else -> "হ্যাঁ, মুছে ফেলুন"
    }

    fun successLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Success!"
        LanguageManager.LANG_AR -> "نجاح!"
        else -> "সফল!"
    }

    fun allDataDeletedMessage(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "All data has been deleted successfully."
        LanguageManager.LANG_AR -> "تم حذف جميع البيانات بنجاح."
        else -> "সব ডেটা সফলভাবে মুছে ফেলা হয়েছে।"
    }

    // ============ Seerah / Aqeedah / Akhlaq ============
    fun seerahFullTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Seerah of the Prophet"
        LanguageManager.LANG_AR -> "السيرة النبوية"
        else -> "সিরাতুন নবী"
    }

    fun seerahFullSubtitle(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count stages • Biography of the Prophet (PBUH)"
        LanguageManager.LANG_AR -> "$count مرحلة • سيرة النبي ﷺ"
        else -> "${count}টি পর্যায় • নবী (সা.) এর জীবনী"
    }

    fun periodWithSections(lang: String, period: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$period • $count sections"
        LanguageManager.LANG_AR -> "$period • $count قسم"
        else -> "$period • ${count}টি অংশ"
    }

    fun arabicNamePeriod(lang: String, arabicName: String, period: String): String = when (lang) {
        LanguageManager.LANG_EN -> "$arabicName • $period"
        LanguageManager.LANG_AR -> "$arabicName • $period"
        else -> "$arabicName • $period"
    }

    fun aqeedahFullTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Aqeedah Learning"
        LanguageManager.LANG_AR -> "تعلم العقيدة"
        else -> "আকিদা শিক্ষা"
    }

    fun aqeedahFullSubtitle(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count topics • Islamic Belief"
        LanguageManager.LANG_AR -> "$count موضوع • الإيمان الإسلامي"
        else -> "${count}টি বিষয় • ইসলামিক বিশ্বাস"
    }

    fun masalaCountAqeedah(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Masalas"
        LanguageManager.LANG_AR -> "$count مسألة"
        else -> "${count}টি মাসআলা"
    }

    fun akhlaqFullTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Akhlaq Learning"
        LanguageManager.LANG_AR -> "تعلم الأخلاق"
        else -> "আখলাক শিক্ষা"
    }

    fun akhlaqFullSubtitle(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count topics • Character & Morality"
        LanguageManager.LANG_AR -> "$count موضوع • الطابع والأخلاق"
        else -> "${count}টি বিষয় • চরিত্র ও নৈতিকতা"
    }

    fun sectionsCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count sections"
        LanguageManager.LANG_AR -> "$count أقسام"
        else -> "${count}টি অংশ"
    }

    fun sectionsWithDesc(lang: String, count: Int, desc: String): String = when (lang) {
        LanguageManager.LANG_EN -> "$count sections • $desc"
        LanguageManager.LANG_AR -> "$count أقسام • $desc"
        else -> "${count}টি অংশ • $desc"
    }

    // ============ Prayer Hub ============
    fun prayerHubTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer & Worship"
        LanguageManager.LANG_AR -> "الصلاة والعبادة"
        else -> "নামাজ ও ইবাদত"
    }

    fun prayerHubSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "All prayer-related features"
        LanguageManager.LANG_AR -> "جميع ميزات الصلاة"
        else -> "সব নামাজ-সংক্রান্ত ফিচার"
    }

    fun prayerHubShortDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Prayer, Qibla, Tracker, Tasbih, Zakat, Ramadan"
        LanguageManager.LANG_AR -> "الصلاة، القبلة، المتابعة، التسبيح، الزكاة، رمضان"
        else -> "নামাজ, কিবলা, ট্র্যাকার, তাসবিহ, যাকাত, রমজান"
    }

    // ============ Mosque Finder ============
    fun mosqueFinderTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Mosque Finder"
        LanguageManager.LANG_AR -> "باحث عن المسجد"
        else -> "মসজিদ ফাইন্ডার"
    }

    fun mosqueFinderSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Find nearby mosques"
        LanguageManager.LANG_AR -> "ابحث عن المساجد القريبة"
        else -> "আশেপাশের মসজিদ খুঁজুন"
    }

    fun nearbyMosques(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Nearby Mosques ($count)"
        LanguageManager.LANG_AR -> "المساجد القريبة ($count)"
        else -> "নিকটবর্তী মসজিদ (${count}টি)"
    }

    fun noMosquesFound(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "No mosque.kt found within 3 km.\nPlease try another area."
        LanguageManager.LANG_AR -> "لم يتم العثور على مسجد في نطاق 3 كيلومترات.\nيرجى المحاولة في منطقة أخرى."
        else -> "৩ কিলোমিটারের মধ্যে কোনো মসজিদ পাওয়া যায়নি।\nঅনুগ্রহ করে অন্য এলাকায় চেষ্টা করুন।"
    }

    fun addressNotFound(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Address not found"
        LanguageManager.LANG_AR -> "العنوان غير موجود"
        else -> "ঠিকানা পাওয়া যায়নি"
    }

    // ============ Time Remaining ============
    fun timeRemaining(lang: String, hours: Int, minutes: Int): String = when (lang) {
        LanguageManager.LANG_EN -> if (hours > 0) "$hours hours $minutes minutes left" else "$minutes minutes left"
        LanguageManager.LANG_AR -> if (hours > 0) "باقي $hours ساعات و $minutes دقائق" else "باقي $minutes دقائق"
        else -> if (hours > 0) "$hours ঘণ্টা $minutes মিনিট বাকি" else "$minutes মিনিট বাকি"
    }
    // ============ Letters Hub ============
    fun lettersHubTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Letters Learning"
        LanguageManager.LANG_AR -> "تعلم الحروف"
        else -> "হরফ শেখা"
    }

    fun lettersHubSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic, Bengali & English"
        LanguageManager.LANG_AR -> "العربية والبنغالية والإنجليزية"
        else -> "আরবি, বাংলা ও ইংরেজি"
    }

    fun lettersHubShortDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Arabic, Bengali & English letters"
        LanguageManager.LANG_AR -> "الحروف العربية والبنغالية والإنجليزية"
        else -> "আরবি, বাংলা ও ইংরেজি হরফ শেখা"
    }

    fun lettersHubArabicDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "28 Arabic letters with pronunciation"
        LanguageManager.LANG_AR -> "٢٨ حرفًا عربيًا مع النطق"
        else -> "২৮টি আরবি হরফ উচ্চারণ সহ"
    }

    fun lettersHubBanglaDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Bengali alphabet for beginners"
        LanguageManager.LANG_AR -> "الأبجدية البنغالية للمبتدئين"
        else -> "শিশুদের জন্য বাংলা বর্ণমালা"
    }

    fun lettersHubEnglishDesc(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "A-Z English alphabet with phonics"
        LanguageManager.LANG_AR -> "الأبجدية الإنجليزية A-Z مع الصوتيات"
        else -> "A-Z English বর্ণমালা উচ্চারণ সহ"
    }
    // ============ Surahs (Kids) ============
    fun surahsCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count Surahs"
        LanguageManager.LANG_AR -> "$count سورة"
        else -> "${count}টি সূরা"
    }

    fun surahMeaningWithVerses(lang: String, meaning: String, verses: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$meaning • $verses verses"
        LanguageManager.LANG_AR -> "$meaning • $verses آية"
        else -> "$meaning • ${verses}টি আয়াত"
    }
    // ============ Surah Detail ============
    fun surahNumberLabel(lang: String, number: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Surah No. $number"
        LanguageManager.LANG_AR -> "سورة رقم $number"
        else -> "সূরা নং $number"
    }
    // ============ Quran Audio ============
    fun nowPlaying(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Now Playing"
        LanguageManager.LANG_AR -> "يتم التشغيل الآن"
        else -> "এখন বাজছে"
    }

    fun stopAudio(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Stop"
        LanguageManager.LANG_AR -> "إيقاف"
        else -> "থামান"
    }

    fun playAudio(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Play audio"
        LanguageManager.LANG_AR -> "تشغيل الصوت"
        else -> "অডিও চালান"
    }
    // ============ Search Screen ============
    fun searchTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Search"
        LanguageManager.LANG_AR -> "بحث"
        else -> "অনুসন্ধান"
    }

    fun searchHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Search Quran, Hadith, Duas..."
        LanguageManager.LANG_AR -> "ابحث في القرآن والحديث والأدعية..."
        else -> "কুরআন, হাদিস, দুআ খুঁজুন..."
    }

    fun searchTypeAtLeast(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Type at least 2 characters"
        LanguageManager.LANG_AR -> "اكتب حرفين على الأقل"
        else -> "কমপক্ষে ২টি অক্ষর লিখুন"
    }

    fun searchNoResults(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "No results found"
        LanguageManager.LANG_AR -> "لا توجد نتائج"
        else -> "কিছু পাওয়া যায়নি"
    }

    fun searchFilterAll(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "All"
        LanguageManager.LANG_AR -> "الكل"
        else -> "সব"
    }

    fun searchFilterQuran(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Quran"
        LanguageManager.LANG_AR -> "القرآن"
        else -> "কুরআন"
    }

    fun searchFilterHadith(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Hadith"
        LanguageManager.LANG_AR -> "الحديث"
        else -> "হাদিস"
    }

    fun searchFilterDua(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Duas"
        LanguageManager.LANG_AR -> "الأدعية"
        else -> "দুআ"
    }

    fun searchResultsCount(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "$count results"
        LanguageManager.LANG_AR -> "$count نتيجة"
        else -> "${count}টি ফলাফল"
    }
    // ============ Islamic Assistant ============
    fun assistantTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Islamic Assistant"
        LanguageManager.LANG_AR -> "المساعد الإسلامي"
        else -> "ইসলামিক সহকারী"
    }

    fun assistantSubtitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Ask anything about Islam"
        LanguageManager.LANG_AR -> "اسأل أي شيء عن الإسلام"
        else -> "ইসলাম সম্পর্কে যেকোনো প্রশ্ন করুন"
    }

    fun assistantHint(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Type your question..."
        LanguageManager.LANG_AR -> "اكتب سؤالك..."
        else -> "আপনার প্রশ্ন লিখুন..."
    }

    fun assistantSearching(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Searching..."
        LanguageManager.LANG_AR -> "جارٍ البحث..."
        else -> "খুঁজছি..."
    }

    fun assistantNoResults(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "No related content found in the app"
        LanguageManager.LANG_AR -> "لم يتم العثور على محتوى ذي صلة في التطبيق"
        else -> "অ্যাপে সম্পর্কিত কোনো কনটেন্ট পাওয়া যায়নি"
    }

    fun assistantDisclaimer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "⚠️ Results are from NoorPath's own content (Quran, Hadith, Duas, etc.). For final rulings, consult a scholar."
        LanguageManager.LANG_AR -> "⚠️ النتائج من محتوى نور باث (القرآن، الحديث، الأدعية، إلخ). للفتوى النهائية، استشر عالمًا."
        else -> "⚠️ ফলাফল NoorPath-এর নিজস্ব কনটেন্ট থেকে (কুরআন, হাদিস, দুআ ইত্যাদি)। চূড়ান্ত মাসআলার জন্য আলেমের পরামর্শ নিন।"
    }

    fun assistantDisclaimerShort(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "AI-like search, no new content generated"
        LanguageManager.LANG_AR -> "بحث شبيه بالذكاء الاصطناعي، بدون محتوى جديد"
        else -> "AI-সদৃশ সার্চ, নতুন কিছু তৈরি হয় না"
    }

    fun assistantYouAsked(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "You asked"
        LanguageManager.LANG_AR -> "سألت"
        else -> "আপনি জিজ্ঞেস করেছেন"
    }

    fun assistantFoundResults(lang: String, count: Int): String = when (lang) {
        LanguageManager.LANG_EN -> "Found $count related items"
        LanguageManager.LANG_AR -> "تم العثور على $count عنصرًا ذا صلة"
        else -> "${count}টি সম্পর্কিত বিষয় পাওয়া গেছে"
    }
    // ============ TalkBack Labels ============
    fun sendLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Send message"
        LanguageManager.LANG_AR -> "إرسال الرسالة"
        else -> "বার্তা পাঠান"
    }

    fun navigateToMosqueLabel(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> "Navigate to mosque.kt"
        LanguageManager.LANG_AR -> "الانتقال إلى المسجد"
        else -> "মসজিদে যাওয়ার পথ"
    }
}