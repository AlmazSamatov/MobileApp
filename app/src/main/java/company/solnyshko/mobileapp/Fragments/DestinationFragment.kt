package company.solnyshko.mobileapp.Fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import company.solnyshko.mobileapp.Map.MapsActivity
import company.solnyshko.mobileapp.R.layout.destination_activity
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import kotlinx.android.synthetic.main.destination_activity.*

class DestinationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(destination_activity, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val sharedPreferences = SharedPreferencesWrapper(activity)

        info.text = sharedPreferences.getDestination()

        show_in_map.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

}