package com.example.marathindi;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView T = findViewById(R.id.textView3);
        TextView T1 = findViewById(R.id.text4);
        TextView T2 = findViewById(R.id.text5);
        TextView T3 = findViewById(R.id.text6);
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,Numbers_activity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(),"Numbers",Toast.LENGTH_SHORT).show();

            }
        });
        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Family_activity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(),"Family",Toast.LENGTH_SHORT).show();
            }
        });

        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Color_activity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(),"Colors",Toast.LENGTH_SHORT).show();
            }
        });

        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Phrase_activity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(),"Phrases",Toast.LENGTH_SHORT).show();
            }
        });
    }
}