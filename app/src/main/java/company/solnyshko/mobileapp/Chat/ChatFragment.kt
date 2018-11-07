package company.solnyshko.mobileapp.Chat

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import company.solnyshko.mobileapp.API.AddressResponse
import company.solnyshko.mobileapp.API.MessageResponse
import company.solnyshko.mobileapp.API.RequestWithID
import company.solnyshko.mobileapp.API.SendMessageRequest
import company.solnyshko.mobileapp.R.layout.activity_chat
import company.solnyshko.mobileapp.R.layout.mobiframework_custom_spinner
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_info.*
import javax.security.auth.callback.Callback
import kotlin.concurrent.thread

class ChatFragment : Fragment() {
    private val handler = Handler()
    private val delay: Long = 1500
    private var id: String = ""
    private var message_adapter: MessageAdapter? = null
    private var token = ""

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_chat, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        message_adapter = MessageAdapter(activity)
        messages_view.adapter = message_adapter

        val sp = SharedPreferencesWrapper(activity.applicationContext)
        id = sp.getId()
        token = sp.getAccessToken()

        send_message.setOnClickListener {
            val apiService = API.create(token)
            val msg = editText.text.toString()
            thread {
                apiService.sendMessage(SendMessageRequest(id, msg)).execute()
            }
            message_adapter!!.add(Message(msg, true))
            messages_view.setSelection(message_adapter!!.count - 1)
            editText.text.clear()
        }

        handler.postDelayed(object : Runnable {
            override fun run() {
                val apiService = API.create(token)
                apiService.getMessage(RequestWithID(id))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            // on Success
                            it: MessageResponse ->
                            if (it.error == 0) {
                                // true success
                                val list = ArrayList<Message>()

                                for (msg in it.message) {
                                    if (msg.control_operator_id == -1)
                                        list.add(Message(msg.message, true))
                                    else
                                        list.add(Message(msg.message, false))
                                }

                                message_adapter!!.messages = list
                                message_adapter!!.notifyDataSetChanged()

                                messages_view.setSelection(list.lastIndex)
                            }

                        }
                handler.postDelayed(this, delay)
            }
        }, 10)
    }


}