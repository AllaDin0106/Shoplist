package shoplister.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import shoplister.R;
import shoplister.core.Instance;

// Requirement 3: DialogFragment
public abstract class BaseDialog extends DialogFragment
{
    protected String positive;
    protected String negative;
    protected String message;
    protected boolean warning = true;
    protected int foregroundColor;
    protected @DrawableRes int icon;
    protected @DrawableRes int backgroundColor;
    protected View.OnClickListener positiveListener, negativeListener;

    public BaseDialog() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Instance.context);

        LayoutInflater inflater = Instance.context.getLayoutInflater();
        View           view     = inflater.inflate(R.layout.dialog_base, null);

        builder.setView(view);

        RelativeLayout baseDialog_imageWrapper    = view.findViewById(R.id.baseDialog_imageWrapper);
        ImageView      baseDialog_iamgeView       = view.findViewById(R.id.baseDialog_imageView);
        TextView       baseDialog_messageTextView = view.findViewById(R.id.baseDialog_messageTextView);
        TextView       baseDialog_negativeButton  = view.findViewById(R.id.baseDialog_negativeButton);
        TextView       baseDialog_positiveButton  = view.findViewById(R.id.baseDialog_positiveButton);

        baseDialog_imageWrapper.setBackgroundResource(backgroundColor);
        baseDialog_iamgeView.setImageTintList(Instance.context.getColorStateList(foregroundColor));
        baseDialog_iamgeView.setImageResource(icon);
        baseDialog_messageTextView.setText(message);

        if (warning) baseDialog_positiveButton.setTextColor(Instance.context.getColor(R.color.Alizarin));
        baseDialog_positiveButton.setText(positive);
        baseDialog_negativeButton.setText(negative);

        negativeListener = v ->  this.dismiss();

        baseDialog_positiveButton.setOnClickListener(positiveListener);
        baseDialog_negativeButton.setOnClickListener(negativeListener);

        return builder.create();
    }

    public void show() {
        this.show(Instance.context.getSupportFragmentManager(), null);
    };

}
