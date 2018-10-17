package company.solnyshko.mobileapp.Login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import company.solnyshko.mobileapp.MainActivity
import company.solnyshko.mobileapp.R
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var loginPresenter: LoginPresenter

    override fun turnOffProgressBar() {
        progress_bar.visibility = GONE
        editText.visibility = VISIBLE
        editText2.visibility = VISIBLE
        button2.visibility = VISIBLE
    }

    override fun turnOnProgressBar() {
        progress_bar.visibility = VISIBLE
        editText.visibility = GONE
        editText2.visibility = GONE
        button2.visibility = GONE
    }

    override fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun launchMainActivity(userID: String) {
        val intent = Intent(baseContext, MainActivity::class.java)
        intent.putExtra("userID", userID)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loginPresenter = LoginPresenter(this, this)

    }
}