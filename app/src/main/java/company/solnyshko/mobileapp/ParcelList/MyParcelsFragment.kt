package company.solnyshko.mobileapp.ParcelList

import android.app.ActionBar
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import company.solnyshko.mobileapp.R
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import company.solnyshko.mobileapp.util.random
import kotlinx.android.synthetic.main.activity_parclist.*
import kotlinx.android.synthetic.main.parcel_item.view.*

class MyParcelsFragment : Fragment(), ParcelListView {

    private lateinit var sharedPreferencesWrapper: SharedPreferencesWrapper

    override fun turnOffProgressBar() {
        progress_bar.visibility = View.GONE
        parcels_list.visibility = View.VISIBLE
    }

    override fun turnOnProgressBar() {
        parcels_list.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    override fun showParcels(parcelsToAdd: ArrayList<Parcel>) {
        val listView: ListView = parcels_list
        val mAdapter = ParcelAdapter(activity, R.id.parcels_list, parcelsToAdd)
        listView.setAdapter(mAdapter)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.activity_parclist, null)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesWrapper = SharedPreferencesWrapper(activity)

        showParcels(sharedPreferencesWrapper.getMyParcels() as ArrayList<Parcel>)
    }
}