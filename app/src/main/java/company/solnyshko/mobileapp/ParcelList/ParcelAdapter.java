package company.solnyshko.mobileapp.ParcelList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import company.solnyshko.mobileapp.R;

public class ParcelAdapter extends ArrayAdapter<Parcel> {

    private Context mContext;
    private List<Parcel> parcelsList = new ArrayList<>();

    public ParcelAdapter(@NonNull Context context, int resource, @NonNull List<Parcel> objects) {
        super(context, resource, objects);
        mContext = context;
        parcelsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.parcel_item,parent,false);

        Parcel currentParcel = parcelsList.get(position);

        ImageView icon = (ImageView)listItem.findViewById(R.id.imageView_icon);
        icon.setImageResource(currentParcel.getIcon());

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentParcel.getType());

        CheckBox check = (CheckBox) listItem.findViewById(R.id.checkBoxView_check);
        check.setChecked(currentParcel.isChecked());

        return listItem;
    }
}
