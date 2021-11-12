package ru.binnyatoff.map.screens.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.geojson.GeoJsonLayer
import ru.binnyatoff.map.R
import ru.binnyatoff.map.databinding.FragmentMapBinding
import ru.binnyatoff.map.screens.MapFragmentViewModel

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private lateinit var mapFragmentViewModel: MapFragmentViewModel
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragmentViewModel = ViewModelProvider(this).get(MapFragmentViewModel::class.java)
        mapFragmentViewModel.getGson()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
}