package com.example.marathindi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Family_activity extends AppCompatActivity {
      private MediaPlayer M ;

      private AudioManager AM;

      private final AudioManager.OnAudioFocusChangeListener AF = new AudioManager.OnAudioFocusChangeListener() {
          @Override
          public void onAudioFocusChange(int focusChange) {
              if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                  releaseMediaPlayer();
              }  else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                      focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                  M.pause();
                  M.seekTo(0);
              } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                  M.start();
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
        setContentView(R.layout.activity_family);

        AM = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> ls = new  ArrayList<Word>();

        ls.add(new Word("Father","बाबा",R.mipmap.family_father,R.raw.number_four));
        ls.add(new Word("Mother","आई",R.mipmap.family_mother,R.raw.number_four));
        ls.add(new Word("Daughter","मुलगी",R.mipmap.family_daughter,R.raw.number_four));
        ls.add(new Word("Son","मुलगा",R.mipmap.family_son,R.raw.number_four));
        ls.add(new Word("GrandFather","आजोबा",R.mipmap.family_grandfather,R.raw.number_four));
        ls.add(new Word("GrandMother","आजी",R.mipmap.family_grandmother,R.raw.number_four));
        ls.add(new Word("OlderSister","ताई",R.mipmap.family_older_sister,R.raw.number_four));
        ls.add(new Word("OlderBrother","दादा",R.mipmap.family_older_brother,R.raw.number_four));
        ls.add(new Word("YoungerBrother","लहान भाऊ",R.mipmap.family_younger_brother,R.raw.number_four));
        ls.add(new Word("YoungerSister","धाकटी बहीण",R.mipmap.family_younger_sister,R.raw.number_four));

        ListView L = (ListView) findViewById(R.id.FlisV);
        WordAdapter W = new WordAdapter(this,ls,R.color.family);
        L.setAdapter(W);

        L.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 releaseMediaPlayer();
                Word W = ls.get(position);
                int result = AM.requestAudioFocus(AF,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                 M = MediaPlayer.create(Family_activity.this,W.Music());
                M.start();
                M.setOnCompletionListener(MM);

            }}
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