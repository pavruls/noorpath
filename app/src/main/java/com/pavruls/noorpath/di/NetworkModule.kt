package com.pavruls.noorpath.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.pavruls.noorpath.data.remote.api.OverpassApi
import com.pavruls.noorpath.data.repository.AuthRepositoryImpl
import com.pavruls.noorpath.data.repository.MosqueRepository
import com.pavruls.noorpath.data.repository.MosqueRepositoryImpl
import com.pavruls.noorpath.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

class UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("User-Agent", "NoorPathApp/1.0 (Contact: support@noorpath.com)")
            .header("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ===== Auth Repository =====
    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        credentialManager: CredentialManager,
        @ApplicationContext context: Context
    ): AuthRepository = AuthRepositoryImpl(auth, credentialManager, context)

    // ===== OkHttp Client =====
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(UserAgentInterceptor())
            .build()
    }

    // ===== Mirror 1: private.coffee =====
    @Provides
    @Singleton
    @Named("Mirror1")
    fun provideRetrofit1(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://overpass.private.coffee/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ===== Mirror 2: kumi.systems =====
    @Provides
    @Singleton
    @Named("Mirror2")
    fun provideRetrofit2(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://overpass.kumi.systems/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ===== Mirror 3: overpass-api.de =====
    @Provides
    @Singleton
    @Named("Mirror3")
    fun provideRetrofit3(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://overpass-api.de/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ===== Overpass APIs =====
    @Provides
    @Singleton
    @Named("Api1")
    fun provideApi1(@Named("Mirror1") retrofit: Retrofit): OverpassApi =
        retrofit.create(OverpassApi::class.java)

    @Provides
    @Singleton
    @Named("Api2")
    fun provideApi2(@Named("Mirror2") retrofit: Retrofit): OverpassApi =
        retrofit.create(OverpassApi::class.java)

    @Provides
    @Singleton
    @Named("Api3")
    fun provideApi3(@Named("Mirror3") retrofit: Retrofit): OverpassApi =
        retrofit.create(OverpassApi::class.java)

    // ===== Mosque Repository =====
    @Provides
    @Singleton
    fun provideMosqueRepository(
        @Named("Api1") api1: OverpassApi,
        @Named("Api2") api2: OverpassApi,
        @Named("Api3") api3: OverpassApi
    ): MosqueRepository {
        return MosqueRepositoryImpl(api1, api2, api3)
    }
}