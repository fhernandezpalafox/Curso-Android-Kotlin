package com.example.felipehernandez.appmapas1


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
                                          LocationListener,
                                          GoogleMap.OnInfoWindowClickListener,
                                          GoogleMap.OnMarkerClickListener{
    //Globales
    private lateinit var map: GoogleMap
    var markerLocation : MarkerOptions? = null

    private var Lasalle : LatLng = LatLng(21.1484813,-101.7074495)
    private var Casa : LatLng = LatLng(21.1269137,-101.6523417)

    private lateinit var locationManager: LocationManager

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    private lateinit var provider:String


    override fun onLocationChanged(location: Location?) {

        var lat: Double
        var lng: Double

        lat = location!!.latitude
        lng =  location!!.longitude

        if (markerLocation == null)
        {
            markerLocation = MarkerOptions()
            markerLocation!!.position(LatLng(lat,lng))
            markerLocation!!.title("Mi Posicion")
            map.addMarker(markerLocation)
        }else {
            markerLocation!!.position(LatLng(lat,lng))
        }

        CameraPosition.builder().target(LatLng(lat,lng))

        var cameraupdate : CameraUpdate
        cameraupdate  =  CameraUpdateFactory.newLatLngZoom(LatLng(lat,lng),15f)
        map.moveCamera(cameraupdate)





    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }


    override fun onInfoWindowClick(p0: Marker?) {
            Toast.makeText(this, p0!!.title, Toast.LENGTH_LONG).show()

            println("Entro en infowindow click")

    }


    override fun onMarkerClick(p0: Marker?): Boolean {

            Toast.makeText(this, p0!!.title, Toast.LENGTH_LONG).show()

            println("Entro en marker click")

        return true

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        Localizacion()
    }


    fun Localizacion(){



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)

            }else{
                locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                provider =  locationManager.getBestProvider(Criteria(),false)

                var location  =  locationManager.getLastKnownLocation(provider)

                if (location ==  null){
                    println("no Localizacion")
                }
                locationManager.requestLocationUpdates(provider!!,8000,
                        1f,this)
            }
        }else {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            provider =  locationManager.getBestProvider(Criteria(),false)

            var location  =  locationManager.getLastKnownLocation(provider)

            if (location ==  null){
                println("no Localizacion")
            }
            locationManager.requestLocationUpdates(provider!!,8000,
                    1f,this)
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        Marcadores()

        OpcionesMapa()

       // map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)
    }


    //Para comprobar si tienes un permiso, llama al método ContextCompat.checkSelfPermission().


    fun OpcionesMapa(){
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled =  true

        map.uiSettings.isMyLocationButtonEnabled = true

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)

            }else{
                map.isMyLocationEnabled  =  true
            }
        }else {
            map.isMyLocationEnabled  =  true
        }

    }


    //url: https://developer.android.com/training/permissions/requesting.html?hl=es-419
    //https://developer.android.com/training/permissions/requesting.html?hl=es-419
    //https://developer.android.com/guide/topics/security/permissions.html?hl=es-419#normal-dangerous

    /*
     Si tu app todavía no tiene el permiso que necesita, debe llamar a uno de los métodos requestPermissions()
     para solicitar los permisos correspondientes. Tu app pasa los permisos que necesita y también un código de solicitud
     de entero que tú especificas para identificar esta solicitud de permiso. Este método funciona de manera asincrónica:
     realiza la devolución inmediatamente y, cuando el usuario responde al cuadro de diálogo, el sistema
     llama al método callback de la app con los resultados y pasa el mismo código de solicitud que la app le pasó a
     requestPermissions().
     */


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Permisos para android 6>
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        map.isMyLocationEnabled =  true

                        locationManager.requestLocationUpdates(provider!!,8000,
                                1f,this)

                    }
                }
            }
        }
    }


    fun Marcadores(){

        // Add a marker in Sydney and move the camera
        val lasalleBajio = LatLng( 21.1523342, -101.7113132)
        map.addMarker(MarkerOptions().position(lasalleBajio).title("La salle bajio"))
        map.moveCamera(CameraUpdateFactory.newLatLng(lasalleBajio))


        map.addMarker(MarkerOptions()
                .position(Casa)
                .title("Casa"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
    }
}

