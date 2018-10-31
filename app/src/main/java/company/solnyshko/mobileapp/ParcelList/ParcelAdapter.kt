package company.solnyshko.mobileapp.ParcelList

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

import company.solnyshko.mobileapp.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.createObject


class ParcelAdapter(private val mContext: Context, resource: Int, objects: ArrayList<Parcel>) : ArrayAdapter<Parcel>(mContext, resource, objects), CompoundButton.OnCheckedChangeListener {
    private var parcelsList = ArrayList<Parcel>()
    private val mCheckStates: SparseBooleanArray

    init {
        parcelsList = objects
        mCheckStates = SparseBooleanArray(parcelsList.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var vh = ViewHolder()
        var listItem = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.parcel_item, parent, false)
            vh.name = listItem!!.findViewById(R.id.textView_name)
            vh.icon = listItem!!.findViewById(R.id.imageView_icon)
            vh.isChecked = listItem.findViewById(R.id.checkBoxView_check)
            listItem.tag = vh
        } else {
            vh = listItem.tag as ViewHolder
        }

        val currentParcel = parcelsList[position]

        //ImageView icon = (ImageView)listItem.findViewById(R.id.imageView_icon);
        vh.icon!!.setImageResource(currentParcel.icon)

        //TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        vh.name!!.text = currentParcel.type

        vh.isChecked!!.tag = position
        //CheckBox check = (CheckBox) listItem.findViewById(R.id.checkBoxView_check);
        vh.isChecked!!.isChecked = mCheckStates.get(position, false)
        vh.isChecked!!.setOnCheckedChangeListener(this)

        return listItem
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        mCheckStates.put(buttonView.tag as Int, isChecked)
        if (isChecked) {
//            Observable.fromCallable {
//
//            }.doOnNext { list ->
//                var str = ""
//                list?.map { str += "Parcel type: " + it.type + "; IsChecked: " + it.isChecked + "\n" }
//                println(str)
//            }.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe()
        }
    }

    fun isChecked(position: Int): Boolean {
        return mCheckStates.get(position, false)
    }

    fun setChecked(position: Int, isChecked: Boolean) {
        mCheckStates.put(position, isChecked)

    }

    fun toggle(position: Int) {
        setChecked(position, !isChecked(position))
    }
}

internal class ViewHolder {
    var name: TextView? = null
    var icon: ImageView? = null
    var isChecked: CheckBox? = null
}