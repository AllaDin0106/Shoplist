package shoplister.layouts;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import shoplister.R;
import shoplister.core.AppCustomActivity;
import shoplister.core.Authentication;
import shoplister.core.Instance;

public class LoginActivity extends AppCustomActivity
{
    Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        Instance.context = this;
        setColors();

        auth = new Authentication(this);

        findViewById(R.id.login_button).setOnClickListener(v -> auth.signIn());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Authentication.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                auth.firebaseAuthWithGoogle(account.getIdToken());
            }
            catch (ApiException e) {
            }
        }
    }

    void setColors()
    {
        ConstraintLayout login_background = findViewById(R.id.login_background);
        Button           login_button     = findViewById(R.id.login_button);

        login_background.setBackgroundResource(Instance.theme);
        login_button.setTextColor(Instance.context.getColor(Instance.theme));
        login_button.setCompoundDrawableTintList(Instance.context.getColorStateList(Instance.theme));
    }
}
