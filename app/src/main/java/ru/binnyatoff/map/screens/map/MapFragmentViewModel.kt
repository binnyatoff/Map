package ru.binnyatoff.map.screens.map

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import ru.binnyatoff.map.room.MapModel
import ru.binnyatoff.map.screens.history.HistoryFragmentVM
import java.net.URL

class MapFragmentViewModel(application: Application) : AndroidViewModel(application){
    var json = MutableLiveData<JSONObject>()
    val sumdistance = MutableLiveData<List<Int>>()
    var historyFragmentVM: HistoryFragmentVM = HistoryFragmentVM(application)

    //получение geogson по ссылке с сайта в coroutine
    fun getGson() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = URL("https://waadsu.com/api/russia.geo.json").readText()
            json.postValue(JSONObject(result))
        }
    }

    //функция вычесления дистанции
    fun distance(layer: GeoJsonLayer) {
        CoroutineScope(Dispatchers.IO).launch {
            val sumlist = mutableListOf<Int>()
            for (feature in layer.features) {
                var j = 0
                val polygons = feature.geometry as GeoJsonMultiPolygon
                //цикл прохождения по полигонам
                while (j < polygons.polygons.size) {
                    val polygon = polygons.polygons[j]
                    val coordinats = polygon.coordinates[0]
                    var i = 1
                    var sum = 0.0
                    //вычисление дистанции по координатам
                    while (i < coordinats.size) {
                        sum += SphericalUtil.computeDistanceBetween(
                            coordinats[i - 1],
                            coordinats[i]
                        )
                        i++
                    }
                    j++
                    sumlist.add(sum.toInt())
                    sumdistance.postValue(sumlist)
                    historyFragmentVM.addData(MapModel(j, sum.toInt()))
                }
            }
        }
    }
}