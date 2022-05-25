package com.zybooks.templateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SensorsExample : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors_example)


    }

    fun accelerometerButtonClicked(view: View) {
        startActivity(Intent(this, SensorAccelerometerExample::class.java))}

    fun compassButtonClicked(view: View) {
        startActivity(Intent(this, SensorCompassExample::class.java))}

    fun gyroscropeButtonClicked(view: View) {
        startActivity(Intent(this, SensorGyroscopeExample::class.java))}

    fun lightSensorButtonClicked(view: View) {
        startActivity(Intent(this, SensorLightExample::class.java))}

    fun proximityButtonClicked(view: View) {
        startActivity(Intent(this, SensorProximityExample::class.java))}

    fun pressureButtonClicked(view: View) {
        startActivity(Intent(this, SensorPressureExample::class.java))}
}