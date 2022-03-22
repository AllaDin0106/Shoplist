package shoplister.dialogs;

import android.content.Intent;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.layouts.NavigatorActivity;

// Requirement 3: DialogFragment
public class SignOutDialog extends BaseDialog
{
    public SignOutDialog()
    {
        positive = "OK";
        negative = "Cancel";
        message  = "Are you sure you want to sign out?";

        icon            = R.drawable.hail_black_48dp;
        backgroundColor = R.color.color_meat;
        foregroundColor = R.color.White;

        positiveListener = v -> {
            Instance.auth.signOut();
            Instance.context.startActivity(new Intent(Instance.context, NavigatorActivity.class));
            Instance.context.finish();
        };
    }

}
