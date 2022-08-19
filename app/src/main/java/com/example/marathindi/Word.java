package com.example.marathindi;

import android.graphics.drawable.Icon;

public class Word {

    // String value
    private String Text;

    // Context of the app
    private String mText;

    private int IMg = -1;

    public static final int NO_IMG = -1 ;

    private int M;

    /**
     * Constructs a new TextView with initial values for text and text color.
     */
    public Word(String S1 ,String S2 , int i , int m) {
        Text = S1;
        mText = S2;
        IMg = i;
        M = m;
    }


    public String EngText(){
        return Text;
    }
    public String MariText(){
        return mText;
    }

    public int Img(){
        return IMg ;
    }

    public int Music(){
        return M;
    }

    public Word(String S1 ,String S2,int m) {
        Text = S1;
        mText = S2;
        M = m;
    }

  public boolean hasImg(){
        return IMg != NO_IMG ;
  }

}
