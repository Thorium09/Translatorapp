package com.example.marathindi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Phrase_activity extends AppCompatActivity {

    private MediaPlayer M;
    private AudioManager AM;
    private final AudioManager.OnAudioFocusChangeListener afChangeListner = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase);

        AM = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> ls = new ArrayList<>();
        ls.add(new Word("Welcome", "तुमचं स्वागत असो", R.raw.number_four));
        ls.add(new Word("How are you", "तू कसा आहेस?", R.raw.number_four));
        ls.add(new Word("Long Time No See", "खूप दिवसात भेटलो नाही", R.raw.number_four));
        ls.add(new Word("What's your name?", "तुझं नाव काय आहे?", R.raw.number_four));
        ls.add(new Word("My name is..", "माझं नाव ... आहे", R.raw.number_four));
        ls.add(new Word("Pleased to meet you", "तुम्हाला भेटून आनंद झाला", R.raw.number_four));
        ls.add(new Word("Have a nice meal", "तजेवणाचा आनंद घ्या!", R.raw.number_four));
        ls.add(new Word("Have a safe journey", "आपला प्रवास सुखाचा होवो!", R.raw.number_four));
        ls.add(new Word("I understand", "मला समजते", R.raw.number_four));
        ls.add(new Word("I don't understand", "मला समजत नाही", R.raw.number_four));
        ls.add(new Word("Please say that again", "पुन्हा सांगा", R.raw.number_four));
        ls.add(new Word("How much is this?", "किती झाले", R.raw.number_four));
        ls.add(new Word("Happy New Year", "नवीन वर्षाच्या हार्दिक शुभेच्छा", R.raw.m));

        WordAdapter W = new WordAdapter(this, ls, R.color.phrase);
        ListView L = (ListView) findViewById(R.id.Plis);
        L.setAdapter(W);
        L.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word W = ls.get(position);
                releaseMediaPlayer();
                int F = AM.requestAudioFocus(afChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (F == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    M = MediaPlayer.create(Phrase_activity.this, W.Music());
                    M.start();
                    M.setOnCompletionListener(MM);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
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
            AM.abandonAudioFocus(afChangeListner);
        }
    }
}