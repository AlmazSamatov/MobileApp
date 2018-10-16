package company.solnyshko.mobileapp.Login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import company.solnyshko.mobileapp.R

class LoginActivity : AppCompatActivity(), LoginView {
    override fun turnOffProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun turnOnProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(s: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchMainActivity(userID: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }
}