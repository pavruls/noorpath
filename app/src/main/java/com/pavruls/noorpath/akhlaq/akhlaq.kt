package com.pavruls.noorpath.akhlaq

import com.pavruls.noorpath.data.preferences.LanguageManager

data class AkhlaqTopic(
    val id: Int,
    val emoji: String,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val arabicName: String,
    val descriptionBn: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val sections: List<AkhlaqSection>
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

data class AkhlaqSection(
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

object AkhlaqData {
    val allTopics = listOf(
        AkhlaqTopic(
            id = 1,
            emoji = "🤝",
            nameBn = "সততা",
            nameEn = "Honesty",
            nameAr = "الصدق",
            arabicName = "الصدق",
            descriptionBn = "সত্যবাদিতা ও বিশ্বস্ততা",
            descriptionEn = "Truthfulness and trustworthiness",
            descriptionAr = "الصدق والأمانة",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "সততা কী?",
                    "What is Honesty?",
                    "ما هو الصدق؟",
                    "সততা মানে সত্য বলা ও সত্যের উপর থাকা। ইসলামে সততা অত্যন্ত গুরুত্বপূর্ণ গুণ।\n\nনবী (সা.) কে সততার কারণে 'আস-সাদিক' (সত্যবাদী) বলা হতো।",
                    "Honesty means speaking the truth and staying on the truth. In Islam, honesty is a very important quality.\n\nThe Prophet (PBUH) was called 'As-Sadiq' (the Truthful) because of his honesty.",
                    "الصدق يعني قول الحق والثبات عليه. في الإسلام، الصدق خلق عظيم.\n\nلُقّب النبي ﷺ بـ 'الصادق' بسبب صدقه.",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    2,
                    "সততার ফজিলত",
                    "Virtue of Honesty",
                    "فضل الصدق",
                    "রাসূল (সা.) বলেছেন: \"সত্যবাদিতা সৎকর্মের দিকে নিয়ে যায়, আর সৎকর্ম জান্নাতে নিয়ে যায়।\"\n\nযে ব্যক্তি সত্য বলতে থাকে, আল্লাহর কাছে সে 'সিদ্দিক' (সত্যবাদী) হিসেবে লিখিত হয়।",
                    "The Messenger (PBUH) said: \"Truthfulness leads to righteousness, and righteousness leads to Paradise.\"\n\nWhoever continues to speak the truth is recorded as 'Siddiq' (truthful) with Allah.",
                    "قال رسول الله ﷺ: 'إن الصدق يهدي إلى البر، وإن البر يهدي إلى الجنة.'\n\nومن يصدق يُكتب عند الله صديقًا.",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    3,
                    "মিথ্যার পরিণাম",
                    "Consequence of Lying",
                    "عاقبة الكذب",
                    "রাসূল (সা.) বলেছেন: \"মিথ্যা বলা পাপের দিকে নিয়ে যায়, আর পাপ জাহান্নামে নিয়ে যায়।\"\n\nমুনাফিকের প্রথম আলামত হলো মিথ্যা বলা।",
                    "The Messenger (PBUH) said: \"Lying leads to sin, and sin leads to Hell.\"\n\nThe first sign of a hypocrite is lying.",
                    "قال رسول الله ﷺ: 'إن الكذب يهدي إلى الفجور، وإن الفجور يهدي إلى النار.'\n\nوأول علامات المنافق الكذب.",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    4,
                    "ব্যবসায় সততা",
                    "Honesty in Business",
                    "الصدق في التجارة",
                    "নবী (সা.) বলেছেন: \"সৎ ও বিশ্বস্ত ব্যবসায়ী কিয়ামতের দিন নবী, সিদ্দিক ও শহীদদের সাথে থাকবে।\"\n\nওজনে কম দেওয়া, মিথ্যা বলা — এসব হারাম।",
                    "The Prophet (PBUH) said: \"The honest and trustworthy merchant will be with the prophets, the truthful, and the martyrs on the Day of Judgment.\"\n\nShort-weighing and lying are forbidden.",
                    "قال النبي ﷺ: 'التاجر الصدوق الأمين مع النبيين والصديقين والشهداء.'\n\nوالتطفيف والكذب حرام.",
                    "তিরমিজি"
                ),
                AkhlaqSection(
                    5,
                    "কথায় সততা",
                    "Truthfulness in Speech",
                    "الصدق في القول",
                    "• প্রতিশ্রুতি রক্ষা করা\n• মিথ্যা সাক্ষ্য না দেওয়া\n• গীবত না করা\n• অঙ্গীকার পূরণ করা\n• সত্য কথা বলা",
                    "• Keeping promises\n• Not giving false testimony\n• Not backbiting\n• Fulfilling pledges\n• Speaking the truth",
                    "• الوفاء بالوعد\n• عدم شهادة الزور\n• ترك الغيبة\n• الوفاء بالعهد\n• قول الحق",
                    "কুরআন"
                ),
                AkhlaqSection(
                    6,
                    "আমানতদারিতা",
                    "Trustworthiness",
                    "الأمانة",
                    "আমানত রক্ষা করা সততার অংশ।\n\nআল্লাহ বলেন: \"নিশ্চয়ই আল্লাহ তোমাদের আমানতদারদের কাছে আমানত প্রত্যর্পণের নির্দেশ দিচ্ছেন।\" (সূরা নিসা: ৫৮)",
                    "Fulfilling trusts is part of honesty.\n\nAllah says: \"Indeed, Allah commands you to render trusts to whom they are due.\" (Surah An-Nisa: 58)",
                    "أداء الأمانة من الصدق.\n\nقال الله تعالى: 'إن الله يأمركم أن تؤدوا الأمانات إلى أهلها' (النساء: ٥٨).",
                    "কুরআন"
                )
            )
        ),
        AkhlaqTopic(
            id = 2,
            emoji = "🧘",
            nameBn = "ধৈর্য",
            nameEn = "Patience",
            nameAr = "الصبر",
            arabicName = "الصبر",
            descriptionBn = "সবর ও সহিষ্ণুতা",
            descriptionEn = "Sabr and endurance",
            descriptionAr = "الصبر والتحمل",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "ধৈর্য কী?",
                    "What is Patience?",
                    "ما هو الصبر؟",
                    "ধৈর্য মানে বিপদে ধৈর্য ধারণ করা, ইবাদতে অবিচল থাকা, পাপ থেকে বিরত থাকা।\n\nআল্লাহ বলেন: \"নিশ্চয়ই আল্লাহ ধৈর্যশীলদের সাথে আছেন।\" (সূরা বাকারা: ১৫৩)",
                    "Patience means being patient in adversity, remaining steadfast in worship, and refraining from sin.\n\nAllah says: \"Indeed, Allah is with the patient.\" (Surah Al-Baqarah: 153)",
                    "الصبر هو الثبات عند البلاء، والاستقامة في العبادة، والامتناع عن المعصية.\n\nقال الله تعالى: 'إن الله مع الصابرين' (البقرة: ١٥٣).",
                    "কুরআন"
                ),
                AkhlaqSection(
                    2,
                    "ধৈর্যের ৩ প্রকার",
                    "3 Types of Patience",
                    "أنواع الصبر الثلاثة",
                    "১. বিপদে ধৈর্য\n২. ইবাদতে ধৈর্য\n৩. পাপ থেকে বেঁচে থাকার ধৈর্য।\n\nতিনটিই অপরিহার্য।",
                    "1. Patience in adversity\n2. Patience in worship\n3. Patience in abstaining from sin.\n\nAll three are essential.",
                    "١. الصبر على البلاء\n٢. الصبر على الطاعة\n٣. الصبر عن المعصية.\n\nوكلها واجبة.",
                    "ফিকহুস সুন্নাহ"
                ),
                AkhlaqSection(
                    3,
                    "ধৈর্যের পুরস্কার",
                    "Reward of Patience",
                    "أجر الصبر",
                    "আল্লাহ বলেন: \"নিশ্চয়ই ধৈর্যশীলদের পুরস্কার বেহিসাব দেওয়া হবে।\" (সূরা যুমার: ১০)\n\nধৈর্যের বিনিময়ে জান্নাত।",
                    "Allah says: \"Indeed, the patient will be given their reward without measure.\" (Surah Az-Zumar: 10)\n\nParadise is the reward for patience.",
                    "قال الله تعالى: 'إنما يوفى الصابرون أجرهم بغير حساب' (الزمر: ١٠).\n\nوالصبر جزاؤه الجنة.",
                    "কুরআন"
                ),
                AkhlaqSection(
                    4,
                    "নবী (সা.) এর ধৈর্য",
                    "Patience of the Prophet (PBUH)",
                    "صبر النبي ﷺ",
                    "তায়েফে পাথর খেয়েও নবী (সা.) বলেননি:\n\n'হে আল্লাহ! আমার কওমকে হেদায়াত দাও, তারা জানে না।'\n\nউহুদ যুদ্ধে রক্ত ঝরতেও ধৈর্য ধরেছেন।",
                    "Even when stoned at Ta'if, the Prophet (PBUH) said:\n\n'O Allah! Guide my people, they do not know.'\n\nHe remained patient even while bleeding at the Battle of Uhud.",
                    "حتى عند رميه بالحجارة في الطائف، قال النبي ﷺ:\n\n'اللهم اهدِ قومي فإنهم لا يعلمون.'\n\nوصبر حتى وهو ينزف في غزوة أحد.",
                    "সিরাত"
                ),
                AkhlaqSection(
                    5,
                    "ধৈর্যের উপকারিতা",
                    "Benefits of Patience",
                    "فوائد الصبر",
                    "• হৃদয়ে শান্তি\n• আল্লাহর সাহায্য\n• ধৈর্যশীলরা বিজয়ী\n• জান্নাতের সুসংবাদ",
                    "• Peace in the heart\n• Allah's help\n• The patient are victorious\n• Glad tidings of Paradise",
                    "• طمأنينة القلب\n• نصر الله\n• الصابرون منصورون\n• بشارة الجنة",
                    "কুরআন"
                ),
                AkhlaqSection(
                    6,
                    "ধৈর্যহীনতার ক্ষতি",
                    "Harm of Impatience",
                    "ضرر قلة الصبر",
                    "• দুশ্চিন্তা\n• হতাশা\n• অস্থিরতা\n• শয়তানের প্ররোচনা\n• আল্লাহর সাহায্য থেকে বঞ্চিত",
                    "• Anxiety\n• Despair\n• Restlessness\n• Satan's whispers\n• Deprivation of Allah's help",
                    "• الهم\n• اليأس\n• القلق\n• وسوسة الشيطان\n• الحرمان من نصر الله",
                    "ইসলামিক শিক্ষা"
                )
            )
        ),
        AkhlaqTopic(
            id = 3,
            emoji = "🙏",
            nameBn = "কৃতজ্ঞতা",
            nameEn = "Gratitude",
            nameAr = "الشكر",
            arabicName = "الشكر",
            descriptionBn = "শুকর আদায় করা",
            descriptionEn = "Showing thankfulness",
            descriptionAr = "أداء الشكر",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "কৃতজ্ঞতা কী?",
                    "What is Gratitude?",
                    "ما هو الشكر؟",
                    "কৃতজ্ঞতা মানে আল্লাহর নিয়ামতের স্বীকৃতি দেওয়া ও প্রশংসা করা।\n\nআল্লাহ বলেন: \"যদি তোমরা কৃতজ্ঞ হও, আমি অবশ্যই তোমাদের আরও বাড়িয়ে দেব।\" (সূরা ইবরাহিম: ৭)",
                    "Gratitude means recognizing and praising Allah's blessings.\n\nAllah says: \"If you are grateful, I will surely increase you [in favor].\" (Surah Ibrahim: 7)",
                    "الشكر هو الاعتراف بنعم الله والثناء عليه.\n\nقال الله تعالى: 'لئن شكرتم لأزيدنكم' (إبراهيم: ٧).",
                    "কুরআন"
                ),
                AkhlaqSection(
                    2,
                    "কৃতজ্ঞতার ৩ প্রকাশ",
                    "3 Expressions of Gratitude",
                    "مظاهر الشكر الثلاثة",
                    "১. অন্তরে স্বীকৃতি\n২. মুখে 'আলহামদুলিল্লাহ' বলা\n৩. অঙ্গ-প্রত্যঙ্গ দিয়ে আল্লাহর আনুগত্য",
                    "1. Recognition in the heart\n2. Saying 'Alhamdulillah' with the tongue\n3. Obedience to Allah with the limbs",
                    "١. الاعتراف بالقلب\n٢. الحمد باللسان\n٣. الطاعة بالجوارح",
                    "ইসলামিক শিক্ষা"
                ),
                AkhlaqSection(
                    3,
                    "নিয়ামত অস্বীকার",
                    "Denial of Blessings",
                    "إنكار النعمة",
                    "নিয়ামত অস্বীকার করা কুফরির দিকে নিয়ে যায়।\n\nআল্লাহ বলেন: \"নিশ্চয়ই কৃতজ্ঞতা প্রকাশকারী ও অকৃতজ্ঞদের আমি জানি।\" (সূরা নাহল: ৫১)",
                    "Denying blessings leads to disbelief.\n\nAllah says: \"Indeed, I am Knowing of the grateful and the ungrateful.\" (Surah An-Nahl: 51)",
                    "إنكار النعمة يؤدي إلى الكفر.\n\nقال الله تعالى: 'إني أعلم الشاكرين والكافرين' (النحل: ٥١).",
                    "কুরআন"
                ),
                AkhlaqSection(
                    4,
                    "শুকরের ফজিলত",
                    "Virtue of Gratitude",
                    "فضل الشكر",
                    "রাসূল (সা.) সারা রাত নামাজ পড়তেন এবং বলতেন:\n\n'আমি কি আল্লাহর শুকরিয়া আদায়কারী বান্দা হবো না?'\n\nশুকর ঈমানের অর্ধেক।",
                    "The Messenger (PBUH) would pray all night and say:\n\n'Should I not be a grateful servant of Allah?'\n\nGratitude is half of faith.",
                    "كان رسول الله ﷺ يصلي حتى تتفطر قدماه ويقول:\n\n'أفلا أكون عبدًا شكورًا.'\n\nوالشكر شطر الإيمان.",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    5,
                    "নিয়ামতের শুকর",
                    "Gratitude for Blessings",
                    "الشكر على النعم",
                    "• স্বাস্থ্যের শুকর\n• সম্পদের শুকর\n• পরিবারের শুকর\n• জ্ঞানের শুকর\n• সময়ের শুকর",
                    "• Gratitude for health\n• Gratitude for wealth\n• Gratitude for family\n• Gratitude for knowledge\n• Gratitude for time",
                    "• الشكر على الصحة\n• الشكر على المال\n• الشكر على الأهل\n• الشكر على العلم\n• الشكر على الوقت",
                    "ইসলামিক শিক্ষা"
                ),
                AkhlaqSection(
                    6,
                    "শুকরের উপকারিতা",
                    "Benefits of Gratitude",
                    "فوائد الشكر",
                    "• নিয়ামত বাড়ে\n• আল্লাহর সন্তুষ্টি\n• হৃদয়ে শান্তি\n• বরকত লাভ\n• কৃতজ্ঞতা জান্নাতের পথ",
                    "• Blessings increase\n• Allah's pleasure\n• Peace in heart\n• Barakah\n• Gratitude is the path to Paradise",
                    "• زيادة النعم\n• رضا الله\n• طمأنينة القلب\n• البركة\n• الشكر طريق الجنة",
                    "কুরআন"
                )
            )
        ),
        AkhlaqTopic(
            id = 4,
            emoji = "🕊️",
            nameBn = "ক্ষমা",
            nameEn = "Forgiveness",
            nameAr = "العفو",
            arabicName = "العفو",
            descriptionBn = "ক্ষমাশীলতা ও উদারতা",
            descriptionEn = "Forgiveness and generosity",
            descriptionAr = "العفو والكرم",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "ক্ষমা কী?",
                    "What is Forgiveness?",
                    "ما هو العفو؟",
                    "ক্ষমা মানে অন্যায়কারীকে শাস্তি না দেওয়া, বরং ক্ষমা করে দেওয়া।\n\nআল্লাহ বলেন: \"যারা ক্রোধ সংবরণ করে এবং মানুষকে ক্ষমা করে — আল্লাহ সৎকর্মশীলদের ভালোবাসেন।\" (সূরা আলে ইমরান: ১৩৪)",
                    "Forgiveness means not punishing the wrongdoer, but pardoning instead.\n\nAllah says: \"Those who restrain anger and pardon people — Allah loves the doers of good.\" (Surah Al-Imran: 134)",
                    "العفو هو ترك معاقبة المسيء والعفو عنه.\n\nقال الله تعالى: 'والكاظمين الغيظ والعافين عن الناس والله يحب المحسنين' (آل عمران: ١٣٤).",
                    "কুরআন"
                ),
                AkhlaqSection(
                    2,
                    "ক্ষমার প্রকার",
                    "Types of Forgiveness",
                    "أنواع العفو",
                    "• আল্লাহর কাছে ক্ষমা চাওয়া\n• অন্যকে ক্ষমা করা\n• নিজেকে ক্ষমা করা (তাওবা)\n• শত্রুকে ক্ষমা করা",
                    "• Seeking Allah's forgiveness\n• Forgiving others\n• Forgiving oneself (Tawbah)\n• Forgiving enemies",
                    "• الاستغفار من الله\n• العفو عن الآخرين\n• التوبة\n• العفو عن الأعداء",
                    "ইসলামিক শিক্ষা"
                ),
                AkhlaqSection(
                    3,
                    "মক্কা বিজয়ের দিন",
                    "Day of the Conquest of Makkah",
                    "يوم فتح مكة",
                    "নবী (সা.) মক্কা বিজয়ের দিন কুরাইশদের জিজ্ঞাসা করেন:\n\n'তোমরা আমার কাছ থেকে কী আশা করো?'\n\nতারা বলে: 'ভালো ব্যবহার।'\n\nনবী (সা.) বলেন: 'যাও, তোমরা সবাই মুক্ত।'",
                    "On the day of the Conquest of Makkah, the Prophet (PBUH) asked the Quraysh:\n\n'What do you expect from me?'\n\nThey said: 'Good treatment.'\n\nThe Prophet (PBUH) said: 'Go, you are all free.'",
                    "في يوم فتح مكة، سأل النبي ﷺ قريشًا:\n\n'ما تظنون أني فاعل بكم؟'\n\nقالوا: 'خيرًا.'\n\nفقال: 'اذهبوا فأنتم الطلقاء.'",
                    "সিরাত"
                ),
                AkhlaqSection(
                    4,
                    "ক্ষমার পুরস্কার",
                    "Reward of Forgiveness",
                    "أجر العفو",
                    "নবী (সা.) বলেছেন: \"যে ক্ষমা করে, আল্লাহ তার সম্মান বাড়িয়ে দেন।\"\n\nক্ষমা করলে আল্লাহর ক্ষমা পাওয়া যায়।",
                    "The Prophet (PBUH) said: \"Whoever forgives, Allah increases him in honor.\"\n\nBy forgiving, one earns Allah's forgiveness.",
                    "قال النبي ﷺ: 'ما زاد الله عبدًا بعفو إلا عزًا.'\n\nوبالعفو يَنال العبد مغفرة الله.",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    5,
                    "ক্ষমার উপকারিতা",
                    "Benefits of Forgiveness",
                    "فوائد العفو",
                    "• হৃদয়ে শান্তি\n• শত্রু বন্ধু হয়\n• আল্লাহর সন্তুষ্টি\n• সমাজে শান্তি\n• বিচার দিবসে রক্ষা",
                    "• Peace in the heart\n• Enemies become friends\n• Allah's pleasure\n• Peace in society\n• Safety on the Day of Judgment",
                    "• طمأنينة القلب\n• تحوّل العدو إلى صديق\n• رضا الله\n• سلام المجتمع\n• النجاة يوم القيامة",
                    "ইসলামিক শিক্ষা"
                ),
                AkhlaqSection(
                    6,
                    "ক্রোধ নিয়ন্ত্রণ",
                    "Controlling Anger",
                    "التحكم في الغضب",
                    "রাসূল (সা.) বলেছেন:\n\n'যে ক্রোধ থাকা সত্ত্বেও সংযত হয়, আল্লাহ তাকে নিরাপত্তা ও ঈমান দান করেন।'\n\n'বলবান সে নয় যে কুস্তিতে জেতে; বলবান সে যে ক্রোধের সময় নিজেকে সংযত রাখে।'",
                    "The Messenger (PBUH) said:\n\n'Whoever restrains his anger despite having power, Allah will grant him security and faith.'\n\n'The strong one is not the one who wins in wrestling; the strong one is the one who controls himself when angry.'",
                    "قال رسول الله ﷺ:\n\n'من كفّ غضبه وهو قادر عليه، أمّنه الله وأعطاه الإيمان.'\n\n'ليس الشديد بالصرعة، إنما الشديد الذي يملك نفسه عند الغضب.'",
                    "সহিহ বুখারী"
                )
            )
        ),
        AkhlaqTopic(
            id = 5,
            emoji = "🌾",
            nameBn = "বিনয়",
            nameEn = "Humility",
            nameAr = "التواضع",
            arabicName = "التواضع",
            descriptionBn = "নম্রতা ও বিনয়",
            descriptionEn = "Modesty and humility",
            descriptionAr = "اللين والتواضع",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "বিনয় কী?",
                    "What is Humility?",
                    "ما هو التواضع؟",
                    "বিনয় মানে অহংকার না করা, মানুষকে ছোট না ভাবা।\n\nনবী (সা.) বলেছেন: \"যে আল্লাহর জন্য বিনয়ী হয়, আল্লাহ তার মর্যাদা বাড়িয়ে দেন।\"",
                    "Humility means not being arrogant and not looking down on others.\n\nThe Prophet (PBUH) said: \"Whoever humbles himself for Allah, Allah elevates him.\"",
                    "التواضع هو ترك الكبر وعدم احتقار الناس.\n\nقال النبي ﷺ: 'من تواضع لله رفعه الله.'",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    2,
                    "নবী (সা.) এর বিনয়",
                    "Humility of the Prophet (PBUH)",
                    "تواضع النبي ﷺ",
                    "• নিজের কাজ নিজে করতেন\n• জুতা নিজে ঠিক করতেন\n• পশুর দুধ নিজে দোতেন\n• দাস-দাসীদের সাথে খেতেন\n• রাস্তায় শিশুদের সালাম দিতেন",
                    "• Did his own work\n• Mended his own shoes\n• Milked the animals himself\n• Ate with servants\n• Greeted children on the street",
                    "• كان يعمل بنفسه\n• كان يصلح نعله بيده\n• كان يحلب الشاة بنفسه\n• كان يأكل مع الخدم\n• كان يسلم على الصبيان في الطريق",
                    "সিরাত"
                ),
                AkhlaqSection(
                    3,
                    "অহংকারের পরিণাম",
                    "Consequence of Arrogance",
                    "عاقبة الكبر",
                    "ইবলিস অহংকারের কারণে জান্নাত থেকে বিতাড়িত হয়েছিল।\n\nনবী (সা.) বলেছেন: \"যার অন্তরে অণু পরিমাণ অহংকার থাকবে, সে জান্নাতে প্রবেশ করবে না।\"",
                    "Iblis was expelled from Paradise due to arrogance.\n\nThe Prophet (PBUH) said: \"Whoever has even an atom of arrogance in his heart will not enter Paradise.\"",
                    "أُخرج إبليس من الجنة بسبب الكبر.\n\nقال النبي ﷺ: 'لا يدخل الجنة من كان في قلبه مثقال ذرة من كبر.'",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    4,
                    "বিনয়ের প্রকার",
                    "Types of Humility",
                    "أنواع التواضع",
                    "• আল্লাহর কাছে বিনয়\n• পিতা-মাতার কাছে বিনয়\n• শিক্ষকের কাছে বিনয়\n• সাধারণ মানুষের সাথে বিনয়\n• জ্ঞানীদের সাথে বিনয়",
                    "• Humility before Allah\n• Humility before parents\n• Humility before teachers\n• Humility with common people\n• Humility with the knowledgeable",
                    "• التواضع لله\n• التواضع للوالدين\n• التواضع للعلماء\n• التواضع مع العامة\n• التواضع لأهل العلم",
                    "ইসলামিক শিক্ষা"
                ),
                AkhlaqSection(
                    5,
                    "বিনয়ের উপকারিতা",
                    "Benefits of Humility",
                    "فوائد التواضع",
                    "• আল্লাহর ভালোবাসা\n• মর্যাদা বৃদ্ধি\n• জ্ঞান বৃদ্ধি\n• সম্মান বৃদ্ধি\n• জান্নাতে উচ্চ মর্যাদা",
                    "• Allah's love\n• Increase in honor\n• Increase in knowledge\n• Increase in respect\n• High rank in Paradise",
                    "• محبة الله\n• رفعة القدر\n• زيادة العلم\n• زيادة الاحترام\n• علو المنزلة في الجنة",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    6,
                    "বিনয়ের চর্চা",
                    "Practicing Humility",
                    "ممارسة التواضع",
                    "• কাউকে ছোট না ভাবা\n• সবার সাথে ভালো ব্যবহার\n• উপকার করে খোটা না দেওয়া\n• প্রশংসা পেয়ে আল্লাহর শুকর\n• মাটিতে পা ফেলা",
                    "• Not looking down on anyone\n• Good behavior with all\n• Not reminding of favors\n• Thanking Allah when praised\n• Walking humbly on the earth",
                    "• عدم احتقار أحد\n• حسن المعاملة مع الجميع\n• عدم المنّ بالمعروف\n• شكر الله عند الثناء\n• المشي بتواضع على الأرض",
                    "ইসলামিক শিক্ষা"
                )
            )
        ),
        AkhlaqTopic(
            id = 6,
            emoji = "👨‍👩‍👧",
            nameBn = "পিতা-মাতার হক",
            nameEn = "Rights of Parents",
            nameAr = "بر الوالدين",
            arabicName = "بر الوالدين",
            descriptionBn = "মা-বাবার সেবা",
            descriptionEn = "Serving one's parents",
            descriptionAr = "خدمة الوالدين",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "পিতা-মাতার মর্যাদা",
                    "Status of Parents",
                    "مكانة الوالدين",
                    "আল্লাহ বলেন: \"তোমার প্রতিপালক নির্দেশ দিয়েছেন যে, তোমরা তিনি ছাড়া অন্য কারো ইবাদত করো না এবং পিতা-মাতার প্রতি সদ্ব্যবহার করো।\" (সূরা ইসরা: ২৩)",
                    "Allah says: \"Your Lord has decreed that you worship none but Him, and that you be kind to parents.\" (Surah Al-Isra: 23)",
                    "قال الله تعالى: 'وقضى ربك ألا تعبدوا إلا إياه وبالوالدين إحسانًا' (الإسراء: ٢٣).",
                    "কুরআন"
                ),
                AkhlaqSection(
                    2,
                    "মায়ের হক",
                    "Rights of the Mother",
                    "حق الأم",
                    "এক ব্যক্তি নবী (সা.) কে জিজ্ঞাসা করলেন:\n\n'কার প্রতি আমি সবচেয়ে বেশি সদ্ব্যবহার করব?'\n\nনবী (সা.) তিনবার বললেন 'তোমার মা', চতুর্থবার বললেন 'তোমার বাবা'।",
                    "A man asked the Prophet (PBUH):\n\n'Who deserves my best treatment?'\n\nThe Prophet (PBUH) said 'Your mother' three times, and the fourth time 'Your father'.",
                    "سأل رجل النبي ﷺ:\n\n'من أحق الناس بحسن صحابتي؟'\n\nقال: 'أمك' ثلاثًا، ثم 'أبوك'.",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    3,
                    "সদ্ব্যবহারের নিয়ম",
                    "Rules of Kindness",
                    "قواعد الإحسان",
                    "• তাদের কথা শোনা\n• তাদের সেবা করা\n• তাদের জন্য দুআ করা\n• তাদের সামনে নরম কথা বলা\n• তাদের অবাধ্য না হওয়া\n• বার্ধক্যে সেবা করা",
                    "• Listening to them\n• Serving them\n• Making dua for them\n• Speaking gently to them\n• Not disobeying them\n• Serving them in old age",
                    "• الاستماع إليهما\n• خدمتهما\n• الدعاء لهما\n• القول اللين لهما\n• عدم عقوقهما\n• خدمتهما عند الكبر",
                    "কুরআন"
                ),
                AkhlaqSection(
                    4,
                    "অবাধ্যতার পরিণাম",
                    "Consequence of Disobedience",
                    "عاقبة العقوق",
                    "নবী (সা.) বলেছেন: \"পিতা-মাতার অবাধ্যতা কবিরা গুনাহের একটি।\"\n\nআল্লাহ বলেন: \"তাদের সাথে 'উফ' শব্দও বলো না।\" (সূরা ইসরা: ২৩)",
                    "The Prophet (PBUH) said: \"Disobedience to parents is one of the major sins.\"\n\nAllah says: \"Do not say to them a word of contempt.\" (Surah Al-Isra: 23)",
                    "قال النبي ﷺ: 'عقوق الوالدين من الكبائر.'\n\nقال الله: 'فلا تقل لهما أف' (الإسراء: ٢٣).",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    5,
                    "মৃত পিতা-মাতার হক",
                    "Rights of Deceased Parents",
                    "حقوق الوالدين بعد وفاتهما",
                    "• তাদের জন্য দুআ করা\n• তাদের জন্য সদকা করা\n• তাদের আত্মীয়দের সাথে সম্পর্ক রাখা\n• তাদের ঋণ পরিশোধ করা\n• তাদের ওসিয়ত পূরণ করা",
                    "• Making dua for them\n• Giving charity on their behalf\n• Maintaining ties with their relatives\n• Paying off their debts\n• Fulfilling their will",
                    "• الدعاء لهما\n• الصدقة عنهما\n• صلة أرحامهما\n• قضاء ديونهما\n• تنفيذ وصيتهما",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    6,
                    "সন্তানের হক",
                    "Rights of Children",
                    "حقوق الأولاد",
                    "• সুন্দর নাম রাখা\n• শিক্ষা দেওয়া\n• ভালো تربیت করা\n• সমান আচরণ করা\n• বিয়ে দেওয়া\n• সম্পদের অংশ দেওয়া",
                    "• Giving a good name\n• Providing education\n• Good upbringing\n• Equal treatment\n• Arranging marriage\n• Giving share in wealth",
                    "• التسمية الحسنة\n• التعليم\n• حسن التربية\n• العدل بينهم\n• التزويج\n• إعطاؤهم نصيبهم من المال",
                    "ইসলামিক শিক্ষা"
                )
            )
        ),
        AkhlaqTopic(
            id = 7,
            emoji = "🗣️",
            nameBn = "জিহ্বার হেফাজত",
            nameEn = "Guarding the Tongue",
            nameAr = "حفظ اللسان",
            arabicName = "حفظ اللسان",
            descriptionBn = "মুখ সামলে চলা",
            descriptionEn = "Controlling one's speech",
            descriptionAr = "ضبط اللسان",
            sections = listOf(
                AkhlaqSection(
                    1,
                    "জিহ্বার গুরুত্ব",
                    "Importance of the Tongue",
                    "أهمية اللسان",
                    "নবী (সা.) বলেছেন: \"যে ব্যক্তি তার জিহ্বা ও লজ্জাস্থান হেফাজত করবে, আমি তার জান্নাতের জামিন।\"\n\nজিহ্বার কারণে মানুষ জাহান্নামে যায়।",
                    "The Prophet (PBUH) said: \"Whoever guards his tongue and his private parts, I guarantee him Paradise.\"\n\nMany enter Hell due to their tongues.",
                    "قال النبي ﷺ: 'من حفظ لسانه وفرجه، أضمن له الجنة.'\n\nوكثير من الناس يدخلون النار بسبب ألسنتهم.",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    2,
                    "গীবত",
                    "Backbiting",
                    "الغيبة",
                    "গীবত মানে কারো অনুপস্থিতিতে তার দোষ বলা।\n\nআল্লাহ বলেন: \"তোমরা গীবত করো না।\" (সূরা হুজুরাত: ১২)\n\nগীবত করা মৃত ভাইয়ের মাংস খাওয়ার সমান।",
                    "Backbiting means mentioning someone's faults in their absence.\n\nAllah says: \"Do not backbite one another.\" (Surah Al-Hujurat: 12)\n\nBackbiting is like eating the flesh of a dead brother.",
                    "الغيبة هي ذكر عيوب أخيك في غيبته.\n\nقال الله: 'ولا يغتب بعضكم بعضًا' (الحجرات: ١٢).\n\nوالغيبة كأكل لحم أخيك ميتًا.",
                    "কুরআন"
                ),
                AkhlaqSection(
                    3,
                    "মিথ্যা",
                    "Lying",
                    "الكذب",
                    "মিথ্যা সব দোষের মূল।\n\nনবী (সা.) বলেছেন: \"মুমিন মিথ্যাবাদী হতে পারে না।\"\n\nসব মিথ্যা হারাম, তবে যুদ্ধ, দাঙ্গা মিটানো ও স্বামী-স্ত্রীর মধ্যে সন্ধি স্থাপনে অনুমতি আছে।",
                    "Lying is the root of all evils.\n\nThe Prophet (PBUH) said: \"A believer cannot be a liar.\"\n\nAll lying is forbidden, except in war, reconciling disputes, and reconciling spouses.",
                    "الكذب أصل الشرور.\n\nقال النبي ﷺ: 'لا يكون المؤمن كذابًا.'\n\nوالكذب حرام، إلا في الحرب وإصلاح ذات البين وإصلاح بين الزوجين.",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    4,
                    "পরনিন্দা",
                    "Slander/Tale-carrying",
                    "النميمة",
                    "পরনিন্দা (নামিমা) মানে মানুষের কথা কুচকুচে বলা।\n\nনবী (সা.) বলেছেন: \"পরনিন্দাকারী জান্নাতে প্রবেশ করবে না।\"",
                    "Namiimah (tale-carrying) means spreading people's words to cause discord.\n\nThe Prophet (PBUH) said: \"The tale-carrier will not enter Paradise.\"",
                    "النميمة هي نقل الكلام بين الناس للإفساد.\n\nقال النبي ﷺ: 'لا يدخل الجنة نمّام.'",
                    "সহিহ মুসলিম"
                ),
                AkhlaqSection(
                    5,
                    "অশ্লীল কথা",
                    "Obscene Speech",
                    "الكلام الفاحش",
                    "• গালি না দেওয়া\n• অশ্লীল কথা না বলা\n• অভিশাপ না দেওয়া\n• বিদ্রুপ না করা\n• মিথ্যা সাক্ষ্য না দেওয়া",
                    "• Not abusing\n• Not using obscene words\n• Not cursing\n• Not mocking\n• Not giving false testimony",
                    "• عدم السب\n• عدم الفحش في القول\n• عدم اللعن\n• عدم السخرية\n• عدم شهادة الزور",
                    "সহিহ বুখারী"
                ),
                AkhlaqSection(
                    6,
                    "ভালো কথা",
                    "Good Speech",
                    "الكلام الطيب",
                    "নবী (সা.) বলেছেন:\n\n'যে আল্লাহ ও আখিরাতের দিনে বিশ্বাস রাখে, সে যেন ভালো কথা বলে অথবা চুপ থাকে।'\n\nভালো কথা সদকা।",
                    "The Prophet (PBUH) said:\n\n'Whoever believes in Allah and the Last Day, let him speak good or remain silent.'\n\nA good word is charity.",
                    "قال النبي ﷺ:\n\n'من كان يؤمن بالله واليوم الآخر فليقل خيرًا أو ليصمت.'\n\nوالكلمة الطيبة صدقة.",
                    "সহিহ বুখারী"
                )
            )
        )
    )

    fun getTopicById(id: Int): AkhlaqTopic? {
        return allTopics.find { it.id == id }
    }
}