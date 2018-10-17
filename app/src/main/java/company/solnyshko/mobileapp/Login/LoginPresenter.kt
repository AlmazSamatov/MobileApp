package company.solnyshko.mobileapp.Login

import android.content.Context
import android.content.SharedPreferences
import company.solnyshko.mobileapp.API.Response
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

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
            view.turnOffProgressBar()
        } else if (password.trim { it <= ' ' }.isEmpty()) {
            view.showError("Type password")
            view.turnOffProgressBar()
        } else {

            apiService.login(login, password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        // on Success
                        it: Response ->
                        view.turnOffProgressBar()
                        if (it.Error == "0") {
                            // true success
                            saveUserID(it.identifier)
                            view.launchMainActivity(it.identifier)

                        } else {
                            // something is wrong
                            view.showError(it.Error)
                        }

                    }, {
                        // on Error
                        it: Throwable ->
                        view.showError(it.toString())

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
