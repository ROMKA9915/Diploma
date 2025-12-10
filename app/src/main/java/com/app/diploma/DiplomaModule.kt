package com.app.diploma

import android.content.Context
import com.app.diploma.data.local.AppSettings
import com.app.diploma.data.local.AppSettingsImpl
import com.app.diploma.data.network.CryptoRepositoryImpl
import com.app.diploma.data.network.CryptoService
import com.app.diploma.domain.CryptoRepository
import com.app.diploma.presentation.navigation.Navigator
import com.app.diploma.presentation.navigation.NavigatorImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiplomaModule {

    @Singleton
    @Binds
    abstract fun bindAppSettings(appSettingsImpl: AppSettingsImpl): AppSettings

    @Singleton
    @Binds
    abstract fun bindCryptoRepository(cryptoRepositoryImpl: CryptoRepositoryImpl): CryptoRepository

    @Singleton
    @Binds
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator

}

@Module
@InstallIn(SingletonComponent::class)
object DiplomaStaticModule {

    @Singleton
    @Provides
    fun providesSharedPreferences(
        @ApplicationContext appContext: Context,
    ) = appContext.getSharedPreferences("diploma_prefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providesJson() = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
        allowTrailingComma = true
        decodeEnumsCaseInsensitive = true
    }

    @Singleton
    @Provides
    fun provideCryptoService(json: Json): CryptoService {
        val client = OkHttpClient.Builder()
            .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .build()

            it.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create<CryptoService>()
    }
}
