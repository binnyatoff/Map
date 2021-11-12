package ru.binnyatoff.map.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.binnyatoff.map.MapRepository
import ru.binnyatoff.map.room.MapDatabase
import ru.binnyatoff.map.room.MapModel

class HistoryFragmentVM(application: Application) : AndroidViewModel(application) {
    private val repository: MapRepository
    val coordinats =  MutableLiveData<List<MapModel>>()

    init {
        val mapDao = MapDatabase.getDatabase(application).todoDao()
        repository = MapRepository(mapDao)
    }

    fun readAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            val res = repository.readAllData()
            coordinats.postValue(res)
        }

    }

    fun updateData(model: MapModel){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateData(model)
        }
    }

    fun addData(model: MapModel){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addData(model)
        }
    }
}