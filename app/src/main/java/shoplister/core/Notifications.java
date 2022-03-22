package shoplister.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

import shoplister.R;
import shoplister.layouts.MainActivity;
import shoplister.models.Contact;
import shoplister.models.NewList;

public class Notifications extends Service
{
    public static final String CHANNEL_1 = "new_list_channel";
    static int counter = 0;
    SharedPreferences pref;

    Notification.Builder builder;

    @Override
    public void onCreate()
    {
        super.onCreate();
        init();

        Instance.database.lists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot s : snapshot.getChildren())
                {
                    for(Contact contact : s.getValue(NewList.class).getContacts().values())
                    {
                        if(contact.getUid().equals(Instance.currentUser.UID)) {
                            if(pref.getBoolean("notifications", false))
                                notif("You have been added to list '" + s.getValue(NewList.class).getTitle() + "'.");
                        }
                    }
                }
            }

            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable @Override public IBinder onBind(Intent intent) { return null; }

    void init() {
        if(Instance.notificationManager == null)
            Instance.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(CHANNEL_1, "List Channel", NotificationManager.IMPORTANCE_DEFAULT);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP / Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        builder = new Notification.Builder(this, CHANNEL_1)
            .setContentTitle("Shoplister")
            .setSmallIcon(R.drawable.icons8_apple_96)
            .setContentIntent(pendingIntent);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplication());

        if(pref.getBoolean("notifications", false)) startForeground(1, notif("Running as foreground service."));
    }

    public Notification notif(String text) {
        builder.setContentText(text).setAutoCancel(true);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        Instance.notificationManager.notify(counter++, notification);
        return notification;
    }
}
