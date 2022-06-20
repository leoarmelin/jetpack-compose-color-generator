package com.example.colorgenerator.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.colorgenerator.models.ColorGenerationMethod
import com.example.colorgenerator.models.ColorLock
import com.example.colorgenerator.repositories.ColorRepository
import com.example.colorgenerator.room.ColorLockRoom
import com.example.colorgenerator.room.ColorLockRoomDatabase
import java.util.*

class ColorViewModel(application: Application) : ViewModel() {
    val allColorsLiveData: LiveData<List<ColorLockRoom>>
    private val colorRepository: ColorRepository

    var colorGeneratorList = mutableStateListOf(
        ColorLock(0),
        ColorLock(0),
        ColorLock(0),
        ColorLock(0),
        ColorLock(0)
    )

    var gradientGeneratorList = mutableStateListOf(
        ColorLock(0),
        ColorLock(0),
    )

    var generationMethod by mutableStateOf(ColorGenerationMethod.Monochromatic)
//    var generationMethod = ColorGenerationMethod.Monochromatic

    init {
        val colorDb = ColorLockRoomDatabase.getInstance(application)
        val colorDao = colorDb.colorLockDao()
        colorRepository = ColorRepository(colorDao)

        allColorsLiveData = colorRepository.allColors
    }

    private fun getRandomColor(): Int {
        val random = Random()
        return Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }

    private fun getNewColor(listName: String): Int {
        val random = Random()

        return when (generationMethod) {
            ColorGenerationMethod.Random -> {
                Color.argb(
                    255,
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
                )
            }

            ColorGenerationMethod.Monochromatic -> {
                val hsl = FloatArray(3)
                when (listName) {
                    "Color" -> ColorUtils.colorToHSL(colorGeneratorList.first().value, hsl)

                    "Gradient" -> ColorUtils.colorToHSL(gradientGeneratorList.first().value, hsl)
                    else -> throw Exception("listName not allowed.")
                }

                hsl[2] = (10f + random.nextInt(90)) / 100f

                Color.HSVToColor(hsl)
            }
        }

    }

    /** Color Generator **/
    fun updateColorGeneratorList() {
        for (i in 0 until colorGeneratorList.size) {
            updateColorGeneratorValue(i)
        }
    }

    fun updateColorGeneratorValue(i: Int) {
        if (colorGeneratorList[i].isLocked) return

        val newColor = ColorLock(
            if (i == 0) getRandomColor()
            else getNewColor("Color"), colorGeneratorList[i].isLocked
        )

        colorGeneratorList[i] = newColor
    }

    fun updateColorGeneratorLock(i: Int) {
        val newColor = ColorLock(
            colorGeneratorList[i].value,
            !colorGeneratorList[i].isLocked
        )
        colorGeneratorList[i] = newColor
    }
    /** END Color Generator **/

    /** Gradient Generator **/
    fun updateGradientGeneratorList() {
        for (i in 0 until gradientGeneratorList.size) {
            updateGradientGeneratorValue(i)
        }
    }

    private fun updateGradientGeneratorValue(i: Int) {
        if (gradientGeneratorList[i].isLocked) return

        val newColor = ColorLock(
            if (i == 0) getRandomColor()
            else getNewColor("Gradient"), gradientGeneratorList[i].isLocked
        )

        gradientGeneratorList[i] = newColor
    }

    fun updateGradientGeneratorLock(i: Int) {
        val newColor = ColorLock(
            gradientGeneratorList[i].value,
            !gradientGeneratorList[i].isLocked
        )
        gradientGeneratorList[i] = newColor
    }
    /** END Gradient Generator **/

    /** Room functions **/
    fun insertColorGeneratorColor(i: Int) {
        val color = ColorLockRoom(
            colorName = "colorGenerator${i + 1}",
            value = colorGeneratorList[i].value,
            isLocked = colorGeneratorList[i].isLocked
        )
        colorRepository.insertColor(color)
    }

    fun insertGradientGeneratorColor(i: Int) {
        val color = ColorLockRoom(
            colorName = "gradientGenerator${i + 1}",
            value = gradientGeneratorList[i].value,
            isLocked = gradientGeneratorList[i].isLocked
        )
        colorRepository.insertColor(color)
    }

    fun updateAllColorsFromRoom(colors: List<ColorLockRoom>?) {
        if (colors.isNullOrEmpty()) {
            updateColorGeneratorList()
            return
        }

        try {
            for (i in 0 until colorGeneratorList.size) {
                val color = colors.find { it.colorName == "colorGenerator${i + 1}" }
                    ?: throw Exception("colorGenerator not found")

                colorGeneratorList[i] = ColorLock(color.value, color.isLocked)
            }

            for (i in 0 until gradientGeneratorList.size) {
                val color = colors.find { it.colorName == "gradientGenerator${i + 1}" }
                    ?: throw Exception("gradientGenerator not found")

                gradientGeneratorList[i] = ColorLock(color.value, color.isLocked)
            }
        } catch (err: Exception) {
            when (err.message) {
                "colorGenerator not found" -> updateColorGeneratorList()
                "gradientGenerator not found" -> updateGradientGeneratorList()
            }
        }
    }
    /** END Room functions **/
}