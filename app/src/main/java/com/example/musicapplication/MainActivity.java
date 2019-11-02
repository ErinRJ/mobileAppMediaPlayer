package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Contract dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create an object of the db contract class
        dbHelper = new Contract(this);
    }

    public void add(View v){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void viewPlaylist(View v){
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }
}
