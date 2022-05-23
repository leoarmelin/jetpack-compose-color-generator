package com.example.colorgenerator.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ColorLockDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertColor(color: ColorLockRoom)

    @Query("SELECT * FROM colors")
    fun getAllColors(): LiveData<List<ColorLockRoom>>
}