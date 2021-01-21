package com.catata.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int PENDING_REQUEST = 5;
    PendingIntent pendingIntent;
    PendingIntent siPending, noPending;
    private static final String CHANNEL_ID = "NOTIFICACION";
    public static final int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enviarNotificacion(View view) {
        setPendingIntent();
        setSiPendingIntent();
        setNoPendingIntent();
        createNotificacionChannel();
        crearNotificacion();

    }

    private void setNoPendingIntent() {
        Intent intent = new Intent(this, NoActivity.class);

        //Para que al dar hacia atrás vaya a la main y no salga (opcional)
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacionActivity.class);
        stackBuilder.addNextIntent(intent);


        noPending = stackBuilder.getPendingIntent(PENDING_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setSiPendingIntent() {
        Intent intent = new Intent(this, SiActivity.class);

        //Para que al dar hacia atrás vaya a la main y no salga (opcional)
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacionActivity.class);
        stackBuilder.addNextIntent(intent);


        siPending = stackBuilder.getPendingIntent(PENDING_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setPendingIntent() {
        Intent intent = new Intent(this, NotificacionActivity.class);

        //Para que al dar hacia atrás vaya a la main y no salga (opcional)
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacionActivity.class);
        stackBuilder.addNextIntent(intent);


        pendingIntent = stackBuilder.getPendingIntent(PENDING_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT);


    }

    //Versiones posteriores a Oreo
    private void createNotificacionChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Características del Canal
            CharSequence name="Notificacion Normal";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationChannel.setAllowBubbles(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Inferiores a Oreo API 26 Android 8.0
    private void crearNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_access_alarms_24);
        builder.setContentTitle("Notificaciones");
        builder.setContentText("Texto Notificación");
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Texto largo para que no cabe en una única línea"));
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000, 1000});
        //builder.setSound()
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);
        builder.setNumber(7);
        builder.addAction(R.drawable.ic_launcher_background,"Sí", siPending);
        builder.addAction(R.drawable.ic_launcher_background,"No", noPending);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
    }
}