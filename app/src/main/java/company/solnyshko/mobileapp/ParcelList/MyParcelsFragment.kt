package company.solnyshko.mobileapp.ParcelList

import android.app.Fragment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_parclist.*

class MyParcelsFragment : Fragment(), ParcelListView {
    override fun turnOffProgressBar() {
        progress_bar.visibility = View.GONE
        parcels_list.visibility = View.VISIBLE
    }

    override fun turnOnProgressBar() {
        parcels_list.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()    }

    override fun showParcels(a: ArrayList<Parcel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}