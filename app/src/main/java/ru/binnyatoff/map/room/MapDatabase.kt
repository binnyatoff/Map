package ru.binnyatoff.map.room

import android.content.Context
import android.graphics.ColorSpace
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MapModel::class], version = 1, exportSchema = false)
abstract class MapDatabase : RoomDatabase() {
    abstract fun todoDao(): MapDao

    companion object {
        @Volatile
        private var INSTANCE: MapDatabase? = null
        fun getDatabase(context: Context): MapDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MapDatabase::class.java,
                "mapdatabase"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}
