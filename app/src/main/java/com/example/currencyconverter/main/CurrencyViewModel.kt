package com.example.currencyconverter.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.MainRepository
import com.example.currencyconverter.utils.DispatcherProvider
import com.example.currencyconverter.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {


    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }


    private val _conversion:MutableStateFlow<CurrencyEvent> = MutableStateFlow(CurrencyEvent.Empty)
    val conversion:StateFlow<CurrencyEvent> = _conversion


    fun convertRate(query:String, from_Amount:String){

        val amountToDouble = from_Amount.toFloat()

        viewModelScope.launch(dispatcherProvider.io) {

            _conversion.value = CurrencyEvent.Loading

            when(val conversionResponse = repository.getConvertedRate(query)){
                is Resource.Error -> {_conversion.value = CurrencyEvent.Failure("Didn't Work")}
                is Resource.Success -> {
                    val CurrencyRate = conversionResponse.data!!.results.USD_PKR.`val`
                    val convertedRate = CurrencyRate*amountToDouble
                    _conversion.value = CurrencyEvent.Success(
                        "$from_Amount${conversionResponse.data.results.USD_PKR.fr} = $convertedRate${conversionResponse.data.results.USD_PKR.to}"
                    )
                }
            }
        }

    }


}