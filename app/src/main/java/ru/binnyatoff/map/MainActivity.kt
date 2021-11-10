package ru.binnyatoff.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil.computeDistanceBetween
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon
import ru.binnyatoff.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private lateinit var layer: GeoJsonLayer
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getGson()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(42.0, 132.0)))
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        //Получение geojson из viewmodel и запуск расчета расстояния
        mainActivityViewModel.json.observe(this, {
            layer = GeoJsonLayer(mMap, it)
            layer.addLayerToMap()
            val distance = distance(layer).sum()
            binding.size.text ="$distance м"
        })
    }

    //функция вычесления дистанции
    fun distance(layer: GeoJsonLayer): MutableList<Int> {
        val sumdistance = mutableListOf<Int>()
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
                    sum += computeDistanceBetween(coordinats[i - 1], coordinats[i])
                    i++
                }
                j++
                sumdistance.add(sum.toInt())
            }
        }
        return sumdistance
    }
}