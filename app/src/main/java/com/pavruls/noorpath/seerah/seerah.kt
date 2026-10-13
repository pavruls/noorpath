package com.pavruls.noorpath.seerah

import com.pavruls.noorpath.data.preferences.LanguageManager

data class SeerahTopic(
    val id: Int,
    val emoji: String,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val arabicName: String,
    val periodBn: String,
    val periodEn: String,
    val periodAr: String,
    val descriptionBn: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val sections: List<SeerahSection>
) {
    fun getName(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> nameEn
        LanguageManager.LANG_AR -> nameAr
        else -> nameBn
    }

    fun getPeriod(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> periodEn
        LanguageManager.LANG_AR -> periodAr
        else -> periodBn
    }

    fun getDescription(lang: String): String = when (lang) {
        LanguageManager.LANG_EN -> descriptionEn
        LanguageManager.LANG_AR -> descriptionAr
        else -> descriptionBn
    }
}

data class SeerahSection(
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

object SeerahData {
    val allTopics = listOf(
        SeerahTopic(
            id = 1,
            emoji = "🌍",
            nameBn = "জন্ম ও শৈশব",
            nameEn = "Birth & Childhood",
            nameAr = "الميلاد والطفولة",
            arabicName = "الميلاد والطفولة",
            periodBn = "৫৭০-৫৭৬ খ্রি.",
            periodEn = "570-576 CE",
            periodAr = "٥٧٠-٥٧٦ م",
            descriptionBn = "মক্কায় জন্ম থেকে মায়ের মৃত্যু",
            descriptionEn = "From birth in Makkah to mother's death",
            descriptionAr = "من الولادة في مكة إلى وفاة الأم",
            sections = listOf(
                SeerahSection(
                    1,
                    "জন্ম",
                    "Birth",
                    "الميلاد",
                    "নবী মুহাম্মদ (সা.) ৫৭০ খ্রিস্টাব্দে (আমুল ফীল) মক্কায় জন্মগ্রহণ করেন।\n\nপিতার নাম: আবদুল্লাহ ইবনে আবদুল মুত্তালিব\nমাতার নাম: আমিনা বিনতে ওয়াহাব\n\nজন্মের পর দাদা আবদুল মুত্তালিব তাঁর নাম রাখেন 'মুহাম্মদ' (প্রশংসিত)।",
                    "Prophet Muhammad (PBUH) was born in 570 CE (Year of the Elephant) in Makkah.\n\nFather's name: Abdullah ibn Abdul Muttalib\nMother's name: Aminah bint Wahb\n\nAfter his birth, his grandfather Abdul Muttalib named him 'Muhammad' (the Praised One).",
                    "وُلد النبي محمد ﷺ عام ٥٧٠م (عام الفيل) في مكة.\n\nاسم الأب: عبد الله بن عبد المطلب\nاسم الأم: آمنة بنت وهب\n\nبعد ولادته، سماه جده عبد المطلب 'محمدًا'."
                ),
                SeerahSection(
                    2,
                    "দুধমাতা হালিমা",
                    "Foster Mother Halima",
                    "المرضعة حليمة",
                    "আরবের রীতি অনুযায়ী শিশু মুহাম্মদকে ধাত্রী হালিমা সাদিয়ার কাছে পাঠানো হয়।\n\nহালিমার ঘরে বরকত নেমে আসে। তিনি বলেন: 'এই শিশুর কারণে আমার ঘরে যা কিছুই হয়েছে, সব বরকতময় হয়েছে।'",
                    "According to Arab custom, baby Muhammad was sent to nurse Halima Sa'diyah.\n\nBlessings descended upon Halima's house. She said: 'Because of this child, everything in my house became blessed.'",
                    "حسب العادة العربية، أُرسل الطفل محمد إلى المرضعة حليمة السعدية.\n\nحلّت البركة في بيت حليمة. قالت: 'بسبب هذا الطفل، أصبح كل شيء في بيتي مباركًا.'"
                ),
                SeerahSection(
                    3,
                    "মায়ের মৃত্যু",
                    "Mother's Death",
                    "وفاة الأم",
                    "৬ বছর বয়সে মা আমিনাকে হারান। আমিনা মদীনা থেকে ফেরার পথে আবওয়া নামক স্থানে ইন্তেকাল করেন।\n\nএরপর দাদা আবদুল মুত্তালিব তাঁকে লালন-পালন করেন।",
                    "At the age of 6, he lost his mother Aminah. She passed away at a place called Abwa on her way back from Madinah.\n\nAfterwards, his grandfather Abdul Muttalib raised him.",
                    "في سن السادسة، فقد أمه آمنة. توفيت في مكان يُسمى الأبواء في طريق عودتها من المدينة.\n\nبعد ذلك، ربّاه جده عبد المطلب."
                ),
                SeerahSection(
                    4,
                    "দাদার মৃত্যু ও চাচার স্নেহ",
                    "Grandfather's Death & Uncle's Love",
                    "وفاة الجد وحنان العم",
                    "৮ বছর বয়সে দাদা আবদুল মুত্তালিব ইন্তেকাল করেন। এরপর চাচা আবু তালিব তাঁকে নিজের সন্তানদের মতো লালন-পালন করেন।\n\nআবু তালিব সবসময় তাঁকে রক্ষা করেছেন।",
                    "At the age of 8, his grandfather Abdul Muttalib passed away. Then his uncle Abu Talib raised him like his own children.\n\nAbu Talib always protected him.",
                    "في سن الثامنة، توفي جده عبد المطلب. ثم ربّاه عمه أبو طالب كأولاده.\n\nكان أبو طالب يحميه دائمًا."
                )
            )
        ),
        SeerahTopic(
            id = 2,
            emoji = "🛡️",
            nameBn = "যুবক ও আল-আমিন",
            nameEn = "Youth & Al-Amin",
            nameAr = "الشباب والأمين",
            arabicName = "الشباب والأمين",
            periodBn = "৫৭৬-৬১০ খ্রি.",
            periodEn = "576-610 CE",
            periodAr = "٥٧٦-٦١٠ م",
            descriptionBn = "সততা ও বিশ্বস্ততার পরিচয়",
            descriptionEn = "The example of honesty & trustworthiness",
            descriptionAr = "نموذج الصدق والأمانة",
            sections = listOf(
                SeerahSection(
                    1,
                    "আল-আমিন উপাধি",
                    "The Title Al-Amin",
                    "لقب الأمين",
                    "কিশোর বয়স থেকেই মুহাম্মদ (সা.) ছিলেন সৎ, সত্যবাদী ও বিশ্বস্ত।\n\nমক্কাবাসী তাঁকে 'আল-আমিন' (বিশ্বস্ত) ও 'আস-সাদিক' (সত্যবাদী) উপাধিতে ভূষিত করে।\n\nতাঁর কথার উপর সবাই ভরসা করত।",
                    "From his youth, Muhammad (PBUH) was honest, truthful and trustworthy.\n\nThe people of Makkah gave him the titles 'Al-Amin' (the Trustworthy) and 'As-Sadiq' (the Truthful).\n\nEveryone trusted his word.",
                    "منذ شبابه، كان محمد ﷺ صادقًا أمينًا.\n\nلقبه أهل مكة بـ 'الأمين' و 'الصادق'.\n\nوكان الجميع يثقون بحديثه."
                ),
                SeerahSection(
                    2,
                    "ব্যবসায়িক সফর",
                    "Trade Journey",
                    "رحلة التجارة",
                    "১২ বছর বয়সে চাচা আবু তালিবের সাথে সিরিয়ায় ব্যবসায়িক সফরে যান। পথে বুহাইরা নামক পাদ্রী তাঁকে নবী হিসেবে চিহ্নিত করেন।\n\nপরবর্তীতে খাদিজা (রা.) এর ব্যবসায়িক কাফেলা পরিচালনা করেন।",
                    "At the age of 12, he went on a trade journey to Syria with his uncle Abu Talib. On the way, a monk named Buhaira identified him as a prophet.\n\nLater, he managed the trade caravan of Khadijah (RA).",
                    "في سن الثانية عشرة، سافر إلى الشام مع عمه أبي طالب للتجارة. وفي الطريق، تعرف الراهب بحيرى عليه أنه نبي.\n\nثم أدار قافلة خديجة التجارية رضي الله عنها."
                ),
                SeerahSection(
                    3,
                    "খাদিজা (রা.) এর সাথে বিবাহ",
                    "Marriage to Khadijah (RA)",
                    "الزواج من خديجة رضي الله عنها",
                    "২৫ বছর বয়সে খাদিজা বিনতে খুওয়াইলিদ (রা.) এর সাথে বিবাহ বন্ধনে আবদ্ধ হন।\n\nখাদিজা (রা.) ছিলেন একজন সম্মানিত ও ধনী ব্যবসায়ী নারী।\n\nতাঁদের সংসারে ৬ জন সন্তান জন্ম নেন।",
                    "At the age of 25, he married Khadijah bint Khuwaylid (RA).\n\nKhadijah (RA) was a respected and wealthy businesswoman.\n\nThey had 6 children.",
                    "في سن الخامسة والعشرين، تزوج خديجة بنت خويلد رضي الله عنها.\n\nكانت خديجة رضي الله عنها امرأة تاجرة محترمة وثرية.\n\nوأنجبا ستة أبناء."
                ),
                SeerahSection(
                    4,
                    "কাবার পুনর্নির্মাণ",
                    "Reconstruction of the Kaaba",
                    "إعادة بناء الكعبة",
                    "নবী (সা.) ৩৫ বছর বয়সে কাবা পুনর্নির্মাণের সময় হাজরে আসওয়াদ স্থাপনের বিরোধ নিষ্পত্তি করেন।\n\nসকল গোত্রের সর্দাররা তাঁর প্রজ্ঞায় মুগ্ধ হন।",
                    "At the age of 35, during the reconstruction of the Kaaba, the Prophet (PBUH) resolved the dispute over placing the Black Stone.\n\nAll tribal chiefs were amazed by his wisdom.",
                    "في سن الخامسة والثلاثين، خلال إعادة بناء الكعبة، حلّ النبي ﷺ النزاع حول وضع الحجر الأسود.\n\nفأُعجب جميع زعماء القبائل بحكمته."
                )
            )
        ),
        SeerahTopic(
            id = 3,
            emoji = "🕋",
            nameBn = "নবুয়ত লাভ",
            nameEn = "Receiving Prophethood",
            nameAr = "البعثة",
            arabicName = "البعثة",
            periodBn = "৬১০ খ্রি.",
            periodEn = "610 CE",
            periodAr = "٦١٠ م",
            descriptionBn = "হেরা গুহায় প্রথম ওহি",
            descriptionEn = "First revelation in the cave of Hira",
            descriptionAr = "أول الوحي في غار حراء",
            sections = listOf(
                SeerahSection(
                    1,
                    "হেরা গুহায় ধ্যান",
                    "Meditation in Cave of Hira",
                    "التعبد في غار حراء",
                    "৩৫ বছর বয়স থেকে নবী (সা.) মক্কার কাছে হেরা গুহায় ধ্যানমগ্ন থাকতেন।\n\nতিনি একা আল্লাহর ইবাদত করতেন এবং সমাজের অন্যায় দেখে চিন্তিত থাকতেন।\n\nএই সময়টি ছিল নবুয়তের প্রস্তুতি।",
                    "From the age of 35, the Prophet (PBUH) used to meditate in the cave of Hira near Makkah.\n\nHe worshipped Allah alone and was concerned about the injustice in society.\n\nThis period was preparation for prophethood.",
                    "منذ سن الخامسة والثلاثين، كان النبي ﷺ يتعبد في غار حراء قرب مكة.\n\nكان يعبد الله وحده، وكان يقلق من ظلم المجتمع.\n\nكانت هذه الفترة إعدادًا للنبوة."
                ),
                SeerahSection(
                    2,
                    "প্রথম ওহি",
                    "First Revelation",
                    "أول الوحي",
                    "৪০ বছর বয়সে (৬১০ খ্রি.) রমজান মাসে জিব্রাইল (আ.) হেরা গুহায় আসেন।\n\nতিনি বলেন: 'পড়ুন!'\n\nনবী (সা.) বলেন: 'আমি পড়তে জানি না।'\n\nজিব্রাইল (আ.) তাঁকে জড়িয়ে ধরে তিনবার বলেন, তারপর সূরা আলাকের প্রথম ৫ আয়াত পড়ে শোনান।",
                    "At the age of 40 (610 CE), in Ramadan, Jibreel (AS) came to the cave of Hira.\n\nHe said: 'Read!'\n\nThe Prophet (PBUH) said: 'I do not know how to read.'\n\nJibreel (AS) embraced him three times and then recited the first 5 verses of Surah Al-Alaq.",
                    "في سن الأربعين (٦١٠م)، في رمضان، جاء جبريل عليه السلام إلى غار حراء.\n\nفقال: 'اقرأ!'\n\nفقال النبي ﷺ: 'ما أنا بقارئ.'\n\nفضمّه جبريل ثلاث مرات، ثم تلا عليه أول ٥ آيات من سورة العلق."
                ),
                SeerahSection(
                    3,
                    "খাদিজা (রা.) এর সমর্থন",
                    "Support of Khadijah (RA)",
                    "دعم خديجة رضي الله عنها",
                    "ভয়ে কাঁপতে কাঁপতে বাড়ি ফিরে খাদিজা (রা.) কে সব বলেন।\n\nখাদিজা (রা.) বলেন: 'আল্লাহর কসম! আল্লাহ আপনাকে কখনো লাঞ্ছিত করবেন না। আপনি আত্মীয়দের সাথে সদাচরণ করেন, সত্য কথা বলেন, অসহায়দের সাহায্য করেন।'",
                    "Trembling with fear, he returned home and told Khadijah (RA) everything.\n\nKhadijah (RA) said: 'By Allah! Allah will never disgrace you. You maintain ties of kinship, speak the truth, and help the helpless.'",
                    "عاد إلى البيت يرتجف خوفًا، وأخبر خديجة رضي الله عنها بكل شيء.\n\nفقالت خديجة: 'والله! لا يخزيك الله أبدًا. إنك لتصل الرحم، وتصدق الحديث، وتعين على نوائب الحق.'"
                ),
                SeerahSection(
                    4,
                    "ওয়ারাকা ইবনে নওফেল",
                    "Waraqah ibn Nawfal",
                    "ورقة بن نوفل",
                    "খাদিজা (রা.) তাঁকে ওয়ারাকা ইবনে নওফেলের কাছে নিয়ে যান।\n\nওয়ারাকা ছিলেন খ্রিস্টান ধর্মের পণ্ডিত। তিনি বলেন: 'এটি সেই ফেরেশতা যিনি মূসা (আ.) এর কাছে এসেছিলেন। হায়! আমি যদি জীবিত থাকতাম যখন তোমার কওম তোমাকে বের করে দেবে।'",
                    "Khadijah (RA) took him to Waraqah ibn Nawfal.\n\nWaraqah was a Christian scholar. He said: 'This is the same angel who came to Musa (AS). Alas! If only I were alive when your people drive you out.'",
                    "أخذته خديجة رضي الله عنها إلى ورقة بن نوفل.\n\nوكان ورقة عالمًا نصرانيًا. فقال: 'هذا هو الملك الذي نزل على موسى عليه السلام. يا ليتني أكون حيًا حين يخرجك قومك.'"
                )
            )
        ),
        SeerahTopic(
            id = 4,
            emoji = "📢",
            nameBn = "মক্কায় দাওয়াত",
            nameEn = "Dawah in Makkah",
            nameAr = "الدعوة في مكة",
            arabicName = "الدعوة في مكة",
            periodBn = "৬১০-৬২২ খ্রি.",
            periodEn = "610-622 CE",
            periodAr = "٦١٠-٦٢٢ م",
            descriptionBn = "গোপন থেকে প্রকাশ্য দাওয়াত",
            descriptionEn = "From secret to public dawah",
            descriptionAr = "من الدعوة السرية إلى العلنية",
            sections = listOf(
                SeerahSection(
                    1,
                    "গোপন দাওয়াত",
                    "Secret Dawah",
                    "الدعوة السرية",
                    "প্রথম ৩ বছর নবী (সা.) গোপনে দাওয়াত দেন।\n\nপ্রথম মুসলিম:\n• খাদিজা (রা.) — প্রথম নারী\n• আবু বকর (রা.) — প্রথম প্রাপ্তবয়স্ক পুরুষ\n• আলী (রা.) — প্রথম শিশু\n• যায়েদ (রা.) — প্রথম মুক্ত দাস",
                    "For the first 3 years, the Prophet (PBUH) gave dawah secretly.\n\nFirst Muslims:\n• Khadijah (RA) — first woman\n• Abu Bakr (RA) — first adult man\n• Ali (RA) — first child\n• Zayd (RA) — first freed slave",
                    "في السنوات الثلاث الأولى، دعا النبي ﷺ سرًا.\n\nأوائل المسلمين:\n• خديجة رضي الله عنها — أول امرأة\n• أبو بكر رضي الله عنه — أول رجل بالغ\n• علي رضي الله عنه — أول صبي\n• زيد رضي الله عنه — أول مولى معتَق"
                ),
                SeerahSection(
                    2,
                    "প্রকাশ্য দাওয়াত",
                    "Public Dawah",
                    "الدعوة العلنية",
                    "সূরা শুআরার ২১৪ আয়াত নাজিল হলে নবী (সা.) সাফা পাহাড়ে উঠে প্রকাশ্যে দাওয়াত দেন।\n\nতিনি সবাইকে এক আল্লাহর ইবাদতের দিকে ডাকেন।\n\nকুরাইশরা বিরোধিতা শুরু করে।",
                    "When verse 214 of Surah Ash-Shu'ara was revealed, the Prophet (PBUH) climbed Mount Safa and gave dawah publicly.\n\nHe called everyone to worship the One Allah.\n\nThe Quraysh began to oppose him.",
                    "لما نزلت الآية ٢١٤ من سورة الشعراء، صعد النبي ﷺ جبل الصفا ودعا علنًا.\n\nدعا الجميع إلى عبادة الله الواحد.\n\nفبدأت قريش تعارضه."
                ),
                SeerahSection(
                    3,
                    "নির্যাতন",
                    "Persecution",
                    "الاضطهاد",
                    "মুসলিমরা চরম নির্যাতনের শিকার হন।\n\n• বিলাল (রা.) কে গরম বালির উপর শোয়ানো হয়\n• সুমাইয়া (রা.) কে হত্যা করা হয় (প্রথম শহীদ)\n• আবু বকর (রা.) কে পিটিয়ে অচেতন করা হয়\n• নবী (সা.) কে পথে ময়লা নিক্ষেপ করা হয়",
                    "The Muslims suffered severe persecution.\n\n• Bilal (RA) was laid on hot sand\n• Sumayyah (RA) was killed (first martyr)\n• Abu Bakr (RA) was beaten unconscious\n• Filth was thrown on the Prophet (PBUH) on the road",
                    "عانى المسلمون اضطهادًا شديدًا.\n\n• أُضجع بلال رضي الله عنه على الرمال الحارة\n• قُتلت سمية رضي الله عنها (أول شهيدة)\n• ضُرب أبو بكر رضي الله عنه حتى فقد الوعي\n• أُلقي القاذورات على النبي ﷺ في الطريق"
                ),
                SeerahSection(
                    4,
                    "আবিসিনিয়ায় হিজরত",
                    "Migration to Abyssinia",
                    "الهجرة إلى الحبشة",
                    "নবী (সা.) সাহাবীদের আবিসিনিয়ায় হিজরতের অনুমতি দেন।\n\nবিশপ নাজাশী মুসলিমদের আশ্রয় দেন এবং কুরআন শুনে কাঁদেন।\n\nতিনি বলেন: 'এই শিক্ষা এবং ঈসা (আ.) এর শিক্ষা একই উৎস থেকে এসেছে।'",
                    "The Prophet (PBUH) permitted his Companions to migrate to Abyssinia.\n\nNegus the king gave refuge to the Muslims and wept upon hearing the Quran.\n\nHe said: 'This teaching and the teaching of Isa (AS) came from the same source.'",
                    "أذن النبي ﷺ لأصحابه بالهجرة إلى الحبشة.\n\nفأجارهم النجاشي، وبكى عند سماع القرآن.\n\nوقال: 'إن هذا والتعليم الذي جاء به عيسى عليه السلام ليخرجان من مشكاة واحدة.'"
                )
            )
        ),
        SeerahTopic(
            id = 5,
            emoji = "🌌",
            nameBn = "মিরাজ",
            nameEn = "Al-Isra wal-Mi'raj",
            nameAr = "الإسراء والمعراج",
            arabicName = "الإسراء والمعراج",
            periodBn = "৬২০ খ্রি.",
            periodEn = "620 CE",
            periodAr = "٦٢٠ م",
            descriptionBn = "আকাশ ভ্রমণ ও পাঁচ ওয়াক্ত নামাজ",
            descriptionEn = "The heavenly journey & five daily prayers",
            descriptionAr = "الرحلة السماوية والصلوات الخمس",
            sections = listOf(
                SeerahSection(
                    1,
                    "কষ্টের বছর",
                    "Year of Sorrow",
                    "عام الحزن",
                    "নবুয়তের ১০ম বছরে নবী (সা.) একে একে হারান:\n\n• চাচা আবু তালিব\n• প্রিয় স্ত্রী খাদিজা (রা.)\n\nতাই এই বছরকে 'আমুল হুযন' (দুঃখের বছর) বলা হয়।",
                    "In the 10th year of prophethood, the Prophet (PBUH) lost one after another:\n\n• His uncle Abu Talib\n• His beloved wife Khadijah (RA)\n\nSo this year is called 'Amul Huzn' (Year of Sorrow).",
                    "في السنة العاشرة من البعثة، فقد النبي ﷺ واحدًا تلو الآخر:\n\n• عمه أبا طالب\n• زوجته الحبيبة خديجة رضي الله عنها\n\nفسمي هذا العام 'عام الحزن'."
                ),
                SeerahSection(
                    2,
                    "তায়েফ সফর",
                    "Journey to Ta'if",
                    "رحلة الطائف",
                    "নবী (সা.) তায়েফে দাওয়াত দিতে যান। সেখানকার লোকেরা পাথর ছুঁড়ে তাঁকে আহত করে।\n\nরক্ত ঝরতেই তিনি বলেন: 'হে আল্লাহ! আমার কওমকে হেদায়াত দাও, তারা জানে না।'",
                    "The Prophet (PBUH) went to Ta'if to give dawah. The people there pelted him with stones and injured him.\n\nAs blood flowed, he said: 'O Allah! Guide my people, for they do not know.'",
                    "ذهب النبي ﷺ إلى الطائف للدعوة. فرماه أهلها بالحجارة فأدموه.\n\nوبينما كان الدم يسيل، قال: 'اللهم اهدِ قومي فإنهم لا يعلمون.'"
                ),
                SeerahSection(
                    3,
                    "মিরাজের ঘটনা",
                    "The Event of Mi'raj",
                    "حادثة المعراج",
                    "এক রাতে জিব্রাইল (আ.) নবী (সা.) কে বোরাকে চড়িয়ে মসজিদুল হারাম থেকে মসজিদুল আকসায় নিয়ে যান (ইসরা)।\n\nসেখান থেকে আসমানে ভ্রমণ করান (মিরাজ)।\n\nতিনি সিদরাতুল মুনতাহা পর্যন্ত পৌঁছান।",
                    "One night, Jibreel (AS) took the Prophet (PBUH) on Buraq from Masjid al-Haram to Masjid al-Aqsa (Isra).\n\nFrom there, he ascended through the heavens (Mi'raj).\n\nHe reached Sidrat al-Muntaha.",
                    "في ليلة، أخذ جبريل عليه السلام النبي ﷺ على البراق من المسجد الحرام إلى المسجد الأقصى (الإسراء).\n\nثم عُرج به إلى السماوات (المعراج).\n\nووصل إلى سدرة المنتهى."
                ),
                SeerahSection(
                    4,
                    "পাঁচ ওয়াক্ত নামাজ",
                    "Five Daily Prayers",
                    "الصلوات الخمس",
                    "মিরাজে আল্লাহ তাআলা প্রথমে ৫০ ওয়াক্ত নামাজ ফরজ করেন।\n\nনবী (সা.) বারবার আবেদন করে ৫ ওয়াক্তে নামিয়ে আনেন।\n\nএটি ছিল উম্মতের প্রতি আল্লাহর বিশেষ রহমত।",
                    "During Mi'raj, Allah first made 50 prayers obligatory.\n\nThe Prophet (PBUH) repeatedly appealed and it was reduced to 5.\n\nThis was a special mercy of Allah upon the Ummah.",
                    "في المعراج، فرض الله أولًا خمسين صلاة.\n\nفطلب النبي ﷺ التخفيف مرارًا حتى صارت خمسًا.\n\nوكانت هذه رحمة خاصة من الله للأمة."
                )
            )
        ),
        SeerahTopic(
            id = 6,
            emoji = "🕊️",
            nameBn = "মদীনায় হিজরত",
            nameEn = "Migration to Madinah",
            nameAr = "الهجرة إلى المدينة",
            arabicName = "الهجرة إلى المدينة",
            periodBn = "৬২২ খ্রি.",
            periodEn = "622 CE",
            periodAr = "٦٢٢ م",
            descriptionBn = "নতুন সমাজ ও রাষ্ট্রের সূচনা",
            descriptionEn = "Beginning of a new society & state",
            descriptionAr = "بداية مجتمع ودولة جديدة",
            sections = listOf(
                SeerahSection(
                    1,
                    "হিজরতের কারণ",
                    "Reason for Migration",
                    "سبب الهجرة",
                    "কুরাইশরা নবী (সা.) কে হত্যার ষড়যন্ত্র করে।\n\nআল্লাহ তাআলা নবী (সা.) কে হিজরতের অনুমতি দেন।\n\nআবু বকর (রা.) সঙ্গী হন।",
                    "The Quraysh plotted to assassinate the Prophet (PBUH).\n\nAllah gave the Prophet (PBUH) permission to migrate.\n\nAbu Bakr (RA) accompanied him.",
                    "تآمرت قريش على قتل النبي ﷺ.\n\nفأذن الله لنبيه ﷺ بالهجرة.\n\nفصحبه أبو بكر رضي الله عنه."
                ),
                SeerahSection(
                    2,
                    "গুহায় তিন দিন",
                    "Three Days in the Cave",
                    "ثلاثة أيام في الغار",
                    "নবী (সা.) ও আবু বকর (রা.) সওর গুহায় তিন দিন অবস্থান করেন।\n\nকুরাইশরা গুহার মুখে পৌঁছে যায়। আবু বকর (রা.) ভয় পান।\n\nনবী (সা.) বলেন: 'ভয় করো না, আল্লাহ আমাদের সাথে আছেন।'",
                    "The Prophet (PBUH) and Abu Bakr (RA) stayed in the cave of Sawr for three days.\n\nThe Quraysh reached the mouth of the cave. Abu Bakr (RA) became afraid.\n\nThe Prophet (PBUH) said: 'Do not fear, Allah is with us.'",
                    "مكث النبي ﷺ وأبو بكر رضي الله عنه في غار ثور ثلاثة أيام.\n\nووصلت قريش إلى فم الغار. فخاف أبو بكر رضي الله عنه.\n\nفقال النبي ﷺ: 'لا تحزن، إن الله معنا.'"
                ),
                SeerahSection(
                    3,
                    "মদীনায় প্রবেশ",
                    "Entering Madinah",
                    "دخول المدينة",
                    "মদীনাবাসী আনন্দে নবী (সা.) কে অভ্যর্থনা জানান।\n\nতারা গেয়ে উঠেন: 'তালা'আল বাদরু আলাইনা...' (আমাদের উপর পূর্ণিমার চাঁদ উদিত হয়েছে)।\n\n৬২২ খ্রিস্টাব্দ থেকে হিজরি সনের সূচনা হয়।",
                    "The people of Madinah welcomed the Prophet (PBUH) with joy.\n\nThey sang: 'Tala'al badru alayna...' (The full moon has risen upon us).\n\nFrom 622 CE, the Hijri calendar began.",
                    "استقبل أهل المدينة النبي ﷺ بفرح.\n\nوغنوا: 'طلع البدر علينا...'.\n\nومن عام ٦٢٢م بدأ التقويم الهجري."
                ),
                SeerahSection(
                    4,
                    "মদীনা সনদ",
                    "Charter of Madinah",
                    "صحيفة المدينة",
                    "নবী (সা.) মদীনায় একটি লিখিত সনদ প্রণয়ন করেন:\n\n• মুসলিম-ইহুদি সব গোত্রের অধিকার নিশ্চিত\n• ধর্মীয় স্বাধীনতা\n• পারস্পরিক সহযোগিতা\n• যৌথ প্রতিরক্ষা\n\nএটি ছিল পৃথিবীর প্রথম লিখিত সংবিধান।",
                    "The Prophet (PBUH) wrote a charter in Madinah:\n\n• Rights of all tribes (Muslim and Jewish) guaranteed\n• Religious freedom\n• Mutual cooperation\n• Joint defense\n\nThis was the world's first written constitution.",
                    "كتب النبي ﷺ صحيفة في المدينة:\n\n• ضمان حقوق جميع القبائل (مسلمين ويهود)\n• الحرية الدينية\n• التعاون المتبادل\n• الدفاع المشترك\n\nوكانت أول دستور مكتوب في العالم."
                )
            )
        ),
        SeerahTopic(
            id = 7,
            emoji = "⚔️",
            nameBn = "মক্কা বিজয়",
            nameEn = "Conquest of Makkah",
            nameAr = "فتح مكة",
            arabicName = "فتح مكة",
            periodBn = "৬৩০ খ্রি.",
            periodEn = "630 CE",
            periodAr = "٦٣٠ م",
            descriptionBn = "কাবা পুনরুদ্ধার ও ক্ষমার উদাহরণ",
            descriptionEn = "Restoration of the Kaaba & example of forgiveness",
            descriptionAr = "استعادة الكعبة ومثال العفو",
            sections = listOf(
                SeerahSection(
                    1,
                    "হুদাইবিয়ার সন্ধি",
                    "Treaty of Hudaybiyyah",
                    "صلح الحديبية",
                    "৬২৮ খ্রিস্টাব্দে কুরাইশদের সাথে ১০ বছরের সন্ধি হয়।\n\nকুরাইশরা সন্ধি ভঙ্গ করলে মক্কা বিজয়ের পথ খুলে যায়।\n\nআল্লাহ এটিকে 'স্পষ্ট বিজয়' বলে অভিহিত করেন।",
                    "In 628 CE, a 10-year treaty was made with the Quraysh.\n\nWhen the Quraysh broke the treaty, the path to conquer Makkah opened.\n\nAllah called it a 'clear victory'.",
                    "في عام ٦٢٨م، عُقد صلح لمدة عشر سنوات مع قريش.\n\nولما نقضت قريش الصلح، فُتح الطريق لفتح مكة.\n\nوسماه الله 'فتحًا مبينًا'."
                ),
                SeerahSection(
                    2,
                    "মক্কায় প্রবেশ",
                    "Entering Makkah",
                    "دخول مكة",
                    "৬৩০ খ্রিস্টাব্দে ১০,০০০ সাহাবী নিয়ে বিনা রক্তপাতে মক্কায় প্রবেশ করেন।\n\nনবী (সা.) ছিলেন বিনয়ী — উটের উপর মাথা নিচু করে আল্লাহর শুকরিয়া আদায় করেন।",
                    "In 630 CE, he entered Makkah with 10,000 Companions without bloodshed.\n\nThe Prophet (PBUH) was humble — he bowed his head on his camel, thanking Allah.",
                    "في عام ٦٣٠م، دخل مكة بعشرة آلاف صحابي دون إراقة دم.\n\nوكان النبي ﷺ متواضعًا — أطأ رأسه على راحلته شكرًا لله."
                ),
                SeerahSection(
                    3,
                    "মূর্তি ধ্বংস",
                    "Destruction of Idols",
                    "تحطيم الأصنام",
                    "কাবায় থাকা ৩৬০টি মূর্তি ধ্বংস করা হয়।\n\nনবী (সা.) বলেন: 'সত্য এসেছে, মিথ্যা বিলীন হয়েছে। মিথ্যা তো বিলীন হবেই।' (সূরা ইসরা: ৮১)",
                    "The 360 idols in the Kaaba were destroyed.\n\nThe Prophet (PBUH) said: 'Truth has come, and falsehood has vanished. Indeed, falsehood is bound to vanish.' (Surah Al-Isra: 81)",
                    "حُطمت ٣٦٠ صنمًا في الكعبة.\n\nفقال النبي ﷺ: 'جاء الحق وزهق الباطل، إن الباطل كان زهوقًا' (الإسراء: ٨١)."
                ),
                SeerahSection(
                    4,
                    "সাধারণ ক্ষমা",
                    "General Amnesty",
                    "العفو العام",
                    "নবী (সা.) কুরাইশদের জিজ্ঞাসা করেন: 'তোমরা আমার কাছ থেকে কী আশা করো?'\n\nতারা বলে: 'ভালো ব্যবহার।'\n\nনবী (সা.) বলেন: 'যাও, তোমরা সবাই মুক্ত।'",
                    "The Prophet (PBUH) asked the Quraysh: 'What do you expect from me?'\n\nThey said: 'Good treatment.'\n\nThe Prophet (PBUH) said: 'Go, you are all free.'",
                    "سأل النبي ﷺ قريشًا: 'ما تظنون أني فاعل بكم؟'\n\nقالوا: 'خيرًا.'\n\nفقال النبي ﷺ: 'اذهبوا فأنتم الطلقاء.'"
                )
            )
        ),
        SeerahTopic(
            id = 8,
            emoji = "🕯️",
            nameBn = "বিদায় হজ ও ইন্তেকাল",
            nameEn = "Farewell Hajj & Passing",
            nameAr = "حجة الوداع والوفاة",
            arabicName = "حجة الوداع والوفاة",
            periodBn = "৬৩২ খ্রি.",
            periodEn = "632 CE",
            periodAr = "٦٣٢ م",
            descriptionBn = "শেষ ভাষণ ও শেষ নিঃশ্বাস",
            descriptionEn = "The final sermon & final breath",
            descriptionAr = "الخطبة الأخيرة والنفس الأخير",
            sections = listOf(
                SeerahSection(
                    1,
                    "বিদায় হজ",
                    "Farewell Hajj",
                    "حجة الوداع",
                    "১০ হিজরিতে নবী (সা.) বিদায় হজ পালন করেন।\n\n১ লক্ষেরও বেশি সাহাবী উপস্থিত ছিলেন।\n\nআরাফায় তিনি ঐতিহাসিক ভাষণ দেন।",
                    "In 10 AH, the Prophet (PBUH) performed the Farewell Hajj.\n\nMore than 100,000 Companions were present.\n\nHe delivered the historic sermon at Arafah.",
                    "في السنة العاشرة للهجرة، أدى النبي ﷺ حجة الوداع.\n\nوحضر أكثر من مائة ألف صحابي.\n\nوألقى الخطبة التاريخية في عرفة."
                ),
                SeerahSection(
                    2,
                    "আরাফার ভাষণ",
                    "Sermon at Arafah",
                    "خطبة عرفة",
                    "নবী (সা.) বলেন:\n\n'আজ আমি তোমাদের দ্বীন পূর্ণ করে দিলাম।'\n\nতিনি আরও বলেন:\n• নারীর অধিকার রক্ষা করো\n• সুদ বন্ধ করো\n• রক্তপাত বন্ধ করো\n• আমানত রক্ষা করো\n• কুরআন ও সুন্নাহ আঁকড়ে ধরো",
                    "The Prophet (PBUH) said:\n\n'Today I have perfected your religion for you.'\n\nHe also said:\n• Protect women's rights\n• Abolish interest\n• Stop bloodshed\n• Fulfill trusts\n• Hold fast to the Quran and Sunnah",
                    "قال النبي ﷺ:\n\n'اليوم أكملت لكم دينكم.'\n\nوقال أيضًا:\n• احفظوا حقوق النساء\n• اتركوا الربا\n• أوقفوا سفك الدماء\n• أدّوا الأمانات\n• تمسكوا بالقرآن والسنة"
                ),
                SeerahSection(
                    3,
                    "শেষ অসুস্থতা",
                    "Final Illness",
                    "المرض الأخير",
                    "সফর মাসে নবী (সা.) অসুস্থ হন।\n\nতিনি আবু বকর (রা.) কে নামাজের ইমামতি করার নির্দেশ দেন।\n\nসবশেষে তিনি বলেছিলেন: 'নামাজ ও অধীনস্তদের ব্যাপারে আল্লাহকে ভয় করো।'",
                    "In the month of Safar, the Prophet (PBUH) became ill.\n\nHe instructed Abu Bakr (RA) to lead the prayer.\n\nFinally he said: 'Fear Allah regarding prayer and those under your authority.'",
                    "في شهر صفر، مرض النبي ﷺ.\n\nفأمر أبا بكر رضي الله عنه أن يصلي بالناس.\n\nوقال أخيرًا: 'اتقوا الله في الصلاة وما ملكت أيمانكم.'"
                ),
                SeerahSection(
                    4,
                    "ইন্তেকাল",
                    "Passing Away",
                    "الوفاة",
                    "১২ রবিউল আউয়াল, ১১ হিজরি (৬৩২ খ্রি.) — ৬৩ বছর বয়সে নবী (সা.) ইন্তেকাল করেন।\n\nতাঁকে আয়েশা (রা.) এর ঘরে দাফন করা হয়।\n\nসেদিন ছিলেন সোমবার।",
                    "On 12 Rabi al-Awwal, 11 AH (632 CE), at the age of 63, the Prophet (PBUH) passed away.\n\nHe was buried in the room of Aishah (RA).\n\nThat day was Monday.",
                    "في ١٢ ربيع الأول، ١١ هـ (٦٣٢م)، توفي النبي ﷺ وعمره ٦٣ سنة.\n\nودُفن في حجرة عائشة رضي الله عنها.\n\nوكان ذلك اليوم يوم الاثنين."
                )
            )
        )
    )

    fun getTopicById(id: Int): SeerahTopic? {
        return allTopics.find { it.id == id }
    }
}