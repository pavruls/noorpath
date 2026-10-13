package com.pavruls.noorpath.fiqh

import com.pavruls.noorpath.data.preferences.LanguageManager

data class FiqhTopic(
    val id: Int,
    val emoji: String,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val arabicName: String,
    val descriptionBn: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val masalas: List<Masala>
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

data class Masala(
    val id: Int,
    val questionBn: String,
    val questionEn: String,
    val questionAr: String,
    val answerBn: String,
    val answerEn: String,
    val answerAr: String,
    val reference: String = ""
) {
    fun getQuestion(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> questionEn
        LanguageManager.LANG_AR -> questionAr
        else -> questionBn
    }
    fun getAnswer(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> answerEn
        LanguageManager.LANG_AR -> answerAr
        else -> answerBn
    }
}

object FiqhData {
    val allTopics = listOf(
        FiqhTopic(
            id = 1,
            emoji = "🕌",
            nameBn = "নামাজ",
            nameEn = "Salah (Prayer)",
            nameAr = "الصلاة",
            arabicName = "الصلاة",
            descriptionBn = "অজু, ৫ ওয়াক্ত, সুন্নত, নফল",
            descriptionEn = "Wudu, 5 times, Sunnah, Nafl",
            descriptionAr = "الوضوء، خمس صلوات، السنة، النفل",
            masalas = listOf(
                Masala(
                    1,
                    "অজুর ফরজ কতটি?",
                    "How many Fard in Wudu?",
                    "كم فرضًا في الوضوء؟",
                    "অজুর ৪টি ফরজ:\n\n১. মুখমণ্ডল ধোয়া\n২. কনুইসহ দুই হাত ধোয়া\n৩. মাথার এক-চতুর্থাংশ মাসেহ করা\n৪. টাখনুসহ দুই পা ধোয়া।\n\nএগুলোর কোনো একটি বাদ পড়লে অজু হবে না।",
                    "There are 4 Fard in Wudu:\n\n1. Washing the face\n2. Washing both hands up to the elbows\n3. Masah (wiping) one-fourth of the head\n4. Washing both feet up to the ankles.\n\nIf any one is missed, Wudu is invalid.",
                    "فرائض الوضوء أربعة:\n\n١. غسل الوجه\n٢. غسل اليدين إلى المرفقين\n٣. مسح ربع الرأس\n٤. غسل الرجلين إلى الكعبين.\n\nإذا فات واحد منها لم يصح الوضوء.",
                    "মারাকিল ফালাহ"
                ),
                Masala(
                    2,
                    "অজু ভঙ্গের কারণ কী কী?",
                    "What breaks Wudu?",
                    "ما نواقض الوضوء؟",
                    "অজু ভঙ্গের প্রধান কারণ:\n\n১. পেশাব-পায়খানা করা\n২. বায়ু নির্গত হওয়া\n৩. শরীরের কোনো স্থান থেকে রক্ত/পুঁজ গড়িয়ে পড়া\n৪. মুখ ভরে বমি করা\n৫. চিৎ/কাত হয়ে ঘুমানো\n৬. নামাজে শব্দ করে হাসা\n৭. পাগল/অচেতন হওয়া",
                    "Main things that break Wudu:\n\n1. Urination or defecation\n2. Passing wind\n3. Flowing blood or pus from the body\n4. Vomiting a mouthful\n5. Sleeping lying down\n6. Laughing aloud in prayer\n7. Becoming insane or unconscious",
                    "نواقض الوضوء الرئيسية:\n\n١. البول والغائط\n٢. خروج الريح\n٣. سيلان الدم أو القيح\n٤. القيء ملء الفم\n٥. النوم مضطجعًا\n٦. الضحك في الصلاة\n٧. الجنون أو الإغماء",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    3,
                    "দিনে কত ওয়াক্ত নামাজ ফরজ?",
                    "How many prayers per day?",
                    "كم صلاة مفروضة في اليوم؟",
                    "দিনে ৫ ওয়াক্ত নামাজ ফরজ:\n\n১. ফজর — ২ রাকাত\n২. যোহর — ৪ রাকাত\n৩. আসর — ৪ রাকাত\n৪. মাগরিব — ৩ রাকাত\n৫. ইশা — ৪ রাকাত\n\nমোট ১৭ রাকাত ফরজ।",
                    "5 prayers are Fard per day:\n\n1. Fajr — 2 Rakats\n2. Dhuhr — 4 Rakats\n3. Asr — 4 Rakats\n4. Maghrib — 3 Rakats\n5. Isha — 4 Rakats\n\nTotal 17 Rakats Fard.",
                    "خمس صلوات مفروضة في اليوم:\n\n١. الفجر — ركعتان\n٢. الظهر — أربع\n٣. العصر — أربع\n٤. المغرب — ثلاث\n٥. العشاء — أربع\n\nالمجموع ١٧ ركعة.",
                    "কুরআন ও সুন্নাহ"
                ),
                Masala(
                    4,
                    "নামাজের ফরজ কতটি?",
                    "How many Fard in Salah?",
                    "كم فرضًا في الصلاة؟",
                    "নামাজের ৭টি ফরজ:\n\n১. তাকবিরে তাহরিমা\n২. কিয়াম (দাঁড়ানো)\n৩. কিরাআত (কুরআন পড়া)\n৪. রুকু\n৫. দুই সিজদা\n৬. শেষ বৈঠক\n৭. সালাম ফিরানো",
                    "There are 7 Fard in Salah:\n\n1. Takbirat al-Ihram\n2. Qiyam (standing)\n3. Qira'ah (recitation)\n4. Ruku\n5. Two Sajdahs\n6. Final sitting\n7. Salam",
                    "فرائض الصلاة سبعة:\n\n١. تكبيرة الإحرام\n٢. القيام\n٣. القراءة\n٤. الركوع\n٥. سجدتان\n٦. القعدة الأخيرة\n٧. السلام",
                    "হেদায়া"
                ),
                Masala(
                    5,
                    "নামাজ ভঙ্গের কারণ কী?",
                    "What invalidates Salah?",
                    "ما مبطلات الصلاة؟",
                    "নামাজ ভঙ্গের কারণ:\n\n১. কথা বলা\n২. শব্দ করে হাসা\n৩. ইচ্ছাকৃত পানাহার\n৪. কিবলা থেকে বুক ঘুরে যাওয়া\n৫. বেশি নড়াচড়া করা\n৬. অজু ভেঙে যাওয়া\n৭. নামাজের রুকন বাদ পড়া",
                    "Things that invalidate Salah:\n\n1. Speaking\n2. Laughing aloud\n3. Eating or drinking intentionally\n4. Turning chest away from Qibla\n5. Excessive movement\n6. Wudu breaking\n7. Missing a pillar of Salah",
                    "مبطلات الصلاة:\n\n١. الكلام\n٢. الضحك بصوت\n٣. الأكل والشرب عمدًا\n٤. الانحراف عن القبلة\n٥. الحركة الكثيرة\n٦. نقض الوضوء\n٧. ترك ركن من الصلاة",
                    "ফিকহুল ইবাদাত"
                ),
                Masala(
                    6,
                    "কাজা নামাজ কী?",
                    "What is Qada prayer?",
                    "ما هي صلاة القضاء؟",
                    "নির্ধারিত সময়ে নামাজ আদায় করতে না পারলে পরে যা পড়তে হয় তাকে কাজা নামাজ বলে।\n\nকাজা করা ফরজ। সুন্নত-নফল কাজা নয়। তবে ফজরের সুন্নত ও বিতর কাজা করা উত্তম।",
                    "Qada prayer is the prayer offered later when one misses the prescribed time.\n\nQada is obligatory. Sunnah and Nafl are not. But Qada of Fajr Sunnah and Witr is better to make up.",
                    "صلاة القضاء هي التي تُؤدى بعد خروج وقتها.\n\nوالقضاء فرض. ولا يُقضى النفل والسنة. لكن قضاء سنة الفجر والوتر أفضل.",
                    "রাদ্দুল মুহতার"
                )
            )
        ),
        FiqhTopic(
            id = 2,
            emoji = "🌙",
            nameBn = "রোজা",
            nameEn = "Fasting (Sawm)",
            nameAr = "الصيام",
            arabicName = "الصيام",
            descriptionBn = "রমজান, কাজা, কাফফারা, মুস্তাহাব",
            descriptionEn = "Ramadan, Qada, Kaffarah, Mustahab",
            descriptionAr = "رمضان، القضاء، الكفارة، المستحب",
            masalas = listOf(
                Masala(
                    1,
                    "রোজা কী এবং কত প্রকার?",
                    "What is Fasting and its types?",
                    "ما هو الصيام وأنواعه؟",
                    "রোজা হলো সুবহে সাদিক থেকে সূর্যাস্ত পর্যন্ত পানাহার ও স্ত্রী-সহবাস থেকে বিরত থাকা।\n\nপ্রকার:\n১. ফরজ — রমজানের রোজা\n২. ওয়াজিব — কাজা, কাফফারা\n৩. সুন্নত — আরাফা, আশুরা\n৪. নফল — সোম-বৃহস্পতি",
                    "Fasting is abstaining from food, drink and marital relations from dawn to sunset.\n\nTypes:\n1. Fard — Ramadan fasting\n2. Wajib — Qada, Kaffarah\n3. Sunnah — Arafah, Ashura\n4. Nafl — Mondays and Thursdays",
                    "الصيام هو الامتناع عن الطعام والشراب والجماع من الفجر إلى المغرب.\n\nأنواعه:\n١. فرض — صيام رمضان\n٢. واجب — القضاء والكفارة\n٣. سنة — عرفة وعاشوراء\n٤. نفل — الاثنين والخميس",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    2,
                    "রোজা ভঙ্গের কারণ কী?",
                    "What breaks the fast?",
                    "ما مفطرات الصيام؟",
                    "রোজা ভঙ্গের কারণ:\n\n১. ইচ্ছাকৃত পানাহার\n২. স্ত্রী-সহবাস\n৩. ইচ্ছাকৃত বমি\n৪. মুখ ভরে বমি\n৫. রক্ত দেওয়া-নেওয়া\n৬. মাসিক বা নেফাস\n৭. পাগল হওয়া",
                    "Things that break the fast:\n\n1. Intentional eating or drinking\n2. Marital relations\n3. Intentional vomiting\n4. Vomiting a mouthful\n5. Blood transfusion\n6. Menstruation or postnatal bleeding\n7. Becoming insane",
                    "مفطرات الصيام:\n\n١. الأكل والشرب عمدًا\n٢. الجماع\n٣. القيء عمدًا\n٤. القيء ملء الفم\n٥. نقل الدم\n٦. الحيض والنفاس\n٧. الجنون",
                    "হেদায়া"
                ),
                Masala(
                    3,
                    "কাফফারা কী?",
                    "What is Kaffarah?",
                    "ما هي الكفارة؟",
                    "রোজা ভঙ্গের কাফফারা:\n\n১. একটি দাস মুক্ত করা\n২. ৬০ দিন ধারাবাহিক রোজা\n৩. ৬০ জন মিসকিনকে খাবার দেওয়া\n\nতবে ইচ্ছাকৃত পানাহার/সহবাসে কাফফারা ওয়াজিব। সাধারণ কারণে শুধু কাজা।",
                    "Kaffarah for breaking the fast:\n\n1. Freeing a slave\n2. Fasting 60 consecutive days\n3. Feeding 60 poor people\n\nKaffarah is Wajib only for intentional eating/drinking or marital relations. Otherwise, only Qada.",
                    "كفارة إفطار رمضان:\n\n١. عتق رقبة\n٢. صيام شهرين متتابعين\n٣. إطعام ستين مسكينًا\n\nوالكفارة واجبة في الأكل أو الجماع عمدًا. وإلا فالقضاء فقط.",
                    "ফিকহুল ইবাদাত"
                ),
                Masala(
                    4,
                    "রোজা অবস্থায় কী কী করা যায়?",
                    "What is allowed while fasting?",
                    "ما يجوز للصائم؟",
                    "রোজা অবস্থায় যা করা যায়:\n\n১. গোসল করা\n২. মিসওয়াক করা\n৩. সুগন্ধি ব্যবহার\n৪. ইনজেকশন নেওয়া (খাদ্যবিহীন)\n৫. রক্ত পরীক্ষা\n৬. ভুলে পানাহার",
                    "Things allowed while fasting:\n\n1. Taking a bath\n2. Using Miswak\n3. Using perfume\n4. Non-nutritional injection\n5. Blood test\n6. Eating or drinking forgetfully",
                    "ما يجوز للصائم:\n\n١. الاغتسال\n٢. السواك\n٣. الطيب\n٤. الإبر غير المغذية\n٥. تحليل الدم\n٦. الأكل أو الشرب نسيانًا",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    5,
                    "ইফতার ও সাহ্রির সময়?",
                    "Time for Iftar and Sahri?",
                    "وقت الإفطار والسحور؟",
                    "সাহ্রি: সুবহে সাদিকের আগে।\nইফতার: সূর্যাস্তের পর।\n\nরাসূল (সা.) বলেছেন: \"তোমরা সাহ্রি খাও, কারণ সাহ্রিতে বরকত আছে।\" (বুখারী)\n\nইফতারে বিলম্ব না করা সুন্নত।",
                    "Sahri: before dawn.\nIftar: after sunset.\n\nThe Messenger (PBUH) said: \"Eat Sahri, for in Sahri there is blessing.\" (Bukhari)\n\nNot delaying Iftar is Sunnah.",
                    "السحور: قبل الفجر.\nالإفطار: بعد المغرب.\n\nقال رسول الله ﷺ: 'تسحروا فإن في السحور بركة' (البخاري).\n\nوتعجيل الإفطار سنة.",
                    "সহিহ বুখারী"
                ),
                Masala(
                    6,
                    "যে সব কারণে রোজা না রাখা যায়?",
                    "When can one skip fasting?",
                    "متى يجوز الإفطار؟",
                    "অসুস্থতা, সফর, গর্ভাবস্থা, স্তন্যদান, বার্ধক্য — এসব কারণে রোজা না রাখা যায়।\n\nপরে কাজা দিতে হবে (গর্ভবতী/স্তন্যদায়িনীর কিছু ক্ষেত্রে ফিদইয়া)।",
                    "Illness, travel, pregnancy, breastfeeding and old age — these allow one to skip fasting.\n\nLater Qada must be made up (in some cases of pregnancy/breastfeeding, Fidyah).",
                    "يجوز الإفطار في المرض والسفر والحمل والإرضاع والكبر.\n\nويجب القضاء لاحقًا (وفي بعض حالات الحمل والإرضاع الفدية).",
                    "সূরা বাকারা ১৮৪-১৮৫"
                )
            )
        ),
        FiqhTopic(
            id = 3,
            emoji = "💰",
            nameBn = "যাকাত",
            nameEn = "Zakat",
            nameAr = "الزكاة",
            arabicName = "الزكاة",
            descriptionBn = "নিসাব, হার, ৮ প্রকার",
            descriptionEn = "Nisab, rate, 8 categories",
            descriptionAr = "النصاب، النسبة، ثمانية أصناف",
            masalas = listOf(
                Masala(
                    1,
                    "যাকাত কী এবং কেন ফরজ?",
                    "What is Zakat and why is it Fard?",
                    "ما هي الزكاة ولماذا فُرضت؟",
                    "যাকাত ইসলামের ৫টি স্তম্ভের একটি।\n\nআল্লাহ বলেন: \"তোমরা নামাজ কায়েম করো এবং যাকাত দাও।\" (সূরা বাকারা: ৪৩)\n\nসামর্থ্যবান মুসলিমের উপর সম্পদের ২.৫% বার্ষিক দিতে হয়।",
                    "Zakat is one of the five pillars of Islam.\n\nAllah says: \"Establish prayer and give Zakat.\" (Surah Al-Baqarah: 43)\n\nA wealthy Muslim must give 2.5% of wealth annually.",
                    "الزكاة أحد أركان الإسلام الخمسة.\n\nقال الله تعالى: 'وأقيموا الصلاة وآتوا الزكاة' (البقرة: ٤٣).\n\nويجب على المسلم المالك للنصاب إخراج ٢.٥% سنويًا.",
                    "কুরআন"
                ),
                Masala(
                    2,
                    "যাকাতের নিসাব কত?",
                    "What is the Nisab of Zakat?",
                    "ما نصاب الزكاة؟",
                    "নিসাব:\n\n১. স্বর্ণ — ৭.৫ তোলা\n২. রূপা — ৫২.৫ তোলা\n৩. নগদ — রূপার সমমূল্য\n৪. ব্যবসার পণ্য — রূপার সমমূল্য\n৫. ফসল — ৫ ওসক (প্রায় ৯৪০ কেজি)\n\nনিসাব অতিক্রম করে এক বছর হলে যাকাত ফরজ।",
                    "Nisab:\n\n1. Gold — 7.5 tola\n2. Silver — 52.5 tola\n3. Cash — equivalent to silver\n4. Business goods — equivalent to silver\n5. Crops — 5 Wasaq (~940 kg)\n\nZakat is due after one year passes on wealth exceeding Nisab.",
                    "النصاب:\n\n١. الذهب — ٧.٥ تولا\n٢. الفضة — ٥٢.٥ تولا\n٣. النقد — ما يعادل الفضة\n٤. عروض التجارة — ما يعادل الفضة\n٥. الزروع — ٥ أوسق (٩٤٠ كجم)\n\nوتجب الزكاة بعد مرور الحول.",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    3,
                    "যাকাতের হার কত?",
                    "What is the Zakat rate?",
                    "ما مقدار الزكاة؟",
                    "সাধারণ সম্পদের যাকাত: ২.৫% (৪০ ভাগের ১ ভাগ)\n\nফসলের যাকাত:\n- বৃষ্টির পানিতে: ১০%\n- সেচ দিয়ে: ৫%\n\nখনিজ সম্পদের যাকাত: ২০% (খুমুস)",
                    "Zakat on general wealth: 2.5% (1/40)\n\nZakat on crops:\n- Rain-watered: 10%\n- Irrigated: 5%\n\nZakat on minerals: 20% (Khums)",
                    "زكاة المال: ٢.٥% (ربع العشر)\n\nزكاة الزروع:\n- بماء المطر: العشر\n- بالسقي: نصف العشر\n\nزكاة المعادن: الخمس (٢٠%)",
                    "হেদায়া"
                ),
                Masala(
                    4,
                    "যাকাত কারা পাবে?",
                    "Who can receive Zakat?",
                    "من يستحق الزكاة؟",
                    "কুরআনে ৮ শ্রেণি:\n\n১. ফকির\n২. মিসকিন\n৩. যাকাত আদায়ে নিয়োজিত\n৪. যাদের অন্তর আকৃষ্ট করতে হয়\n৫. দাস মুক্তির জন্য\n৬. ঋণগ্রস্ত\n৭. আল্লাহর পথে\n৮. মুসাফির",
                    "Eight categories in the Quran:\n\n1. The poor\n2. The needy\n3. Zakat collectors\n4. Those whose hearts are to be reconciled\n5. To free slaves\n6. Those in debt\n7. In the way of Allah\n8. The wayfarer",
                    "ثمانية أصناف في القرآن:\n\n١. الفقراء\n٢. المساكين\n٣. العاملون عليها\n٤. المؤلفة قلوبهم\n٥. في الرقاب\n٦. الغارمون\n٧. في سبيل الله\n٨. ابن السبيل",
                    "সূরা তাওবা: ৬০"
                ),
                Masala(
                    5,
                    "যাকাত না দিলে কী হবে?",
                    "What if one does not pay Zakat?",
                    "ما جزاء مانع الزكاة؟",
                    "আল্লাহ বলেন: \"যারা স্বর্ণ-রূপা জমা করে রাখে এবং আল্লাহর পথে ব্যয় করে না, তাদের কঠিন শাস্তির সুসংবাদ দাও।\" (সূরা তাওবা: ৩৪)\n\nহাদিসে বলা হয়েছে: যাকাত না দিলে সম্পদ বিষধর সাপে পরিণত হবে।",
                    "Allah says: \"Those who hoard gold and silver and do not spend in Allah's way — give them tidings of a painful punishment.\" (Surah At-Tawbah: 34)\n\nHadith says wealth will become a poisonous snake for those who do not give Zakat.",
                    "قال الله تعالى: 'والذين يكنزون الذهب والفضة ولا ينفقونها في سبيل الله فبشرهم بعذاب أليم' (التوبة: ٣٤).\n\nوفي الحديث: أن المال يصير حية يطوّق بها.",
                    "সহিহ মুসলিম"
                ),
                Masala(
                    6,
                    "ফিতরা কী এবং কত?",
                    "What is Fitrah and its amount?",
                    "ما هي الفطرة ومقدارها؟",
                    "ফিতরা হলো রমজানের শেষে ঈদের নামাজের আগে দেয়া ওয়াজিব সদকা।\n\nপরিমাণ: ৩.৫ সে.প্র. (প্রায় ২.৫ কেজি) গম/যব/খেজুর/কিশমিশ বা তার মূল্য।\n\nপরিবারের প্রত্যেক সদস্যের জন্য দিতে হয়।",
                    "Fitrah is the Wajib charity given at the end of Ramadan before Eid prayer.\n\nAmount: ~2.5 kg of wheat/barley/dates/raisins or its value.\n\nMust be given for every family member.",
                    "الفطرة صدقة واجبة عند انتهاء رمضان قبل صلاة العيد.\n\nالمقدار: صاع من البر أو الشعير أو التمر أو الزبيب أو قيمته.\n\nتُخرج عن كل فرد من الأسرة.",
                    "ফিকহুল ইবাদাত"
                )
            )
        ),
        FiqhTopic(
            id = 4,
            emoji = "🕋",
            nameBn = "হজ",
            nameEn = "Hajj",
            nameAr = "الحج",
            arabicName = "الحج",
            descriptionBn = "ফরজ, ওয়াজিব, সুন্নত, আহকাম",
            descriptionEn = "Fard, Wajib, Sunnah, Rulings",
            descriptionAr = "الفرض، الواجب، السنة، الأحكام",
            masalas = listOf(
                Masala(
                    1,
                    "হজ কাদের উপর ফরজ?",
                    "On whom is Hajj Fard?",
                    "على من يجب الحج؟",
                    "হজ ফরজ হওয়ার শর্ত:\n\n১. মুসলিম হওয়া\n২. প্রাপ্তবয়স্ক হওয়া\n৩. সুস্থ মস্তিষ্ক\n৪. স্বাধীন হওয়া\n৫. সামর্থ্য থাকা (আর্থিক ও শারীরিক)\n\nজীবনে একবার হজ ফরজ।",
                    "Conditions for Hajj being Fard:\n\n1. Being Muslim\n2. Being adult\n3. Sound mind\n4. Being free\n5. Being able (financially & physically)\n\nHajj is Fard once in a lifetime.",
                    "شروط وجوب الحج:\n\n١. الإسلام\n٢. البلوغ\n٣. العقل\n٤. الحرية\n٥. القدرة (المالية والبدنية)\n\nويجب مرة واحدة في العمر.",
                    "সূরা আলে ইমরান ৯৭"
                ),
                Masala(
                    2,
                    "হজের ফরজ কতটি?",
                    "How many Fard in Hajj?",
                    "كم فرضًا في الحج؟",
                    "হজের ৩টি ফরজ:\n\n১. ইহরাম বাঁধা\n২. ৯ জিলহজ আরাফায় অবস্থান\n৩. তাওয়াফে যিয়ারত\n\nএগুলো ছুটে গেলে হজ হবে না।",
                    "Three Fard in Hajj:\n\n1. Ihram\n2. Standing at Arafah on 9 Dhul-Hijjah\n3. Tawaf al-Ziyarah\n\nIf missed, Hajj is invalid.",
                    "فرائض الحج ثلاثة:\n\n١. الإحرام\n٢. الوقوف بعرفة في ٩ ذي الحجة\n٣. طواف الزيارة\n\nومن تركها لم يصح حجه.",
                    "হেদায়া"
                ),
                Masala(
                    3,
                    "হজের ওয়াজিব কতটি?",
                    "Wajibat of Hajj?",
                    "واجبات الحج؟",
                    "হজের প্রধান ওয়াজিব:\n\n১. মুযদালিফায় রাত্রি যাপন\n২. সাফা-মারওয়া সাঈ\n৩. মাথা মুণ্ডানো\n৪. মক্কার বাইরে থেকে তাওয়াফ\n৫. বিদায়ী তাওয়াফ\n৬. জামরায় কঙ্কর নিক্ষেপ",
                    "Main Wajibat of Hajj:\n\n1. Staying overnight at Muzdalifah\n2. Sa'i between Safa and Marwah\n3. Shaving or trimming hair\n4. Tawaf from outside Makkah\n5. Farewell Tawaf\n6. Stoning the Jamarat",
                    "واجبات الحج:\n\n١. المبيت بمنى\n٢. السعي بين الصفا والمروة\n٣. الحلق أو التقصير\n٤. طواف الإفاضة من خارج مكة\n٥. طواف الوداع\n٦. رمي الجمار",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    4,
                    "ইহরাম কী?",
                    "What is Ihram?",
                    "ما هو الإحرام؟",
                    "ইহরাম হলো হজ বা উমরার নিয়তসহ নির্দিষ্ট সীমা (মিকাত) থেকে বিশেষ পোশাক পরিধান করে কিছু কাজ থেকে বিরত থাকা।\n\nপুরুষ: দুই টুকরো সেলাইবিহীন কাপড়\nনারী: সাধারণ পোশাক (মুখ-হাত খোলা রাখবে)",
                    "Ihram is entering the state of Hajj/Umrah at Miqat with special clothing and abstaining from certain acts.\n\nMen: two unstitched sheets\nWomen: normal clothing (face & hands uncovered)",
                    "الإحرام هو الدخول في نسك الحج أو العمرة من الميقات بلباس مخصوص مع الامتناع عن محظورات.\n\nالرجل: إزار ورداء\nالمرأة: لباسها العادي مع كشف الوجه واليدين",
                    "ফিকহুল ইবাদাত"
                ),
                Masala(
                    5,
                    "ইহরাম অবস্থায় কী নিষিদ্ধ?",
                    "What is forbidden in Ihram?",
                    "محظورات الإحرام؟",
                    "ইহরামে নিষিদ্ধ:\n\n১. চুল/নখ কাটা\n২. সুগন্ধি ব্যবহার\n৩. শিকার করা\n৪. বিবাহ করা/দেওয়া\n৫. স্ত্রী-সহবাস\n৬. সেলাইযুক্ত পোশাক (পুরুষ)\n৭. মুখ ঢাকা (পুরুষ)",
                    "Forbidden in Ihram:\n\n1. Cutting hair/nails\n2. Using perfume\n3. Hunting\n4. Marrying or giving in marriage\n5. Marital relations\n6. Stitched clothing (men)\n7. Covering the face (men)",
                    "محظورات الإحرام:\n\n١. حلق الشعر وتقليم الأظافر\n٢. الطيب\n٣. الصيد\n٤. النكاح\n٥. الجماع\n٦. لباس المخيط (للرجال)\n٧. تغطية الوجه (للرجال)",
                    "হেদায়া"
                ),
                Masala(
                    6,
                    "উমরা কী?",
                    "What is Umrah?",
                    "ما هي العمرة؟",
                    "উমরা হলো ছোট হজ। এটি সুন্নত মুয়াক্কাদা।\n\nউমরার ফরজ ২টি:\n১. ইহরাম\n২. তাওয়াফ\n\nসাঈ ও হালাল হওয়া ওয়াজিব।\n\nরমজানে উমরার সওয়াব হজের সমান।",
                    "Umrah is the lesser Hajj. It is Sunnah Mu'akkadah.\n\nTwo Fard in Umrah:\n1. Ihram\n2. Tawaf\n\nSa'i and Halal are Wajib.\n\nUmrah in Ramadan equals Hajj in reward.",
                    "العمرة هي الحج الأصغر. وهي سنة مؤكدة.\n\nفرائضها اثنان:\n١. الإحرام\n٢. الطواف\n\nوالسعي والحلق واجبان.\n\nوعمرة في رمضان تعدل حجة.",
                    "সহিহ বুখারী"
                )
            )
        ),
        FiqhTopic(
            id = 5,
            emoji = "⚖️",
            nameBn = "লেনদেন",
            nameEn = "Transactions",
            nameAr = "المعاملات",
            arabicName = "المعاملات",
            descriptionBn = "ব্যবসা, সুদ, হালাল-হারাম",
            descriptionEn = "Business, interest, halal-haram",
            descriptionAr = "التجارة، الربا، الحلال والحرام",
            masalas = listOf(
                Masala(
                    1,
                    "হালাল ব্যবসার শর্ত কী?",
                    "Conditions of halal business?",
                    "شروط التجارة الحلال؟",
                    "হালাল ব্যবসার শর্ত:\n\n১. পণ্য হালাল হতে হবে\n২. ক্রেতা-বিক্রেতার সম্মতি\n৩. মালিকানার অধিকার\n৪. মূল্য নির্দিষ্ট\n৫. প্রতারণা না থাকা\n৬. মিথ্যা না বলা",
                    "Conditions for halal business:\n\n1. Product must be halal\n2. Mutual consent\n3. Ownership\n4. Price specified\n5. No deception\n6. No lying",
                    "شروط التجارة الحلال:\n\n١. المبيع حلال\n٢. التراضي\n٣. الملكية\n٤. تحديد الثمن\n٥. عدم الغش\n٦. عدم الكذب",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    2,
                    "সুদ (রিবা) কী?",
                    "What is Riba (interest)?",
                    "ما هو الربا؟",
                    "সুদ হলো ঋণ বা লেনদেনে ন্যায্যতার বাইরে অতিরিক্ত কিছু নেওয়া।\n\nআল্লাহ বলেন: \"আল্লাহ সুদকে নিষিদ্ধ করেছেন এবং ব্যবসাকে হালাল করেছেন।\" (সূরা বাকারা: ২৭৫)\n\nসুদ মহাপাপ। রাসূল (সা.) সুদদাতা, গ্রহীতা, লেখক ও সাক্ষী সবাইকে অভিশাপ দিয়েছেন।",
                    "Riba is taking extra wealth in a loan or exchange beyond fairness.\n\nAllah says: \"Allah has forbidden interest and permitted trade.\" (Surah Al-Baqarah: 275)\n\nRiba is a major sin. The Prophet (PBUH) cursed the giver, taker, writer, and witness of interest.",
                    "الربا هو الزيادة في القرض أو المعاملة بغير حق.\n\nقال الله تعالى: 'وأحل الله البيع وحرم الربا' (البقرة: ٢٧٥).\n\nوالربا من الكبائر. ولعن النبي ﷺ آكله وموكله وكاتبه وشاهديه.",
                    "সহিহ মুসলিম"
                ),
                Masala(
                    3,
                    "কোন ব্যবসা হারাম?",
                    "Which businesses are haram?",
                    "ما التجارات المحرمة؟",
                    "হারাম ব্যবসা:\n\n১. মদ, ড্রাগ\n২. শূকরের মাংস\n৩. মূর্তি\n৪. জুয়া\n৫. সুদ\n৬. প্রতারণা\n৭. চুরির মাল\n৮. পতিতাবৃত্তি",
                    "Haram businesses:\n\n1. Alcohol, drugs\n2. Pork\n3. Idols\n4. Gambling\n5. Interest\n6. Deception\n7. Stolen goods\n8. Prostitution",
                    "التجارات المحرمة:\n\n١. الخمر والمخدرات\n٢. لحم الخنزير\n٣. الأصنام\n٤. الميسر\n٥. الربا\n٦. الغش\n٧. المسروق\n٨. البغاء",
                    "কুরআন ও সুন্নাহ"
                ),
                Masala(
                    4,
                    "ঋণ দেওয়া-নেওয়ার নিয়ম?",
                    "Rules of lending & borrowing?",
                    "أحكام القرض؟",
                    "ঋণ দেওয়া সদকা। তবে:\n\n১. লিখে রাখা উত্তম\n২. সাক্ষী রাখা ভালো\n৩. নির্দিষ্ট সময় ঠিক করা\n৪. বেশি নেওয়া সুদ\n৫. সময়মতো পরিশোধ করা ঋণগ্রহীতার দায়িত্ব",
                    "Lending is charity. But:\n\n1. Writing it down is better\n2. Having witnesses is good\n3. Fixing a time\n4. Taking extra is Riba\n5. Repaying on time is the borrower's duty",
                    "القرض صدقة. ولكن:\n\n١. الكتابة أفضل\n٢. الإشهاد حسن\n٣. تحديد الأجل\n٤. الزيادة ربا\n٥. السداد في الوقت واجب على المدين",
                    "সূরা বাকারা: ২৮২"
                ),
                Masala(
                    5,
                    "বিশ্বাসঘাতকতা ও প্রতারণা?",
                    "Betrayal & deception?",
                    "الغش والخيانة؟",
                    "রাসূল (সা.) বলেছেন: \"যে প্রতারণা করে, সে আমার উম্মতের নয়।\" (মুসলিম)\n\nপ্রতারণার প্রকার:\n১. পণ্যে দোষ গোপন করা\n২. ওজনে কম দেওয়া\n৩. মিথ্যা বলা\n৪. মাপে কম দেওয়া",
                    "The Prophet (PBUH) said: \"Whoever deceives is not one of us.\" (Muslim)\n\nTypes of deception:\n1. Hiding defects\n2. Short-weighing\n3. Lying\n4. Short-measuring",
                    "قال النبي ﷺ: 'من غش فليس مني' (مسلم).\n\nأنواع الغش:\n١. إخفاء العيب\n٢. التطفيف في الوزن\n٣. الكذب\n٤. التطفيف في الكيل",
                    "সহিহ মুসলিম"
                ),
                Masala(
                    6,
                    "শেয়ার ব্যবসা করা যাবে কি?",
                    "Is share trading allowed?",
                    "هل تجوز تجارة الأسهم؟",
                    "শর্তসাপেক্ষে জায়েজ:\n\n✅ হালাল ব্যবসায় শেয়ার\n✅ ইসলামিক ব্যাংক\n❌ সুদভিত্তিক ব্যাংক\n❌ মদ/জুয়ার কোম্পানি\n❌ হারাম পণ্যের ব্যবসা",
                    "Permissible with conditions:\n\n✅ Shares in halal business\n✅ Islamic bank\n❌ Interest-based bank\n❌ Alcohol/gambling company\n❌ Haram goods business",
                    "تجوز بشروط:\n\n✅ أسهم شركة حلال\n✅ بنك إسلامي\n❌ بنك ربوي\n❌ شركة خمر أو قمار\n❌ تجارة محرمة",
                    "ইসলামিক ফিকহ"
                )
            )
        ),
        FiqhTopic(
            id = 6,
            emoji = "👨‍👩‍👧",
            nameBn = "পরিবার",
            nameEn = "Family",
            nameAr = "الأسرة",
            arabicName = "الأسرة",
            descriptionBn = "বিবাহ, তালাক, উত্তরাধিকার",
            descriptionEn = "Marriage, divorce, inheritance",
            descriptionAr = "النكاح، الطلاق، الميراث",
            masalas = listOf(
                Masala(
                    1,
                    "বিবাহ কী এবং কেন?",
                    "What is marriage and why?",
                    "ما هو النكاح ولماذا؟",
                    "বিবাহ সুন্নত। রাসূল (সা.) বলেছেন: \"বিবাহ আমার সুন্নত। যে আমার সুন্নত ভালোবাসে না, সে আমার দলভুক্ত নয়।\"\n\nবিবাহের উদ্দেশ্য:\n১. পবিত্রতা রক্ষা\n২. বংশ রক্ষা\n৩. শান্তি লাভ",
                    "Marriage is Sunnah. The Prophet (PBUH) said: \"Marriage is my Sunnah. Whoever does not love my Sunnah is not of me.\"\n\nPurposes:\n1. Preserving chastity\n2. Preserving lineage\n3. Attaining peace",
                    "النكاح سنة. قال النبي ﷺ: 'النكاح سنتي، فمن لم يحب سنتي فليس مني.'\n\nأغراضه:\n١. حفظ العفة\n٢. حفظ النسل\n٣. السكينة",
                    "সুনানে ইবনে মাজাহ"
                ),
                Masala(
                    2,
                    "বিবাহের শর্ত কী?",
                    "Conditions of marriage?",
                    "شروط النكاح؟",
                    "বিবাহের শর্ত:\n\n১. পাত্র-পাত্রীর সম্মতি\n২. দুজন সাক্ষী\n৩. মোহরানা\n৪. অভিভাবকের অনুমতি (কন্যার)\n৫. প্রকাশ্য ঘোষণা",
                    "Conditions of marriage:\n\n1. Consent of both parties\n2. Two witnesses\n3. Mahr\n4. Guardian's consent (for bride)\n5. Public announcement",
                    "شروط النكاح:\n\n١. التراضي\n٢. شاهدان\n٣. المهر\n٤. إذن الولي\n٥. الإعلان",
                    "ফিকহুস সুন্নাহ"
                ),
                Masala(
                    3,
                    "মোহরানা কী?",
                    "What is Mahr?",
                    "ما هو المهر؟",
                    "মোহরানা হলো স্ত্রীকে বিবাহের সময় দেওয়া সম্পদ। এটি স্ত্রীর অধিকার।\n\nকম-বেশ যেকোনো পরিমাণ হতে পারে। তবে ন্যায্য হওয়া উচিত।\n\nমোহরানা ছাড়া বিবাহ বৈধ, তবে ঋণ হিসেবে থাকবে।",
                    "Mahr is the wealth given to the bride at marriage. It is her right.\n\nIt can be any amount, but should be reasonable.\n\nMarriage without Mahr is valid, but it remains as debt.",
                    "المهر هو المال الذي يُعطى للزوجة عند الزواج. وهو حقها.\n\nيجوز قليلًا أو كثيرًا، لكن المعقول أفضل.\n\nالنكاح بدون مهر صحيح، لكن يبقى دينًا.",
                    "হেদায়া"
                ),
                Masala(
                    4,
                    "তালাক কত প্রকার?",
                    "Types of divorce?",
                    "أنواع الطلاق؟",
                    "তালাক প্রকার:\n\n১. তালাকে সুন্নি — সুন্নত অনুযায়ী\n২. তালাকে বিদআত — বিদআত অনুযায়ী\n৩. তালাকে রাজঈ — ফিরিয়ে আনা যায়\n৪. তালাকে বাইন — ফিরিয়ে আনা যায় না\n\nতালাক সবচেয়ে অপছন্দনীয় হালাল কাজ।",
                    "Types of Talaq:\n\n1. Talaq Sunnah — according to Sunnah\n2. Talaq Bid'ah — innovated\n3. Talaq Raj'i — revocable\n4. Talaq Ba'in — irrevocable\n\nTalaq is the most disliked halal act.",
                    "أنواع الطلاق:\n\n١. طلاق السنة\n٢. طلاق البدعة\n٣. طلاق رجعي\n٤. طلاق بائن\n\nوالطلاق أبغض الحلال إلى الله.",
                    "আবু দাউদ"
                ),
                Masala(
                    5,
                    "ইদ্দত কী?",
                    "What is Iddah?",
                    "ما هي العدة؟",
                    "ইদ্দত হলো তালাক বা স্বামীর মৃত্যুর পর নারীর অপেক্ষার সময়।\n\nসময়কাল:\n- তালাকের পর: ৩ হায়েজ\n- স্বামীর মৃত্যু: ৪ মাস ১০ দিন\n- গর্ভবতী: সন্তান প্রসব পর্যন্ত",
                    "Iddah is the waiting period for a woman after divorce or husband's death.\n\nDuration:\n- After divorce: 3 menstrual cycles\n- After husband's death: 4 months 10 days\n- If pregnant: until delivery",
                    "العدة هي مدة الانتظار بعد الطلاق أو وفاة الزوج.\n\nمدتها:\n- بعد الطلاق: ثلاث حيض\n- بعد وفاة الزوج: أربعة أشهر وعشرة أيام\n- للحامل: حتى الوضع",
                    "কুরআন"
                ),
                Masala(
                    6,
                    "উত্তরাধিকারের নিয়ম?",
                    "Rules of inheritance?",
                    "أحكام الميراث؟",
                    "আল্লাহ কুরআনে উত্তরাধিকারের হিসাব দিয়েছেন:\n\n- পুত্র: কন্যার দ্বিগুণ\n- স্বামী: স্ত্রী সন্তান থাকলে ¼, না থাকলে ½\n- স্ত্রী: সন্তান থাকলে ⅛, না থাকলে ¼\n- মা: সন্তান থাকলে ⅙\n- বাবা: সন্তান থাকলে ⅙",
                    "Allah has detailed inheritance in the Quran:\n\n- Son: double the daughter's share\n- Husband: ¼ if wife has children, else ½\n- Wife: ⅛ if children, else ¼\n- Mother: ⅙ if children\n- Father: ⅙ if children",
                    "بيّن الله الميراث في القرآن:\n\n- للابن ضعف البنت\n- للزوج: الربع إن كان للزوجة ولد، وإلا النصف\n- للزوجة: الثمن إن كان ولد، وإلا الربع\n- للأم: السدس إن كان ولد\n- للأب: السدس إن كان ولد",
                    "সূরা নিসা: ১১-১২"
                )
            )
        )
    )

    fun getTopicById(id: Int): FiqhTopic? {
        return allTopics.find { it.id == id }
    }
}