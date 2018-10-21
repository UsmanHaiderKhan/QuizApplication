package com.example.usmankhan.quizapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notify extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notify );

        Button btn = (Button) findViewById( R.id.btnNotiy );
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotification();
            }
        } );
    }
    private void getNotification() {
        Intent intent = new Intent( this, Notify.class );
        PendingIntent pendingIntent = PendingIntent.getActivity( this, (int) System.currentTimeMillis(), intent, 0 );

        Notification notification = new Notification.Builder( this )
                .setContentTitle( "Pakistan" )
                .setContentIntent( pendingIntent )
                .setContentText( "Pakistan Zinda Bad" )
                .setSmallIcon( R.mipmap.ic_launcher )
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify( 0, notification );

    }
}
