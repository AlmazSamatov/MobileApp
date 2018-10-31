package company.solnyshko.mobileapp.Login

import android.content.Context
import company.solnyshko.mobileapp.API.LoginBody
import company.solnyshko.mobileapp.API.LoginResponse
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginPresenter internal constructor(internal var view: LoginView, context: Context) : LoginPresenterInterface {
    private val sharedPreferences: SharedPreferencesWrapper = SharedPreferencesWrapper(context)
    private val apiService = API.create()

    private val userID: String
        get() = sharedPreferences.getString("userID")

    override fun login(login: String, password: String) {
        if (login.trim { it <= ' ' }.isEmpty()) {
            view.showError("Type login")
        } else if (password.trim { it <= ' ' }.isEmpty()) {
            view.showError("Type password")
        } else {
            view.turnOnProgressBar()
            apiService.login(LoginBody(login, password))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        // on Success
                        it: LoginResponse ->
                        view.turnOffProgressBar()
                        if (it.error == 0) {
                            // true success
                            // TODO: we should save access_token in request_headers (when send requests)
                            // X-Token: <access_token>
                            saveUserID(it.access_token)
                            view.launchMainActivity(it.access_token)

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
    }

    private fun saveUserID(userID: String) {
        sharedPreferences.putString("accessToken", userID)
    }

    override fun checkLoggedIn() {
        val userID = userID
        if (userID.isNotEmpty())
            view.launchMainActivity(userID)
    }
}
