package com.example.colorgenerator.viewmodel

import android.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.colorgenerator.models.ColorLock
import java.util.*

class ColorViewModel : ViewModel() {
    var colorOne by mutableStateOf(ColorLock(0))
    var colorTwo by mutableStateOf(ColorLock(0))
    var colorThree by mutableStateOf(ColorLock(0))
    var colorFour by mutableStateOf(ColorLock(0))
    var colorFive by mutableStateOf(ColorLock(0))

    private fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    fun updateAllColors() {
        updateColorOneValue()
        updateColorTwoValue()
        updateColorThreeValue()
        updateColorFourValue()
        updateColorFiveValue()
    }

    fun updateColorOneValue() {
        if (colorOne.isLocked) return

        val newColor = ColorLock(
            getRandomColor(),
            colorOne.isLocked
        )
        colorOne = newColor
    }

    fun updateColorTwoValue() {
        if (colorTwo.isLocked) return

        val newColor = ColorLock(
            getRandomColor(),
            colorTwo.isLocked
        )
        colorTwo = newColor
    }

    fun updateColorThreeValue() {
        if (colorThree.isLocked) return

        val newColor = ColorLock(
            getRandomColor(),
            colorThree.isLocked
        )
        colorThree = newColor
    }

    fun updateColorFourValue() {
        if (colorFour.isLocked) return

        val newColor = ColorLock(
            getRandomColor(),
            colorFour.isLocked
        )
        colorFour = newColor
    }

    fun updateColorFiveValue() {
        if (colorFive.isLocked) return

        val newColor = ColorLock(
            getRandomColor(),
            colorFive.isLocked
        )
        colorFive = newColor
    }

    fun updateColorOneLock() {
        val newColor = ColorLock(
            colorOne.value,
            !colorOne.isLocked
        )
        colorOne = newColor
    }

    fun updateColorTwoLock() {
        val newColor = ColorLock(
            colorTwo.value,
            !colorTwo.isLocked
        )
        colorTwo = newColor
    }

    fun updateColorThreeLock() {
        val newColor = ColorLock(
            colorThree.value,
            !colorThree.isLocked
        )
        colorThree = newColor
    }

    fun updateColorFourLock() {
        val newColor = ColorLock(
            colorFour.value,
            !colorFour.isLocked
        )
        colorFour = newColor
    }

    fun updateColorFiveLock() {
        val newColor = ColorLock(
            colorFive.value,
            !colorFive.isLocked
        )
        colorFive = newColor
    }
}