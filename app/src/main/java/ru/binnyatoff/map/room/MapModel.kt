package ru.binnyatoff.map.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mapdatabase")
data class MapModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "distance")
    val distance: Int
)