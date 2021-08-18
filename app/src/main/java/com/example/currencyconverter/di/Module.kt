package com.example.currencyconverter.di

import com.example.currencyconverter.data.CurrencyApi
import com.example.currencyconverter.data.DefaultMainRepository
import com.example.currencyconverter.data.MainRepository
import com.example.currencyconverter.utils.Constants.Companion.BASE_URL
import com.example.currencyconverter.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDefaultMainRepository(api: CurrencyApi): MainRepository = DefaultMainRepository(api)


    @Singleton
    @Provides
    fun getDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
    }


}