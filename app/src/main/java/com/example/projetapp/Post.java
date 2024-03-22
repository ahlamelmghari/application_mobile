package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Post extends AppCompatActivity {

    private TextView announcementCountTextView;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        announcementCountTextView = findViewById(R.id.announcementCountTextView);
        DB = new DBHelper(this);

        String city = getIntent().getStringExtra("city");
        int count = DB.countAnnouncementsByCity(city);

        String message = "Il y a actuellement " + count + " annonce(s) dans la ville : " + city;
        announcementCountTextView.setText(message);

    }

}