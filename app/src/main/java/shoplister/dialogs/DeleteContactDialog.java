package shoplister.dialogs;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.models.Contact;

// Requirement 3: DialogFragment
public class DeleteContactDialog extends BaseDialog
{
    Contact contact;
    public DeleteContactDialog(Contact contact)
    {
        this.contact = contact;

        positive = "Delete";
        negative = "Cancel";
        message = "Are you sure you want to delete the contact?";

        icon = R.drawable.delete_black_48dp;
        backgroundColor = R.color.Midnightblue;
        foregroundColor = R.color.White;

        positiveListener = v -> {
            Instance.database.currentUser.child("contacts").child(contact.getUid()).removeValue();
            dismiss();
        };
    }
}
