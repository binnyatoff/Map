package ru.binnyatoff.map.room

import android.graphics.ColorSpace
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MapDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(mapModel: MapModel)

    @Update
    suspend fun updateData(model: MapModel)

    @Query("SELECT * FROM mapdatabase ORDER BY id ASC")
    suspend fun readAllData(): List<MapModel>
}