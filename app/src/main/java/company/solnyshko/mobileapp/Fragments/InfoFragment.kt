package company.solnyshko.mobileapp.Fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import company.solnyshko.mobileapp.R
import company.solnyshko.mobileapp.R.layout.activity_info
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_main.view.*

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(activity_info, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    fun setOnClicks() {
        val fragmentManager = getFragmentManager().beginTransaction()
        destination.setOnClickListener {
            it -> fragmentManager.add(R.id.fragment, DestinationFragment())
        }


    }


}