package company.solnyshko.mobileapp.Login

import android.content.Context
import company.solnyshko.mobileapp.API.LoginBody
import company.solnyshko.mobileapp.API.LoginResponse
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginPresenter internal constructor(internal var view: LoginView, context: Context) : LoginPresenterInterface {
    val sharedPreferences: SharedPreferencesWrapper = SharedPreferencesWrapper(context)
    private val apiService = API.create()

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
                            sharedPreferences.putAccessToken(it.access_token)
                            sharedPreferences.putId(it.id)
                            view.launchMainActivity(it.access_token, it.id)

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

    override fun checkLoggedIn() {
        val userID = sharedPreferences.getId()
        val token = sharedPreferences.getAccessToken()
        if (userID.isNotEmpty() && token.isNotEmpty())
            view.launchMainActivity(token, userID)
    }


}
