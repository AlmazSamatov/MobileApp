package company.solnyshko.mobileapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import company.solnyshko.mobileapp.API.OperatorLocation
import company.solnyshko.mobileapp.API.SendMessageRequest
import company.solnyshko.mobileapp.Chat.ChatFragment
import company.solnyshko.mobileapp.Fragments.DestinationFragment
import company.solnyshko.mobileapp.Fragments.InfoFragment
import company.solnyshko.mobileapp.Map.MapsActivity
import company.solnyshko.mobileapp.ParcelList.ParcelListFragment
import company.solnyshko.mobileapp.ParcelList.ParcelListLeaveFragment
import company.solnyshko.mobileapp.util.MyConstants
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import java.net.ProtocolException
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    lateinit var settingsClient: SettingsClient

    lateinit var locationRequest: LocationRequest

    lateinit var locationCallback: LocationCallback

    private var id: Int = 0
    private var token = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragmentManager = fragmentManager.beginTransaction()
            fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
        }

        setBottomNavigationListener()

        bottom_navigation.selectedItemId = R.id.action_info

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()
        createLocationCallBack()
        startLocationUpdates()

        val sp = SharedPreferencesWrapper(this)
        id = sp.getId().toInt()
        token = sp.getAccessToken()

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
                R.id.action_map -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                }
                R.id.action_call -> {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:89991564759"))
                    startActivity(intent)
                }
            }
            true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val currFrag = fragmentManager.findFragmentById(R.id.fragment)
            if (fragmentManager.findFragmentById(R.id.fragment) is DestinationFragment
                    || fragmentManager.findFragmentById(R.id.fragment) is ChatFragment
                    || fragmentManager.findFragmentById(R.id.fragment) is ParcelListFragment
                    || fragmentManager.findFragmentById(R.id.fragment) is ParcelListLeaveFragment) {
                val fragmentManager = fragmentManager.beginTransaction()
                fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    @SuppressLint("MissingPermission")
    fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        settingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = settingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {

            }
        }
    }
    fun createLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                Log.e(TAG, locationResult.toString())
                //TODO: send to the api current location
                val apiService = API.create(token)
                val lat = locationResult.lastLocation.latitude.toString()
                val lon = locationResult.lastLocation.longitude.toString()
                thread {
                    try {
                        apiService.sendLocation(OperatorLocation(id, lat, lon)).execute()
                        Log.e("Thread.error", locationResult.toString())
                    } catch (e: ProtocolException){
                        Log.e(TAG, e.message)
                    }
                }


            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


}
