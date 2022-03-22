package shoplister.core;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import shoplister.R;
import shoplister.layouts.MainActivity;

import static shoplister.core.Logger.Log;

public class Authentication
{
    public static final int RC_SIGN_IN = 9001;

    AppCompatActivity   context;
    GoogleSignInOptions gso;
    GoogleSignInClient  gsc;
    FirebaseAuth        mAuth;


    public Authentication(AppCompatActivity context)
    {
        this.context = context;

        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(context, gso);
    }

    public void signIn()
    {
        Intent signInIntent = gsc.getSignInIntent();
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(context, task -> {
                    if(task.isSuccessful()) {
                        Log("signInWithCredential: SUCCESS");
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                    else Log(task.getException().toString());
                });
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}
