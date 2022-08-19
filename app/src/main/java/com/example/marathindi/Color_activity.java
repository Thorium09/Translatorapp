package com.example.marathindi;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Color_activity extends AppCompatActivity {

    private  MediaPlayer M ;

   private AudioManager AM;
   private AudioManager.OnAudioFocusChangeListener AF = new AudioManager.OnAudioFocusChangeListener() {
       @Override
       public void onAudioFocusChange(int focusChange) {
           if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
               M.pause();
               M.seekTo(0);
           } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
               M.start();
           } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
               releaseMediaPlayer();
           }
       }
   };

    private MediaPlayer.OnCompletionListener MM = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

     final    ArrayList<Word> ls = new  ArrayList();
        AM = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ls.add(new Word("Black","काळा",R.mipmap.color_black,R.raw.number_four));
        ls.add(new Word("Brown","तपकिरी",R.mipmap.color_brown,R.raw.number_four));
        ls.add(new Word("Yellow","पिवळा",R.mipmap.color_dusty_yellow,R.raw.number_four));
        ls.add(new Word("Gray","राखाडी",R.mipmap.color_gray,R.raw.number_four));
        ls.add(new Word("Green","हिरवा",R.mipmap.color_green,R.raw.number_four));
        ls.add(new Word("MustardYellow","मोहरी पिवळी",R.mipmap.color_mustard_yellow,R.raw.number_four));
        ls.add(new Word("Red","लाल",R.mipmap.color_red,R.raw.number_four));
        ls.add(new Word("White","सफेद",R.mipmap.color_white,R.raw.number_four));

        WordAdapter W = new WordAdapter(this,ls,R.color.colors);
        ListView L = (ListView)findViewById(R.id.listN);
        L.setAdapter(W);

        L.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word W = ls.get(position);

                int result = AM.requestAudioFocus(AF,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    M = MediaPlayer.create(Color_activity.this, W.Music());
                    M.start();
                    M.setOnCompletionListener(MM);
                }    }
        });


    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (M != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            M.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            M = null;

            AM.abandonAudioFocus(AF);

        }
    }
}