package com.agilie.agmobilegiftinterface.gravity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

internal class GravitySensorListener(context: Context, var gravityDeltaX: Float, var gravityDeltaY: Float, var minUpdateDelta: Int) : SensorEventListener {

    interface onGravityListener {
        fun onGravity(x: Float, y: Float)
    }

    var gravityListener: onGravityListener? = null

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    var lastGravityX = 0.0f
    var lastGravityY = 0.0f
    var lastUpdateTime: Long = 0

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastUpdateTime < minUpdateDelta) {
                return
            }

            if (Math.abs(lastGravityX - event.values[0]) < gravityDeltaX || Math.abs(lastGravityY - event.values[1]) < gravityDeltaY) {
                return
            }

            lastGravityX = event.values[0]
            lastGravityY = event.values[1]
            lastUpdateTime = currentTime
            gravityListener?.onGravity(lastGravityX, lastGravityY)
        }
    }

    fun onStopSensor() {
        sensorManager.unregisterListener(this)
    }

    fun onResumeSensor() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }
}