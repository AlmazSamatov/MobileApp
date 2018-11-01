package company.solnyshko.mobileapp.API
import company.solnyshko.mobileapp.ParcelList.Parcel

data class LoginResponse(val error: Int, val access_token: String, val id: String)
data class LoginBody(val login: String, val password: String)
data class Parcels(val error: Int, val remark : String, val parcels: ArrayList<Parcel>, val id: String)
data class AddressResponse(val error: Int, val address_to: String, val parcels_to_pick: List<Parcel>,
                           val parcels_to_deliver: List<Parcel>)
data class RequestWithID(val delivery_operator_id: String)
data class SendMessageRequest(val delivery_operator_id: String, val message: String, val control_operator_id: Int = -1)
data class MessageResponse(val error: Int, val message: List<Message>)
data class Message(val control_operator_id: Int, val send_time: String, val message: String)
data class Response(val error: Int)