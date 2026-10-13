package com.pavruls.noorpath.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pavruls.noorpath.presentation.screens.akhlaq.AkhlaqDetailScreen
import com.pavruls.noorpath.presentation.screens.akhlaq.AkhlaqListScreen
import com.pavruls.noorpath.presentation.screens.aqeedah.AqeedahDetailScreen
import com.pavruls.noorpath.presentation.screens.aqeedah.AqeedahListScreen
import com.pavruls.noorpath.presentation.screens.books.BookLibraryScreen
import com.pavruls.noorpath.presentation.screens.books.BookListScreen
import com.pavruls.noorpath.presentation.screens.books.BookReaderScreen
import com.pavruls.noorpath.presentation.screens.dailyhadith.DailyHadithScreen
import com.pavruls.noorpath.presentation.screens.fiqh.FiqhDetailScreen
import com.pavruls.noorpath.presentation.screens.fiqh.FiqhListScreen
import com.pavruls.noorpath.presentation.screens.hadith.HadithDetailScreen
import com.pavruls.noorpath.presentation.screens.hadith.HadithListScreen
import com.pavruls.noorpath.presentation.screens.kids.ArabicLettersScreen
import com.pavruls.noorpath.presentation.screens.kids.BanglaLettersScreen
import com.pavruls.noorpath.presentation.screens.kids.DuaDetailScreen
import com.pavruls.noorpath.presentation.screens.kids.DuasScreen
import com.pavruls.noorpath.presentation.screens.kids.EnglishLettersScreen
import com.pavruls.noorpath.presentation.screens.kids.KidsHomeScreen
import com.pavruls.noorpath.presentation.screens.kids.KidsPrayerScreen
import com.pavruls.noorpath.presentation.screens.kids.PrayerDetailScreen
import com.pavruls.noorpath.presentation.screens.kids.ProphetStoriesScreen
import com.pavruls.noorpath.presentation.screens.kids.ProphetStoryDetailScreen
import com.pavruls.noorpath.presentation.screens.kids.QuizScreen
import com.pavruls.noorpath.presentation.screens.kids.SurahDetailScreen
import com.pavruls.noorpath.presentation.screens.kids.SurahsScreen
import com.pavruls.noorpath.presentation.screens.mode.ModeSelectionScreen
import com.pavruls.noorpath.presentation.screens.parent.ParentDashboardScreen
import com.pavruls.noorpath.presentation.screens.parent.ParentPinScreen
import com.pavruls.noorpath.presentation.screens.prayer.MosqueFinderScreen
import com.pavruls.noorpath.presentation.screens.prayer.PrayerHubScreen
import com.pavruls.noorpath.presentation.screens.prayer.PrayerTimeScreen
import com.pavruls.noorpath.presentation.screens.prayer.PrayerTrackerScreen
import com.pavruls.noorpath.presentation.screens.prayer.QiblaScreen
import com.pavruls.noorpath.presentation.screens.prayer.RamadanScreen
import com.pavruls.noorpath.presentation.screens.prayer.TasbihScreen
import com.pavruls.noorpath.presentation.screens.prayer.ZakatScreen
import com.pavruls.noorpath.presentation.screens.quran.QuranDetailScreen
import com.pavruls.noorpath.presentation.screens.quran.QuranSurahListScreen
import com.pavruls.noorpath.presentation.screens.search.SearchScreen
import com.pavruls.noorpath.presentation.screens.seerah.SeerahDetailScreen
import com.pavruls.noorpath.presentation.screens.seerah.SeerahListScreen
import com.pavruls.noorpath.presentation.screens.settings.SettingsScreen
import com.pavruls.noorpath.presentation.screens.splash.SplashScreen
import com.pavruls.noorpath.presentation.screens.tafsir.TafsirDetailScreen
import com.pavruls.noorpath.presentation.screens.tafsir.TafsirListScreen
import com.pavruls.noorpath.search.SearchSource
import com.pavruls.noorpath.presentation.screens.kids.LettersHubScreen
import com.pavruls.noorpath.presentation.screens.assistant.AssistantScreen
private const val ANIM_DURATION = 300

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Routes.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,

        // ➡️ সামনে যাওয়ার সময় (Forward)
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it / 6 },
                animationSpec = tween(ANIM_DURATION, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(ANIM_DURATION))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 6 },
                animationSpec = tween(ANIM_DURATION, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(ANIM_DURATION))
        },

        // ⬅️ পিছনে যাওয়ার সময় (Pop / Back)
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 6 },
                animationSpec = tween(ANIM_DURATION, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(ANIM_DURATION))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it / 6 },
                animationSpec = tween(ANIM_DURATION, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(ANIM_DURATION))
        }
    ) {
        // ============ Splash ============
        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.MODE_SELECTION) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ============ Mode Selection ============
        composable(Routes.MODE_SELECTION) {
            ModeSelectionScreen(
                onKidsClick = { navController.navigate(Routes.KIDS_HOME) },
                onBooksClick = { navController.navigate(Routes.BOOK_LIBRARY) },
                onParentClick = { navController.navigate(Routes.PARENT_PIN) },
                onPrayerHubClick = { navController.navigate(Routes.PRAYER_HUB) },
                onQiblaClick = { navController.navigate(Routes.QIBLA) },
                onTasbihClick = { navController.navigate(Routes.TASBIH) },
                onDailyHadithClick = { navController.navigate(Routes.DAILY_HADITH) },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onQuranClick = { navController.navigate(Routes.QURAN) },
                onSearchClick = { navController.navigate(Routes.SEARCH) },
                onAssistantClick = { navController.navigate(Routes.ISLAMIC_ASSISTANT) }
            )
        }

        // ============ Mosque Finder ============
        composable("mosque_finder") {
            MosqueFinderScreen()
        }

        // ============ Prayer Hub ============
        composable(Routes.PRAYER_HUB) {
            PrayerHubScreen(
                onBackClick = { navController.popBackStack() },
                onPrayerTimeClick = { navController.navigate(Routes.PRAYER_TIME) },
                onQiblaClick = { navController.navigate(Routes.QIBLA) },
                onTrackerClick = { navController.navigate(Routes.PRAYER_TRACKER) },
                onTasbihClick = { navController.navigate(Routes.TASBIH) },
                onZakatClick = { navController.navigate(Routes.ZAKAT) },
                onRamadanClick = { navController.navigate(Routes.RAMADAN) },
                onMosqueFinderClick = { navController.navigate("mosque_finder") }
            )
        }

        // ============ Kids Mode ============
        composable(Routes.KIDS_HOME) {
            KidsHomeScreen(
                onBackClick = { navController.popBackStack() },
                onMenuClick = { route -> navController.navigate(route) }
            )
        }
        // ============ Letters Hub ============
        composable(Routes.LETTERS_HUB) {
            LettersHubScreen(
                onBackClick = { navController.popBackStack() },
                onArabicClick = { navController.navigate(Routes.ARABIC_LETTERS) },
                onBanglaClick = { navController.navigate(Routes.BANGLA_LETTERS) },
                onEnglishClick = { navController.navigate(Routes.ENGLISH_LETTERS) }
            )
        }

        composable(Routes.ARABIC_LETTERS) {
            ArabicLettersScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Routes.BANGLA_LETTERS) {
            BanglaLettersScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Routes.ENGLISH_LETTERS) {
            EnglishLettersScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Routes.DUAS) {
            DuasScreen(
                onBackClick = { navController.popBackStack() },
                onDuaClick = { duaId ->
                    navController.navigate("${Routes.DUA_DETAIL}/$duaId")
                }
            )
        }

        composable("${Routes.DUA_DETAIL}/{duaId}") { backStackEntry ->
            val duaId = backStackEntry.arguments?.getString("duaId")?.toIntOrNull() ?: 1
            DuaDetailScreen(
                duaId = duaId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.SURAHS) {
            SurahsScreen(
                onBackClick = { navController.popBackStack() },
                onSurahClick = { surahId ->
                    navController.navigate("${Routes.SURAH_DETAIL}/$surahId")
                }
            )
        }

        composable("${Routes.SURAH_DETAIL}/{surahId}") { backStackEntry ->
            val surahId = backStackEntry.arguments?.getString("surahId")?.toIntOrNull() ?: 1
            SurahDetailScreen(
                surahId = surahId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.KIDS_PRAYER) {
            KidsPrayerScreen(
                onBackClick = { navController.popBackStack() },
                onTopicClick = { topicId ->
                    navController.navigate("${Routes.PRAYER_DETAIL}/$topicId")
                }
            )
        }

        composable("${Routes.PRAYER_DETAIL}/{topicId}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId")?.toIntOrNull() ?: 1
            PrayerDetailScreen(
                topicId = topicId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.PROPHET_STORIES) {
            ProphetStoriesScreen(
                onBackClick = { navController.popBackStack() },
                onStoryClick = { storyId ->
                    navController.navigate("${Routes.PROPHET_STORY_DETAIL}/$storyId")
                }
            )
        }

        composable("${Routes.PROPHET_STORY_DETAIL}/{storyId}") { backStackEntry ->
            val storyId = backStackEntry.arguments?.getString("storyId")?.toIntOrNull() ?: 1
            ProphetStoryDetailScreen(
                storyId = storyId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.QUIZ) {
            QuizScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Book Mode ============
        composable(Routes.BOOK_LIBRARY) {
            BookLibraryScreen(
                onBackClick = { navController.popBackStack() },
                onCategoryClick = { categoryId ->
                    navController.navigate("${Routes.BOOK_LIST}/$categoryId")
                },
                onHadithClick = { navController.navigate(Routes.HADITH) },
                onTafsirClick = { navController.navigate(Routes.TAFSIR) },
                onFiqhClick = { navController.navigate(Routes.FIQH) },
                onSeerahClick = { navController.navigate(Routes.SEERAH) },
                onAqeedahClick = { navController.navigate(Routes.AQEEDAH) },
                onAkhlaqClick = { navController.navigate(Routes.AKHLAQ) }
            )
        }

        composable("${Routes.BOOK_LIST}/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 1
            BookListScreen(
                categoryId = categoryId,
                onBackClick = { navController.popBackStack() },
                onBookClick = { bookId ->
                    navController.navigate("${Routes.BOOK_READER}/$bookId")
                }
            )
        }

        composable("${Routes.BOOK_READER}/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull() ?: 101
            BookReaderScreen(
                bookId = bookId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Hadith ============
        composable(Routes.HADITH) {
            HadithListScreen(
                onBackClick = { navController.popBackStack() },
                onBookClick = { prefix, title ->
                    navController.navigate("${Routes.HADITH_DETAIL}/$prefix/$title")
                }
            )
        }

        composable("${Routes.HADITH_DETAIL}/{prefix}/{title}") { backStackEntry ->
            val prefix = backStackEntry.arguments?.getString("prefix") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            HadithDetailScreen(
                bookPrefix = prefix,
                bookTitle = title,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Tafsir ============
        composable(Routes.TAFSIR) {
            TafsirListScreen(
                onBackClick = { navController.popBackStack() },
                onSurahClick = { surahId, surahName ->
                    navController.navigate("${Routes.TAFSIR_DETAIL}/$surahId/$surahName")
                }
            )
        }

        composable("${Routes.TAFSIR_DETAIL}/{surahId}/{surahName}") { backStackEntry ->
            val surahId = backStackEntry.arguments?.getString("surahId")?.toIntOrNull() ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
            TafsirDetailScreen(
                surahId = surahId,
                surahName = surahName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Fiqh ============
        composable(Routes.FIQH) {
            FiqhListScreen(
                onBackClick = { navController.popBackStack() },
                onTopicClick = { topicId, topicName ->
                    navController.navigate("${Routes.FIQH_DETAIL}/$topicId/$topicName")
                }
            )
        }

        composable("${Routes.FIQH_DETAIL}/{topicId}/{topicName}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId")?.toIntOrNull() ?: 1
            val topicName = backStackEntry.arguments?.getString("topicName") ?: ""
            FiqhDetailScreen(
                topicId = topicId,
                topicName = topicName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Seerah ============
        composable(Routes.SEERAH) {
            SeerahListScreen(
                onBackClick = { navController.popBackStack() },
                onTopicClick = { topicId, topicName ->
                    navController.navigate("${Routes.SEERAH_DETAIL}/$topicId/$topicName")
                }
            )
        }

        composable("${Routes.SEERAH_DETAIL}/{topicId}/{topicName}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId")?.toIntOrNull() ?: 1
            val topicName = backStackEntry.arguments?.getString("topicName") ?: ""
            SeerahDetailScreen(
                topicId = topicId,
                topicName = topicName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Aqeedah ============
        composable(Routes.AQEEDAH) {
            AqeedahListScreen(
                onBackClick = { navController.popBackStack() },
                onTopicClick = { topicId, topicName ->
                    navController.navigate("${Routes.AQEEDAH_DETAIL}/$topicId/$topicName")
                }
            )
        }

        composable("${Routes.AQEEDAH_DETAIL}/{topicId}/{topicName}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId")?.toIntOrNull() ?: 1
            val topicName = backStackEntry.arguments?.getString("topicName") ?: ""
            AqeedahDetailScreen(
                topicId = topicId,
                topicName = topicName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Akhlaq ============
        composable(Routes.AKHLAQ) {
            AkhlaqListScreen(
                onBackClick = { navController.popBackStack() },
                onTopicClick = { topicId, topicName ->
                    navController.navigate("${Routes.AKHLAQ_DETAIL}/$topicId/$topicName")
                }
            )
        }

        composable("${Routes.AKHLAQ_DETAIL}/{topicId}/{topicName}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId")?.toIntOrNull() ?: 1
            val topicName = backStackEntry.arguments?.getString("topicName") ?: ""
            AkhlaqDetailScreen(
                topicId = topicId,
                topicName = topicName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Parent Dashboard ============
        composable(Routes.PARENT_PIN) {
            ParentPinScreen(
                onBackClick = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Routes.PARENT_DASHBOARD) {
                        popUpTo(Routes.PARENT_PIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PARENT_DASHBOARD) {
            ParentDashboardScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Prayer Time ============
        composable(Routes.PRAYER_TIME) {
            PrayerTimeScreen(
                onBackClick = { navController.popBackStack() },
                onQiblaClick = { navController.navigate(Routes.QIBLA) }
            )
        }

        composable(Routes.QIBLA) {
            QiblaScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Prayer Tracker ============
        composable(Routes.PRAYER_TRACKER) {
            PrayerTrackerScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Tasbih ============
        composable(Routes.TASBIH) {
            TasbihScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Daily Hadith ============
        composable(Routes.DAILY_HADITH) {
            DailyHadithScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Zakat ============
        composable(Routes.ZAKAT) {
            ZakatScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Ramadan ============
        composable(Routes.RAMADAN) {
            RamadanScreen(onBackClick = { navController.popBackStack() })
        }

        // ============ Quran ============
        composable(Routes.QURAN) {
            QuranSurahListScreen(
                onBackClick = { navController.popBackStack() },
                onSurahClick = { surahId, surahName ->
                    navController.navigate("${Routes.QURAN_DETAIL}/$surahId/$surahName")
                }
            )
        }

        composable("${Routes.QURAN_DETAIL}/{surahId}/{surahName}") { backStackEntry ->
            val surahId = backStackEntry.arguments?.getString("surahId")?.toIntOrNull() ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
            QuranDetailScreen(
                surahId = surahId,
                surahName = surahName,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ============ Settings ============
        composable(Routes.SETTINGS) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }
        // ============ Islamic Assistant (RAG) ============
        composable(Routes.ISLAMIC_ASSISTANT) {
            AssistantScreen(
                onBackClick = { navController.popBackStack() },
                onResultClick = { source, meta ->
                    when (source) {
                        SearchSource.QURAN -> {
                            val surahId = meta.substringBefore(":").toIntOrNull() ?: 1
                            navController.navigate("${Routes.QURAN_DETAIL}/$surahId/সূরা")
                        }
                        SearchSource.HADITH -> {
                            val prefix = meta.substringBefore(":")
                            navController.navigate("${Routes.HADITH_DETAIL}/$prefix/${Routes.HADITH}")
                        }
                        SearchSource.DUA -> {
                            val duaId = meta.toIntOrNull() ?: 1
                            navController.navigate("${Routes.DUA_DETAIL}/$duaId")
                        }
                        else -> { /* Do nothing */ }
                    }
                }
            )
        }

        // ============ Search ============
        composable(Routes.SEARCH) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onResultClick = { hit ->
                    when (hit.source) {
                        SearchSource.QURAN -> {
                            val surahId = hit.meta.substringBefore(":").toIntOrNull() ?: 1
                            val surahName = hit.title.substringBefore(" (").trim()
                            navController.navigate("${Routes.QURAN_DETAIL}/$surahId/$surahName")
                        }
                        SearchSource.HADITH -> {
                            val prefix = hit.meta.substringBefore(":")
                            val bookTitle = hit.title.substringBefore(" —").trim()
                            navController.navigate("${Routes.HADITH_DETAIL}/$prefix/$bookTitle")
                        }
                        SearchSource.DUA -> {
                            val duaId = hit.meta.toIntOrNull() ?: 1
                            navController.navigate("${Routes.DUA_DETAIL}/$duaId")
                        }
                        else -> { /* ALL — কিছু করবেন না */ }
                    }
                }
            )
        }
    }
}