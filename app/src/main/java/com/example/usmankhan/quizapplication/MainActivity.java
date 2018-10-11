package com.example.usmankhan.quizapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView t;
    Button   s_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        s_btn=(Button) findViewById( R.id.start_btn );

        s_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();

            }
        } );

        t=(TextView) findViewById( R.id.text_view );
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/old-english-gothic-1361508485.ttf" );
        t.setTypeface( typeface );
    }

    private void startQuiz(){
        Intent intent=new Intent(MainActivity.this,QuizActivity.class);
        startActivity( intent );
    }
}
