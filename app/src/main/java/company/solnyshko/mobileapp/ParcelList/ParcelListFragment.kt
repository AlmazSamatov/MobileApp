package company.solnyshko.mobileapp.ParcelList

import android.app.ActionBar
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.parcel_item.view.*
import kotlin.collections.ArrayList
import android.widget.CheckBox
import android.widget.TextView



class ParcelListFragment : Fragment(), ParcelListView {
    override fun showParcels(parcelsToAdd: ArrayList<Parcel>) {
        val listView: ListView = parcels_list
        val mAdapter = ParcelAdapter(activity, R.id.parcels_list, parcelsToAdd)
        listView.setAdapter(mAdapter)
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemIdAtPosition(position)
            val isChecked = view.checkBoxView_check
        }
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

        parcelPresenter = ParcelListPresenter(this, activity)
        parcelPresenter.loadParcels()

    }
}