package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    Contract database;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        name = (EditText)findViewById(R.id.name_input);
        database = new Contract(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void delete(View view) {
        //get the track name entered by the user
        String track = name.getText().toString();
        //call the delete() in the database class
        Boolean result = database.delete(track);
        //give Toast message to user indicating whether it has been removed properly
        if(result){
            Toast.makeText(getApplicationContext(), "Track: " + track + " correctly removed from playlist", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Track: " + track + " was unable to be removed from the playlist", Toast.LENGTH_SHORT).show();
        }
    }

    public void modify(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        startActivity(intent);
    }
}
