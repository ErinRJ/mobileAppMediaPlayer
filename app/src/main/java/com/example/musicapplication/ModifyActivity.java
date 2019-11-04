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
    Contract helper;
    String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        nameTxt = (EditText)findViewById(R.id.name_txt);
        urlTxt = (EditText)findViewById(R.id.url_txt);

        helper = new Contract(this);
        //on page creation, the track name and url should already be populated
        //get name from intent bundle
        oldName = getIntent().getExtras().getString("name");
        nameTxt.setText(oldName);
        //get the url from the database
        String url = helper.getTrack(oldName);
        urlTxt.setText(url);

    }

    public void back(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void modify(View view) {
        String newName = nameTxt.getText().toString();
        String newUrl = urlTxt.getText().toString();
        boolean result = helper.update(oldName, newName, newUrl);
        if(result){
            Toast.makeText(getApplicationContext(), "Track info updated successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Track info unable to be updated", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
