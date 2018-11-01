package company.solnyshko.mobileapp.Chat

import android.widget.TextView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import company.solnyshko.mobileapp.R


class MessageAdapter(internal var context: Context) : BaseAdapter() {

    internal var messages: MutableList<Message> = ArrayList()

    fun add(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(i: Int): Any {
        return messages[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }


    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View {

        val message = messages[i]


        val view = if (message.isBelongsToCurrentUser) {
            LayoutInflater.from(context).inflate(R.layout.my_message, viewGroup, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.their_message, viewGroup, false)
        }

        val textView: TextView = view.findViewById<TextView>(R.id.message_body)
        textView.text = message.text

        return view

    }

}