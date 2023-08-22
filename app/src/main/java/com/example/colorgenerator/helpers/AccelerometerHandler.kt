package com.example.colorgenerator.helpers

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class AccelerometerHandler(
    private val sensorManager: SensorManager
) {
    private var accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    var accelerometerListener: SensorEventListener? = null
    private var mAccel = 0f
    private var mAccelCurrent = SensorManager.GRAVITY_EARTH
    private var mAccelLast = SensorManager.GRAVITY_EARTH

    fun setOnShakeListener(onShake: () -> Unit) {
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

    fun registerListener() {
        sensorManager.registerListener(
            accelerometerListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(
            accelerometerListener,
        )
    }
}