package com.zybooks.templateapp


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.zybooks.templateapp.databinding.ActivityLocationExampleBinding

class LocationExample : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityLocationExampleBinding
    private var client: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var updateCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        val bar = findViewById<SeekBar>(R.id.locationSeekBar)
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                googleMap.animateCamera(CameraUpdateFactory.zoomTo((bar.progress.toString() + "f").toFloat()))
                findViewById<TextView>(R.id.seekbarValueDisplay).text = bar.progress.toString()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                trackLocation()
            }

        })

        if (hasLocationPermission()) {
            trackLocation()
        }
    }



    private fun trackLocation() {

        // Create location request
        locationRequest = LocationRequest.create()
            .setInterval(10000)
            .setFastestInterval(500)
            //priority must be high accuracy
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        // Create location callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    updateMap(location)
                }
            }
        }

        client = LocationServices.getFusedLocationProviderClient(this)
    }
    //update map, run schedule based on trackLocation.locationRequest.interval ^^^
    private fun updateMap(location: Location) {

        // Get current location
        val currentLatLng = LatLng(location.latitude,
            location.longitude)

        val geocoder = Geocoder(this)
        val addressList = geocoder.getFromLocation(currentLatLng.latitude, currentLatLng.longitude, 1)
        findViewById<TextView>(R.id.locationAddressTextView).text = addressList[0].getAddressLine(0)

        // Remove previous marker
        googleMap.clear()

        // Place a marker at the current location
        val markerOptions = MarkerOptions()
            .title("Here you are!")
            .position(currentLatLng)
        googleMap.addMarker(markerOptions)

        // Move and zoom to current location at the street level
        val seekbar :SeekBar = findViewById(R.id.locationSeekBar)

        val update: CameraUpdate =
            CameraUpdateFactory.newLatLngZoom(currentLatLng,
                (seekbar.progress.toString() + "f").toFloat()
            )
        googleMap.animateCamera(update)

        val output : TextView = findViewById(R.id.latlongupdateTextView)
        output.text = "Location Update Count: ${updateCount++}" + "\n" + currentLatLng.toString()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    override fun onPause() {
        super.onPause()
        client?.removeLocationUpdates(locationCallback!!)
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (hasLocationPermission()) {
            client?.requestLocationUpdates(
                locationRequest!!, locationCallback!!, Looper.getMainLooper())
        }
    }

    private fun hasLocationPermission(): Boolean {

        // Request fine location permission if not already granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return false
        }
        return true
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            trackLocation()
        }
    }
}