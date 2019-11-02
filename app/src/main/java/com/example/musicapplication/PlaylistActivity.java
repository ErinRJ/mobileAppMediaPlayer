package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    //database variables
    Contract helper;
    ArrayList<String> playlist;
    private ListView listView;
    String play_url ="";

    //media player variables
    private MediaPlayer player;
    private int currentPosition;
    Boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //initialize database variables
        helper = new Contract(this);
        listView = (ListView)findViewById(R.id.listView);

        //create the media player for audio
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        populateListView();

        //create a listener for user to be able to select a song
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                String url = helper.getTrack(name);
                play_url = url;
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


    //download and play the song
    public void downloadAndPlay(View view) {
        //make sure the user has selected a song from the playlist
        if(play_url.equals("")){
            Toast.makeText(getApplicationContext(), "Please select a song to play", Toast.LENGTH_SHORT).show();
        }
        else{
            //check if the player is currently running
            if(player == null){
                //if it's the first time playing a file
                //set the mediaplayer object
                player = new MediaPlayer();
            }else {
                if (paused) {
                    resume();
                } else {
                    //stop currently playing music
                    player.stop();
                    //reset is the same as release
                    player.reset();
                }
            }
        }

        try {
            //pass the url to be played
            player.setDataSource(play_url);
            //prepare the song, listen for when it's prepared
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //when the media is prepared, perform the following code:
//                    currentPosition = 0;
                    player.start();
                    Toast.makeText(getApplicationContext(), "The song is prepared", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //pause the song being played
    public void pause(View v){
        //check if the player is currently playing, if so then pause
        if(player != null){
            //log the value where the media will pause
            currentPosition = player.getCurrentPosition();
            //pause the media
            player.pause();
            paused = true;
        }

    }

    //resume playing the song at the last-saved location
    public void resume(){
        paused = false;
        if(player != null){
            //continue playing from the saved position (when paused)
            player.seekTo(currentPosition);
            player.start();
        }
    }

    //stop the song being played
    public void stop(View v){
        if(player != null){
            //stop and release the media player
            player.stop();
            player.release();
            player = null;
        }
    }

    //return to the main activity
    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
