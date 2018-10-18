package com.example.felipehernandez.appmapaejemplo1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.geojson.GeoJsonLayer
import com.google.maps.android.kml.KmlLayer
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator


class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap

    private var Lasalle : LatLng = LatLng(21.1484813,-101.7074495)
    private var Casa : LatLng = LatLng(21.1269137,-101.6523417)

    private var Lista :MutableList<Marcadores> = mutableListOf()

    private var Ubicacion: LatLng? = null

    private var apiClient : GoogleApiClient? = null

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLastLocation: Location? = null


    private var mClusterManager: ClusterManager<Marcador>? = null

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    fun configuracionClusterer(){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(21.1523342,-101.7113132),10f))

        mClusterManager =  ClusterManager<Marcador>(this,mMap)

        agregarMarcadores()
    }

    fun agregarMarcadores(){


        for (i in 0..9) {
            val latLng = LatLng((-34 + i).toDouble(), (21 + i).toDouble())

            println(latLng.latitude)
            println(latLng.longitude)

            mClusterManager!!.addItem(Marcador(latLng.latitude,latLng.longitude,String.format("Marcador #%s",i.toString())))
        }

        mClusterManager!!.addItem(Marcador(-101.70421,21.15255,"Luagr 1"))
        mClusterManager!!.addItem(Marcador(-101.66181,21.1612,"Luagr 2" ))
        mClusterManager!!.addItem(Marcador(-101.67709,21.14855,"Luagr 3"))
        mClusterManager!!.addItem(Marcador(-101.70679,21.12533,"Luagr 4"))
        mClusterManager!!.addItem(Marcador(-101.64602,21.15095,"Luagr 5"))
        mClusterManager!!.addItem(Marcador(-101.68653,21.12789,"Luagr 6"))
        mClusterManager!!.addItem(Marcador(-101.6546,21.1343,"Luagr 7"))
        mClusterManager!!.addItem(Marcador(-101.66181,21.1612,"Luagr 8"))
        mClusterManager!!.addItem(Marcador(-101.69769,21.14391,"Luagr 9"))

        //mClusterManager!!.renderer.onAdd()
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)



        mClusterManager!!.cluster()



       /* mClusterManager!!.setOnClusterClickListener(
                object: ClusterManager.OnClusterClickListener<Marcador> {
                    override fun onClusterClick(cluster:Cluster<Marcador>):Boolean {
                        Toast.makeText(this@MapsActivity, "Cluster click", Toast.LENGTH_SHORT).show()
                        // if true, do not move camera
                        return false
                    }
                })
                */
       /* mClusterManager!!.setOnClusterClickListener {
            Toast.makeText(this@MapsActivity, "Cluster click", Toast.LENGTH_SHORT).show()
            // if true, do not move camera
            false
        }*/

      /*  mClusterManager!!.setOnClusterItemClickListener {
            Toast.makeText(this@MapsActivity, "Cluster item click", Toast.LENGTH_SHORT).show()
            // if true, click handling stops here and do not show info view, do not move camera
            // you can avoid this by calling:
            // renderer.getMarker(clusterItem).showInfoWindow();
            false
        }*/


        mClusterManager!!.markerCollection.setOnInfoWindowAdapter(CustomInfoViewAdapter(LayoutInflater.from(this)))
        mMap.setInfoWindowAdapter(mClusterManager!!.markerManager)


        mClusterManager!!.setOnClusterItemInfoWindowClickListener(
                object:ClusterManager.OnClusterItemInfoWindowClickListener<Marcador> {
                   override fun onClusterItemInfoWindowClick(stringClusterItem:Marcador) {
                        Toast.makeText(this@MapsActivity, "Clicked info window: " + stringClusterItem.title,
                                Toast.LENGTH_SHORT).show()
                    }
                })
        mMap.setOnInfoWindowClickListener(mClusterManager)



        var renderer = CustomClusterRenderers(this,mMap,mClusterManager!!)
        mClusterManager!!.renderer = renderer

        //this.mClusterManager.setRenderer(new MapaFragment.OwnIconRenderer(this, getActivity(), this.map, this.mClusterManager));



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onStart() {
        super.onStart()

         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

             if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                 requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)

             }else{
                 getLastLocation()
             }
         }else {
             getLastLocation()
         }
    }

    fun getLastLocation(){

        //Permisos para android 6>
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                /*mFusedLocationClient!!.lastLocation.addOnCompleteListener(this,(Task<Location>())->{

                        geoLocalizacion()
                })*/



            }
        }

    }


    fun CargarKml(){
        var layer =  KmlLayer(mMap,R.raw.mapa,applicationContext)
        layer.addLayerToMap()
    }

    fun CargarGeoJson(){

        var layer = GeoJsonLayer(mMap,R.raw.mapa1,applicationContext)
        layer.addLayerToMap()

        var layer2 = GeoJsonLayer(mMap,R.raw.mapa2,applicationContext)
        layer2.addLayerToMap()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
       //val sydney = LatLng(-34.0, 151.0)
        val sydney  =  LatLng(21.15255,-101.70421)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in the Salle"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        OpcionesMapa()

        Marcadores()


        CargarGeoJson()

       // CargarKml()

        configuracionClusterer()

        mMap.setOnMarkerClickListener { marker ->
            var valor: String

            valor = if (marker.title == null){
                marker.snippet.toString()
            }else {
                marker.title
            }

            Toast.makeText(
                    this@MapsActivity,
                    ("Marcador pulsado:\n" + valor),
                    Toast.LENGTH_SHORT).show()
            true
        }

        /*
         mMap.setOnMarkerClickListener(object:GoogleMap.OnMarkerClickListener() {
            override fun onMarkerClick(marker:Marker):Boolean {
                Toast.makeText(
                        this@MainActivity,
                        ("Marcador pulsado:\n" + marker.getTitle()),
                        Toast.LENGTH_SHORT).show()
                return true
            }
        })
        * */

    }

    fun OpcionesMapa(){

        mMap.mapType =  GoogleMap.MAP_TYPE_NORMAL

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled =  true
        mMap.uiSettings.isMapToolbarEnabled =  true


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)

            }else{
                mMap.isMyLocationEnabled  =  true
            }
        }else {
            mMap.isMyLocationEnabled  =  true
        }



    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Permisos para android 6>
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.isMyLocationEnabled =  true

                    }
                }
            }
        }
    }


    fun Marcadores(){

        mMap.addMarker(MarkerOptions()
                .position(Lasalle)
                .title("La salle bajio"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Lasalle))


        mMap.addMarker(MarkerOptions()
                .position(Casa)
                .title("Casa"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))

        MarcadoresLista()

        Lista.forEach { obj->
            mMap.addMarker(MarkerOptions()
                    .position(LatLng(obj.Lat,obj.Lng))
                    .title(obj.Titulo))
                    .setIcon(BitmapDescriptorFactory.fromResource(obj.Imagen))
        }

    }


    fun MarcadoresLista(){

        Lista.add(Marcadores("Lugar 1",21.14391, -101.69769,R.drawable.ic_action_name))
        Lista.add(Marcadores("Lugar 2",21.1612, -101.66181,R.drawable.ic_action_name))
        Lista.add(Marcadores("Lugar 3",21.13862, -101.66799,R.drawable.ic_action_name))

    }


    fun geoLocalizacion(localizacion: Location){

        if (localizacion!=null){
            Ubicacion = LatLng(localizacion.latitude,localizacion.longitude)

            mMap.addMarker(MarkerOptions()
                    .position(Ubicacion!!)
                    .title("M ubicacion"))
        }



    }



    class Marcadores constructor(titulo:String, lat:Double,lng:Double,imagen:Int){
                var Titulo : String =  titulo
                var Lat:Double = lat
                var Lng:Double = lng
                var Imagen:Int = imagen
            }



     class Marcador(lng: Double,lat: Double, title: String) : ClusterItem {
        private val mPosition: LatLng = LatLng(lat, lng)
         val title: String = title

        override fun getPosition(): LatLng {
            return mPosition
        }
    }


    class CustomClusterRenderers( mContext: Context, map: GoogleMap,
                                  clusterManager: ClusterManager<MapsActivity.Marcador>) :

            DefaultClusterRenderer<MapsActivity.Marcador>(mContext, map, clusterManager){

        var mContextInternal : Context? = null
        var mClusterIconGenerator : IconGenerator? = null

        init {

            mContextInternal = mContext
            mClusterIconGenerator =  IconGenerator(mContext.applicationContext)
        }

        override fun onBeforeClusterItemRendered(item: MapsActivity.Marcador?,
                                                 markerOptions: MarkerOptions?) {

              var markerDescriptor : BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
              markerOptions!!.icon(markerDescriptor).snippet(item!!.title)

        }

    }


    class CustomInfoViewAdapter(inflater:LayoutInflater):GoogleMap.InfoWindowAdapter {
        private val mInflater: LayoutInflater = inflater

        override fun getInfoWindow(marker: Marker): View {
            val popup = mInflater.inflate(R.layout.infowindow, null)
            (popup.findViewById<TextView>(R.id.lblTitulo) as TextView).text = marker.getSnippet()

            return popup
            //return null;
        }
         override fun getInfoContents(marker:Marker):View {
            val popup = mInflater.inflate(R.layout.infowindow, null)
             (popup.findViewById<TextView>(R.id.lblTitulo) as TextView).text = marker.getSnippet()
            return popup
        }
    }



}
