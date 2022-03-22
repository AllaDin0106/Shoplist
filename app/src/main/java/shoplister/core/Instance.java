package shoplister.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import shoplister.R;

public class Instance
{
    public static UserInfo          currentUser;
    public static Authentication    auth;
    public static Database          database;
    public static AppCompatActivity context;
    public static int               theme = R.color.Turquoise;

    public static NotificationManager notificationManager;

}
