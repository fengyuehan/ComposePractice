package com.example.composepractice.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import com.example.composepractice.CookieStore
import com.example.composepractice.MusicSettings
import com.example.composepractice.db.MusicDatabase
import com.example.composepractice.http.NetMusicApi
import com.example.composepractice.store.CookieStoreSerializer
import com.example.composepractice.store.MusicSettingsSerializer
import com.mrlin.composemany.net.PersistCookieJar
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

import javax.inject.Singleton

/**
 * 声明生命范围
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @NetMusicRetrofit
    @Provides
    fun provideNetMusicRetrofit(
        cookieDataStore:DataStore<CookieStore>
    ):Retrofit  {
        return Retrofit.Builder()
            .baseUrl("https://mrlin-netease-cloud-music-api-iota-silk.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(EnumRetrofitConverterFactory())
            .client(OkHttpClient.
                Builder().
                cookieJar(PersistCookieJar(cookieDataStore)).
                addInterceptor(OkhttpLogger).
                build())
            .build()
    }

    private object OkhttpLogger : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            Log.d("OkhttpLogger", "raw request：${request}")
            val response = chain.proceed(request)
            Log.d("OkhttpLogger", "raw response：${response.peekBody((1024 * 10).toLong()).string()}")
            return response
        }
    }

    @Singleton
    @Provides
    fun provideNetMusicApi(@NetMusicRetrofit retrofit: Retrofit):NetMusicApi{
        return retrofit.create(NetMusicApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMusicDatabase(@ApplicationContext context: Context):MusicDatabase =
        Room.databaseBuilder(context,MusicDatabase::class.java,"net-ease-music").build()

    @Provides
    fun provideMusicSettings(@ApplicationContext context: Context): DataStore<MusicSettings> =
        context.musicSettingsDataStore

    @Provides
    fun provideCookieStore(@ApplicationContext context: Context): DataStore<CookieStore> =
        context.cookieStoreDataStore

    private val Context.musicSettingsDataStore: DataStore<MusicSettings> by dataStore(
        fileName = "music_settings.pb",
        serializer = MusicSettingsSerializer
    )

    private val Context.cookieStoreDataStore: DataStore<CookieStore> by dataStore(
        fileName = "cookie_store.pb",
        serializer = CookieStoreSerializer
    )

}