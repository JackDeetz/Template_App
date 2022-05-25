package com.zybooks.templateapp

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MotionSensorsExample : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_sensors_example)


    }

    fun accelerometerButtonClicked(view: View) {
        startActivity(Intent(this, AccelerometerExample::class.java))}

    fun compassButtonClicked(view: View) {
        startActivity(Intent(this, CompassExample::class.java))}

    fun gyroscropeButtonClicked(view: View) {
        startActivity(Intent(this, GyroscopeExample::class.java))}

    fun lightSensorButtonClicked(view: View) {
        startActivity(Intent(this, LightSensorExample::class.java))}

    fun proximityButtonClicked(view: View) {
        startActivity(Intent(this, ProximitySensorExample::class.java))}

    fun pressureButtonClicked(view: View) {
        startActivity(Intent(this, PressureSensorExample::class.java))}
}