package company.solnyshko.mobileapp.Chat

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import company.solnyshko.mobileapp.R.layout.activity_chat
import company.solnyshko.mobileapp.util.SharedPreferencesWrapper
import kotlinx.android.synthetic.main.activity_chat.*

class ChatFragment : Fragment() {
    private val handler = Handler()
    private val delay: Long = 1500

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_chat, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val message_adapter = MessageAdapter(activity)
        messages_view.adapter = message_adapter

        message_adapter.add(Message("Hello! What is your problem?", false))

        val sp = SharedPreferencesWrapper(activity.applicationContext)
        val apiService = API.create(sp.getAccessToken())

        send_message.setOnClickListener {
            message_adapter.add(Message(editText.text.toString(), true))
            editText.text.clear()
        }

//        handler.postDelayed(Runnable {
//            apiService.send
//            handler.postDelayed(this, delay)
//        }, delay)
    }


}