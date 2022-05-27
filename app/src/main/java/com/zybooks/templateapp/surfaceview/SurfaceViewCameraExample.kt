package com.zybooks.templateapp.surfaceview

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zybooks.templateapp.R
import com.zybooks.templateapp.databinding.ActivitySurfaceViewCameraExampleBinding
import org.w3c.dom.Text
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException



class SurfaceViewCameraExample : AppCompatActivity(), SurfaceHolder.Callback, Camera.PictureCallback {

    private lateinit var binding: ActivitySurfaceViewCameraExampleBinding

    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null
    private var frontOrBack : Int = 0

    private val neededPermissions = arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurfaceViewCameraExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = checkPermission()
        if (result) {
            setupSurfaceHolder()
        }
    }

    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()
            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size > 0) {
                var shouldShowAlert = false
                for (permission in permissionsNotGranted) {
                    shouldShowAlert =
                        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                }

                val arr = arrayOfNulls<String>(permissionsNotGranted.size)
                val permissions = permissionsNotGranted.toArray(arr)
                if (shouldShowAlert) {
                    showPermissionAlert(permissions)
                } else {
                    requestPermissions(permissions)
                }
                return false
            }
        }
        return true
    }

    private fun showPermissionAlert(permissions: Array<String?>) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.permission_required)
        alertBuilder.setMessage(R.string.permission_message)
        alertBuilder.setPositiveButton(R.string.yes) { _, _ -> requestPermissions(permissions) }
        val alert = alertBuilder.create()
        alert.show()
    }

    private fun requestPermissions(permissions: Array<String?>) {
        ActivityCompat.requestPermissions(this@SurfaceViewCameraExample, permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(
                            this@SurfaceViewCameraExample,
                            R.string.permission_warning,
                            Toast.LENGTH_LONG
                        ).show()
                        binding.showPermissionMsg.visibility = View.VISIBLE
                        return
                    }
                }

                setupSurfaceHolder()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupSurfaceHolder() {
        binding.startBtn.visibility = View.VISIBLE
        binding.surfaceView.visibility = View.VISIBLE

        surfaceHolder = binding.surfaceView.holder
        binding.surfaceView.holder.addCallback(this)
        setBtnClick()
    }

    private fun setBtnClick() {
        binding.startBtn.setOnClickListener { captureImage() }
        binding.switchCameraButton.setOnClickListener {
            switchCamera()
            findViewById<TextView>(R.id.cameraInUseTextView).text = when (frontOrBack) {
                                                                         0 -> "Back"
                                                                         1 -> "Font"
                                                                        else -> "Other"
            }
        }
    }

    private fun switchCamera() {
        frontOrBack++
        if (frontOrBack > Camera.getNumberOfCameras() - 1)
        {
            frontOrBack = 0
        }
        releaseCamera()
        startCamera()
    }

    private fun captureImage() {
        if (camera != null) {
            camera!!.takePicture(null, null, this)
        }
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        startCamera()
    }

    private fun startCamera() {
        camera = Camera.open(frontOrBack)
        camera!!.setDisplayOrientation(90)
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        resetCamera()
    }

    private fun resetCamera() {
        if (surfaceHolder!!.surface == null) {
            // Return if preview surface does not exist
            return
        }

        // Stop if preview surface is already running.
        camera!!.stopPreview()
        try {
            // Set preview display
            camera!!.setPreviewDisplay(surfaceHolder)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Start the camera preview...
        camera!!.startPreview()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        releaseCamera()
    }

    private fun releaseCamera() {
        camera!!.stopPreview()
        camera!!.release()
        camera = null
    }

    override fun onPictureTaken(bytes: ByteArray, camera: Camera) {
        saveImage(bytes)
        resetCamera()
    }

    private fun saveImage(bytes: ByteArray) {
        val outStream: FileOutputStream
        try {
            val fileName = "TUTORIALWING_" + System.currentTimeMillis() + ".jpg"
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                fileName
            )
            outStream = FileOutputStream(file)
            outStream.write(bytes)
            outStream.close()
            Toast.makeText(this@SurfaceViewCameraExample, "Picture Saved: $fileName", Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}
