package com.example.usmankhan.quizapplication;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        t=(TextView) findViewById( R.id.text_view );
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/old-english-gothic-1361508485.ttf" );
        t.setTypeface( typeface );
    }
}
