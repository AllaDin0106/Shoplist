package shoplister.core;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

public class UserInfo
{

    public String firstName;
    public String lastName;
    public String fullName;
    public String UID;
    public String email;
    public Uri    photoURL;

    public UserInfo(FirebaseUser user)
    {
        fullName = user.getDisplayName();

        if (fullName.contains(" ")) {
            String[] parts = fullName.split(" ");
            firstName = parts[0];
            lastName  = parts[1];
        } else firstName = user.getDisplayName();

        UID      = user.getUid();
        email    = user.getEmail();
        photoURL = user.getPhotoUrl();
    }

}