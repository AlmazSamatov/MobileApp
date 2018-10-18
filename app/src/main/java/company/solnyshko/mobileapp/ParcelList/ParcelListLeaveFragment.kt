package company.solnyshko.mobileapp.ParcelList

import android.app.ActionBar
import android.app.Fragment
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
import kotlinx.android.synthetic.main.activity_parclist.*
import kotlin.collections.ArrayList

class ParcelListLeaveFragment : Fragment(), ParcelListView {
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var actionBar = (getActivity() as AppCompatActivity).supportActionBar
        actionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE or ActionBar.DISPLAY_SHOW_CUSTOM)
        actionBar.setCustomView(R.layout.custom_action_bar)

        val listView: ListView = parcels_list
        val parcelsList = ArrayList<Parcel>()
        parcelsList.add(Parcel(R.drawable.box, "Small box 20x20", false))
        parcelsList.add(Parcel(R.drawable.letter, "Letter 10x10", false))
        parcelsList.add(Parcel(R.drawable.box, "Box 120x120", false))
        parcelsList.add(Parcel(R.drawable.letter, "Letter 25x60", false))
        parcelsList.add(Parcel(R.drawable.box, "Red box", false))
        parcelsList.add(Parcel(R.drawable.box, "Small white box", false))
        val mAdapter = ParcelAdapter(activity, R.id.parcels_list, parcelsList)
        listView.setAdapter(mAdapter)
    }
}