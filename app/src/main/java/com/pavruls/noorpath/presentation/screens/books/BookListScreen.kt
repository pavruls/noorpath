package com.pavruls.noorpath.presentation.screens.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavruls.noorpath.data.preferences.AppStrings
import com.pavruls.noorpath.data.preferences.LanguageManager

data class Book(
    val id: Int,
    val categoryId: Int,
    val emoji: String,
    val title: String,
    val author: String,
    val description: String,
    val pages: Int,
    val content: List<BookPage>
)

data class BookPage(
    val pageNumber: Int,
    val title: String,
    val content: String
)

object BookListData {

    val allBooks = listOf(
        // ============ তাফসির ============
        Book(101, 1, "📗", "তাফসির ইবনে কাসির", "ইমাম ইবনে কাসির", "কুরআনের বিখ্যাত তাফসির", 3,
            listOf(
                BookPage(1, "ভূমিকা", "তাফসির ইবনে কাসির কুরআনের সবচেয়ে নির্ভরযোগ্য তাফসিরগুলোর একটি।"),
                BookPage(2, "গুরুত্ব", "কুরআন বোঝার জন্য তাফসির অপরিহার্য।"),
                BookPage(3, "সূরা আল-ফাতিহা", "সূরা আল-ফাতিহা কুরআনের প্রথম সূরা।")
            )
        ),
        Book(102, 1, "📘", "তাফসিরে জালালাইন", "ইমাম জালালুদ্দিন", "সংক্ষিপ্ত ও সহজ তাফসির", 2,
            listOf(
                BookPage(1, "ভূমিকা", "তাফসিরে জালালাইন দুইজন জালালুদ্দিনের রচনা।"),
                BookPage(2, "বিশেষত্ব", "এই তাফসিরটি ছাত্রদের জন্য উপযোগী।")
            )
        ),
        Book(103, 1, "📙", "তাফসিরে তাবারী", "ইমাম তাবারী", "প্রাচীন ও বিস্তারিত তাফসির", 2,
            listOf(
                BookPage(1, "পরিচিতি", "তাফসিরে তাবারী ইসলামের সবচেয়ে প্রাচীন তাফসির।"),
                BookPage(2, "গুরুত্ব", "এতে সাহাবা ও তাবেঈদের বক্তব্য আছে।")
            )
        ),
        Book(104, 1, "📕", "তাফসিরে কুরতুবী", "ইমাম কুরতুবী", "ফিকহভিত্তিক তাফসির", 2,
            listOf(
                BookPage(1, "ভূমিকা", "তাফসিরে কুরতুবী ফিকহের দৃষ্টিকোণ থেকে ব্যাখ্যা দেয়।"),
                BookPage(2, "বৈশিষ্ট্য", "এতে আহকাম ও বিধান সম্পর্কে আলোচনা আছে।")
            )
        ),
        Book(105, 1, "📔", "তাফসিরে রুহুল মাআনি", "ইমাম আলুসী", "গভীর ব্যাখ্যামূলক তাফসির", 2,
            listOf(
                BookPage(1, "পরিচিতি", "রুহুল মাআনি গভীর অর্থ ও ব্যাখ্যা নিয়ে আলোচনা করে।"),
                BookPage(2, "বিশেষত্ব", "এতে আরবি ভাষার গভীরতা আছে।")
            )
        ),

        // ============ হাদিস ============
        Book(201, 2, "📚", "সহিহ বুখারী", "ইমাম বুখারী", "সবচেয়ে বিশুদ্ধ হাদিস গ্রন্থ", 3,
            listOf(
                BookPage(1, "পরিচিতি", "সহিহ বুখারী ইসলামের সবচেয়ে বিশুদ্ধ হাদিস সংকলন।"),
                BookPage(2, "সংকলনের পদ্ধতি", "ইমাম বুখারী ১৬ বছর সময় নিয়ে এটি সংকলন করেন।"),
                BookPage(3, "প্রথম হাদিস", "'নিশ্চয়ই সব কাজ নিয়তের উপর নির্ভরশীল'।")
            )
        ),
        Book(202, 2, "📖", "সহিহ মুসলিম", "ইমাম মুসলিম", "দ্বিতীয় বিশুদ্ধ হাদিস গ্রন্থ", 2,
            listOf(
                BookPage(1, "পরিচিতি", "সহিহ মুসলিম দ্বিতীয় সবচেয়ে বিশুদ্ধ হাদিস গ্রন্থ।"),
                BookPage(2, "বৈশিষ্ট্য", "এতে হাদিসগুলো সুন্দরভাবে সাজানো হয়েছে।")
            )
        ),
        Book(203, 2, "📕", "সুনানে আবু দাউদ", "ইমাম আবু দাউদ", "ফিকহভিত্তিক হাদিস", 2,
            listOf(
                BookPage(1, "পরিচিতি", "সুনানে আবু দাউদে ফিকহ সম্পর্কিত হাদিস বেশি।"),
                BookPage(2, "গুরুত্ব", "এটি হানাফি ফিকহের জন্য গুরুত্বপূর্ণ।")
            )
        ),
        Book(204, 2, "📗", "সুনানে তিরমিজি", "ইমাম তিরমিজি", "হাদিসের মান নির্ণয়", 2,
            listOf(
                BookPage(1, "পরিচিতি", "সুনানে তিরমিজিতে হাদিসের মান নির্ণয় করা হয়েছে।"),
                BookPage(2, "বিশেষত্ব", "প্রতিটি হাদিসের সাথে সহিহ/দুর্বল উল্লেখ আছে।")
            )
        ),
        Book(205, 2, "📘", "সুনানে নাসাঈ", "ইমাম নাসাঈ", "হাদিসের বিশুদ্ধতা যাচাই", 2,
            listOf(
                BookPage(1, "পরিচিতি", "সুনানে নাসাঈতে হাদিসের বিশুদ্ধতা যাচাই করা হয়েছে।"),
                BookPage(2, "বৈশিষ্ট্য", "এতে রাবীদের বিস্তারিত আলোচনা আছে।")
            )
        ),
        Book(206, 2, "📙", "সুনানে ইবনে মাজাহ", "ইমাম ইবনে মাজাহ", "হাদিস সংকলন", 2,
            listOf(
                BookPage(1, "পরিচিতি", "সুনানে ইবনে মাজাহ ছয়টি প্রধান হাদিস গ্রন্থের একটি।"),
                BookPage(2, "বৈশিষ্ট্য", "এতে ৪৩৪১টি হাদিস সংকলিত হয়েছে।")
            )
        ),

        // ============ ফিকহ ============
        Book(301, 3, "⚖️", "ফিকহুস সুন্নাহ", "সাইয়েদ সাবিক", "সহজ ফিকহ শিক্ষা", 3,
            listOf(
                BookPage(1, "পরিচিতি", "ফিকহুস সুন্নাহ একটি জনপ্রিয় ফিকহ গ্রন্থ।"),
                BookPage(2, "বিষয়বস্তু", "এতে ইবাদত, লেনদেন ও পারিবারিক বিষয়ক বিধান আছে।"),
                BookPage(3, "বৈশিষ্ট্য", "সহজ ভাষায় লেখা, দলিলসহ প্রতিটি মাসলা।")
            )
        ),
        Book(302, 3, "📕", "হেদায়াতুল মুস্তাফিজ", "আল্লামা ইবনে হুমাম", "হানাফি ফিকহের প্রামাণ্য গ্রন্থ", 2,
            listOf(
                BookPage(1, "পরিচিতি", "এটি হানাফি মাযহাবের একটি প্রামাণ্য ফিকহ গ্রন্থ।"),
                BookPage(2, "গুরুত্ব", "মুফতি ও আলেমদের কাছে খুবই সমাদৃত।")
            )
        ),
        Book(303, 3, "📗", "ফিকহুল ইবাদাত", "আল্লামা ইউসুফ", "ইবাদতের ফিকহ", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইবাদত সম্পর্কিত ফিকহের সহজ ব্যাখ্যা।"),
                BookPage(2, "বিষয়", "নামাজ, রোজা, হজ ও যাকাতের বিধান।")
            )
        ),
        Book(304, 3, "📘", "আহকামুল কুরআন", "ইমাম জাসসাস", "কুরআনের বিধান", 2,
            listOf(
                BookPage(1, "পরিচিতি", "কুরআনে বর্ণিত বিধানসমূহের ব্যাখ্যা।"),
                BookPage(2, "গুরুত্ব", "ফিকহ চর্চায় গুরুত্বপূর্ণ ভূমিকা পালন করে।")
            )
        ),

