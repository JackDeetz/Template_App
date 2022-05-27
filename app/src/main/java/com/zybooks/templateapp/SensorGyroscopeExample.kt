package com.zybooks.templateapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SensorGyroscopeExample  : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscope: Sensor
    private lateinit var gyroValuesTextView: TextView
    private var gyroSensorExists : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_gyroscope_example)

        gyroValuesTextView = findViewById(R.id.gyroscope_values_text_view)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyroSensorExists = true
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        }
        else
        {
            gyroValuesTextView.text = "No Gyro Sensor"
        }
    }

    override fun onSensorChanged(event: SensorEvent) {

        // Display values in the UI
        val newline = System.getProperty("line.separator")
        val message = "X axis = ${event.values[0]}$newline Y axis = ${event.values[1]}$newline Z axis = ${event.values[2]}"
        gyroValuesTextView.text = message
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nothing to do
    }

    override fun onResume() {
        super.onResume()
        if (gyroSensorExists)
        {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        if (gyroSensorExists)
        {
            sensorManager.unregisterListener(this, gyroscope)
        }
    }
}