package company.solnyshko.mobileapp.ParcelList

import android.app.ActionBar
import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import company.solnyshko.mobileapp.API.Parcels
import company.solnyshko.mobileapp.R
import company.solnyshko.mobileapp.R.layout.activity_parclist
import company.solnyshko.mobileapp.util.MyConstants
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import company.solnyshko.mobileapp.util.random
import kotlinx.android.synthetic.main.activity_parclist.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import java.util.*
import kotlin.collections.ArrayList

class ParcelListLeaveFragment : Fragment(), ParcelListView {
    private lateinit var sharedPreferencesWrapper: SharedPreferencesWrapper

    override fun showParcels(parcelsToAdd: ArrayList<Parcel>) {
        val listView: ListView = parcels_list
        val mAdapter = ParcelAdapter(activity, R.id.parcels_list, parcelsToAdd)
        listView.setAdapter(mAdapter)
    }

    private lateinit var parcelPresenter: ParcelListPresenter

    override fun turnOffProgressBar() {
        progress_bar.visibility = GONE
        parcels_list.visibility = VISIBLE
    }

    override fun turnOnProgressBar() {
        parcels_list.visibility = GONE
        progress_bar.visibility = VISIBLE
    }

    override fun showError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_parclist, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            var index = (0 until parcels_list.adapter.count).random()

            val parcel = parcels_list.adapter.getItem(index) as Parcel

            (parcels_list.adapter as ParcelAdapter).setChecked(index, true)

            (parcels_list.adapter as ParcelAdapter).notifyDataSetChanged()

            sharedPreferencesWrapper.deleteFromParcelToDeliver(parcel)

            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesWrapper = SharedPreferencesWrapper(activity)
        var actionBar = (getActivity() as AppCompatActivity).supportActionBar
        actionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE or ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar.setCustomView(R.layout.custom_action_bar)

        activity.camera.setOnClickListener {
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(intent, 0)
        }

        showParcels(sharedPreferencesWrapper.getParcelsToDeliver() as ArrayList<Parcel>)
    }

    override fun onDestroy() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE)
        super.onDestroy()
    }

}