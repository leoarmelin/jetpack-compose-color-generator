package com.example.colorgenerator

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.colorgenerator.ui.components.ColorScreen
import com.example.colorgenerator.viewmodel.ColorViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    private var accelerometerListener: SensorEventListener? = null
    private var accelerometerSensor: Sensor? = null
    private var mAccel = 0f
    private var mAccelCurrent = SensorManager.GRAVITY_EARTH
    private var mAccelLast = SensorManager.GRAVITY_EARTH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ?: null

        val colorViewModel = ColorViewModel()
        colorViewModel.updateAllColors()

        setContent {
            val systemUiController = rememberSystemUiController()

            configureAccelerometer {
                colorViewModel.updateAllColors()
            }

            ConfigureApp(systemUiController, colorViewModel)

            ColorScreen(colorViewModel)
        }
    }

    override fun onResume() {
        super.onResume()

        if (::sensorManager.isInitialized) {
            sensorManager.registerListener(
                accelerometerListener,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (::sensorManager.isInitialized) {
            sensorManager.unregisterListener(
                accelerometerListener,
            )
        }
    }

    private fun configureAccelerometer(onShake: () -> Unit) {
        if (accelerometerSensor != null) {
            accelerometerListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    mAccelLast = mAccelCurrent
                    mAccelCurrent = sqrt(x * x + y * y + z * z)

                    val delta = mAccelCurrent - mAccelLast
                    mAccel = mAccel * 0.9f + delta * 0.1f

                    if (mAccel > 0.5f || mAccel < -0.5f) {
                        onShake()
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                accelerometerListener,
                accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }
}

@Composable
fun ConfigureApp(uiController: SystemUiController, colorViewModel: ColorViewModel) {
    val animatedColorOne = animateColorAsState(Color(colorViewModel.colorOne.value))
    val animatedColorFive = animateColorAsState(Color(colorViewModel.colorFive.value))
    uiController.setStatusBarColor(
        color = animatedColorOne.value,
        darkIcons = false
    )
    uiController.setNavigationBarColor(
        color = animatedColorFive.value,
        navigationBarContrastEnforced = true
    )
}

@Preview
@Composable
fun PreviewColorScreen() {
    val colorViewModel = ColorViewModel()
    ColorScreen(colorViewModel)
}