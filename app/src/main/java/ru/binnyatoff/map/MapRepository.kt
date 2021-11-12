package ru.binnyatoff.map

import androidx.lifecycle.LiveData
import ru.binnyatoff.map.room.MapDao
import ru.binnyatoff.map.room.MapModel

class MapRepository(private val mapDao: MapDao) {
    suspend fun readAllData() : List<MapModel>{
        return mapDao.readAllData()
    }
    suspend fun updateData(model: MapModel){
        mapDao.updateData(model)
    }
    suspend fun addData(model: MapModel){
        mapDao.addData(model)
    }
}