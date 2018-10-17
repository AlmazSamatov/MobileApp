package company.solnyshko.mobileapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import company.solnyshko.mobileapp.Fragments.InfoFragment
import kotlinx.android.synthetic.main.activity_info.*

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
