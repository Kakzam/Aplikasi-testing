package com.jibril.malaikat.layoutsatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class DasboardActivity extends AppCompatActivity {

    PrefManager prefManager;
    LinearLayout llpengiriman,lltambah,llprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        setTitle("Dasboard");

        //Tambah
        lltambah = findViewById(R.id.ll_tambah);
        lltambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(),Tambah.class);
                startActivity(b);
            }
        });

        //senter
        llpengiriman = findViewById(R.id.ll_pengiriman);
        llpengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(l);
            }
        });


        //profil
        llprofile = findViewById(R.id.ll_profile);
        llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(p);
            }
        });
    }

    //////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_dashboard, menu);
        return true;
    }


    //Option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                prefManager = new PrefManager(this);
                if(!prefManager.isFirstTimeLaunch()){
                    prefManager.setIsFirstTimeLaunch(true);
                    Intent a = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(a);
                    finish();
                }
                break;
        }
        return true;
    }
}