        // ============ সিরাত ============
        Book(401, 4, "🌟", "আর-রাহিকুল মাখতুম", "সফিউর রহমান মোবারকপুরী", "নবী (সা.) এর জীবনী", 3,
            listOf(
                BookPage(1, "ভূমিকা", "আর-রাহিকুল মাখতুম নবী মুহাম্মদ (সা.) এর সম্পূর্ণ জীবনী।"),
                BookPage(2, "জন্ম ও শৈশব", "নবী (সা.) ৫৭০ খ্রিস্টাব্দে মক্কায় জন্মগ্রহণ করেন।"),
                BookPage(3, "নবুয়ত লাভ", "৪০ বছর বয়সে হেরা গুহায় জিব্রাইল (আ.) প্রথম ওহি নিয়ে আসেন।")
            )
        ),
        Book(402, 4, "📖", "সিরাতুন নবী", "ইবনে হিশাম", "প্রাচীন জীবনী গ্রন্থ", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইবনে হিশাম রচিত প্রাচীনতম সিরাত গ্রন্থ।"),
                BookPage(2, "গুরুত্ব", "নবী (সা.) এর জীবনী জানার প্রধান উৎস।")
            )
        ),
        Book(403, 4, "📗", "মুহাম্মদ (সা.) এর জীবনী", "মাওলানা সাদী", "সহজ জীবনী", 2,
            listOf(
                BookPage(1, "পরিচিতি", "সহজ ভাষায় নবী (সা.) এর জীবনী।"),
                BookPage(2, "বৈশিষ্ট্য", "শিশুদের জন্যও উপযোগী।")
            )
        ),
        Book(404, 4, "📘", "খাতামুন নাবিয়্যীন", "আল্লামা কাসেমী", "শেষ নবীর জীবন", 2,
            listOf(
                BookPage(1, "পরিচিতি", "শেষ নবী হযরত মুহাম্মদ (সা.) এর জীবনী।"),
                BookPage(2, "বৈশিষ্ট্য", "গভীর বিশ্লেষণমূলক গ্রন্থ।")
            )
        ),
        Book(405, 4, "📙", "নবীদের কাহিনী", "ইবনে কাসির", "সব নবীর জীবনী", 2,
            listOf(
                BookPage(1, "পরিচিতি", "আদম (আ.) থেকে মুহাম্মদ (সা.) পর্যন্ত সব নবীর কাহিনী।"),
                BookPage(2, "উৎস", "কুরআন ও হাদিস থেকে সংকলিত।")
            )
        ),

        // ============ আকিদা ============
        Book(501, 5, "🕌", "কিতাবুত তাওহিদ", "শাইখ মুহাম্মদ ইবনে আবদুল ওয়াহহাব", "তাওহিদের মূল বিষয়", 2,
            listOf(
                BookPage(1, "পরিচিতি", "তাওহিদ বা আল্লাহর একত্ববাদ সম্পর্কিত প্রামাণ্য গ্রন্থ।"),
                BookPage(2, "বিষয়", "শিরক, কুফর, নিফাক থেকে বেঁচে থাকার শিক্ষা।")
            )
        ),
        Book(502, 5, "📗", "আকিদাতুল ওয়াসিতীয়া", "ইবনে তাইমিয়া", "সহিহ আকিদার ব্যাখ্যা", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইবনে তাইমিয়া রচিত আকিদা গ্রন্থ।"),
                BookPage(2, "বৈশিষ্ট্য", "কুরআন ও হাদিসভিত্তিক সহিহ আকিদা।")
            )
        ),
        Book(503, 5, "📘", "ইসলামী আকিদা", "আল্লামা শফী", "বিশ্বাসের মূল ভিত্তি", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইসলামের মূল বিশ্বাসের সহজ ব্যাখ্যা।"),
                BookPage(2, "বিষয়", "আল্লাহ, ফেরেশতা, কিতাব, রাসূল, আখিরাত।")
            )
        ),

        // ============ আখলাক ============
        Book(601, 6, "💎", "মাকারিমুল আখলাক", "ইমাম গাযযালী", "উত্তম চরিত্রের শিক্ষা", 3,
            listOf(
                BookPage(1, "পরিচিতি", "ইমাম গাযযালী রচিত চরিত্র শিক্ষার গ্রন্থ।"),
                BookPage(2, "বিষয়", "সততা, ধৈর্য, কৃতজ্ঞতা, বিনয়।"),
                BookPage(3, "গুরুত্ব", "শিশুদের চরিত্র গঠনে সহায়ক।")
            )
        ),
        Book(602, 6, "🌸", "আদবুল মুফরাদ", "ইমাম বুখারী", "শিষ্টাচার ও আদব", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইমাম বুখারী রচিত আদব শিক্ষার গ্রন্থ।"),
                BookPage(2, "বিষয়", "পিতা-মাতার সাথে, প্রতিবেশীর সাথে আচরণ।")
            )
        ),
        Book(603, 6, "🌟", "রিয়াদুস সালিহীন", "ইমাম নববী", "সৎ কাজের হাদিস", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইমাম নববী রচিত সৎকর্মের হাদিস সংকলন।"),
                BookPage(2, "বিষয়", "প্রতিদিনের জীবনে করণীয়।")
            )
        ),
        Book(604, 6, "💫", "এহইয়াউল উলুম", "ইমাম গাযযালী", "দ্বীনি জ্ঞানের পুনরুজ্জীবন", 2,
            listOf(
                BookPage(1, "পরিচিতি", "ইসলামী জ্ঞানের বিখ্যাত গ্রন্থ।"),
                BookPage(2, "বৈশিষ্ট্য", "ইবাদত, আখলাক, সমাজ নিয়ে বিস্তারিত।")
            )
        )
    )

    fun getCategoryById(categoryId: Int): BookCategory? {
        return BookData.categories.find { it.id == categoryId }
    }

    fun getBooksByCategory(categoryId: Int): List<Book> {
        return allBooks.filter { it.categoryId == categoryId }
    }

    fun getBookById(bookId: Int): Book? {
        return allBooks.find { it.id == bookId }
    }
}

