package shoplister.core;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import shoplister.R;

public class AppCustomActivity extends AppCompatActivity
{
    @Override
    public Resources.Theme getTheme()
    {
        Resources.Theme theme = super.getTheme();
        theme.applyStyle(getPrefTheme(), true);

        return theme;
    }

    // Requirement 6: User shared preferences
    int getPrefTheme()
    {
        SharedPreferences pref  = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String            theme = pref.getString("themes", "null");

        if (theme.equals("null")) pref.edit().putString("themes", "Turquoise").commit();

        if (theme.equals("Turquoise")) {
            Instance.theme = R.color.Turquoise;
            return R.style.Theme_ShoplisterTurquoise;
        }
        if (theme.equals("Alizarin")) {
            Instance.theme = R.color.Alizarin;
            return R.style.Theme_ShoplisterAlizarin;
        }
        if (theme.equals("Belizehole")) {
            Instance.theme = R.color.Belizehole;
            return R.style.Theme_ShoplisterBelizehole;
        }
        if (theme.equals("Amethyst")) {
            Instance.theme = R.color.Amethyst;
            return R.style.Theme_ShoplisterAmethyst;
        }

        return R.style.Theme_ShoplisterTurquoise;
    }
}
