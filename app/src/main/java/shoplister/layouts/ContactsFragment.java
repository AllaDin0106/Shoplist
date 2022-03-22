package shoplister.layouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import shoplister.R;
import shoplister.adapters.ContactsAdapter;
import shoplister.adapters.ContactsFragmentAdapter;
import shoplister.core.Instance;
import shoplister.core.Logger;
import shoplister.models.Contact;

public class ContactsFragment extends Fragment
{
    private RecyclerView                                                         recyclerView;
    private FirebaseRecyclerAdapter<Contact, ContactsFragmentAdapter.ViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = Instance.context.findViewById(R.id.contactsFragmentRecyclerView);

        adapter = new ContactsFragmentAdapter().getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instance.context));

        init();
    }

    private void init()
    {

    }

}
