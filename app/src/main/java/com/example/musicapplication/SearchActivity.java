package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    Contract database;
    EditText name;
    TextView urlTxt;
    TextView nameTxt;
    TextView urlLabel;
    TextView nameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        name = (EditText)findViewById(R.id.name_txt);
        urlTxt = (TextView)findViewById(R.id.urlResult);
        nameTxt = (TextView)findViewById(R.id.nameResult);
        nameLabel = (TextView)findViewById(R.id.nameLabel);
        urlLabel = (TextView)findViewById(R.id.urlLabel);

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
        //get the inputted track name
        String track = name.getText().toString();

        //make sure that the track name exists in the playlist database
        String url = database.getTrack(track);
        if(url.equals("URL not found")){
            Toast.makeText(getApplicationContext(), "That track name doesn't exist in the playlist", Toast.LENGTH_SHORT).show();
        }
        else {
            //bundle it up and send it over to the modify activity
            Intent intent = new Intent(this, ModifyActivity.class);
            intent.putExtra("name", track);
            startActivity(intent);
        }
    }

    public void search(View view) {
        //get the track name entered by the user
        String track = name.getText().toString();
        String url = database.getTrack(track);
        if(url.equals("URL not found")){
            Toast.makeText(getApplicationContext(), "That track name doesn't exist in the playlist", Toast.LENGTH_SHORT).show();
        }
        else{
            nameTxt.setText(track);
            urlTxt.setText(url);
        }
    }
}
