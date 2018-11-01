package company.solnyshko.mobileapp.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import company.solnyshko.mobileapp.API.AddressResponse
import company.solnyshko.mobileapp.API.RequestWithID
import company.solnyshko.mobileapp.ParcelList.MyParcelsFragment
import company.solnyshko.mobileapp.ParcelList.ParcelListFragment
import company.solnyshko.mobileapp.ParcelList.ParcelListLeaveFragment
import company.solnyshko.mobileapp.R
import company.solnyshko.mobileapp.R.layout.activity_info
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_info.*
import kotlin.concurrent.thread

class InfoFragment : Fragment() {

    private var errorMsg = ""
    private var isShowFragment = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_info, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNext()

        setOnClicks()
    }

    fun loadNext() {

        val sharedPreferences = SharedPreferencesWrapper(activity)

        if (sharedPreferences.getDestination().isEmpty()){
            showProgressBar()

            val apiService = API.create(sharedPreferences.getAccessToken())

            apiService.nextAddress(RequestWithID(sharedPreferences.getId()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        // on Success
                        it: AddressResponse ->
                        if (it.error == 0) {
                            // true success

                            destination.text = "Destination address: \n ${it.address_to}"

                            thread {
                                sharedPreferences.putDestination(it.address_to)
                                sharedPreferences.putParcelsToDeliver(it.parcels_to_deliver)
                                sharedPreferences.putParcelsToPick(it.parcels_to_pick)
                            }

                        } else {
                            // something is wrong
                            showError("Error code: " + it.error.toString())
                        }

                        showFragment()

                    }, {
                        // on Error
                        it: Throwable ->
                        showError(it.toString())
                        showFragment()

                    })
        }

        showFragment()

    }

    fun showFragment() {
        if (activity == null)
            isShowFragment = true
        else {
            destination.visibility = View.VISIBLE
            parcels_to_pick_up.visibility = View.VISIBLE
            parcels_to_deliver.visibility = View.VISIBLE
            current_parcels.visibility = View.VISIBLE
            val sharedPreferences = SharedPreferencesWrapper(activity)
            if (sharedPreferences.isBreakTime()){
                start_session.visibility = View.VISIBLE
            } else{
                take_break.visibility = View.VISIBLE
                move_to_next.visibility = View.VISIBLE
            }
            progress_bar.visibility = View.GONE
        }
    }

    fun showProgressBar() {
        destination.visibility = View.GONE
        parcels_to_pick_up.visibility = View.GONE
        parcels_to_deliver.visibility = View.GONE
        current_parcels.visibility = View.GONE
        start_session.visibility = View.GONE
        take_break.visibility = View.GONE
        move_to_next.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    fun showError(msg: String) {
        if (activity != null)
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_LONG).show()
        else
            errorMsg = msg
    }

    override fun onAttach(context: Context?) {
        if (errorMsg.isNotEmpty()) {
            Toast.makeText(activity.applicationContext, errorMsg, Toast.LENGTH_LONG).show()
            errorMsg = ""
        }
        if (isShowFragment) {
            showFragment()
            isShowFragment = false
        }
        super.onAttach(context)
    }

    fun setOnClicks() {
        val fragmentManager = fragmentManager.beginTransaction()
        destination.setOnClickListener {
            fragmentManager.replace(R.id.fragment, DestinationFragment()).commit()
        }
        parcels_to_pick_up.setOnClickListener { it ->
            fragmentManager.replace(R.id.fragment, ParcelListFragment()).commit()
        }
        parcels_to_deliver.setOnClickListener { it ->
            fragmentManager.replace(R.id.fragment, ParcelListLeaveFragment()).commit()
        }
        current_parcels.setOnClickListener { it ->
            fragmentManager.replace(R.id.fragment, MyParcelsFragment()).commit()
        }

        start_session.setOnClickListener {
            start_session.visibility = GONE
            take_break.visibility = VISIBLE
            move_to_next.visibility = VISIBLE

            val sharedPreferences = SharedPreferencesWrapper(activity)
            sharedPreferences.setWorkTime()
        }

        take_break.setOnClickListener {
            start_session.visibility = VISIBLE
            take_break.visibility = GONE
            move_to_next.visibility = GONE

            val sharedPreferences = SharedPreferencesWrapper(activity)
            sharedPreferences.setWorkTime()
        }

        move_to_next.setOnClickListener {
            start_session.visibility = VISIBLE
            take_break.visibility = GONE
            move_to_next.visibility = GONE
            val sharedPreferences = SharedPreferencesWrapper(activity)
            sharedPreferences.setBreakTime()
            loadNext()
        }
    }
}