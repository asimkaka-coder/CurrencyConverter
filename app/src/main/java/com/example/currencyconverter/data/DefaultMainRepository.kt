package com.example.currencyconverter.data

import com.example.currencyconverter.models.CurrencyResponse
import com.example.currencyconverter.utils.Constants.Companion.API_KEY
import com.example.currencyconverter.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val currencyApi: CurrencyApi
):MainRepository {
    override suspend fun getConvertedRate(convertQuery:String): Resource<CurrencyResponse> {
        return try {
            val response = currencyApi.getConvertedRate(convertQuery)
            val result = response.body()
            if(response.isSuccessful && result!=null){
                Resource.Success(result)
            }
            else{
            Resource.Error(response.message())}
        }
        catch (e:Exception){
            Resource.Error(e.message.toString())
        }
    }




}