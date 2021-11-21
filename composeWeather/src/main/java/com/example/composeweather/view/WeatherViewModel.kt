package com.example.composeweather.view

import android.app.Application
import android.location.Address
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.composeweather.R
import com.example.composeweather.common.ContentState
import com.example.composeweather.common.Error
import com.example.composeweather.common.Loading
import com.example.composeweather.common.Success
import com.example.composeweather.model.WeatherModel
import com.example.composeweather.room.CityInfo
import com.example.composeweather.room.WeatherDatabase
import com.example.composeweather.util.*
import com.qweather.sdk.bean.base.Lang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) :AndroidViewModel(application){
    @RequiresApi(Build.VERSION_CODES.N)
    private var language:Lang = getDefaultLocale(getApplication())
    private var cityInfoDao = WeatherDatabase.getDataBase(application).cityInfoDao()
    private var weatherJob:Job? = null
    private var refreshCityJob:Job? = null
    private var updateCityJob:Job? = null

    private val _searchCityInfo = MutableLiveData(0)
    val searchCityInfo:LiveData<Int> = _searchCityInfo

    private val _cityInfoList = MutableLiveData(listOf<CityInfo>())
    val cityInfoList:LiveData<List<CityInfo>> = _cityInfoList

    fun onSearchCityInfoChanged(page:Int){
        if (page == _searchCityInfo.value){
            XLog.d("onSearchCityInfoChanged no change")
            return
        }
        _searchCityInfo.postValue(page)
    }

    private fun onCityInfoListChange(list: List<CityInfo>){
        if (list == _cityInfoList.value){
            XLog.d("onCityInfoListChanged no change")
            return
        }
        _cityInfoList.postValue(list)
    }

    private val _weatherModel = MutableLiveData<ContentState<WeatherModel>>(Loading)
    val weatherModel:LiveData<ContentState<WeatherModel>> = _weatherModel

    private fun onWeatherModelChanged(contentState: ContentState<WeatherModel>){
        if (contentState == _weatherModel.value){
            XLog.d("onWeatherModelChanged no change")
            return
        }
        _weatherModel.postValue(contentState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getWeather(location:String){
        if (!NetCheckUtil.checkNet(getApplication())){
            showToast(getApplication(), R.string.bad_network_view_tip)
            onWeatherModelChanged(Error(IllegalStateException("当前没有网络，请检查网络")))
        }
        weatherJob.checkCoroutines()
        weatherJob = viewModelScope.launch(Dispatchers.IO) {
            val weatherNow = weatherRepository.getWeatherNew(location = location, lang = language)
            val weather24Hour = weatherRepository.getWeather24Hour(location = location, lang = language)
            val weather7Day = weatherRepository.getWeather7Day(location = location, lang = language)
            val airNow = weatherRepository.getAirNow(location = location, lang = language)
            val weatherModel = WeatherModel(
                nowBaseBean = weatherNow,
                hourlyBeanList = weather24Hour,
                dailyBean = weather7Day.first,
                dailyBeanList = weather7Day.second,
                airNowBean = airNow
            )
            withContext(Dispatchers.Main){
                onWeatherModelChanged(Success(weatherModel))
            }
        }
    }

    fun refreshCityList(){
        refreshCityJob.checkCoroutines()
        refreshCityJob = viewModelScope.launch(Dispatchers.IO) {
            val cityInfoList = weatherRepository.refreshCityList()
            withContext(Dispatchers.Main){
                onCityInfoListChange(cityInfoList)
                weatherRepository.updateCityIsIndex(cityInfoList = cityInfoList){
                    index -> onSearchCityInfoChanged(index)
                }
            }
        }
    }

    fun hasLocation(): Boolean {
        val isLocation = runBlocking { cityInfoDao.getIsLocationList() }
        return isLocation.isNotEmpty()
    }

    /**
     * 修改当前的位置信息
     *
     * @param location 位置
     * @param result Address
     */
    fun updateCityInfo(location: Location, result: MutableList<Address>) {
        updateCityJob.checkCoroutines()
        updateCityJob = viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.updateCityInfo(location, result) {
                refreshCityList()
            }
        }
    }



}