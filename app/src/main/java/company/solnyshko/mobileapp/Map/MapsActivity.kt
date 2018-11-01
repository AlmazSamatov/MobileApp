package company.solnyshko.mobileapp.Map

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import company.solnyshko.mobileapp.Chat.ChatFragment
import company.solnyshko.mobileapp.Fragments.InfoFragment
import company.solnyshko.mobileapp.R
import kotlinx.android.synthetic.main.activity_maps.*
import org.json.JSONObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(p0: Marker?) = false

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setBottomNavigationListener()
        bottom_navigation.selectedItemId = R.id.action_map
    }

    @SuppressLint("MissingPermission")
    fun setBottomNavigationListener() {

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragmentManager = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.action_map -> {
                    if (!map.isVisible) {
                        map.view?.visibility = VISIBLE
                        fragment.visibility = GONE
                    }
                }
                R.id.action_info -> {
                    map.view?.visibility = GONE
                    fragment.visibility = VISIBLE
                    fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
                }
                R.id.action_chat -> {
                    map.view?.visibility = GONE
                    fragment.visibility = VISIBLE
                    fragmentManager.replace(R.id.fragment, ChatFragment()).commit()
                }
                R.id.action_call -> {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:89991564759"))
                    startActivity(intent)
                }
            }
            true
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)
        setUpMap()



    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        // 1
        mMap.isMyLocationEnabled = true


// 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)

                var myLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)

                val latLngDestination = LatLng(55.753051, 48.743203) // SM City

                mMap.addMarker(MarkerOptions().position(myLatLng).title("Current Position"))
                mMap.addMarker(MarkerOptions().position(latLngDestination).title("Destination"))



                val path: MutableList<List<LatLng>> = ArrayList()
                val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin="+myLatLng.latitude.toString()+","+myLatLng.longitude.toString()+"&destination=55.753051,48.743203&key=AIzaSyA24mwjp2WEz31XDV5DGl-EBQe4ceFbnzc"


                val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, Response.Listener<String> {
                    response ->
                    val jsonResponse = JSONObject(response)
                    // Get routes
                    val routes = jsonResponse.getJSONArray("routes")
                    val legs = routes.getJSONObject(0).getJSONArray("legs")
                    val steps = legs.getJSONObject(0).getJSONArray("steps")
                    for (i in 0 until steps.length()) {
                        val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                        path.add(PolyUtil.decode(points))
                    }
                    for (i in 0 until path.size) {

                        mMap.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))

                    }
                }, Response.ErrorListener {
                    _ ->
                }){}
                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(directionsRequest)


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 12.0f))


            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
