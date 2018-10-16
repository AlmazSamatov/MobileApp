package company.solnyshko.mobileapp.Login

interface LoginPresenterInterface {
    fun login(login: String, password: String)

    fun checkLoggedIn()
}
