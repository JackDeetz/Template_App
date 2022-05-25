package com.zybooks.templateapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SensorPressureExample  : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var pressure: Sensor
    private lateinit var pressureValuesTextView: TextView
    private var pressureSensorExists : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_pressure_example)

        pressureValuesTextView = findViewById(R.id.pressure_values_text_view)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            pressureSensorExists = true
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        }
        else
        {
            pressureValuesTextView.text = "No proximity Sensor"
        }
    }

    override fun onSensorChanged(event: SensorEvent) {

        // Display values in the UI
        val newline = System.getProperty("line.separator")
        val message = "X axis = ${event.values[0]}"
        pressureValuesTextView.text = message;
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nothing to do
    }

    override fun onResume() {
        super.onResume()
        if (pressureSensorExists)
        {
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        if (pressureSensorExists)
        {
            sensorManager.unregisterListener(this, pressure)
        }
    }
}