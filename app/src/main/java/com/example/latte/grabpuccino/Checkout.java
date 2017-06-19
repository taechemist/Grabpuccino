package com.example.latte.grabpuccino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Checkout extends AppCompatActivity {

    private String TAG = "checkoutActivity";
    private DatabaseReference menuDatabase;
    private String REF = "menus";
    private Coffee menu;
    private TextView coffeeName;
    private TextView coffeeDescription;
    private ImageView coffeeImage;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        int vendorId = intent.getIntExtra("vendorId", 0);
        int menuId = intent.getIntExtra("menuId", 0);
        String vendorIdString = Integer.toString(vendorId);
        String menuIdString = Integer.toString(menuId);

        menuDatabase = FirebaseDatabase.getInstance().getReference(REF).child(vendorIdString).child(menuIdString);
        getMenuData();

        coffeeName = (TextView) findViewById(R.id.coffeeName);
        coffeeDescription = (TextView) findViewById(R.id.coffeeLocation);
        coffeeImage = (ImageView) findViewById(R.id.coffeeImage);
        statusText = (TextView) findViewById(R.id.statusText);
    }

    private void getMenuData() {
        ValueEventListener vendorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get vendor object and use the values to update the UI
                menu = dataSnapshot.getValue(Coffee.class);

                coffeeName.setText("Your order is: "+menu.getName());
                coffeeDescription.setText("$"+menu.getPrice());
                int rID = getResources().getIdentifier("icon_"+menu.getIcon(), "drawable", getPackageName());
                coffeeImage.setImageResource(rID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting vendor failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        menuDatabase.addValueEventListener(vendorListener);
    }
}
