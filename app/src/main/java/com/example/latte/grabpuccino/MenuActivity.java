package com.example.latte.grabpuccino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class MenuActivity extends AppCompatActivity {

    private String TAG = "MenuActivity";
    private final String REF = "menus";
    FirebaseListAdapter<Coffee> adapter;
    private DatabaseReference coffeeDatabase;
    private List<Coffee> menus;
    private ListView menuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        int vendorId = intent.getIntExtra("vendorId", -1);
        String vendorIdText = Integer.toString(vendorId);

        coffeeDatabase = FirebaseDatabase.getInstance().getReference(REF).child(vendorIdText);
        getMenuData();

        menuListView = (ListView) findViewById(R.id.coffeeList);
        getMenuList(menuListView);

    }

    private void getMenuData() {
        ValueEventListener vendorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get vendor object and use the values to update the UI
                GenericTypeIndicator<List<Coffee>> t = new GenericTypeIndicator<List<Coffee>>() {};
                menus = dataSnapshot.getValue(t);
//                Log.d(TAG, menus.get(1).getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting vendor failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        coffeeDatabase.addValueEventListener(vendorListener);
    }

    @NonNull
    private void getMenuList(ListView listview) {
        //rendering data into ListView
        adapter = new FirebaseListAdapter<Coffee>(this, Coffee.class, R.layout.list_coffee, coffeeDatabase) {
            @Override
            protected void populateView(View v, Coffee model, int position) {
                TextView nameText = (TextView) v.findViewById(R.id.nameOnList);
                TextView detailText = (TextView) v.findViewById(R.id.detailsOnList);
                ImageView iconImage = (ImageView) v.findViewById(R.id.iconOnList);

                nameText.setText(model.getName());
                detailText.setText(Double.toString(model.getPrice()));
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
                menuListView.setEmptyView(listEmptyText);
            }
        };
        listview.setAdapter(adapter);
    }
}
