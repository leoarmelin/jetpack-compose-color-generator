package com.example.colorgenerator.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ColorLockRoom::class)], version = 1)
abstract class ColorLockRoomDatabase : RoomDatabase() {
    abstract fun colorLockDao(): ColorLockDAO

    companion object {
        private var INSTANCE: ColorLockRoomDatabase? = null

        fun getInstance(context: Context): ColorLockRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ColorLockRoomDatabase::class.java,
                        "colors_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}