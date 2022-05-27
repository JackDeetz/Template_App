package com.zybooks.templateapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SensorProximityExample  : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var proximity: Sensor
    private lateinit var proximityValuesTextView: TextView
    private var proximitySensorExists : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_proximity_example)

        proximityValuesTextView = findViewById(R.id.proximity_values_text_view)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensorExists = true
            proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        }
        else
        {
            proximityValuesTextView.text = "No proximity Sensor"
        }
    }

    override fun onSensorChanged(event: SensorEvent) {

        // Display values in the UI
        val newline = System.getProperty("line.separator")
        val message = "X axis = ${event.values[0]}"
        proximityValuesTextView.text = message
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nothing to do
    }

    override fun onResume() {
        super.onResume()
        if (proximitySensorExists)
        {
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        if (proximitySensorExists)
        {
            sensorManager.unregisterListener(this, proximity)
        }
    }
}