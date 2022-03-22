package shoplister.layouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import shoplister.R;
import shoplister.core.AppCustomActivity;
import shoplister.core.Logger;

//מעברים בין אקטיביטי - ממנו מתחילים ואז יודעים לנווט לפי אם בכלל קיים חיבור
public class NavigatorActivity extends AppCustomActivity
{
    boolean authenticated = tryAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent;

        if (authenticated) intent = new Intent(this, MainActivity.class);
        else intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }
// בדיקת חיבור
    boolean tryAuth()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }
}