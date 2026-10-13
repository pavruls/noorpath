package com.pavruls.noorpath.aqeedah

import com.pavruls.noorpath.data.preferences.LanguageManager

data class AqeedahTopic(
    val id: Int,
    val emoji: String,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val arabicName: String,
    val descriptionBn: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val sections: List<AqeedahSection>
) {
    fun getName(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> nameEn
        LanguageManager.LANG_AR -> nameAr
        else -> nameBn
    }

    fun getDescription(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> descriptionEn
        LanguageManager.LANG_AR -> descriptionAr
        else -> descriptionBn
    }
}

data class AqeedahSection(
    val id: Int,
    val titleBn: String,
    val titleEn: String,
    val titleAr: String,
    val contentBn: String,
    val contentEn: String,
    val contentAr: String,
    val reference: String = ""
) {
    fun getTitle(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> titleEn
        LanguageManager.LANG_AR -> titleAr
        else -> titleBn
    }

    fun getContent(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> contentEn
        LanguageManager.LANG_AR -> contentAr
        else -> contentBn
    }
}

object AqeedahData {
    val allTopics = listOf(
        AqeedahTopic(
            id = 1,
            emoji = "☝️",
            nameBn = "তাওহিদ",
            nameEn = "Tawhid",
            nameAr = "التوحيد",
            arabicName = "التوحيد",
            descriptionBn = "আল্লাহর একত্ববাদ",
            descriptionEn = "Oneness of Allah",
            descriptionAr = "وحدانية الله",
            sections = listOf(
                AqeedahSection(
                    1,
                    "তাওহিদ কী?",
                    "What is Tawhid?",
                    "ما هو التوحيد؟",
                    "তাওহিদ মানে আল্লাহকে এক ও অদ্বিতীয় মনে করা। ইসলামের মূল ভিত্তি হলো তাওহিদ।\n\nআল্লাহ বলেন: \"বলুন, তিনি আল্লাহ, এক ও অদ্বিতীয়।\" (সূরা ইখলাস: ১)",
                    "Tawhid means believing Allah is One and Unique. Tawhid is the foundation of Islam.\n\nAllah says: \"Say, He is Allah, the One.\" (Surah Al-Ikhlas: 1)",
                    "التوحيد هو الإيمان بأن الله واحد أحد. والتوحيد أساس الإسلام.\n\nقال الله تعالى: 'قل هو الله أحد' (الإخلاص: ١).",
                    "সূরা ইখলাস"
                ),
                AqeedahSection(
                    2,
                    "তাওহিদের ৩ প্রকার",
                    "3 Types of Tawhid",
                    "أنواع التوحيد الثلاثة",
                    "১. তাওহিদে রুবুবিয়্যাত — সৃষ্টি, রিজিক ও পরিচালনায় আল্লাহ একক\n\n২. তাওহিদে উলুহিয়্যাত — ইবাদতে আল্লাহ একক\n\n৩. তাওহিদে আসমা ওয়াস সিফাত — নাম ও গুণে আল্লাহ একক",
                    "1. Tawhid ar-Rububiyyah — Allah alone in creation, provision and management\n\n2. Tawhid al-Uluhiyyah — Allah alone in worship\n\n3. Tawhid al-Asma was-Sifat — Allah alone in names and attributes",
                    "١. توحيد الربوبية — الله وحده في الخلق والرزق والتدبير\n\n٢. توحيد الألوهية — الله وحده في العبادة\n\n٣. توحيد الأسماء والصفات — الله وحده في الأسماء والصفات",
                    "কিতাবুত তাওহিদ"
                ),
                AqeedahSection(
                    3,
                    "আল্লাহর ৯৯ নাম",
                    "99 Names of Allah",
                    "أسماء الله التسعة والتسعون",
                    "আল্লাহর ৯৯টি সুন্দর নাম আছে। কিছু নাম:\n\n• আর-রাহমান (পরম করুণাময়)\n• আর-রাহিম (অসীম দয়ালু)\n• আল-মালিক (মালিক)\n• আল-কুদ্দুস (পবিত্র)\n• আস-সালাম (শান্তিদাতা)\n• আল-খালিক (সৃষ্টিকর্তা)",
                    "Allah has 99 beautiful names. Some names:\n\n• Ar-Rahman (The Most Merciful)\n• Ar-Rahim (The Most Compassionate)\n• Al-Malik (The King)\n• Al-Quddus (The Holy)\n• As-Salam (The Source of Peace)\n• Al-Khaliq (The Creator)",
                    "لله تسعة وتسعون اسمًا حسنى. ومنها:\n\n• الرحمن\n• الرحيم\n• الملك\n• القدوس\n• السلام\n• الخالق",
                    "সহিহ বুখারী"
                ),
                AqeedahSection(
                    4,
                    "শিরক কী?",
                    "What is Shirk?",
                    "ما هو الشرك؟",
                    "শিরক হলো আল্লাহর সাথে কাউকে শরিক করা। এটি সবচেয়ে বড় গুনাহ।\n\nআল্লাহ বলেন: \"নিশ্চয়ই আল্লাহ শিরক ক্ষমা করেন না।\" (সূরা নিসা: ৪৮)\n\nবড় শিরক: মূর্তিপূজা, কবরপূজা, অলীদের কাছে সাহায্য চাওয়া।\n\nছোট শিরক: রিয়া (লোক দেখানো ইবাদত)।",
                    "Shirk means associating partners with Allah. It is the greatest sin.\n\nAllah says: \"Indeed, Allah does not forgive shirk.\" (Surah An-Nisa: 48)\n\nMajor shirk: idol worship, grave worship, seeking help from saints.\n\nMinor shirk: showing off (riya).",
                    "الشرك هو إشراك غير الله مع الله. وهو أعظم الذنوب.\n\nقال الله تعالى: 'إن الله لا يغفر أن يشرك به' (النساء: ٤٨).\n\nالشرك الأكبر: عبادة الأصنام، وعبادة القبور، وطلب الحاجات من الأولياء.\n\nالشرك الأصغر: الرياء.",
                    "সূরা নিসা"
                ),
                AqeedahSection(
                    5,
                    "আল্লাহ কোথায় আছেন?",
                    "Where is Allah?",
                    "أين الله؟",
                    "আল্লাহ আরশের উপর সমুন্নত। তিনি সর্বত্র তার জ্ঞানে আছেন।\n\nআল্লাহ বলেন: \"আর-রাহমান আরশের উপর সমুন্নত।\" (সূরা ত্বহা: ৫)\n\nতিনি সর্বশ্রোতা, সর্বদ্রষ্টা — সবকিছু তার জ্ঞানে।",
                    "Allah is exalted above the Throne. He is everywhere with His knowledge.\n\nAllah says: \"Ar-Rahman rose above the Throne.\" (Surah Taha: 5)\n\nHe is All-Hearing, All-Seeing — everything is in His knowledge.",
                    "الله مستوٍ على العرش، وهو بكل مكان بعلمه.\n\nقال الله تعالى: 'الرحمن على العرش استوى' (طه: ٥).\n\nوهو السميع البصير، وكل شيء في علمه.",
                    "কুরআন"
                ),
                AqeedahSection(
                    6,
                    "তাওহিদের উপকারিতা",
                    "Benefits of Tawhid",
                    "فوائد التوحيد",
                    "১. জান্নাতে প্রবেশের কারণ\n২. সব গুনাহ ক্ষমার মাধ্যম\n৩. হৃদয়ে শান্তি\n৪. ভয় ও দুশ্চিন্তা দূর হয়\n৫. আল্লাহর ভালোবাসা লাভ\n\nনবী (সা.) বলেছেন: \"যে ব্যক্তি 'লা ইলাহা ইল্লাল্লাহ' বলে sincerity সহ মৃত্যুবরণ করবে, সে জান্নাতে প্রবেশ করবে।\"",
                    "1. Cause of entering Paradise\n2. Means of forgiveness of all sins\n3. Peace in the heart\n4. Removal of fear and anxiety\n5. Attainment of Allah's love\n\nThe Prophet (PBUH) said: \"Whoever dies with sincerity saying 'La ilaha illallah' will enter Paradise.\"",
                    "١. سبب دخول الجنة\n٢. سبب مغفرة الذنوب\n٣. طمأنينة القلب\n٤. زوال الخوف والهم\n٥. نيل محبة الله\n\nقال النبي ﷺ: 'من مات وهو يعلم أن لا إله إلا الله دخل الجنة.'",
                    "সহিহ মুসলিম"
                )
            )
        ),
        AqeedahTopic(
            id = 2,
            emoji = "📜",
            nameBn = "ফেরেশতা",
            nameEn = "Angels",
            nameAr = "الملائكة",
            arabicName = "الملائكة",
            descriptionBn = "ফেরেশতাদের প্রতি ঈমান",
            descriptionEn = "Belief in angels",
            descriptionAr = "الإيمان بالملائكة",
            sections = listOf(
                AqeedahSection(
                    1,
                    "ফেরেশতা কী?",
                    "What are Angels?",
                    "ما هي الملائكة؟",
                    "ফেরেশতারা নূরের তৈরি, তারা আল্লাহর আদেশ পালন করে। তাদের খাওয়া-দাওয়া নেই, ঘুম নেই।\n\nআল্লাহ বলেন: \"তারা আল্লাহর আদেশ অমান্য করে না।\" (সূরা তাহরিম: ৬)",
                    "Angels are created from light; they obey Allah's commands. They do not eat, drink, or sleep.\n\nAllah says: \"They do not disobey Allah's commands.\" (Surah At-Tahrim: 6)",
                    "الملائكة خُلقوا من نور، يطيعون أوامر الله. لا يأكلون ولا يشربون ولا ينامون.\n\nقال الله تعالى: 'لا يعصون الله ما أمرهم' (التحريم: ٦).",
                    "কুরআন"
                ),
                AqeedahSection(
                    2,
                    "প্রধান ফেরেশতা",
                    "Chief Angels",
                    "الملائكة المقربون",
                    "• জিব্রাইল (আ.) — ওহি বহনকারী\n• মিকাইল (আ.) — রিজিকের দায়িত্বে\n• ইসরাফিল (আ.) — শিঙা ফুঁকবেন\n• আজরাইল (আ.) — রূহ কবজকারী",
                    "• Jibreel (AS) — bearer of revelation\n• Mikail (AS) — in charge of provision\n• Israfil (AS) — will blow the trumpet\n• Azrail (AS) — the angel of death",
                    "• جبريل عليه السلام — حامل الوحي\n• ميكائيل عليه السلام — موكل بالرزق\n• إسرافيل عليه السلام — ينفخ في الصور\n• عزرائيل عليه السلام — ملك الموت",
                    "কিতাবুত তাওহিদ"
                ),
                AqeedahSection(
                    3,
                    "কিরামান কাতিবিন",
                    "Kiraman Katibin",
                    "الكرام الكاتبون",
                    "প্রত্যেক মানুষের দুই কাঁধে দুই ফেরেশতা আছেন।\n\nডান দিকে — ভালো কাজ লেখেন\nবাম দিকে — মন্দ কাজ লেখেন\n\nআল্লাহ বলেন: \"তোমাদের উপর তত্ত্বাবধায়ক নিযুক্ত আছে, সম্মানিত লেখক।\" (সূরা ইনফিতার: ১০-১১)",
                    "Every person has two angels on his two shoulders.\n\nOn the right — they record good deeds\nOn the left — they record bad deeds\n\nAllah says: \"Over you are appointed guardians, noble recorders.\" (Surah Al-Infitar: 10-11)",
                    "لكل إنسان ملكان على كتفيه.\n\nعلى اليمين — يكتب الحسنات\nوعلى الشمال — يكتب السيئات\n\nقال الله تعالى: 'وإن عليكم لحافظين، كرامًا كاتبين' (الانفطار: ١٠-١١).",
                    "কুরআন"
                ),
                AqeedahSection(
                    4,
                    "মুনকার ও নাকির",
                    "Munkar and Nakir",
                    "منكر ونكير",
                    "কবরে মুনকার ও নাকির ফেরেশতা আসবেন।\n\nতারা প্রশ্ন করবেন:\n• তোমার রব কে?\n• তোমার দ্বীন কী?\n• তোমার নবী কে?",
                    "In the grave, the angels Munkar and Nakir will come.\n\nThey will ask:\n• Who is your Lord?\n• What is your religion?\n• Who is your prophet?",
                    "في القبر يأتي الملكان منكر ونكير.\n\nفيسألان:\n• من ربك؟\n• ما دينك؟\n• من نبيك؟",
                    "হাদিস"
                ),
                AqeedahSection(
                    5,
                    "ফেরেশতাদের গুণাবলি",
                    "Attributes of Angels",
                    "صفات الملائكة",
                    "• তারা নূরের তৈরি\n• তারা আল্লাহর আদেশ পালন করেন\n• তারা অহংকার করেন না\n• তারা সর্বদা ইবাদত করেন\n• তাদের সংখ্যা অসংখ্য",
                    "• Created from light\n• They obey Allah's commands\n• They are not arrogant\n• They always worship\n• Their number is countless",
                    "• خُلقوا من نور\n• يطيعون أوامر الله\n• لا يستكبرون\n• يعبدون الله دائمًا\n• عددهم لا يحصى",
                    "সহিহ বুখারী"
                ),
                AqeedahSection(
                    6,
                    "ফেরেশতাদের প্রতি ঈমান",
                    "Belief in Angels",
                    "الإيمان بالملائكة",
                    "ফেরেশতাদের প্রতি ঈমান আনা ঈমানের ৬টি স্তম্ভের একটি।\n\nযে ফেরেশতাদের অস্বীকার করে, সে কাফির।",
                    "Belief in angels is one of the six pillars of faith.\n\nWhoever denies the angels is a disbeliever.",
                    "الإيمان بالملائكة أحد أركان الإيمان الستة.\n\nومن أنكر الملائكة فهو كافر.",
                    "কুরআন"
                )
            )
        ),
        AqeedahTopic(
            id = 3,
            emoji = "📚",
            nameBn = "কিতাবসমূহ",
            nameEn = "Divine Books",
            nameAr = "الكتب",
            arabicName = "الكتب",
            descriptionBn = "আসমানি কিতাবে বিশ্বাস",
            descriptionEn = "Belief in divine books",
            descriptionAr = "الإيمان بالكتب السماوية",
            sections = listOf(
                AqeedahSection(
                    1,
                    "আসমানি কিতাব কী?",
                    "What are Divine Books?",
                    "ما هي الكتب السماوية؟",
                    "আল্লাহ যেসব কিতাব নাজিল করেছেন নবীদের উপর। এগুলোর প্রতি ঈমান আনা ফরজ।",
                    "The books Allah revealed to the prophets. Believing in them is obligatory.",
                    "الكتب التي أنزلها الله على الأنبياء. والإيمان بها واجب.",
                    "কুরআন"
                ),
                AqeedahSection(
                    2,
                    "৪টি প্রধান কিতাব",
                    "4 Major Books",
                    "الكتب الأربعة الرئيسية",
                    "• তাওরাত — মূসা (আ.)\n• যাবুর — দাউদ (আ.)\n• ইঞ্জিল — ঈসা (আ.)\n• কুরআন — মুহাম্মদ (সা.)\n\nঅন্যান্য কিতাব: সহিফা (ইব্রাহিম ও মূসা)",
                    "• Tawrat — Musa (AS)\n• Zabur — Dawud (AS)\n• Injil — Isa (AS)\n• Quran — Muhammad (PBUH)\n\nOther books: Suhuf (Ibrahim & Musa)",
                    "• التوراة — موسى عليه السلام\n• الزبور — داود عليه السلام\n• الإنجيل — عيسى عليه السلام\n• القرآن — محمد ﷺ\n\nكتب أخرى: الصحف (إبراهيم وموسى)",
                    "কুরআন"
                ),
                AqeedahSection(
                    3,
                    "কুরআনের বৈশিষ্ট্য",
                    "Characteristics of the Quran",
                    "خصائص القرآن",
                    "• সর্বশেষ কিতাব\n• অপরিবর্তিত থাকবে কিয়ামত পর্যন্ত\n• সব মানবজাতির জন্য\n• মু'জিযা",
                    "• The last Book\n• Will remain unchanged until the Day of Judgment\n• For all of humanity\n• A miracle",
                    "• آخر الكتب\n• محفوظ إلى يوم القيامة\n• لجميع البشر\n• معجزة",
                    "কুরআন"
                ),
                AqeedahSection(
                    4,
                    "কুরআনের সংখ্যা",
                    "Numbers in the Quran",
                    "أرقام القرآن",
                    "• ১১৪টি সূরা\n• ৩০ পারা\n• ৬২৩৬ আয়াত\n• ২৩ বছরে নাজিল",
                    "• 114 Surahs\n• 30 Juz\n• 6236 verses\n• Revealed over 23 years",
                    "• ١١٤ سورة\n• ٣٠ جزءًا\n• ٦٢٣٦ آية\n• نزل في ٢٣ سنة",
                    "কুরআন"
                ),
                AqeedahSection(
                    5,
                    "কুরআন পড়ার ফজিলত",
                    "Virtue of Reciting the Quran",
                    "فضل قراءة القرآن",
                    "নবী (সা.) বলেছেন: \"তোমাদের মধ্যে সর্বোত্তম সেই ব্যক্তি, যে কুরআন শেখে এবং অন্যকে শেখায়।\" (বুখারী)\n\nএকটি অক্ষর পড়লে ১০টি নেকি।",
                    "The Prophet (PBUH) said: \"The best among you is the one who learns the Quran and teaches it.\" (Bukhari)\n\nEach letter earns 10 rewards.",
                    "قال النبي ﷺ: 'خيركم من تعلم القرآن وعلمه' (البخاري).\n\nوبكل حرف عشر حسنات.",
                    "সহিহ বুখারী"
                ),
                AqeedahSection(
                    6,
                    "কুরআনের হক",
                    "Rights of the Quran",
                    "حقوق القرآن",
                    "• পাঠ করা\n• বোঝা ও চিন্তা করা\n• আমল করা\n• অন্যকে শেখানো\n• সম্মান করা",
                    "• Reciting it\n• Understanding and reflecting\n• Acting upon it\n• Teaching it to others\n• Respecting it",
                    "• تلاوته\n• تدبره وفهمه\n• العمل به\n• تعليمه للآخرين\n• احترامه",
                    "কুরআন"
                )
            )
        ),
        AqeedahTopic(
            id = 4,
            emoji = "🌌",
            nameBn = "আখিরাত",
            nameEn = "The Hereafter",
            nameAr = "الآخرة",
            arabicName = "الآخرة",
            descriptionBn = "পরকালে বিশ্বাস",
            descriptionEn = "Belief in the Hereafter",
            descriptionAr = "الإيمان بالآخرة",
            sections = listOf(
                AqeedahSection(
                    1,
                    "আখিরাত কী?",
                    "What is the Hereafter?",
                    "ما هي الآخرة؟",
                    "মৃত্যুর পরের জীবন। কিয়ামতের দিন থেকে চিরকাল।\n\nআল্লাহ বলেন: \"আখিরাতই চিরস্থায়ী।\" (সূরা আ'লা: ১৭)",
                    "The life after death. From the Day of Judgment forever.\n\nAllah says: \"The Hereafter is everlasting.\" (Surah Al-A'la: 17)",
                    "الحياة بعد الموت. من يوم القيامة إلى الأبد.\n\nقال الله تعالى: 'والآخرة خير وأبقى' (الأعلى: ١٧).",
                    "কুরআন"
                ),
                AqeedahSection(
                    2,
                    "কিয়ামতের আলামত",
                    "Signs of the Hour",
                    "علامات الساعة",
                    "ছোট আলামত:\n• ইলম কমে যাবে\n• যিনা বাড়বে\n• মদপান বাড়বে\n\nবড় আলামত:\n• ইমাম মাহদী\n• দাজ্জাল\n• ঈসা (আ.) এর আগমন\n• সূর্য পশ্চিমে উদয়",
                    "Minor signs:\n• Knowledge will decrease\n• Adultery will increase\n• Drinking will increase\n\nMajor signs:\n• Imam Mahdi\n• Dajjal\n• Coming of Isa (AS)\n• Sunrise from the West",
                    "العلامات الصغرى:\n• قلة العلم\n• كثرة الزنا\n• كثرة شرب الخمر\n\nالعلامات الكبرى:\n• الإمام المهدي\n• الدجال\n• نزول عيسى عليه السلام\n• طلوع الشمس من المغرب",
                    "সহিহ মুসলিম"
                ),
                AqeedahSection(
                    3,
                    "কবরের জীবন",
                    "Life in the Grave",
                    "الحياة البرزخية",
                    "কবর হলো আখিরাতের প্রথম ধাপ।\n\n• প্রশ্ন-উত্তর\n• আজাব/শান্তি\n• মুমিনের জন্য বাগান\n• কাফিরের জন্য জাহান্নাম",
                    "The grave is the first stage of the Hereafter.\n\n• Questioning\n• Punishment/peace\n• Garden for the believer\n• Hell for the disbeliever",
                    "القبر أول منازل الآخرة.\n\n• السؤال\n• العذاب أو النعيم\n• روضة للمؤمن\n• حفرة من حفر النار للكافر",
                    "হাদিস"
                ),
                AqeedahSection(
                    4,
                    "হাশর ময়দান",
                    "The Plain of Resurrection",
                    "أرض المحشر",
                    "কিয়ামতের দিন সবাই পুনরুত্থিত হবে।\n\n• হিসাব\n• মীযান (দাঁড়িপাল্লা)\n• পুলসিরাত\n• শাফায়াত",
                    "On the Day of Judgment, all will be resurrected.\n\n• Reckoning\n• Mizan (the scales)\n• Sirat (the bridge)\n• Intercession",
                    "في يوم القيامة يُبعث الجميع.\n\n• الحساب\n• الميزان\n• الصراط\n• الشفاعة",
                    "কুরআন"
                ),
                AqeedahSection(
                    5,
                    "জান্নাত",
                    "Paradise",
                    "الجنة",
                    "জান্নাতে:\n• চিরস্থায়ী সুখ\n• নদী প্রবাহিত\n• ফলমূল\n• হুর\n• আল্লাহর দীদার (সর্বোচ্চ পুরস্কার)",
                    "In Paradise:\n• Eternal bliss\n• Flowing rivers\n• Fruits\n• Hur\n• Vision of Allah (highest reward)",
                    "في الجنة:\n• نعيم دائم\n• أنهار جارية\n• فواكه\n• حور عين\n• رؤية الله (أعلى النعيم)",
                    "কুরআন"
                ),
                AqeedahSection(
                    6,
                    "জাহান্নাম",
                    "Hell",
                    "جهنم",
                    "জাহান্নামে:\n• আগুনের শাস্তি\n• কাঁটার খাবার\n• ফুটন্ত পানি\n• চিরস্থায়ী আজাব (কাফিরদের জন্য)",
                    "In Hell:\n• Punishment of fire\n• Thorny food\n• Boiling water\n• Eternal torment (for disbelievers)",
                    "في جهنم:\n• عذاب النار\n• طعام من زقوم\n• ماء حميم\n• عذاب دائم (للكافرين)",
                    "কুরআন"
                )
            )
        ),
        AqeedahTopic(
            id = 5,
            emoji = "🌟",
            nameBn = "রিসালাত",
            nameEn = "Prophethood",
            nameAr = "الرسالة",
            arabicName = "الرسالة",
            descriptionBn = "নবীদের প্রতি ঈমান",
            descriptionEn = "Belief in the prophets",
            descriptionAr = "الإيمان بالأنبياء",
            sections = listOf(
                AqeedahSection(
                    1,
                    "নবী কারা?",
                    "Who are the Prophets?",
                    "من هم الأنبياء؟",
                    "আল্লাহ যাদের মনোনীত করে মানুষের কাছে তাঁর বাণী পৌঁছানোর জন্য পাঠান।\n\n• ১,২৪,০০০ নবী\n• ৩১৩ জন রাসূল\n• প্রথম: আদম (আ.)\n• শেষ: মুহাম্মদ (সা.)",
                    "Those whom Allah chose to convey His message to people.\n\n• 124,000 prophets\n• 313 messengers\n• First: Adam (AS)\n• Last: Muhammad (PBUH)",
                    "من اختارهم الله لتبليغ رسالته إلى الناس.\n\n• ١٢٤٠٠٠ نبي\n• ٣١٣ رسولًا\n• الأول: آدم عليه السلام\n• الأخير: محمد ﷺ",
                    "হাদিস"
                ),
                AqeedahSection(
                    2,
                    "নবীদের গুণাবলি",
                    "Attributes of the Prophets",
                    "صفات الأنبياء",
                    "• সত্যবাদী\n• বিশ্বস্ত\n• নিষ্পাপ\n• বুদ্ধিমান\n• আল্লাহর বাণী পৌঁছান",
                    "• Truthful\n• Trustworthy\n• Infallible\n• Intelligent\n• Convey Allah's message",
                    "• الصدق\n• الأمانة\n• العصمة\n• الفطانة\n• تبليغ الرسالة",
                    "কুরআন"
                ),
                AqeedahSection(
                    3,
                    "উলুল আযম নবী",
                    "Prophets of Strong Will",
                    "أولو العزم من الرسل",
                    "৫ জন শ্রেষ্ঠ নবী:\n\n১. নূহ (আ.)\n২. ইব্রাহিম (আ.)\n৩. মূসা (আ.)\n৪. ঈসা (আ.)\n৫. মুহাম্মদ (সা.)",
                    "The 5 greatest prophets:\n\n1. Nuh (AS)\n2. Ibrahim (AS)\n3. Musa (AS)\n4. Isa (AS)\n5. Muhammad (PBUH)",
                    "خمسة من أولي العزم:\n\n١. نوح عليه السلام\n٢. إبراهيم عليه السلام\n٣. موسى عليه السلام\n٤. عيسى عليه السلام\n٥. محمد ﷺ",
                    "কুরআন"
                ),
                AqeedahSection(
                    4,
                    "মুহাম্মদ (সা.)",
                    "Muhammad (PBUH)",
                    "محمد ﷺ",
                    "• শেষ নবী\n• সব মানুষের জন্য\n• খাতামুন নাবিয়্যীন\n• রাহমাতুল লিল আলামীন",
                    "• Last Prophet\n• For all humanity\n• Khatam an-Nabiyyin (Seal of the Prophets)\n• Rahmatul lil Alamin (Mercy to all worlds)",
                    "• خاتم الأنبياء\n• لجميع الناس\n• رحمة للعالمين",
                    "কুরআন"
                ),
                AqeedahSection(
                    5,
                    "নবীদের প্রতি ঈমান",
                    "Belief in the Prophets",
                    "الإيمان بالأنبياء",
                    "• সব নবীকে মানা\n• কাউকে অস্বীকার না করা\n• শেষ নবীকে মানা\n• তাদের সুন্নত অনুসরণ",
                    "• Believing in all prophets\n• Not denying any\n• Believing in the last Prophet\n• Following their Sunnah",
                    "• الإيمان بجميع الأنبياء\n• عدم إنكار أحدهم\n• الإيمان بخاتم الأنبياء\n• اتباع سنتهم",
                    "কুরআন"
                ),
                AqeedahSection(
                    6,
                    "নবী (সা.) এর সুন্নত",
                    "Sunnah of the Prophet (PBUH)",
                    "سنة النبي ﷺ",
                    "• ইবাদত\n• চরিত্র\n• লেনদেন\n• পারিবারিক জীবন\n• সমাজ জীবন\n• যুদ্ধ-শান্তি",
                    "• Worship\n• Character\n• Transactions\n• Family life\n• Social life\n• War and peace",
                    "• العبادة\n• الأخلاق\n• المعاملات\n• الحياة الأسرية\n• الحياة الاجتماعية\n• الحرب والسلم",
                    "সিরাত"
                )
            )
        ),
        AqeedahTopic(
            id = 6,
            emoji = "⚖️",
            nameBn = "তাকদির",
            nameEn = "Divine Decree (Qadar)",
            nameAr = "القدر",
            arabicName = "القدر",
            descriptionBn = "ভাগ্য ও আল্লাহর ইচ্ছা",
            descriptionEn = "Fate and Allah's will",
            descriptionAr = "القضاء والقدر",
            sections = listOf(
                AqeedahSection(
                    1,
                    "তাকদির কী?",
                    "What is Qadar?",
                    "ما هو القدر؟",
                    "আল্লাহর পূর্বজ্ঞান ও ইচ্ছা। সব কিছু আল্লাহর ইলমে লিখিত।\n\nআল্লাহ বলেন: \"আমি সব কিছু সুনির্দিষ্ট পরিমাণে সৃষ্টি করেছি।\" (সূরা কামার: ৪৯)",
                    "Allah's foreknowledge and will. Everything is written in Allah's knowledge.\n\nAllah says: \"Indeed, We have created everything with precise measure.\" (Surah Al-Qamar: 49)",
                    "علم الله السابق ومشيئته. وكل شيء مكتوب في علم الله.\n\nقال الله تعالى: 'إنا كل شيء خلقناه بقدر' (القمر: ٤٩).",
                    "কুরআন"
                ),
                AqeedahSection(
                    2,
                    "তাকদিরের ৪ স্তর",
                    "4 Levels of Qadar",
                    "مراتب القدر الأربع",
                    "১. আল্লাহর জ্ঞান\n২. লাওহে মাহফুযে লেখা\n৩. আল্লাহর ইচ্ছা\n৪. সৃষ্টি ও নির্ধারণ",
                    "1. Allah's knowledge\n2. Written in Al-Lawh Al-Mahfuz\n3. Allah's will\n4. Creation and decree",
                    "١. علم الله\n٢. الكتابة في اللوح المحفوظ\n٣. مشيئة الله\n٤. الخلق والتقدير",
                    "কিতাবুত তাওহিদ"
                ),
                AqeedahSection(
                    3,
                    "তাকদির ও স্বাধীন ইচ্ছা",
                    "Qadar and Free Will",
                    "القدر والاختيار",
                    "মানুষের স্বাধীন ইচ্ছা আছে। সে ভালো-মন্দ বেছে নিতে পারে।\n\nতবে আল্লাহর জ্ঞানে সব পূর্বনির্ধারিত।",
                    "Humans have free will. They can choose good or evil.\n\nBut everything is foreknown in Allah's knowledge.",
                    "للإنسان إرادة حرة. يستطيع اختيار الخير أو الشر.\n\nولكن كل شيء معلوم سابقًا في علم الله.",
                    "কুরআন"
                ),
                AqeedahSection(
                    4,
                    "তাকদিরে বিশ্বাসের উপকারিতা",
                    "Benefits of Belief in Qadar",
                    "فوائد الإيمان بالقدر",
                    "• ধৈর্য বাড়ে\n• দুশ্চিন্তা কমে\n• তাওয়াক্কুল বাড়ে\n• কৃতজ্ঞতা বাড়ে",
                    "• Patience increases\n• Anxiety decreases\n• Reliance on Allah increases\n• Gratitude increases",
                    "• زيادة الصبر\n• قلة الهم\n• زيادة التوكل\n• زيادة الشكر",
                    "ইসলামিক শিক্ষা"
                ),
                AqeedahSection(
                    5,
                    "তাকদিরে বিশ্বাসের ভুল",
                    "Errors in Belief in Qadar",
                    "الأخطاء في الإيمان بالقدر",
                    "❌ ভাগ্যবাদ — সব আল্লাহর, চেষ্টার দরকার নেই\n❌ কাদারিয়া — সব মানুষের হাতে\n\n✅ সঠিক: আল্লাহর জ্ঞান + মানুষের চেষ্টা",
                    "❌ Fatalism — everything is Allah's, no effort needed\n❌ Qadariyyah — everything is in man's hands\n\n✅ Correct: Allah's knowledge + man's effort",
                    "❌ الجبرية — كل شيء من الله ولا حاجة للسعي\n❌ القدرية — كل شيء بيد الإنسان\n\n✅ الصواب: علم الله + سعي الإنسان",
                    "আকিদা"
                ),
                AqeedahSection(
                    6,
                    "সবর ও শুকর",
                    "Patience and Gratitude",
                    "الصبر والشكر",
                    "• বিপদে সবর করো\n• নিয়ামতে শুকর করো\n• আল্লাহর উপর ভরসা রাখো\n• সর্বদা দুআ করো",
                    "• Be patient in adversity\n• Be grateful for blessings\n• Put trust in Allah\n• Always make dua",
                    "• اصبر عند البلاء\n• اشكر على النعم\n• توكل على الله\n• ادعُ الله دائمًا",
                    "কুরআন"
                )
            )
        )
    )

    fun getTopicById(id: Int): AqeedahTopic? {
        return allTopics.find { it.id == id }
    }
}