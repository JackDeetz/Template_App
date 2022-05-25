package com.zybooks.templateapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class SensorCompassExample : AppCompatActivity(), SensorEventListener {

    private lateinit var compassImage: ImageView
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var magneticField: Sensor
    private var accelValues: FloatArray? = null
    private var magneticValues: FloatArray? = null
    private val rotationMatrix = FloatArray(9)
    private val orientation = FloatArray(3)
    private var magneticSensorExists : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_compass_example)

        // Image has north facing up
        compassImage = findViewById(R.id.compass_image)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
        {
            magneticSensorExists = true
            magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        }
        else
        {
            val results : TextView = findViewById(R.id.gyroscope_success_textview)
            results.text = "No Magneticfield Sensor"
        }
    }

    override fun onSensorChanged(event: SensorEvent) {

        // Get sensor data
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            accelValues = event.values
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticValues = event.values
        }

        // Make sure both values have been obtained
        if (accelValues != null && magneticValues != null) {

            // Compute rotation matrix
            if (SensorManager.getRotationMatrix(rotationMatrix, null,
                    accelValues, magneticValues)) {

                // Compute orientation values
                SensorManager.getOrientation(rotationMatrix, orientation)

                // Convert azimuth rotation angle from radians to degrees
                val azimuthAngle = Math.toDegrees(orientation[0].toDouble()).toFloat()

                // Rotate the image
                compassImage.rotation = -azimuthAngle
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Nothing to do
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        if (magneticSensorExists)
        {
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)   // All sensors
    }
}