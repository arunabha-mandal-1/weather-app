package com.arunabha.weatherapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunabha.weatherapp.api.Constant
import com.arunabha.weatherapp.api.NetworkResponse
import com.arunabha.weatherapp.api.RetrofitInstance
import com.arunabha.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: MutableLiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String){
//        Log.d("abc", "okkk")
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey, city)
                if(response.isSuccessful){
//                Log.d("Response", response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
//                Log.d("Error", response.errorBody().toString())
                    _weatherResult.value = NetworkResponse.Error("Failed to load data!")
                }
            }
            catch (e: Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data!")
            }
        }
    }
}