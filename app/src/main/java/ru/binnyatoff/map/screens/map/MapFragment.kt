package ru.binnyatoff.map.screens.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.geojson.GeoJsonLayer
import ru.binnyatoff.map.databinding.FragmentMapBinding
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import ru.binnyatoff.map.R


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private lateinit var mapFragmentViewModel: MapFragmentViewModel
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapFragment: MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapFragmentViewModel = ViewModelProvider(this).get(MapFragmentViewModel::class.java)
        mapFragmentViewModel.getGson()
        binding = FragmentMapBinding.inflate(inflater, container, false)
        mapFragment = binding.map
        mapFragment.onCreate(savedInstanceState)
        mapFragment.getMapAsync(this)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(42.0, 132.0)))
        mapFragmentViewModel = ViewModelProvider(this).get(MapFragmentViewModel::class.java)
        //Получение geojson из viewmodel и запуск расчета расстояния
        mapFragmentViewModel.json.observe(viewLifecycleOwner, {
            val layer = GeoJsonLayer(googleMap, it)
            mapFragmentViewModel.distance(layer)
            layer.addLayerToMap()
        })
        mapFragmentViewModel.sumdistance.observe(viewLifecycleOwner, {
            val distance = it.sum()
            binding.size.text = "$distance м"
        })
    }

    override fun onResume() {
        mapFragment.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapFragment.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapFragment.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapFragment.onLowMemory()
    }

}