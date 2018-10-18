package company.solnyshko.mobileapp.Fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import company.solnyshko.mobileapp.R
import company.solnyshko.mobileapp.R.layout.activity_info
import kotlinx.android.synthetic.main.activity_info.*

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_info, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    fun setOnClicks() {
        val fragmentManager = fragmentManager.beginTransaction()
        destination.setOnClickListener {
            fragmentManager.replace(R.id.fragment, DestinationFragment()).commit()
        }

        start_session.setOnClickListener {
            start_session.visibility = GONE
            take_break.visibility = VISIBLE
            move_to_next.visibility = VISIBLE
        }

        take_break.setOnClickListener {
            start_session.visibility = VISIBLE
            take_break.visibility = GONE
            move_to_next.visibility = GONE
        }

        move_to_next.setOnClickListener {
            start_session.visibility = VISIBLE
            take_break.visibility = GONE
            move_to_next.visibility = GONE
        }


    }

}