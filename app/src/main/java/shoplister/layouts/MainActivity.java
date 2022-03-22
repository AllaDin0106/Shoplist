package shoplister.layouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import shoplister.R;
import shoplister.adapters.ListsAdapater;
import shoplister.core.AppCustomActivity;
import shoplister.core.Authentication;
import shoplister.core.Database;
import shoplister.core.Instance;
import shoplister.core.NetworkReceiver;
import shoplister.core.Notifications;
import shoplister.core.UserInfo;
import shoplister.dialogs.SignOutDialog;
import shoplister.models.NewList;

public class MainActivity extends AppCustomActivity
{
    RecyclerView                                               recyclerView;
    FirebaseRecyclerAdapter<NewList, ListsAdapater.ViewHolder> adapter;

    TextView    main_welcome_textview;
    EditText    main_editTextNewList;
    ImageView   main_userImage;
    ImageButton main_newListButton;

    FragmentManager FM;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Instance.context             = this;
        Instance.auth                = new Authentication(this);
        Instance.currentUser         = new UserInfo(Instance.auth.getCurrentUser());
        Instance.database            = new Database();

        Intent intent = new Intent(this, Notifications.class);
        startService(intent);
        startForegroundService(intent);

        recyclerView                 = findViewById(R.id.main_recyclerView);

        adapter = new ListsAdapater().getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instance.context));

        init();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Instance.context = this;
    }

    // Requirement 4: Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    // Requirement 4: Menu items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.menu_new_list:
                startActivity(new Intent(this, NewListActivity.class));
                return true;

            case R.id.menu_contacts:
                startActivity(new Intent(this, ContactsActivity.class));
                return true;

            // Requirement 1: Fragment - Preferences
            case R.id.menu_preferences:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;

            // Requirement 3: DialogFragment - Confirm sign out
            case R.id.menu_sign_out:
                new SignOutDialog().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        FM = getSupportFragmentManager();

        main_welcome_textview = findViewById(R.id.main_welcome_textview);
        main_userImage        = findViewById(R.id.main_userImage);
        main_newListButton    = findViewById(R.id.main_newListButton);
        main_editTextNewList  = findViewById(R.id.main_editTextNewList);

        main_welcome_textview.setText(String.format("Any shopping today, %s?", Instance.currentUser.firstName));
        main_welcome_textview.setTextColor(Instance.context.getColor(Instance.theme));
        main_userImage.setClipToOutline(true);

        Picasso.get().load(Instance.currentUser.photoURL).into(main_userImage);

        main_newListButton.setOnClickListener(v -> {
            String value  = main_editTextNewList.getText().toString();
            Intent intent = new Intent(this, NewListActivity.class);
            intent.putExtra("listName", value);
            startActivity(intent);
        });

        // Requirement 7: BroadcastReceiver - Network Activity
        NetworkReceiver.registerReceiver();
    }
}