package ru.binnyatoff.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class MainActivityViewModel : ViewModel() {
    var json = MutableLiveData<JSONObject>()
    //получение geogson по ссылке с сайта в coroutine
    fun getGson(){
        CoroutineScope(Dispatchers.Default).launch {
            val result = URL("https://waadsu.com/api/russia.geo.json").readText()
            json.postValue(JSONObject(result))
        }
    }
}