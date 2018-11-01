package company.solnyshko.mobileapp.Login

internal interface LoginView {
    fun launchMainActivity(token: String, id: String)

    fun turnOffProgressBar()

    fun turnOnProgressBar()

    fun showError(s: String)
}
