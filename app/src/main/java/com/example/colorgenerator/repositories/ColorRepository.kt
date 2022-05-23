package com.example.colorgenerator.repositories

import androidx.lifecycle.LiveData
import com.example.colorgenerator.room.ColorLockDAO
import com.example.colorgenerator.room.ColorLockRoom
import kotlinx.coroutines.*

class ColorRepository(private val colorLockDAO: ColorLockDAO) {
    val allColors: LiveData<List<ColorLockRoom>> = colorLockDAO.getAllColors()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertColor(color: ColorLockRoom) {
        coroutineScope.launch(Dispatchers.IO) {
            colorLockDAO.insertColor(color)
        }
    }
}