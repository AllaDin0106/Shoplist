package shoplister.layouts;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import shoplister.R;
import shoplister.adapters.ContactsAdapter;
import shoplister.core.AppCustomActivity;
import shoplister.core.Instance;
import shoplister.models.Contact;
import shoplister.models.User;

public class ContactsActivity extends AppCustomActivity
{
    // Requirement 5: Recyclerview - Contacts
    private RecyclerView                                                 recyclerView;
    private FirebaseRecyclerAdapter<Contact, ContactsAdapter.ViewHolder> adapter;

    private EditText    contacts_editTextNewContact;
    private ImageButton contacts_newContactButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Instance.context = this;

        recyclerView = findViewById(R.id.contactsRecyclerView);

        adapter = new ContactsAdapter().getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instance.context));

        init();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Instance.context = this;
    }

    private void init()
    {
        contacts_editTextNewContact = findViewById(R.id.contacts_editTextNewContact);
        contacts_newContactButton   = findViewById(R.id.contacts_newContactButton);

        contacts_newContactButton.setOnClickListener(v -> {
            String value = contacts_editTextNewContact.getText().toString();

            // Requirement 2: Cookbook Listener #3 - Anonymous Inner Class
            Instance.database.users.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    snapshot.getChildren().forEach(dataSnapshot -> {
                        if (dataSnapshot.getValue(User.class).getEmail().equals(value))
                        {
                            Contact contact = new Contact(dataSnapshot.getValue(User.class).getName(), dataSnapshot.getValue(User.class).getUid());
                            Instance.database.users
                                    .child(Instance.currentUser.UID)
                                    .child("contacts")
                                    .child(dataSnapshot.getValue(User.class).getUid())
                                    .setValue(contact);
                            contacts_editTextNewContact.setText(null);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        });
    }
}
