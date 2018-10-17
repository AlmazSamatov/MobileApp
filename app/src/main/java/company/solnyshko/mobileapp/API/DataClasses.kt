package company.solnyshko.mobileapp.API

data class Response(val error: Int, val access_token: String)
data class LoginBody(val login: String, val password: String)