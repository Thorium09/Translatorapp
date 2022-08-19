package com.example.marathindi;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {


    private int mC;
    public WordAdapter(Activity context , ArrayList<Word> ls,int C){
        super(context,0,ls);
        mC = C;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View T = convertView ;
        if(T == null){
            T = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        ListView L = T.findViewById(R.id.listN);

        Word W = getItem(position);

        TextView t1 = (TextView)T.findViewById(R.id.lsi1);
        t1.setText(W.EngText());

        TextView t2 = (TextView)T.findViewById(R.id.lsi2);
        t2.setText(W.MariText());

        ImageView I = (ImageView)T.findViewById(R.id.img1);
        if (W.hasImg()){
            I.setImageResource(W.Img());
            I.setVisibility(View.VISIBLE);
        }
        else {
            I.setVisibility(View.GONE);
        }

        View textContainer = T.findViewById(R.id.text_item);
        int color = ContextCompat.getColor(getContext(),mC);
        textContainer.setBackgroundColor(color);
        return T;
    }

}
