package com.example.currencyconverter.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CurrencyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.convertButton.setOnClickListener{
            val queryConvert = "${binding.spinnerFrom.selectedItem}_${binding.spinnerTo.selectedItem}"
            val amount = binding.inputAmount.text.toString()
            Log.d("After",amount)
            viewModel.convertRate(queryConvert,
               amount
                )


        }


        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect(){event->
                when(event){
                    is CurrencyViewModel.CurrencyEvent.Success ->{
                        binding.progess.visibility = View.GONE
                        binding.conversionResult.setTextColor(Color.BLACK)
                        binding.conversionResult.text = event.resultText
                    }
                    is CurrencyViewModel.CurrencyEvent.Failure->{
                        binding.progess.visibility = View.GONE
                        binding.conversionResult.setTextColor(Color.BLACK)
                        binding.conversionResult.text = event.errorText
                    }
                    is CurrencyViewModel.CurrencyEvent.Loading->{
                        binding.progess.visibility = View.VISIBLE
                    }
                    else-> Unit
                }
            }
        }
    }
}