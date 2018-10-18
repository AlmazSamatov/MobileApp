package company.solnyshko.mobileapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import company.solnyshko.mobileapp.Fragments.DestinationFragment
import android.view.View
import company.solnyshko.mobileapp.Fragments.InfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_action_bar.*

class MainActivity : AppCompatActivity() {

    lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userID = intent.extras.getString("userID")

        if (savedInstanceState == null) {
            val fragmentManager = fragmentManager.beginTransaction()
            fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
        }

        setBottomNavigationListener()
    }

    fun cameraOnClick(v: View){
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent, 0)
    }

    @SuppressLint("MissingPermission")
    fun setBottomNavigationListener() {

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragmentManager = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.action_info -> {
                    fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
                }
                //R.id.action_map ->
                //R.id.action_chat ->
                R.id.action_call -> {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("89991564759"))
                    startActivity(intent)
                }
            }
            true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val currFrag = fragmentManager.findFragmentById(R.id.fragment)
            if (fragmentManager.findFragmentById(R.id.fragment) is DestinationFragment) {
                val fragmentManager = fragmentManager.beginTransaction()
                fragmentManager.replace(R.id.fragment, InfoFragment()).commit()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
