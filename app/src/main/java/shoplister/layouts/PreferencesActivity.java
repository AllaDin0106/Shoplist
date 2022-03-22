package shoplister.layouts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import shoplister.R;
import shoplister.core.AppCustomActivity;

public class PreferencesActivity extends AppCustomActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getSupportActionBar().setTitle("Preferences");

        // Requirement 1: Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_container, new PreferencesFragment())
                .commit();
    }
}