package shoplister.dialogs;

import android.content.Intent;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.layouts.MainActivity;

public class DeleteShoplist extends BaseDialog
{
    public DeleteShoplist(String key)
    {
        positive = "Delete";
        negative = "Cancel";
        message = "Delete the list?";

        icon = R.drawable.delete_black_48dp;
        backgroundColor = R.color.Greensea;
        foregroundColor = R.color.White;

        positiveListener = v -> {
            Instance.database.lists.child(key).removeValue();
            startActivity(new Intent(Instance.context, MainActivity.class));
        };
    }
}
