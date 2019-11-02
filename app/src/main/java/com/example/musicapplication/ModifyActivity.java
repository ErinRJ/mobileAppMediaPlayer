package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {
    EditText nameTxt;
    EditText urlTxt;
    Contract database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        nameTxt = (EditText)findViewById(R.id.name_txt);
        urlTxt = (EditText)findViewById(R.id.url_txt);

        //on page creation, the track name and url should already be populated
        //get name from intent bundle
        String name = getIntent().getExtras().getString("name");
        nameTxt.setText(name);
        //get the url from the database
//        String url = database.getTrack(name);
//        Toast.makeText(getApplicationContext(), "the url: " + url, Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
