package company.solnyshko.mobileapp.ParcelList

import android.content.Context
import android.widget.ListView
import company.solnyshko.mobileapp.API.Parcels
import company.solnyshko.mobileapp.R
import company.solnyshko.mobileapp.R.id.parcels_list
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ParcelListPresenter internal constructor(internal var view: ParcelListView, context: Context) : ParcelListPresenterInterface {
    private val apiService = API.create()
    private val context = context

    override fun loadParcels() {
        view.turnOnProgressBar()
        apiService.allParcels()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    // on Success
                    it: Parcels ->
                    view.turnOffProgressBar()
                    if (it.error == 0) {
                        // true success )
                        saveParcelsList(it.parcels)

                    } else {
                        // something is wrong
                        view.showError("Error code: " + it.error.toString())
                    }

                }, {
                    // on Error
                    it: Throwable ->
                    view.showError(it.toString())
                    view.turnOffProgressBar()

                })
    }

    fun saveParcelsList(parcelsList : ArrayList<Parcel>){

        val parcelsToAdd = ArrayList<Parcel>()
        parcelsList.forEach {
            if (it.type.toLowerCase().contains("box"))
                parcelsToAdd.add(Parcel(R.drawable.box, it.type, false))
            else if (it.type.toLowerCase().contentEquals("letter"))
                parcelsToAdd.add(Parcel(R.drawable.letter, it.type, false))
            else
                parcelsToAdd.add(Parcel(R.drawable.box, it.type, false))
        }
        view.showParcels(parcelsToAdd)
    }
}