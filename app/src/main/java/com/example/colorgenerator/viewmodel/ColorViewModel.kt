package com.example.colorgenerator.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.colorgenerator.models.ColorLock
import com.example.colorgenerator.repositories.ColorRepository
import com.example.colorgenerator.room.ColorLockRoom
import com.example.colorgenerator.room.ColorLockRoomDatabase
import java.util.*

class ColorViewModel(application: Application) : ViewModel() {
    val allColorsLiveData: LiveData<List<ColorLockRoom>>
    private val colorRepository: ColorRepository

    var colorList = mutableStateListOf(
        ColorLock(0),
        ColorLock(0),
        ColorLock(0),
        ColorLock(0),
        ColorLock(0)
    )

    init {
        val colorDb = ColorLockRoomDatabase.getInstance(application)
        val colorDao = colorDb.colorLockDao()
        colorRepository = ColorRepository(colorDao)

        allColorsLiveData = colorRepository.allColors
    }

    private fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    fun updateAllColors() {
        for (i in 0 until colorList.size) {
            updateColorValue(i)
        }
    }

    fun updateColorValue(i: Int) {
        if (colorList[i].isLocked) return

        val newColor = ColorLock(getRandomColor(), colorList[i].isLocked)

        colorList[i] = newColor
    }

    fun updateColorLock(i: Int) {
        val newColor = ColorLock(
            colorList[i].value,
            !colorList[i].isLocked
        )
        colorList[i] = newColor
    }

    /** Room functions **/
    fun insertColor(i: Int) {
        val product = ColorLockRoom(
            colorName = when (i) {
                0 -> "colorOne"
                1 -> "colorTwo"
                2 -> "colorThree"
                3 -> "colorFour"
                4 -> "colorFive"
                else -> throw Exception("colorName not expected, out of range")
            },
            value = colorList[i].value,
            isLocked = colorList[i].isLocked
        )
        colorRepository.insertColor(product)
    }
    /** END Room functions **/
}