package com.example.latte.grabpuccino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    ListView list;
    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War",
            "Grape",
            "Macbook",
            "iOS",
            "Eleven",
            "Glass",
            "Japan"
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
            R.drawable.pic8,
            R.drawable.pic8,
            R.drawable.pic8,
            R.drawable.pic8,
            R.drawable.pic8,
            R.drawable.pic8,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.coffeeList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
