package company.solnyshko.mobileapp.Login

import android.content.Context
import android.content.SharedPreferences
import company.solnyshko.mobileapp.API.LoginBody
import company.solnyshko.mobileapp.API.Response
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlin.math.log

class LoginPresenter internal constructor(internal var view: LoginView, context: Context) : LoginPresenterInterface {
    private val sharedPreferences: SharedPreferences
    private val NAME_OF_SHARED_PREF = "userData"
    private val apiService = API.create()

    private val userID: String
        get() = sharedPreferences.getString("userID", "")

    init {
        sharedPreferences = context.getSharedPreferences(NAME_OF_SHARED_PREF, Context.MODE_PRIVATE)
    }

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
                        it: Response ->
                        view.turnOffProgressBar()
                        if (it.error == 0) {
                            // true success
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
        val editor = sharedPreferences.edit()
        editor.putString("userID", userID)
        editor.apply()
    }

    override fun checkLoggedIn() {
        val userID = userID
        if (userID.isNotEmpty())
            view.launchMainActivity(userID)
    }
}
