package shoplister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.core.Logger;
import shoplister.dialogs.DeleteContactDialog;
import shoplister.layouts.ContactsFragment;
import shoplister.layouts.NewListActivity;
import shoplister.models.Contact;
import shoplister.models.NewList;

public class ContactsFragmentAdapter extends BaseAdapter<Contact, ContactsFragmentAdapter.ViewHolder>
{
    FragmentManager     FM;
    FragmentTransaction FT;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public View         itemView;
        public TextView     contact_itemTextView;
        public ImageView    contact_imageView;
        public LinearLayout contact_container;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.itemView             = itemView;
            this.contact_itemTextView = itemView.findViewById(R.id.contact_itemTextView);
            this.contact_imageView    = itemView.findViewById(R.id.contact_imageView);
            this.contact_container    = itemView.findViewById(R.id.contact_container);
        }
    }

    @Override
    void initAdapter()
    {
        FM = Instance.context.getSupportFragmentManager();

        options = new FirebaseRecyclerOptions.Builder<Contact>().setQuery(Instance.database.currentUser.child("contacts"), Contact.class).build();

        adapter = new FirebaseRecyclerAdapter<Contact, ViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Contact model)
            {
                holder.contact_itemTextView.setText(model.getName());
                holder.contact_imageView.setClipToOutline(true);

                holder.contact_container.setOnClickListener(v -> {
                    NewListActivity.list.setRecipient(model);
                    ((TextView) Instance.context.findViewById(R.id.newList_sharedTextView)).setText(model.getName());

                    ContactsFragment CF = (ContactsFragment) FM.findFragmentByTag("CONTACTS_FRAGMENT");
                    if (CF != null) {
                        FT = FM.beginTransaction();
                        FT.detach(CF).commit();
                        FM.executePendingTransactions();
                    }
                });

                // Requirement 2: Cookbook Listener #3 - Anonymous Inner Class
                Instance.database.users.child(model.getUid()).child("photo").addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String url = snapshot.getValue().toString();
                        Picasso.get().load(url).into(holder.contact_imageView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(Instance.context).inflate(R.layout.contact_recycleritem, parent, false);
                return new ViewHolder(view);
            }
        };
    }
}
