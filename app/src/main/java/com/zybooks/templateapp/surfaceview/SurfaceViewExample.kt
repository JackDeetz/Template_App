package com.zybooks.templateapp.surfaceview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zybooks.templateapp.R

class SurfaceViewExample : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surface_view_example)


    }

    fun surfaceViewCameraButtonClicked(view: View) {
        startActivity(Intent(baseContext, SurfaceViewCameraExample::class.java))}

    fun surfaceViewMediaPlayerButtonClicked(view: View) {
        startActivity(Intent(baseContext, SurfaceViewMediaPlayerExample::class.java))}

}