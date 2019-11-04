package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    Contract database;
    EditText urlTxt;
    EditText nameTxt;
    private static final String TAG = "database";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        database = new Contract(this);

        //find the editText boxes
        urlTxt = findViewById(R.id.url_txt);
        nameTxt = findViewById(R.id.name_txt);
    }

    //go back to main page
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //add the track to the database
    public void addTrack(View v){
        //convert the values into strings
        String url = urlTxt.getText().toString();
        String name = nameTxt.getText().toString();

        //check and make sure the name isn't null
        Boolean isNull = false;
        if(name.equals("")){
            Log.d(TAG, "Track name entered is empty (AddActivity.java)");
            isNull = true;
            //let the user know how to fix the issue
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a Track Name", Toast.LENGTH_SHORT);
            toast.show();
        }

        //ensure the URL is pointing to a URL with *.mp3 at the end
        Boolean isNotValid = true;
        if(url.endsWith(".mp3")){
            Log.d(TAG, "URL is valid (AddActivity.java)");
            isNotValid = false;
        }
        else{
            //let the user know how to fix the issue
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter a proper URL ending with '.mp3'", Toast.LENGTH_SHORT);
            toast.show();
        }

        //if both the name and the url are valid, enter info in the database
        if((!isNotValid) && (!isNull) ){
            //enter the values into the database
            boolean result = database.addData(name, url);

            if(result){
                //toast message saying it was inputted correctly
                Toast toast = Toast.makeText(getApplicationContext(), "Track inputted into database", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                //toast message saying it wasn't inputted correctly
                Toast toast = Toast.makeText(getApplicationContext(), "Track unable to be inputted into database", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
