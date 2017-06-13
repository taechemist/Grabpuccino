package com.example.latte.grabpuccino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {

    private String TAG = "MenuActivity";
    private final String REF = "vendors";
    FirebaseListAdapter<Vendor> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView listView = (ListView) findViewById(R.id.coffeeList);
        adapter = new FirebaseListAdapter<Vendor>(this, Vendor.class, R.layout.mylist, FirebaseDatabase.getInstance().getReference(REF)){
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
        };

        listView.setAdapter(adapter);

//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("vendors");
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                JSONObject value = dataSnapshot.getValue(JSONObject.class);
////                Log.d(TAG, value.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

//        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
//        list=(ListView)findViewById(R.id.coffeeList);
//        list.setAdapter(adapter);
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String Slecteditem= itemname[+position];
//                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
//            }
//        });


    }
}
