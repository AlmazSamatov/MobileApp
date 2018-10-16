package company.solnyshko.mobileapp.Login

internal interface LoginView {
    fun launchMainActivity(userID: String)

    fun turnOffProgressBar()

    fun turnOnProgressBar()

    fun showError(s: String)
}
