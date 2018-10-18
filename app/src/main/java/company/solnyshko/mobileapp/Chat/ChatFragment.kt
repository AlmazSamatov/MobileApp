package company.solnyshko.mobileapp.Chat

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import company.solnyshko.mobileapp.R.layout.activity_chat
import kotlinx.android.synthetic.main.activity_chat.*

class ChatFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_chat, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val message_adapter = MessageAdapter(activity)
        messages_view.adapter = message_adapter

        message_adapter.add(Message("Hello! What is your problem?", false))

        send_message.setOnClickListener {
            message_adapter.add(Message(editText.text.toString(), true))
            editText.text.clear()
            Handler().postDelayed({
                message_adapter.add(Message("Ok, we try to solve it", false))
            }, 1000)
        }
    }
}