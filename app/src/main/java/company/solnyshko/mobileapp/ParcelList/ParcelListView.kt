package company.solnyshko.mobileapp.ParcelList

internal interface ParcelListView {

    fun turnOffProgressBar()

    fun turnOnProgressBar()

    fun showError(s: String)

    fun showParcels(a: ArrayList<Parcel>)
}
