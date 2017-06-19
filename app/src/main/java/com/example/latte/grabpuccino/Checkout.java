package com.example.latte.grabpuccino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String vendorIdString;
    private String menuIdString;
    private Button serveBtn;
    private Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        int vendorId = intent.getIntExtra("vendorId", 0);
        int menuId = intent.getIntExtra("menuId", 0);
        vendorIdString = Integer.toString(vendorId);
        menuIdString = Integer.toString(menuId);

        menuDatabase = FirebaseDatabase.getInstance().getReference(REF).child(vendorIdString).child(menuIdString);
        getMenuData();

        coffeeName = (TextView) findViewById(R.id.coffeeName);
        coffeeDescription = (TextView) findViewById(R.id.coffeeLocation);
        coffeeImage = (ImageView) findViewById(R.id.coffeeImage);
        statusText = (TextView) findViewById(R.id.statusText);
        checkBtn = (Button) findViewById(R.id.checkoutBtn);
        serveBtn = (Button) findViewById(R.id.ServeBtn);
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

    public void placeOrder(View view) {
        FirebaseDatabase.getInstance().getReference("order").child("menuId").setValue(menuIdString);
        FirebaseDatabase.getInstance().getReference("order").child("vendorId").setValue(vendorIdString);
        FirebaseDatabase.getInstance().getReference("order").child("status").setValue("Preparing your order...");
        getOrderData();

//        checkBtn.setVisibility(View.INVISIBLE);
//        serveBtn.setVisibility(View.VISIBLE);
//        checkBtn.setEnabled(false);
//        serveBtn.setEnabled(true);
    }

    private void getOrderData(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("order");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statusData = dataSnapshot.child("status").getValue().toString();
                LinearLayout layout = (LinearLayout) findViewById(R.id.status);
                if(layout.getVisibility() != View.VISIBLE){
                    layout.setVisibility(View.VISIBLE);
                }
                statusText.setText(statusData);
                Toast.makeText(Checkout.this, statusData, Toast.LENGTH_SHORT);
                //                Log.d(TAG, order.getStatus());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void onServe(View view) {
        FirebaseDatabase.getInstance().getReference("order").child("status").setValue("Your order is ready");
//        checkBtn.setVisibility(View.VISIBLE);
//        serveBtn.setVisibility(View.INVISIBLE);
//        checkBtn.setEnabled(true);
//        serveBtn.setEnabled(false);
    }

    public void backOnBtn(View view){
        Intent intent = new Intent(this, HomeActivity.class );
        startActivity(intent);
    }
}
