package com.example.latte.grabpuccino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "HomeActivity";
    private final String REF = "vendors";
    FirebaseListAdapter<Vendor> adapter;
    private DatabaseReference shopDatabase;
    private List<Vendor> vendors;
    private ListView shopListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set database reference
        shopDatabase = FirebaseDatabase.getInstance().getReference(REF);

        //rendering data into ListView
        shopListView = (ListView) findViewById(R.id.shopList);
        getVendorList(shopListView);

        //reading realtime data from Firebase
        getVendorData();

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                String item = vendors.get(position+1).getName();

                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                intent.putExtra("vendorId", position);
                startActivity(intent);
            }
        });

    }

    private void getVendorData() {
        ValueEventListener vendorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get vendor object and use the values to update the UI
                GenericTypeIndicator<List<Vendor>> t = new GenericTypeIndicator<List<Vendor>>() {};
                vendors = dataSnapshot.getValue(t);
                //Log.d(TAG, vendors.get(1).getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting vendor failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        shopDatabase.addValueEventListener(vendorListener);
    }

    @NonNull
    private void getVendorList(ListView listview) {
        //rendering data into ListView
        adapter = new FirebaseListAdapter<Vendor>(this, Vendor.class, R.layout.list_vendor, shopDatabase){
            @Override
            protected void populateView(View v, Vendor model, int position) {
                TextView nameText = (TextView) v.findViewById(R.id.nameOnList);
                TextView detailText = (TextView) v.findViewById(R.id.detailsOnList);
                ImageView iconImage = (ImageView) v.findViewById(R.id.iconOnList);

                nameText.setText(model.getName());
                detailText.setText(model.getDetails());
                String icon = "icon_"+model.getIcon();
                int rID = getResources().getIdentifier(icon, "drawable", getPackageName());
                iconImage.setImageResource(rID);
            }

            @Override
            protected void onDataChanged() {
                //Hide progress bar and show no message if there is no data
                ProgressBar progressBarForList = (ProgressBar) findViewById(R.id.listProgressBar);
                progressBarForList.setVisibility(View.GONE);
                TextView listEmptyText = (TextView) findViewById(R.id.listEmpty);
                shopListView.setEmptyView(listEmptyText);
            }
        };
        listview.setAdapter(adapter);
    }
}
