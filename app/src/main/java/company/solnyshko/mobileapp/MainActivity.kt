package company.solnyshko.mobileapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
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
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragmentManager = fragmentManager.beginTransaction()
            fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
        }

        setBottomNavigationListener()

        bottom_navigation.selectedItemId = R.id.action_info
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

}
