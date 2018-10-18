package company.solnyshko.mobileapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import company.solnyshko.mobileapp.Fragments.InfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_action_bar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            val fragmentManager = fragmentManager.beginTransaction()
            fragmentManager.replace(R.id.fragment, InfoFragment())
            fragmentManager.commit()
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
                    fragmentManager.replace(R.id.fragment, InfoFragment())
                    fragmentManager.commit()
                }
                //R.id.action_map ->
                //R.id.action_chat ->
                R.id.action_call -> {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:89991564759"))
                    startActivity(intent)
                }
            }
            true
        }
    }
}
