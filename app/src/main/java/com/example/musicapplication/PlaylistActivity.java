package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {

    Contract helper;
    ArrayList<String> playlist;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        helper = new Contract(this);
        listView = (ListView)findViewById(R.id.listView);

        populateListView();
        //create a listener for user to be able to select a song
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                String url = helper.getTrack(name);
                Toast.makeText(getApplicationContext(), "Track: " + name + " URL: " + url, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populateListView() {
        Cursor data = helper.getData();
        playlist = new ArrayList<>();

        //check if the playlist is empty
        if(data.getCount() == 0){
            //notify the user
            Toast toast = Toast.makeText(getApplicationContext(), "Playlist is empty", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            //get the info from the db
            while (data.moveToNext()) {
                playlist.add(data.getString(1));
            }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlist);
            listView.setAdapter(adapter);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
