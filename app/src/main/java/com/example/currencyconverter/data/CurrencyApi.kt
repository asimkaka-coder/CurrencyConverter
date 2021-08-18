package com.example.currencyconverter.data

import com.example.currencyconverter.models.CurrencyResponse
import com.example.currencyconverter.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/api/v7/convert?")
    suspend fun getConvertedRate(
        @Query("q") query:String,
        @Query("apiKey") key:String = API_KEY
    ):Response<CurrencyResponse>
}