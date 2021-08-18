package com.example.currencyconverter.data

import com.example.currencyconverter.models.CurrencyResponse
import com.example.currencyconverter.utils.Resource

interface MainRepository {

    suspend fun getConvertedRate(convertQuery:String):Resource<CurrencyResponse>
}