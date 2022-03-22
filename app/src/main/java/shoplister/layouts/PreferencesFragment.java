package shoplister.layouts;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import shoplister.R;
import shoplister.core.Logger;

// Requirement 1: Fragment
public class PreferencesFragment extends PreferenceFragmentCompat
{
    SwitchPreference notifications;
    ListPreference   themes;

    @Override
    public void onCreatePreferences(Bundle bundle, String s)
    {
        setPreferencesFromResource(R.xml.preferences, s);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        notifications = findPreference("notifications");
        themes        = findPreference("themes");
    }
}