@Composable
fun BookListScreen(
    categoryId: Int,
    onBackClick: () -> Unit,
    onBookClick: (Int) -> Unit
) {
    val language by LanguageManager.languageFlow.collectAsState()

    val category = BookListData.getCategoryById(categoryId) ?: BookData.categories.first()
    val books = BookListData.getBooksByCategory(categoryId)

    // Category-র নাম ভাষা অনুযায়ী
    val categoryTitle = when (category.titleKey) {
        "tafsir" -> AppStrings.tafsir(language)
        "hadith" -> AppStrings.hadith(language)
        "fiqh" -> AppStrings.fiqh(language)
        "seerah" -> AppStrings.seerah(language)
        "aqeedah" -> AppStrings.aqeedah(language)
        "akhlaq" -> AppStrings.akhlaq(language)
        else -> category.englishName
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0A2E1F), Color(0xFF1B5E20))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = AppStrings.back(language),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))

                Column {
                    Text(
                        text = categoryTitle,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${books.size} books",
                        fontSize = 12.sp,
                        color = Color(0xFFD4AF37)
                    )
                }
            }

            if (books.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📭", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No books available",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(books) { book ->
                        BookCard(
                            book = book,
                            color = category.color,
                            language = language,
                            onClick = { onBookClick(book.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookCard(
    book: Book,
    color: Color,
    language: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(color.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = book.emoji, fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = book.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = book.author,
                fontSize = 12.sp,
                color = Color(0xFFD4AF37)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = book.description,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.6f),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "📄 ${book.pages} pages",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = AppStrings.openLabel(language),
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(28.dp)
        )
    }
}