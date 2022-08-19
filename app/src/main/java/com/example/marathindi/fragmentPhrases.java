package com.example.marathindi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentPhrases#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentPhrases extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentPhrases() {
        // Required empty public constructor
    }
    private MediaPlayer M;
    private AudioManager AM;

    private AudioManager.OnAudioFocusChangeListener afChangeListner = new AudioManager.OnAudioFocusChangeListener() {
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

    private  MediaPlayer.OnCompletionListener MM = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    public static fragmentPhrases newInstance(String param1, String param2) {
        fragmentPhrases fragment = new fragmentPhrases();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_phrase, container, false);
      AM = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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

        ListView L = (ListView) view.findViewById(R.id.Plis);
        WordAdapter W = new WordAdapter(getActivity(),ls,R.color.phrase);

        L.setAdapter(W);

        L.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word w = ls.get(position);
                releaseMediaPlayer();
                int F = AM.requestAudioFocus(afChangeListner,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (F == AM.AUDIOFOCUS_REQUEST_GRANTED){
                    M = MediaPlayer.create(getActivity(),w.Music());
                    M.start();
                    M.setOnCompletionListener(MM);
                }

            }
        });

        return view;
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

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            AM.abandonAudioFocus(afChangeListner);
        }
    }
}
