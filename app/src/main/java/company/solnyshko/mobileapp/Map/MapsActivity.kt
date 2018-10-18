package company.solnyshko.mobileapp.Map

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import company.solnyshko.mobileapp.Chat.ChatFragment
import company.solnyshko.mobileapp.Fragments.InfoFragment
import company.solnyshko.mobileapp.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setBottomNavigationListener()
    }

    @SuppressLint("MissingPermission")
    fun setBottomNavigationListener() {

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragmentManager = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.action_info -> {
                    fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
                }
                R.id.action_chat -> {
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

        // Add a marker in Sydney and move the camer
        val latLngOrigin = LatLng(55.781744, 49.139601) // Ayala
        val latLngDestination = LatLng(55.753051, 48.743203) // SM City
        mMap.addMarker(MarkerOptions().position(latLngOrigin).title("A"))
        mMap.addMarker(MarkerOptions().position(latLngDestination).title("B"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin, 14.5f))

        val path: MutableList<List<LatLng>> = ArrayList()
        val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=55.781744,49.139601&destination=55.753051,48.743203&key=AIzaSyA24mwjp2WEz31XDV5DGl-EBQe4ceFbnzc"


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
    }
}
