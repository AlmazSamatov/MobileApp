package company.solnyshko.mobileapp.ParcelList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import company.solnyshko.mobileapp.R;

public class ParcelListActivity extends Activity {
    private ListView listView;
    private ParcelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parclist);


        listView = (ListView) findViewById(R.id.parcels_list);
        ArrayList<Parcel> parcelsList = new ArrayList<>();
        parcelsList.add(new Parcel(R.drawable.box, "Small box 20x20", false));
        parcelsList.add(new Parcel(R.drawable.letter, "Letter 10x10", false));
        parcelsList.add(new Parcel(R.drawable.box, "Box 120x120", false));
        parcelsList.add(new Parcel(R.drawable.letter, "Letter 25x60", false));
        parcelsList.add(new Parcel(R.drawable.box, "Red box", false));
        parcelsList.add(new Parcel(R.drawable.box, "Small white box", false));

        mAdapter = new ParcelAdapter(this, R.id.parcels_list, parcelsList);
        listView.setAdapter(mAdapter);
    }
}
