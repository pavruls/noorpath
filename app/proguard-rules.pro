# ============================================================
# NoorPath — ProGuard Rules
# ============================================================

# ============ Retrofit ============
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# ============ Gson ============
-keepattributes Signature, *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ============ Hilt / Dagger ============
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper
-keepclasseswithmembers,allowshrinking,allowobfuscation class * {
    @dagger.hilt.android.qualifiers.* <fields>;
}

# ============ Room ============
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <methods>;
}

# ============ Firebase ============
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# ============ Google Maps ============
-keep class com.google.android.gms.maps.** { *; }
-keep class com.google.maps.android.** { *; }
-dontwarn com.google.maps.android.**

# ============ Media3 / ExoPlayer ============
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

# ============ WorkManager ============
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.ListenableWorker
-keep class * extends androidx.work.CoroutineWorker
-keepclassmembers class * extends androidx.work.ListenableWorker {
    <init>(android.content.Context, androidx.work.WorkerParameters);
}

# ============ Hilt Worker ============
-keep class * extends androidx.hilt.work.HiltWorker

# ============ Glance Widget ============
-keep class androidx.glance.** { *; }
-keep class * extends androidx.glance.appwidget.GlanceAppWidget
-keep class * extends androidx.glance.appwidget.GlanceAppWidgetReceiver
-dontwarn androidx.glance.**

# ============ Kotlin ============
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# ============ Coroutines ============
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ============ Compose ============
-dontwarn androidx.compose.**
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.platform.** { *; }

# ============ Data Classes (Gson deserialization) ============
# আপনার সব data class গুলো রাখুন
-keep class com.pavruls.noorpath.data.** { *; }
-keep class com.pavruls.noorpath.domain.** { *; }
-keep class com.pavruls.noorpath.search.** { *; }
-keep class com.pavruls.noorpath.quran.** { *; }
-keep class com.pavruls.noorpath.hadith.** { *; }
-keep class com.pavruls.noorpath.fiqh.** { *; }
-keep class com.pavruls.noorpath.seerah.** { *; }
-keep class com.pavruls.noorpath.aqeedah.** { *; }
-keep class com.pavruls.noorpath.akhlaq.** { *; }
-keep class com.pavruls.noorpath.tafsir.** { *; }

# ============ Parcelize ============
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# ============ Enum ============
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ============ Serializable ============
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ============ Native methods ============
-keepclasseswithmembernames class * {
    native <methods>;
}

# ============ WebView ============
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}

# ============ View Binding ============
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}

# ============ Suppress warnings ============
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
-dontwarn org.slf4j.**
-dontwarn javax.annotation.**