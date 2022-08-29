package com.example.nasagalleryex.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.nasagalleryex.model.NasaDataItem
import com.example.nasagalleryex.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class NasaViewModel(val context: Application) : AndroidViewModel(context) {

    private var dataGet = MutableLiveData<ArrayList<NasaDataItem>>()
    private var dataGet1 = ArrayList<NasaDataItem>()

    init {
        getJsonFromAssets()
    }


    fun getJsonFromAssets() {
        viewModelScope.launch {
            val jsonFileString = Utils.getJsonFromAssets(context, "data.json")
            Log.e("file is", jsonFileString.toString())
            val gson = Gson()
            val listUserType: Type = object : TypeToken<ArrayList<NasaDataItem?>?>() {}.type
            dataGet1 =
                gson.fromJson(jsonFileString, listUserType)
            dataGet.value = dataGet1
        }

    }

    fun getUsers(): MutableLiveData<ArrayList<NasaDataItem>> {
        return dataGet
    }
}