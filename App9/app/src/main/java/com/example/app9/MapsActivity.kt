package com.example.app9

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap:GoogleMap? = null
    private val permiso = 1234
    private var mLocationProviderClient:FusedLocationProviderClient? =  null
    private var mLocationRequest:LocationRequest? = null
    private var mLocationCallback:LocationCallback? = null
    private var mCurrentLocation:Location? =  null
    private var mSettingsclient:SettingsClient?  = null
    private var mlocationSettingsRequest:LocationSettingsRequest? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsclient = LocationServices.getSettingsClient(this)
        createLocationCallBack()
        createLocationRequest()
        buildLocationSettingsRequest()
    }

    override fun onMapReady(googleMap:GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        requestLocationUpdates()
    }

    private fun updateLocation() {
        if (mCurrentLocation != null)
        {
            if (marker != null)
            {
                marker!!.remove()
            }
            marker = mMap!!.addMarker(MarkerOptions().position(
                LatLng(mCurrentLocation!!.latitude,
                    mCurrentLocation!!.longitude
                )).title("My Location"))
        }
    }

    private fun updateUI() {
        updateLocation()
    }

    private fun requestLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED)))
            {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), permiso)
            }
            else
            {
                mSettingsclient!!.checkLocationSettings(mlocationSettingsRequest)
                    .addOnSuccessListener(this, object:
                        OnSuccessListener<LocationSettingsResponse> {
                        override fun onSuccess(locationSettingsResponse:LocationSettingsResponse) {
                            if (((ActivityCompat.checkSelfPermission(this@MapsActivity,
                                    Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) && ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED))
                            {
                                return
                            }
                            mLocationProviderClient!!.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper())
                            updateUI()
                        }
                    })
                    .addOnFailureListener(this) {
                        val toast = Toast.makeText(applicationContext, "hubo un problema", Toast.LENGTH_LONG)
                        toast.show()
                        updateUI()
                    }
            }
        }
        else
        {
            mSettingsclient!!.checkLocationSettings(mlocationSettingsRequest)
                .addOnSuccessListener(this, object: OnSuccessListener<LocationSettingsResponse> {
                    override fun onSuccess(locationSettingsResponse:LocationSettingsResponse) {
                        if (ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED)
                        {
                            return
                        }
                        mLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                        updateUI()
                    }
                })
                .addOnFailureListener(this) {
                    val toast = Toast.makeText(applicationContext, "hubo un problema", Toast.LENGTH_LONG)
                    toast.show()
                    updateUI()
                }
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLocationCallBack() {
        mLocationCallback = object:LocationCallback() {
            override fun onLocationResult(locationResult:LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                updateLocation()
            }
        }
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mlocationSettingsRequest = builder.build()
    }

    override fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:IntArray) {
        when (requestCode) {
            1234 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED)
                    {
                        requestLocationUpdates()
                    }
                }
            }
        }
    }
}
